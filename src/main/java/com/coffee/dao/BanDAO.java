package com.coffee.dao;

import com.coffee.entity.Ban;
import com.coffee.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BanDAO extends CoffeeDAO<Ban, String> {

    String insertBan = "INSERT INTO Ban VALUES (?, ?,?)";
    String updateBan = "UPDATE Ban SET TenBan=? WHERE MaBan=?";
    String updateTrangThai = "UPDATE Ban SET TrangThai=? WHERE MaBan=?";
    String deleteBan = "DELETE FROM Ban WHERE MaBan=?";
    String selectAllBan = "SELECT * FROM Ban";
    String selectByIdBan = "SELECT * FROM Ban WHERE MaBan=?";
    public static boolean loi = false;
    @Override
    public void insert(Ban entity) {
        XJdbc.update(insertBan,
                entity.getMaBan(),
                entity.getTenBan(),
                entity.isTrangThai());
    }

    @Override
    public void update(Ban entity) {
        XJdbc.update(updateBan,
                entity.getTenBan(), entity.getMaBan()
        );
    }
    public void updateTrangThai(Ban entity)
    {
        XJdbc.update(updateTrangThai,
                entity.isTrangThai(),
                entity.getMaBan()
        );
    }
    @Override
    public void delete(String id) {
        XJdbc.update(deleteBan, id);
    }

    @Override
    public List<Ban> selectAll() {
        return this.selectBySql(selectAllBan);
    }

    @Override
    public List<Ban> selectBySql(String sql, Object... args) {
        List<Ban> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = XJdbc.query(sql, args);
            while (resultSet.next()) {
                Ban ban = new Ban();
                ban.setMaBan(resultSet.getInt(1));
                ban.setTenBan(resultSet.getString(2));
                ban.setTrangThai(resultSet.getBoolean(3));
                
                list.add(ban);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException ex) {
        }
        return list;
    }

    @Override
    public Ban selectById(String id) {
        List<Ban> list = this.selectBySql(selectByIdBan, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

}
