/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.coffee.ui;

import com.coffee.dao.CaLamViecDAO;
import com.coffee.dao.ChiTietLuongDAO;
import com.coffee.dao.NhanVienDAO;
import com.coffee.entity.CaLamViec;
import com.coffee.entity.ChiTietLuong;
import com.coffee.utils.MsgBox;
import com.coffee.utils.XDate;
import java.awt.Color;
import java.awt.Cursor;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

/**
 *
 * 
 */
public final class luongform extends javax.swing.JPanel {

    /**
     * Creates new form luongform
     */
    public luongform() {
        initComponents();
        fillTable();
        viTri = -1;//vi tri đc chọn hiện tại trên bảng
        fillTableNV();
        txtNgayLamViec.setText(XDate.toString(now, "dd-MM-yyyy"));
        fillTable();
        fillCombobox();
        txtMaNV.setEditable(false);
    }
     ChiTietLuongDAO luongDao = new ChiTietLuongDAO();
    java.util.List<ChiTietLuong> listLuong = luongDao.selectAll();
    CaLamViecDAO caLamViecDao = new CaLamViecDAO();
    java.util.List<CaLamViec> listCa = caLamViecDao.selectAll();
    NhanVienDAO nhanVienDao = new NhanVienDAO();
    java.util.List<com.coffee.entity.NhanVien> listNV = nhanVienDao.selectAll();
    Date now = new Date();
    String maNV;
    int viTri;

   
     

