package com.coffee.dao;

import com.coffee.entity.SanPham;
import com.coffee.utils.XDate;
import com.coffee.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO extends CoffeeDAO<SanPham, String> {

    String insertSanPham = "INSERT INTO SANPHAM VALUES (?,?,?,?,?,?,?,?,?,?)";
    String updateSanPham = "UPDATE SANPHAM SET TenSP = ?,MaLoai=?,SoLuong = ?, GiaNhap = ?, GiaBan = ?, NgayNhap = ?, MoTa = ?,HinhAnh = ?,TrangThai = ? WHERE MaSP = ?";
    String deleteSanPham = "DELETE FROM SANPHAM WHERE MaSP=?";
    String selectAllSanPham = "SELECT * FROM SANPHAM";
    String selectByIdSanPham = "SELECT * FROM SANPHAM WHERE MaSP=?";
    String updateSoLuongSanPham = "UPDATE SANPHAM SET SoLuong = ? WHERE MaSP = ?";

    @Override
    public void insert(SanPham entity) {
        XJdbc.update(insertSanPham,
                entity.getMaSP(),
                entity.getTenSP(),
                entity.getLoaiSP(),
                entity.getSoLuong(),
                entity.getGiaNhap(),
                entity.getGiaBan(),
                entity.getNgayNhap(),
                entity.getMoTa(),
                entity.getHinhAnh(),
                entity.isTrangThai()
        );
    }

    @Override
    public void update(SanPham entity) {
        XJdbc.update(updateSanPham,
                entity.getTenSP(),
                entity.getLoaiSP(),
                entity.getSoLuong(),
                entity.getGiaNhap(),
                entity.getGiaBan(),
                entity.getNgayNhap(),
                entity.getMoTa(),
                entity.getHinhAnh(),
                entity.isTrangThai(),
                entity.getMaSP()
        );
    }

    public void updateSoLuong(SanPham entity) {
        XJdbc.update(updateSoLuongSanPham,
                entity.getSoLuong(),
                entity.getMaSP()
        );
    }

    @Override
    public void delete(String id) {
        XJdbc.update(deleteSanPham, id);
    }

    @Override
    public List<SanPham> selectAll() {
        return this.selectBySql(selectAllSanPham);
    }

    @Override
    public List<SanPham> selectBySql(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = XJdbc.query(sql, args);
            while (resultSet.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(resultSet.getString(1));
                sp.setTenSP(resultSet.getString(2));
                sp.setLoaiSP(resultSet.getString(3));
                sp.setSoLuong(resultSet.getInt(4));
                sp.setGiaNhap(resultSet.getDouble(5));
                sp.setGiaBan(resultSet.getDouble(6));
                sp.setNgayNhap(XDate.toString(resultSet.getDate(7), "dd-MM-yyyy"));
                sp.setMoTa(resultSet.getString(8));
                sp.setHinhAnh(resultSet.getString(9));
                sp.setTrangThai(resultSet.getBoolean(10));
                list.add(sp);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException ex) {
        }
        return list;
    }

    @Override
    public SanPham selectById(String id) {
        List<SanPham> list = this.selectBySql(selectByIdSanPham, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public List<SanPham> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM SanPham WHERE TenSP LIKE ? ";
        return this.selectBySql(sql, "%" + keyword + "%");

    }

    public void deleteLoai(String id) {
        String deleteLoai = "DELETE FROM SANPHAM WHERE MaLoai=?";
        XJdbc.update(deleteLoai, id);
    }

    public List<SanPham> selectSanPham(String key) {

        String sql = "select * from sanpham where MaSP = ?";

        return this.selectBySql(sql, key);
    }
}
