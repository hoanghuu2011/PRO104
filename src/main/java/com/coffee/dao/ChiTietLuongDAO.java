package com.coffee.dao;

import com.coffee.entity.ChiTietLuong;
import com.coffee.utils.XDate;
import com.coffee.utils.XJdbc;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietLuongDAO extends CoffeeDAO<ChiTietLuong, String> {

    String insertChiTietLuong = "INSERT INTO LUONGCHITIET VALUES (?,?,?,?)";
    String updateChiTietLuong = "UPDATE LUONGCHITIET SET NgayLamViec=?, MaCa=?, MaNV=? WHERE MaLuongCT=?";
    String deleteChiTietLuong = "DELETE FROM LUONGCHITIET WHERE MaLuongCT=?";
    String selectAllChiTietLuong = "SELECT * FROM LUONGCHITIET";
    String selectByIdChiTietLuong = "SELECT * FROM LUONGCHITIET WHERE MaLuongCT=?";

    public void insert(ChiTietLuong entity) {
        XJdbc.update(insertChiTietLuong,
                entity.getMaLuongCT(),
                entity.getNgayLamViec(),
                entity.getMaCa(),
                entity.getMaNV()
        );
    }

    @Override
    public void update(ChiTietLuong entity) {
        XJdbc.update(updateChiTietLuong,
                entity.getNgayLamViec(),
                entity.getMaCa(),
                entity.getMaNV(),
                entity.getMaLuongCT()
        );
    }

    @Override
    public void delete(String id) {
        XJdbc.update(deleteChiTietLuong, id);
    }

    @Override
    public List<ChiTietLuong> selectAll() {
        return this.selectBySql(selectAllChiTietLuong);
    }

    @Override
    public List<ChiTietLuong> selectBySql(String sql, Object... args) {
        List<ChiTietLuong> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = XJdbc.query(sql, args);
            while (resultSet.next()) {
                ChiTietLuong ctl = new ChiTietLuong();
                ctl.setMaLuongCT(resultSet.getString(1));
                ctl.setNgayLamViec(XDate.toString(resultSet.getDate(2), "dd-MM-yyyy"));
                ctl.setMaCa(resultSet.getInt(3));
                ctl.setMaNV(resultSet.getString(4));
                list.add(ctl);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException ex) {
        }
        return list;
    }

    @Override
    public ChiTietLuong selectById(String id) {
        List<ChiTietLuong> list = this.selectBySql(selectByIdChiTietLuong, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public List<ChiTietLuong> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM Luongchitiet a inner join NhanVien b on a.MaNV = b.MaNV where TenNV=?";
        return this.selectBySql(sql,keyword);

    }
}
