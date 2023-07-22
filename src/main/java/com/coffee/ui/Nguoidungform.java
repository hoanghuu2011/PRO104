/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.coffee.ui;

import com.coffee.dao.HoaDonDAO;
import com.coffee.dao.NguoiDungDAO;
import com.coffee.dao.NhanVienDAO;
import com.coffee.entity.HoaDon;
import com.coffee.entity.NguoiDung;
import com.coffee.entity.NhanVien;
import com.coffee.utils.MsgBox;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * 
 */
public class Nguoidungform extends javax.swing.JPanel {
int   viTri;
    /**
     * Creates new form Nguoidungform
     */
    public Nguoidungform() {
        initComponents();
        init();
    }
    public void init() {
        fillComboBoxMaND();
//        nameCollum();
        fillTable();
    }
    
         NguoiDungDAO dao = new NguoiDungDAO();
    NhanVien nv;
    NhanVienDAO daoNV = new NhanVienDAO();
    List<NguoiDung> list = dao.selectAll();
    HoaDonDAO daoHD = new HoaDonDAO();
    List<HoaDon> listHD = daoHD.selectAll();

    

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblNguoiDung.getModel();
        model.setRowCount(0);
        String tenND = txtTimKiem.getText();

        List<NguoiDung> list = dao.selectByKeyword(tenND);
        for (NguoiDung nd : list) {
            Object[] rows = new Object[]{nd.getMaND(), nd.getTenND(),
                nd.getPhanQuyen(), nd.getTrangThai2()};
            model.addRow(rows);
        }
    }

    public void fillComboBoxMaND() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboMaND.getModel();
        model.removeAllElements();
        List<NhanVien> list = daoNV.selectNhanVien();
        for (NhanVien nv : list) {

            model.addElement(nv);

        }
        cboMaND.setSelectedIndex(0);

    }

    public NguoiDung readForm() {
        NguoiDung nd = new NguoiDung();
        nv = (NhanVien) cboMaND.getSelectedItem();
        nd.setMaND(nv.getMaNV());
        nd.setTenND(txtTenND.getText());
        nd.setMatKhau(pswMatKhau.getText());
        nd.setPhanQuyen(cboPhanQuyen.getSelectedItem().toString());
        if (rdoHoatDong.isSelected()) {
            nd.setTrangThai("1");
        } else {
            nd.setTrangThai("0");
        }
        return nd;
    }

    public NguoiDung readFormUpdate() {
        NguoiDung nd = new NguoiDung();
        nv = (NhanVien) cboMaND.getSelectedItem();
        nd.setMaND(nv.getMaNV());
        nd.setTenND(txtTenND.getText());
        if (!pswMatKhau.getText().isEmpty()) {
            nd.setMatKhau(pswMatKhau.getText());
        } else {
            String tenND = txtTenND.getText();

            NguoiDung list = dao.selectById(tenND);

            nd.setMatKhau(list.getMatKhau());
        }
        nd.setPhanQuyen(cboPhanQuyen.getSelectedItem().toString());
        if (rdoHoatDong.isSelected()) {
            nd.setTrangThai("1");
        } else {
            nd.setTrangThai("0");
        }
        return nd;
    }

    public void writeForm() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboMaND.getModel();
        nv = (NhanVien) cboMaND.getSelectedItem();
        tabNhanVien.setSelectedIndex(0);
        viTri = tblNguoiDung.getSelectedRow();
        // txtMaND.setEnabled(false);
        txtTenND.setEnabled(false);
        String phanQuyen = tblNguoiDung.getValueAt(viTri, 2).toString();
        String maND = tblNguoiDung.getValueAt(viTri, 0).toString();

        List<NhanVien> list = daoNV.selectNhanVien();
        if (!list.isEmpty()) {
            for (NhanVien nv : list) {
                if (nv.getMaNV().equals(maND)) {
                    model.setSelectedItem(nv);
                }

            }
        }

        // txtMaND.setText(maND);
        txtTenND.setText(tblNguoiDung.getValueAt(viTri, 1).toString());
        pswMatKhau.setText("");
        pswXacNhanMK.setText("");
        cboPhanQuyen.setSelectedItem(tblNguoiDung.getValueAt(viTri, 2));
        if (tblNguoiDung.getValueAt(viTri, 3).toString().equalsIgnoreCase("Hoạt động")) {
            rdoHoatDong.setSelected(true);
        } else {
            rdoKhongHoatDong.setSelected(true);
        }
        if (phanQuyen.equalsIgnoreCase("admin")) {
            cboPhanQuyen.setEnabled(false);
//            eventButtonEnable(btnXoa);
           
            rdoKhongHoatDong.setEnabled(false);
        } else {
            cboPhanQuyen.setEnabled(true);
           // eventButton(btnXoa, new Color(255, 0, 0));
         
            rdoKhongHoatDong.setEnabled(true);
        }

    }

   /* public void eventButtonEnable(JButton button) {
        button.setEnabled(false);
        button.setBackground(Color.GRAY);
    }

    public void eventButton(JButton button, Color color) {
        button.setEnabled(true);
        button.setBackground(color);
    }*/

    public void insert() {
        NguoiDung nd = this.readForm();
        dao.insert(nd);
        fillTable();
        MsgBox.alert(this, "Thêm thành công");
        tabNhanVien.setSelectedIndex(1);

    }

    public void delete() {
        viTri = tblNguoiDung.getSelectedRow();
        if (viTri < 0) {
            MsgBox.alert(this, "Vui lòng chọn người dùng từ danh sách!");
            return;
        }
        String phanQuyen = tblNguoiDung.getValueAt(viTri, 2).toString();

        if (phanQuyen.equalsIgnoreCase("admin")) {
            MsgBox.alert(this, "Bạn không thể xóa!");
            return;
        }
        boolean chon = MsgBox.confirm(this, "Bạn có chắc chắn xóa "), xoa = true;
        DefaultTableModel model = (DefaultTableModel) tblNguoiDung.getModel();
        String tenNguoiDung = txtTenND.getText();
        if (chon) {
            for (int i = 0; i < listHD.size(); i++) {
                if (listHD.get(i).getTenDN().equals(tenNguoiDung)) {
                    MsgBox.alert(this, "Người dùng này không thể xóa!!!");
                    xoa = false;
                    break;
                }
            }

            if (xoa) {
                dao.delete(tenNguoiDung);
                fillTable();
                MsgBox.alert(this, "Đã xóa thành công");
                txtTenND.setText("");
                txtTenND.setEnabled(true);
            }

        } else {
            MsgBox.alert(this, "Chưa được xóa");
        }
    }

    public void update() {

        NguoiDung nd = this.readFormUpdate();
        dao.update(nd);
        fillTable();
        MsgBox.alert(this, "Sửa thành công");
        tabNhanVien.setSelectedIndex(1);
        for (int i = 0; i < tblNguoiDung.getRowCount(); i++) {
            if (nd.getMaND().equals(tblNguoiDung.getValueAt(i, 0))) {
                tblNguoiDung.setRowSelectionInterval(i, i);
            }
        }
    }

    public boolean checkNguoiDung() {
        //String maND = txtMaND.getText();
        String tenND = txtTenND.getText();
        String matKhau = pswMatKhau.getText();
        String nhapLaiMK = pswXacNhanMK.getText();
        String loi = "";
        if (tenND.isEmpty() && matKhau.isEmpty() && nhapLaiMK.isEmpty()) {
            loi += "Không được bỏ trống !? \n";
        } else {
//            if (maND.isEmpty()) {
//                loi += "Vui lòng nhập mã người dùng !\n";
//
//            }
            if (tenND.isEmpty()) {
                loi += "Vui lòng nhập tên người dùng !\n";

            }
            if (!matKhau.isEmpty()) {
                if (nhapLaiMK.isEmpty()) {
                    loi += "Vui lòng nhập lại mật khẩu !\n";

                } else if (!matKhau.equals(nhapLaiMK)) {
                    loi += "Mật khẩu không trùng khớp !\n";
                    pswXacNhanMK.requestFocus();
                }

            } else {
                loi += "Vui lòng nhập mật khẩu !\n";
            }

        }
        if (cboPhanQuyen.getSelectedIndex() == 0) {
            loi += "Vui lòng chọn vai trò !\n";
        }
        if (!rdoHoatDong.isSelected() && !rdoKhongHoatDong.isSelected()) {
            loi += "Vui lòng chọn trạng thái \n";
        }
        if (loi.length() != 0) {
            MsgBox.alert(this, loi);
            return false;
        }

        return true;
    }

    public boolean checkBoTrongUpdate() {
//        viTri = tblNguoiDung.getSelectedRow();
//        if (viTri < 0) {
//            MsgBox.alert(this, "Vui lòng chọn người dùng từ danh sách!");
//            return false;
//        }
        // String maND = txtMaND.getText();
        String tenND = txtTenND.getText();
        String matKhau = pswMatKhau.getText();
        String nhapLaiMK = pswXacNhanMK.getText();
        String loi = "";
        if (tenND.isEmpty() && matKhau.isEmpty() && nhapLaiMK.isEmpty()) {
            loi += "Không được bỏ trống !? \n";
        } else {
//            if (maND.isEmpty()) {
//                loi += "Vui lòng nhập mã người dùng !\n";
//
//            }
            if (tenND.isEmpty()) {
                loi += "Vui lòng nhập tên người dùng !\n";

            }
            if (!matKhau.isEmpty()) {
                if (nhapLaiMK.isEmpty()) {
                    loi += "Vui lòng nhập lại mật khẩu !\n";

                } else if (!matKhau.equals(nhapLaiMK)) {
                    loi += "Mật khẩu không trùng khớp !\n";
                    pswXacNhanMK.requestFocus();
                }

            }

        }
        if (cboPhanQuyen.getSelectedIndex() == 0) {
            loi += "Vui lòng chọn vai trò !\n";
        }
        if (!rdoHoatDong.isSelected() && !rdoKhongHoatDong.isSelected()) {
            loi += "Vui lòng chọn trạng thái \n";
        }
        if (loi.length() != 0) {
            MsgBox.alert(this, loi);
            return false;
        }

        return true;
    }

    public boolean checkTrung() {
        list = dao.selectAll();

        //String maND = txtMaND.getText();
        //List<NhanVien> nv = daoNV.selectByKeyword(maND);
        String tenND = txtTenND.getText();
        String loi = "";
        for (int i = 0; i < list.size(); i++) {

//            if (maND.equals(list.get(i).getMaND())) {
//
//                loi += "Nhân viên này đã có tài khoản! \n";
//
//            }
            if (tenND.equals(list.get(i).getTenND()) && !tenND.isEmpty()) {

                loi += "Tên tài khoản đã tồn tại! \n";

            }
        }

//        if (nv.size() == 0 && !maND.isEmpty()) {
//
//            loi += "Mã người dùng không hợp lệ \nNhân viên này ko tồn tại! \n";
//
//        }
        if (loi.length() != 0) {
            MsgBox.alert(this, loi);
            return false;
        }
        return true;
    }

    public void clearForm() {
        nv = (NhanVien) cboMaND.getSelectedItem();
        List<NguoiDung> list = dao.selectByMaND(nv.getMaNV());
        if (list.isEmpty()) {
            //txtMaND.setText("");
            txtTenND.setText("");
            pswMatKhau.setText("");
            pswXacNhanMK.setText("");
            rdoHoatDong.setSelected(true);

            cboPhanQuyen.setSelectedIndex(0);
        } else {
            pswMatKhau.setText("");
            pswXacNhanMK.setText("");
            rdoHoatDong.setSelected(true);
            if (!nv.getMaNV().equals("admin")) {
                cboPhanQuyen.setSelectedIndex(0);
            }

            viTri = 0;
        }

    }
    public void maNDEvent() {
        if (cboMaND.getSelectedItem() == null) {

        } else {
//            if (viTri > 0) {
//                return ;
//            }
            nv = (NhanVien) cboMaND.getSelectedItem();
            List<NguoiDung> list = dao.selectByMaND(nv.getMaNV());
            if (list.isEmpty()) {
                //txtMaND.setText(nv.getMaNV());
                txtTenND.setText("");
                cboPhanQuyen.setEnabled(true);
                cboPhanQuyen.setSelectedIndex(0);

                rdoHoatDong.setSelected(false);
                txtTenND.setEnabled(true);
//                eventButtonEnable(btnXoa);
         

             //   eventButtonEnable(btnSua);
                
            } else {
               // eventButton(btnSua, new Color(81, 145, 255));
                txtTenND.setEnabled(false);
            }
            for (NguoiDung nd : list) {

                //txtMaND.setText(nd.getMaND());
                txtTenND.setText(nd.getTenND());
                if (nd.getPhanQuyen().equalsIgnoreCase("admin")) {
                    cboPhanQuyen.setEnabled(false);
                    cboPhanQuyen.setSelectedItem("Admin");

                } else if (nd.getPhanQuyen().equalsIgnoreCase("Quản lý")) {
                    cboPhanQuyen.setEnabled(true);
                    cboPhanQuyen.setSelectedItem("Quản lý");
                
                
                //} else {
                 //   cboPhanQuyen.setEnabled(true);
                   /// cboPhanQuyen.setSelectedItem("Thu ngân");
                }
                
                if (nd.getTrangThai().equals("1")) {
                    rdoHoatDong.setSelected(true);
                } else if (nd.getTrangThai().equals("0")) {
                    rdoKhongHoatDong.setSelected(true);
                }
                String phanQuyen = nd.getPhanQuyen();
                if (phanQuyen.equalsIgnoreCase("admin")) {
                   // eventButtonEnable(btnXoa);
                   
                    rdoKhongHoatDong.setEnabled(false);
                } else {
                    //eventButton(btnXoa, new Color(255, 0, 0));
                   
                    rdoKhongHoatDong.setEnabled(true);
                }
            }
        }
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tabNhanVien = new javax.swing.JTabbedPane();
        pnlCapNhat = new javax.swing.JPanel();
        txtTenND = new javax.swing.JTextField();
        lblTenND = new javax.swing.JLabel();
        lblMatKhau = new javax.swing.JLabel();
        lblPhanQuyen = new javax.swing.JLabel();
        lblNhapLaiMatKhau = new javax.swing.JLabel();
        cboPhanQuyen = new javax.swing.JComboBox<>();
        lblTrangThai = new javax.swing.JLabel();
        rdoHoatDong = new javax.swing.JRadioButton();
        rdoKhongHoatDong = new javax.swing.JRadioButton();
        pswXacNhanMK = new javax.swing.JPasswordField();
        pswMatKhau = new javax.swing.JPasswordField();
        cboMaND = new javax.swing.JComboBox<>();
        lblMaND1 = new javax.swing.JLabel();
        btnLamMoi = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        pnlDanhSach = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNguoiDung = new javax.swing.JTable();
        pnlTimKiem = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        pnlTablePane = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 2, 2, 2, new java.awt.Color(204, 255, 255)));

        tabNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        tabNhanVien.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabNhanVien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tabNhanVien.setOpaque(true);

        pnlCapNhat.setBackground(new java.awt.Color(255, 255, 255));

        txtTenND.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTenND.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtTenND.setEnabled(false);
        txtTenND.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenNDActionPerformed(evt);
            }
        });

        lblTenND.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTenND.setText("TÊN ĐĂNG NHẬP:");

        lblMatKhau.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMatKhau.setText("MẬT KHẨU :");

        lblPhanQuyen.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPhanQuyen.setText("PHÂN QUYỀN :");

        lblNhapLaiMatKhau.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNhapLaiMatKhau.setText("NHẬP LẠI MẬT KHẨU :");

        cboPhanQuyen.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboPhanQuyen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Chọn phân quyền---", "Admin", "Quản lý", " ", " " }));

        lblTrangThai.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTrangThai.setText("TRẠNG THÁI:");

        rdoHoatDong.setBackground(new java.awt.Color(255, 255, 255));
        rdoHoatDong.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        rdoHoatDong.setText("Hoạt động");
        rdoHoatDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoHoatDongActionPerformed(evt);
            }
        });

        rdoKhongHoatDong.setBackground(new java.awt.Color(255, 255, 255));
        rdoKhongHoatDong.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        rdoKhongHoatDong.setText("Không hoạt động");
        rdoKhongHoatDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoKhongHoatDongActionPerformed(evt);
            }
        });

        pswXacNhanMK.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pswXacNhanMK.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        pswMatKhau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pswMatKhau.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        pswMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pswMatKhauActionPerformed(evt);
            }
        });

        cboMaND.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboMaND.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMaND.setBorder(null);
        cboMaND.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboMaNDItemStateChanged(evt);
            }
        });

        lblMaND1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMaND1.setText("HỌ TÊN NGƯỜI DÙNG:");

        btnLamMoi.setText("Làm Mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 102, 255));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(255, 0, 51));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCapNhatLayout = new javax.swing.GroupLayout(pnlCapNhat);
        pnlCapNhat.setLayout(pnlCapNhatLayout);
        pnlCapNhatLayout.setHorizontalGroup(
            pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pswMatKhau)
            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addComponent(cboMaND, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20))
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addComponent(txtTenND)
                        .addContainerGap())
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addComponent(pswXacNhanMK)
                        .addContainerGap())
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLamMoi)
                            .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblTenND)
                                .addComponent(lblMatKhau)
                                .addComponent(lblNhapLaiMatKhau)
                                .addComponent(lblPhanQuyen)
                                .addComponent(cboPhanQuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(rdoHoatDong)
                                    .addGap(18, 18, 18)
                                    .addComponent(rdoKhongHoatDong))
                                .addComponent(lblTrangThai)
                                .addComponent(lblMaND1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnThem)
                        .addGap(18, 18, 18)
                        .addComponent(btnSua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXoa)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pnlCapNhatLayout.setVerticalGroup(
            pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMaND1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboMaND, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTenND)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenND, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblMatKhau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pswMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNhapLaiMatKhau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pswXacNhanMK, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPhanQuyen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboPhanQuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblTrangThai)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoHoatDong)
                    .addComponent(rdoKhongHoatDong))
                .addGap(26, 26, 26)
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLamMoi)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa))
                .addGap(59, 59, 59))
        );

        tabNhanVien.addTab("Cập Nhật", pnlCapNhat);

        pnlDanhSach.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);

        tblNguoiDung.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblNguoiDung.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã người dùng", "Tên đăng nhập", "Phân quyền", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNguoiDung.setFocusable(false);
        tblNguoiDung.setRowHeight(25);
        tblNguoiDung.setSelectionBackground(new java.awt.Color(255, 0, 0));
        tblNguoiDung.getTableHeader().setReorderingAllowed(false);
        tblNguoiDung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNguoiDungMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNguoiDung);

        pnlTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        pnlTimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        txtTimKiem.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        txtTimKiem.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/coffee/icon/Search.png"))); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTimKiemLayout = new javax.swing.GroupLayout(pnlTimKiem);
        pnlTimKiem.setLayout(pnlTimKiemLayout);
        pnlTimKiemLayout.setHorizontalGroup(
            pnlTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTimKiemLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 719, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTimKiem)
                .addContainerGap())
        );
        pnlTimKiemLayout.setVerticalGroup(
            pnlTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTimKiemLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlTimKiemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTimKiem))
        );

        javax.swing.GroupLayout pnlDanhSachLayout = new javax.swing.GroupLayout(pnlDanhSach);
        pnlDanhSach.setLayout(pnlDanhSachLayout);
        pnlDanhSachLayout.setHorizontalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(pnlTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlDanhSachLayout.setVerticalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabNhanVien.addTab("Danh Sách", pnlDanhSach);

        pnlTablePane.setBackground(new java.awt.Color(255, 255, 255));
        pnlTablePane.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(255, 255, 255)));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Quản lý người dùng");

        javax.swing.GroupLayout pnlTablePaneLayout = new javax.swing.GroupLayout(pnlTablePane);
        pnlTablePane.setLayout(pnlTablePaneLayout);
        pnlTablePaneLayout.setHorizontalGroup(
            pnlTablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlTablePaneLayout.setVerticalGroup(
            pnlTablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tabNhanVien)
                    .addComponent(pnlTablePane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnlTablePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(tabNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTenNDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenNDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenNDActionPerformed

    private void rdoHoatDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoHoatDongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoHoatDongActionPerformed

    private void rdoKhongHoatDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoKhongHoatDongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoKhongHoatDongActionPerformed

    private void pswMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pswMatKhauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pswMatKhauActionPerformed

    private void cboMaNDItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboMaNDItemStateChanged

        maNDEvent();

    }//GEN-LAST:event_cboMaNDItemStateChanged

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (checkTrung() && checkNguoiDung()) {
            insert();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (checkBoTrongUpdate()) {
            update();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void tblNguoiDungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguoiDungMouseClicked
        if (evt.getClickCount() == 2) {
            writeForm();
        }
    }//GEN-LAST:event_tblNguoiDungMouseClicked

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        fillTable();
    }//GEN-LAST:event_btnTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboMaND;
    private javax.swing.JComboBox<String> cboPhanQuyen;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMaND1;
    private javax.swing.JLabel lblMatKhau;
    private javax.swing.JLabel lblNhapLaiMatKhau;
    private javax.swing.JLabel lblPhanQuyen;
    private javax.swing.JLabel lblTenND;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JPanel pnlCapNhat;
    private javax.swing.JPanel pnlDanhSach;
    private javax.swing.JPanel pnlTablePane;
    private javax.swing.JPanel pnlTimKiem;
    private javax.swing.JPasswordField pswMatKhau;
    private javax.swing.JPasswordField pswXacNhanMK;
    private javax.swing.JRadioButton rdoHoatDong;
    private javax.swing.JRadioButton rdoKhongHoatDong;
    private javax.swing.JTabbedPane tabNhanVien;
    private javax.swing.JTable tblNguoiDung;
    private javax.swing.JTextField txtTenND;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
