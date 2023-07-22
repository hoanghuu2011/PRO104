package com.coffee.dao;

import com.coffee.entity.CaLamViec;
import com.coffee.utils.XJdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CaLamViecDAO extends CoffeeDAO<CaLamViec, String> {

    String insertCaLamViec = "INSERT INTO CALAMVIEC VALUES (?, ?, ?, ?, ?)";
    String updateCaLamViec = "UPDATE CALAMVIEC SET TenCa=?, GioBatDau=?, GioKetThuc=?, Luong = ? WHERE MaCa=?";
    String deleteCaLamViec = "DELETE FROM CALAMVIEC WHERE MaCa=?";
    String selectAllCaLamViec = "SELECT * FROM CALAMVIEC";
    String selectByIdCaLamViec = "SELECT * FROM CALAMVIEC WHERE MaCa=?";

    @Override
    public void insert(CaLamViec entity) {
        XJdbc.update(insertCaLamViec,
                entity.getMaCa(),
                entity.getTenCa(),
                entity.getGioBatDau(),
                entity.getGioKetThuc(),
                entity.getLuong()
        );
    }

    @Override
    public void update(CaLamViec entity) {
        XJdbc.update(updateCaLamViec,
                entity.getTenCa(),
                entity.getGioBatDau(),
                entity.getGioKetThuc(),
                entity.getLuong(),
                entity.getMaCa()
        );
    }

    @Override
    public void delete(String id) {
        XJdbc.update(deleteCaLamViec, id);
    }

    @Override
    public List<CaLamViec> selectAll() {
        return this.selectBySql(selectAllCaLamViec);
    }

    @Override
    public List<CaLamViec> selectBySql(String sql, Object... args) {
        List<CaLamViec> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = XJdbc.query(sql, args);
            while (resultSet.next()) {
                CaLamViec clv = new CaLamViec();
                clv.setMaCa(resultSet.getInt(1));
                clv.setTenCa(resultSet.getString(2));
                clv.setGioBatDau(resultSet.getString(3));
                clv.setGioKetThuc(resultSet.getString(4));
                clv.setLuong(resultSet.getDouble(5));
                list.add(clv);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException ex) {
        }
        return list;
    }

    @Override
    public CaLamViec selectById(String id) {
        List<CaLamViec> list = this.selectBySql(selectByIdCaLamViec, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