    public void buttonPaint(JButton b, Color c) {
        Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
        b.setBackground(c);
        b.setCursor(cursor);
    }

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
        model.setRowCount(0);
        String tenNV = "", tenCa = "";
        String Vaitro;
        double luong = 0;
        listLuong = luongDao.selectAll();
        listCa = caLamViecDao.selectAll();
        for (int i = 0; i < listLuong.size(); i++) {
            for (int j = 0; j < listNV.size(); j++) {
                if (listNV.get(j).getChucVu().equals("Nhân Viên")){
                    if (listLuong.get(i).getMaNV().equals(listNV.get(j).getMaNV())) {
                        tenNV = listNV.get(j).getTenNV();
                        break;
                    }
                }
            }
            for (int j = 0; j < listCa.size(); j++) {
                if (listLuong.get(i).getMaCa() == (listCa.get(j).getMaCa())) {
                    luong = listCa.get(j).getLuong();
                    tenCa = listCa.get(j).getTenCa();
                    break;
                }
            }
            Object[] rows = new Object[]{listLuong.get(i).getMaLuongCT(), listLuong.get(i).getMaNV(), tenNV,
                listLuong.get(i).getNgayLamViec(), tenCa, luong
            };
            model.addRow(rows);
        }
    }

    public void fillTableNV() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        String tenNV = "";
        listNV = nhanVienDao.selectAll();
        for (int i = 0; i < listNV.size(); i++) {
            if (listNV.get(i).getChucVu().equals("Nhân Viên")) {

                Object[] rows = new Object[]{listNV.get(i).getMaNV(), listNV.get(i).getTenNV(), listNV.get(i).getGioiTinh2()
                };
                model.addRow(rows);
            }
        }
    }

    public void fillCombobox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboCaLam.getModel();
        model.removeAllElements();
        listCa = caLamViecDao.selectAll();
        model.addElement("---Chọn Ca---");
        for (int i = 0; i < listCa.size(); i++) {
            model.addElement(listCa.get(i).getTenCa());
        }

    }

    ChiTietLuong readForm() {
        ChiTietLuong chiTietLuong = new ChiTietLuong();
        for (int i = 0; i < listCa.size(); i++) {
            if (listCa.get(i).getTenCa().equals((String) cboCaLam.getSelectedItem())) {
                chiTietLuong.setMaCa(listCa.get(i).getMaCa());
                break;
            }
        }
        chiTietLuong.setMaLuongCT(txtMaLuongCT.getText());
        chiTietLuong.setMaNV(txtMaNV.getText());
        chiTietLuong.setNgayLamViec(XDate.toString(XDate.toDate(txtNgayLamViec.getText(), "dd-MM-yyyy"), "yyyy-MM-dd"));
        return chiTietLuong;
    }

    public void writeForm(ChiTietLuong chiTietLuong) {
        txtMaLuongCT.setText(chiTietLuong.getMaLuongCT());
        for (int i = 0; i < listCa.size(); i++) {
            if (chiTietLuong.getMaCa() == (listCa.get(i).getMaCa())) {
                cboCaLam.setSelectedItem(listCa.get(i).getTenCa());
                break;
            }
        }

        txtNgayLamViec.setText(chiTietLuong.getNgayLamViec());
        txtMaNV.setText(chiTietLuong.getMaNV());
    }

    public void showTable() {
        viTri = tblDanhSach.getSelectedRow();
        ChiTietLuong chiTietLuong = listLuong.get(viTri);
        writeForm(chiTietLuong);
        tabLoai.setSelectedIndex(0);
//        buttonSh();
        txtMaLuongCT.setEditable(false);
    }

    public void clear() {
        ChiTietLuong chiTietLuong = new ChiTietLuong();
        txtMaLuongCT.setText("");
        txtMaNV.setText("");
        cboCaLam.setSelectedIndex(0);
        txtMaLuongCT.setEditable(true);
        txtNgayLamViec.setText(XDate.toString(now, "dd-MM-yyyy"));
        viTri = -1;
      //  buttonSh();
    }

    public void insert() {
        ChiTietLuong chiTietLuong = this.readForm();
        luongDao.insert(chiTietLuong);
        MsgBox.alert(this, "Thêm thành công");
        fillTable();
        clear();
    }

    public void update() {
        ChiTietLuong chiTietLuong = this.readForm();
        luongDao.update(chiTietLuong);
        MsgBox.alert(this, "Sửa thành công");
        fillTable();
        viTri = -1;
        //buttonSh();
        clear();
    }

    public void remove() {
        viTri = tblDanhSach.getSelectedRow();
        boolean chon = MsgBox.confirm(this, "Bạn có chắc chắn xóa bàn này");
        DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
        if (chon) {
            String maSanPham = tblDanhSach.getValueAt(viTri, 0).toString();
            luongDao.delete(maSanPham);
            MsgBox.alert(this, "Đã xóa thành công");
            fillTable();
            clear();
        } else {
            MsgBox.alert(this, "Chưa được xóa");
        }
        viTri = -1;
    }

    public void showTableNV() {
        viTri = tblNhanVien.getSelectedRow();
        txtMaNV.setText(tblNhanVien.getValueAt(viTri, 0).toString());
        //txtMaNV.setText(listNV.get(viTri).getMaNV());
    }

    public boolean validateChiTietLuong() {
        String maChiTietLuong = txtMaLuongCT.getText();
        String maNV = txtMaNV.getText();
        String loi = "";
        if (btnThem.getBackground() != Color.LIGHT_GRAY) {
            if (maChiTietLuong.equals("")) {
                loi += "Vui lòng nhập mã lương\n";
            } else {
                for (int i = 0; i < listLuong.size(); i++) {
                    if (maChiTietLuong.equals(listLuong.get(i).getMaLuongCT())) {
                        loi += "Mã đã tồn tại\n";
                        break;
                    }
                }
            }
        }
        if (maNV.equals("")) {
            loi += "Vui lòng chọn nhân viên\n";
        }
        if (cboCaLam.getSelectedIndex() == 0) {
            loi += "Vui lòng chọn ca làm\n";
        }

        if (loi.length() > 0) {
            MsgBox.alert(this, loi);
            return false;
        } else {
            return true;

        }
    }

    public void selectTab(int index) {
        tabLoai.setSelectedIndex(index);
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
        tabLoai = new javax.swing.JTabbedPane();
        panelcalam = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cboCaLam = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        txtNgayLamViec = new javax.swing.JTextField();
        txtMaNV = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMaLuongCT = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnReNew = new javax.swing.JButton();
        PanelDanhSach = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSach = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 2, 2, 2, new java.awt.Color(204, 255, 255)));

        tabLoai.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabLoai.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        panelcalam.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Ngày làm việc :");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Ca làm :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Nhân viên :");

        cboCaLam.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboCaLam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ca 1", "Ca 2", "Ca 3", " " }));

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblNhanVien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Họ tên", "Giới tính"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.setFocusable(false);
        tblNhanVien.setRowHeight(25);
        tblNhanVien.setSelectionBackground(new java.awt.Color(0, 102, 215));
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNhanVien);

        txtNgayLamViec.setEditable(false);
        txtNgayLamViec.setBackground(new java.awt.Color(255, 255, 255));
        txtNgayLamViec.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtNgayLamViec.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtNgayLamViec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayLamViecActionPerformed(evt);
            }
        });

        txtMaNV.setEditable(false);
        txtMaNV.setBackground(new java.awt.Color(255, 255, 255));
        txtMaNV.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMaNV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtMaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNVActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Mã lương :");

        txtMaLuongCT.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMaLuongCT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtMaLuongCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaLuongCTActionPerformed(evt);
            }
        });

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 0, 255));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(255, 0, 0));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnReNew.setText("Mới");
        btnReNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReNewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelcalamLayout = new javax.swing.GroupLayout(panelcalam);
        panelcalam.setLayout(panelcalamLayout);
        panelcalamLayout.setHorizontalGroup(
            panelcalamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcalamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelcalamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelcalamLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelcalamLayout.createSequentialGroup()
                        .addComponent(txtMaLuongCT)
                        .addGap(9, 9, 9))
                    .addGroup(panelcalamLayout.createSequentialGroup()
                        .addGroup(panelcalamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cboCaLam, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelcalamLayout.createSequentialGroup()
                                .addGroup(panelcalamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelcalamLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(panelcalamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtNgayLamViec, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(panelcalamLayout.createSequentialGroup()
                        .addGroup(panelcalamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelcalamLayout.createSequentialGroup()
                                .addComponent(btnThem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSua)
                                .addGap(33, 33, 33)
                                .addComponent(btnXoa))
                            .addComponent(btnReNew))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        panelcalamLayout.setVerticalGroup(
            panelcalamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcalamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelcalamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelcalamLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(37, Short.MAX_VALUE))
                    .addGroup(panelcalamLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaLuongCT, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboCaLam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNgayLamViec, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelcalamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThem)
                            .addComponent(btnSua)
                            .addComponent(btnXoa))
                        .addGap(60, 60, 60))))
        );

        //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        //        Date now = new Date();
        //        txtNgayLamViec.setText(sdf.format(now) + "");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        txtNgayLamViec.setText(sdf.format(now) + "");

        tabLoai.addTab("Ca làm", panelcalam);

        PanelDanhSach.setBackground(new java.awt.Color(255, 255, 255));

        tblDanhSach.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblDanhSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Lương", "Mã nhân viên", "Tên nhân viên", "Ngày làm", "Tên Ca", "Lương"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSach.setFocusable(false);
        tblDanhSach.setRowHeight(25);
        tblDanhSach.setSelectionBackground(new java.awt.Color(0, 102, 215));
        tblDanhSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSach);

        javax.swing.GroupLayout PanelDanhSachLayout = new javax.swing.GroupLayout(PanelDanhSach);
        PanelDanhSach.setLayout(PanelDanhSachLayout);
        PanelDanhSachLayout.setHorizontalGroup(
            PanelDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 845, Short.MAX_VALUE))
        );
        PanelDanhSachLayout.setVerticalGroup(
            PanelDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE))
        );

        tabLoai.addTab("CHI TIẾT LƯƠNG", PanelDanhSach);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Quản Lý Lương");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabLoai, javax.swing.GroupLayout.DEFAULT_SIZE, 851, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        showTableNV();
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void txtNgayLamViecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayLamViecActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayLamViecActionPerformed

    private void txtMaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNVActionPerformed

    private void txtMaLuongCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaLuongCTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaLuongCTActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if (btnThem.getBackground() != Color.LIGHT_GRAY) {
            if (validateChiTietLuong()) {
                insert();
                fillTable();
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:

        update();

    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        if (btnXoa.getBackground() != Color.LIGHT_GRAY) {
            remove();
        } else {

        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnReNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReNewActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnReNewActionPerformed

    private void tblDanhSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachMouseClicked
        // TODO add your handling code here:
        showTable();
    }//GEN-LAST:event_tblDanhSachMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelDanhSach;
    private javax.swing.JButton btnReNew;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboCaLam;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelcalam;
    private javax.swing.JTabbedPane tabLoai;
    private javax.swing.JTable tblDanhSach;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtMaLuongCT;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNgayLamViec;
    // End of variables declaration//GEN-END:variables
}
