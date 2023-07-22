package com.coffee.dao;

import com.coffee.entity.NhanVien;
import com.coffee.utils.Auth;
import com.coffee.utils.XJdbc;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO extends CoffeeDAO<NhanVien, String> {

    String insertNhanVien = "INSERT INTO NHANVIEN VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    String updateNhanVien = "UPDATE NHANVIEN SET TenNV = ?,GioiTinh = ?,SDT = ?, DiaChi = ?, ChucVu = ?, Email = ? WHERE MaNV = ?";
    String deleteNhanVien = "DELETE FROM NHANVIEN WHERE MaNV=?";
    String selectAllNhanVien = "SELECT * FROM NHANVIEN WHERE MaNV != 'admin'";
    String selectAllNhanVien2 = "SELECT * FROM NHANVIEN";
    String selectByIdNhanVien = "SELECT * FROM NHANVIEN WHERE MaNV=?";

    @Override
    public void insert(NhanVien entity) {
        XJdbc.update(insertNhanVien,
                entity.getMaNV(),
                    entity.getTenNV(),
                    entity.getGioiTinh(),
                    entity.getSdt(),
                    entity.getDiaChi(),
                    entity.getChucVu(),
                    entity.getEmail(),
                    entity.getNgayVaoLam()
        );
    }

    @Override
    public void update(NhanVien entity) {
        XJdbc.update(updateNhanVien,
                entity.getTenNV(),
                entity.getGioiTinh(),
                entity.getSdt(),
                entity.getDiaChi(),
                entity.getChucVu(),
                entity.getEmail(),
                entity.getMaNV()
        );
    }

    @Override
    public void delete(String id) {
        XJdbc.update(deleteNhanVien, id);
    }

    @Override
    public List<NhanVien> selectAll() {
        String select = "";
        if (Auth.isLogin()) {
            if (Auth.getManager().equalsIgnoreCase("admin")) {
                select = selectAllNhanVien2;
            } else {
                select = selectAllNhanVien;
            }
        } else {
            select = selectAllNhanVien;
        }
        return this.selectBySql(select);
    }

    @Override
    public List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = XJdbc.query(sql, args);
            while (resultSet.next()) {
                NhanVien nv = new NhanVien();

                nv.setMaNV(resultSet.getString(1));
                nv.setTenNV(resultSet.getString(2));
                nv.setGioiTinh(resultSet.getString(3));
                nv.setSdt(resultSet.getString(4));
                nv.setDiaChi(resultSet.getString(5));
                nv.setChucVu(resultSet.getString(6));
                nv.setEmail(resultSet.getString(7));
                nv.setNgayVaoLam(resultSet.getString(8));

                list.add(nv);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException ex) {
        }
        return list;
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = this.selectBySql(selectByIdNhanVien, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public List<NhanVien> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM NHANVIEN WHERE MaNV = ?";
        return this.selectBySql(sql, keyword);

    }

    public List<NhanVien> selectByKeyword2(String keyword) {
        String sql = "SELECT * FROM NHANVIEN WHERE TenNV LIKE ? and MaNV != 'admin'";
        return this.selectBySql(sql, "%" + keyword + "%");
    }
    public List<NhanVien> selectByKeyword3(String keyword) {
        String sql = "SELECT * FROM NHANVIEN WHERE MaNV in (SELECT MAND FROM NGUOIDUNG WHERE TENDN = ?)";
        return this.selectBySql(sql, keyword);

    }

    public List<NhanVien> selectNhanVien() {
        String sql = "SELECT * FROM NHANVIEN WHERE ChucVu in ( 'admin',N'Quản lý') ";//WHERE MaND not in (SELECT MaNV FROM NGUOIDUNG)
        return this.selectBySql(sql);

    }

}
