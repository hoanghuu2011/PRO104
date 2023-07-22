/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.coffee.ui;

import com.coffee.dao.CaLamViecDAO;
import com.coffee.entity.CaLamViec;
import com.coffee.utils.MsgBox;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * 
 */
public class Calamform extends javax.swing.JPanel {

    /**
     * Creates new form Calamform
     */
    public Calamform() {
        initComponents();
         fillTable();
        viTri = -1;
    }
      CaLamViecDAO caLamViec = new CaLamViecDAO();
    List<CaLamViec> list = caLamViec.selectAll();
    
    int viTri;

   
   

   

    public String cutTime(String s, JComboBox cbo) {
        s = s.substring(0, 5);
        int gio = Integer.parseInt(s.substring(0, 2));
        int phut = Integer.parseInt(s.substring(3, 5));
        String muiGio = "";
        if (gio >= 12) {
            if (gio > 12) {
                gio = gio - 12;
            }
            muiGio = " CH";
        } else {
            muiGio = " SA";
        }
        if (viTri > -1) {
            cbo.setSelectedItem(muiGio.trim());
        }
        if (gio < 10) {
            s = "0" + gio + ":";
        } else {
            s = gio + ":";
        }
        if (phut < 10) {
            s += "0" + phut + muiGio;
        } else {
            s += phut + muiGio;
        }
        return s;
    }

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblCaLamViec.getModel();
        model.setRowCount(0);
        list = caLamViec.selectAll();
        for (int i = 0; i < list.size(); i++) {
            String gioBD = cutTime(list.get(i).getGioBatDau(), cboGioBD);
            String gioKT = cutTime(list.get(i).getGioKetThuc(), cboGioKT);
            Object[] rows = new Object[]{list.get(i).getMaCa(), list.get(i).getTenCa(), gioBD, gioKT, list.get(i).getLuong()
            };
            model.addRow(rows);
        }

    }

    public String setTime(String time, JTextField txt, JComboBox cbo) {
        String s = txt.getText();
        int gio = Integer.parseInt(s.substring(0, 2));
        int phut = Integer.parseInt(s.substring(3, 5));
        if (cbo.getSelectedItem().equals("CH")) {
            if (gio != 12) {
                gio = gio + 12;
            }
            time = gio + ":" + phut;
        } else {
            if (gio == 12) {
                time = "00:00";
            } else {
                time = s;
            }
        }

        return time;
    }

    CaLamViec readForm() {
        CaLamViec ca = new CaLamViec();
        String gioBD = "";
        String gioKT = "";
        gioBD = setTime(gioBD, txtGioBatDau, cboGioBD);
        gioKT = setTime(gioKT, txtGioKetThuc, cboGioKT);
        ca.setMaCa(Integer.parseInt(txtMaCa.getText()));
        ca.setTenCa(txtTenCa.getText());
        ca.setGioBatDau(gioBD);
        ca.setGioKetThuc(gioKT);
        ca.setLuong(Float.parseFloat(txtLuong.getText()));
        return ca;
    }

    public void writeForm(CaLamViec ca) {
        String gioBD = "";
        String gioKT = "";
        txtMaCa.setText(ca.getMaCa() + "");
        txtTenCa.setText(ca.getTenCa());
        if (ca.getGioBatDau() != null || ca.getGioKetThuc() != null) {
            gioBD = cutTime(ca.getGioBatDau(), cboGioBD);
            gioKT = cutTime(ca.getGioKetThuc(), cboGioKT);
            gioBD = gioBD.substring(0, 5);
            gioKT = gioKT.substring(0, 5);

        }
        txtGioBatDau.setText(gioBD);
        txtGioKetThuc.setText(gioKT);
        txtLuong.setText(ca.getLuong() + "");
    }

    public void clear() {
        CaLamViec ca = new CaLamViec();
        writeForm(ca);
        txtMaCa.setText("");
        viTri = -1;
//        buttonSh();
        txtLuong.setText("");
        txtMaCa.setEditable(true);
    }

    public void insert() {
        CaLamViec ca = this.readForm();
        caLamViec.insert(ca);
        MsgBox.alert(this, "Thêm thành công");
        fillTable();
        clear();
    }

    public void update() {
        CaLamViec ca = this.readForm();
        caLamViec.update(ca);
        MsgBox.alert(this, "Sửa thành công");
        fillTable();
        viTri = -1;
//        buttonSh();
        clear();
    }

    public void showTable() {
        viTri = tblCaLamViec.getSelectedRow();
        CaLamViec ca = list.get(viTri);
        writeForm(ca);
        tabCaLamViec.setSelectedIndex(0);
//        buttonSh();
        txtMaCa.setEditable(false);
    }

    public void remove() {
        viTri = tblCaLamViec.getSelectedRow();
        boolean chon = MsgBox.confirm(this, "Bạn có chắc chắn xóa ca làm này");
        DefaultTableModel model = (DefaultTableModel) tblCaLamViec.getModel();
        if (chon) {
            String maCaLamViec = tblCaLamViec.getValueAt(viTri, 0).toString();
            caLamViec.delete(maCaLamViec);
            MsgBox.alert(this, "Đã xóa thành công");
            fillTable();
            clear();
        } else {
            MsgBox.alert(this, "Chưa được xóa");
        }
        viTri = -1;
    }

    public String valiTime(JTextField txt, JComboBox cbo) {
        String loi = "";
        int gio = Integer.parseInt(txt.getText().replace(":", ""));
        int gio1 = Integer.parseInt(txt.getText().substring(0, 2));
        if (cbo.getSelectedItem().equals("CH")) {
            if (gio1 < 12) {
                gio = gio + 1200;
            }
        } else {
            if (gio1 == 12) {
                gio = gio - 1200;
            }
        }
        if (gio < 600 || gio > 2300) {
            loi += " phải từ 6:00 SA đến 23:00 CH\n";
        }
        return loi;
    }

    public boolean validateCaLamViec() {
        String maCaLamViec = txtMaCa.getText();
        String tenCaLamViec = txtTenCa.getText();
        String gioBatDau = txtGioBatDau.getText();
        String gioKetThuc = txtGioKetThuc.getText();
        String luong = txtLuong.getText();

        String loi = "";
        try {
            int so = Integer.parseInt(maCaLamViec);
            if (so <= 0) {
                loi += "Mã Ca phải lớn hơn 0\n";

            }
            if (btnThem.getBackground() != Color.LIGHT_GRAY) {
                for (int i = 0; i < list.size(); i++) {
                    if (so == list.get(i).getMaCa()) {
                        loi += "Mã ca đã tồn tại\n";
                        break;
                    }
                }
            }
        } catch (NumberFormatException e) {
            loi += "Vui lòng nhập mã ca là số\n";
        }
        if (tenCaLamViec.equals("")) {
            loi += "Vui lòng nhập tên ca\n";
        }
        if (gioBatDau.equals("")) {
            loi += "Vui lòng nhập giờ bắt đầu \n";
        } else if (gioBatDau.length() < 5) {
            loi += "Vui lòng nhập đúng định dạng giờ (VD: 06:00)\n";
        } else if (valiTime(txtGioBatDau, cboGioBD).length() > 0) {
            loi += "Giờ bắt đầu";
            loi += valiTime(txtGioBatDau, cboGioBD);
        }
        if (gioKetThuc.equals("")) {
            loi += "Vui lòng nhập giờ kết thúc \n";
        } else if (gioKetThuc.length() < 5) {
            loi += "Vui lòng nhập đúng định dạng giờ (VD: 06:00)\n";
        } else if (valiTime(txtGioKetThuc, cboGioKT).length() > 0) {
            loi += "Giờ kết thúc";
            loi += valiTime(txtGioKetThuc, cboGioKT);
        }
        if (gioBatDau.equals(gioKetThuc) && cboGioBD.getSelectedItem().equals(cboGioKT.getSelectedItem())) {
            loi += "Giờ bắt đầu và kết thúc không đuọc trùng\n";
        }
        int gioBD = Integer.parseInt(txtGioBatDau.getText().replace(":", ""));
        int gioKT = Integer.parseInt(txtGioKetThuc.getText().replace(":", ""));
        if (cboGioBD.getSelectedItem().equals("CH")) {
            if (gioBD < 1200) {
                gioBD = gioBD + 1200;
            }
        }
        if (cboGioKT.getSelectedItem().equals("CH")) {
            if (gioKT < 1200) {
                gioKT = gioKT + 1200;
            }
        }
        if (gioKT < gioBD) {
            loi += "Giờ bắt đầu không được lớn giờ kết thúc\n";
        } else if (gioKT - gioBD < 100) {
            loi += "Một ca làm ít nhất là 1 tiếng\n";
        }
        for (int i = 0; i < list.size(); i++) {
            if (gioBatDau.equals(cutTime(list.get(i).getGioBatDau(), cboGioBD).substring(0, 5))) {
                int so = Integer.parseInt(maCaLamViec);
                if (btnThem.getBackground() != Color.LIGHT_GRAY) {
                    loi += "Giờ bắt đầu đã tồn tại\n";
                } else if (so != list.get(i).getMaCa()) {
                    loi += "Giờ bắt đầu đã tồn tại\n";
                }
                break;
            }
        }

        try {
            float tien = Float.parseFloat(luong);
            if (tien <= 0) {
                loi += "Lương phải lớn hơn 0\n";
            }
        } catch (NumberFormatException e) {
            loi += "Vui lòng nhập lương là số\n";
        }
        if (loi.length() > 0) {
            MsgBox.alert(this, loi);
            return false;
        } else {
            return true;

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
        tabCaLamViec = new javax.swing.JTabbedPane();
        panelCapNhat = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMaCa = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTenCa = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtLuong = new javax.swing.JTextField();
        cboGioKT = new javax.swing.JComboBox<>();
        cboGioBD = new javax.swing.JComboBox<>();
        txtGioKetThuc = new javax.swing.JTextField();
        txtGioBatDau = new javax.swing.JTextField();
        lblVND = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnReNew = new javax.swing.JButton();
        panelDanhSach = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCaLamViec = new javax.swing.JTable();
        pnlTitleBarr17 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 2, 2, 2, new java.awt.Color(204, 255, 255)));

        tabCaLamViec.setBackground(new java.awt.Color(255, 255, 255));
        tabCaLamViec.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabCaLamViec.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        panelCapNhat.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Mã ca:");

        txtMaCa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMaCa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Tên ca:");

        txtTenCa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTenCa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Giờ bắt đầu: ");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Giờ kết thúc:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Lương: ");

        txtLuong.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtLuong.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLuongActionPerformed(evt);
            }
        });

        cboGioKT.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboGioKT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SA", "CH" }));
        cboGioKT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        cboGioKT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboGioKTActionPerformed(evt);
            }
        });

        cboGioBD.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboGioBD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SA", "CH" }));
        cboGioBD.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        cboGioBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboGioBDActionPerformed(evt);
            }
        });

        txtGioKetThuc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtGioKetThuc.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtGioKetThuc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGioKetThucFocusLost(evt);
            }
        });
        txtGioKetThuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtGioKetThucKeyPressed(evt);
            }
        });

        txtGioBatDau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtGioBatDau.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtGioBatDau.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGioBatDauFocusLost(evt);
            }
        });
        txtGioBatDau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtGioBatDauKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGioBatDauKeyReleased(evt);
            }
        });

        lblVND.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblVND.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblVND.setText("VND");
        lblVND.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

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

        btnXoa.setBackground(new java.awt.Color(255, 0, 51));
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

        javax.swing.GroupLayout panelCapNhatLayout = new javax.swing.GroupLayout(panelCapNhat);
        panelCapNhat.setLayout(panelCapNhatLayout);
        panelCapNhatLayout.setHorizontalGroup(
            panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCapNhatLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCapNhatLayout.createSequentialGroup()
                        .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelCapNhatLayout.createSequentialGroup()
                        .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCapNhatLayout.createSequentialGroup()
                                .addComponent(txtGioBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addComponent(cboGioBD, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtMaCa)
                            .addComponent(txtTenCa)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCapNhatLayout.createSequentialGroup()
                                .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtLuong)
                                    .addComponent(txtGioKetThuc))
                                .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cboGioKT, 0, 66, Short.MAX_VALUE)
                                    .addComponent(lblVND, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(panelCapNhatLayout.createSequentialGroup()
                                .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(10, 10, 10))))
            .addGroup(panelCapNhatLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(btnReNew)
                .addGap(84, 84, 84)
                .addComponent(btnThem)
                .addGap(18, 18, 18)
                .addComponent(btnSua)
                .addGap(18, 18, 18)
                .addComponent(btnXoa)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelCapNhatLayout.setVerticalGroup(
            panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCapNhatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaCa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenCa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboGioBD, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGioBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(11, 11, 11)
                .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboGioKT, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGioKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblVND, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGap(36, 36, 36)
                .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnReNew))
                .addContainerGap())
        );

        tabCaLamViec.addTab("CẬP NHẬT", panelCapNhat);

        panelDanhSach.setBackground(new java.awt.Color(255, 255, 255));

        tblCaLamViec.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblCaLamViec.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã ca", "Tên ca", "Giờ bắt đầu", "Giờ kết thúc", "Lương"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCaLamViec.setFocusable(false);
        tblCaLamViec.setRowHeight(25);
        tblCaLamViec.setSelectionBackground(new java.awt.Color(0, 102, 255));
        tblCaLamViec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCaLamViecMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCaLamViec);

        javax.swing.GroupLayout panelDanhSachLayout = new javax.swing.GroupLayout(panelDanhSach);
        panelDanhSach.setLayout(panelDanhSachLayout);
        panelDanhSachLayout.setHorizontalGroup(
            panelDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDanhSachLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        panelDanhSachLayout.setVerticalGroup(
            panelDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabCaLamViec.addTab("DANH SÁCH", panelDanhSach);

        pnlTitleBarr17.setBackground(new java.awt.Color(81, 145, 255));
        pnlTitleBarr17.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlTitleBarr17MouseDragged(evt);
            }
        });
        pnlTitleBarr17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlTitleBarr17MousePressed(evt);
            }
        });

        javax.swing.GroupLayout pnlTitleBarr17Layout = new javax.swing.GroupLayout(pnlTitleBarr17);
        pnlTitleBarr17.setLayout(pnlTitleBarr17Layout);
        pnlTitleBarr17Layout.setHorizontalGroup(
            pnlTitleBarr17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 619, Short.MAX_VALUE)
        );
        pnlTitleBarr17Layout.setVerticalGroup(
            pnlTitleBarr17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Ca làm việc");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlTitleBarr17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(tabCaLamViec, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlTitleBarr17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel8)
                .addGap(0, 0, 0)
                .addComponent(tabCaLamViec, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void txtLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLuongActionPerformed

    private void cboGioKTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGioKTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboGioKTActionPerformed

    private void cboGioBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGioBDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboGioBDActionPerformed

    private void txtGioKetThucFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGioKetThucFocusLost
        // TODO add your handling code here:
        txtGioKetThuc.setEditable(true);
    }//GEN-LAST:event_txtGioKetThucFocusLost

    private void txtGioKetThucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGioKetThucKeyPressed
        // TODO add your handling code here:
        nhapGio(evt, txtGioKetThuc);
    }//GEN-LAST:event_txtGioKetThucKeyPressed

    private void txtGioBatDauFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGioBatDauFocusLost
        // TODO add your handling code here:
        txtGioBatDau.setEditable(true);
    }//GEN-LAST:event_txtGioBatDauFocusLost

    private void txtGioBatDauKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGioBatDauKeyPressed
        // TODO add your handling code here:
        nhapGio(evt, txtGioBatDau);
    }//GEN-LAST:event_txtGioBatDauKeyPressed

    private void txtGioBatDauKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGioBatDauKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGioBatDauKeyReleased

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if (validateCaLamViec()) {
            insert();
            txtMaCa.setEditable(true);
        } else {

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

    private void tblCaLamViecMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCaLamViecMouseClicked
        // TODO add your handling code here:
        showTable();
    }//GEN-LAST:event_tblCaLamViecMouseClicked

    private void pnlTitleBarr17MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleBarr17MouseDragged

        // TODO add your handling code here:
        
    }//GEN-LAST:event_pnlTitleBarr17MouseDragged

    private void pnlTitleBarr17MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleBarr17MousePressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_pnlTitleBarr17MousePressed
 public void nhapGio(KeyEvent evt, JTextField txt) {
        String parten = "[0-9]";
        String nhap = String.valueOf(evt.getKeyChar());
        String text = txt.getText();
        if (evt.getKeyChar() == evt.VK_BACK_SPACE) {
            txt.setEditable(true);
        } else {
            if (!nhap.matches(parten)) {
                txt.setEditable(false);
            } else if (text.length() < 1) {
                String parten1 = "[0-1]";
                if (!nhap.matches(parten1)) {
                    txt.setText("0");
                }
            } else {
                txt.setEditable(true);
                String parten2 = "[0-5]";
                if (text.length() == 2) {
                    txt.setText(text + ":");
                    if (!nhap.matches(parten2)) {
                        txt.setText(text + ":0");
                    }
                }
                if (text.length() == 3) {
                    if (!nhap.matches(parten2)) {
                        txt.setText(text + "0");
                    }
                }
            }
            if (text.length() == 5) {
                txt.setEditable(false);
            }
            if (text.length() > 5) {
                txt.setText("");
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReNew;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboGioBD;
    private javax.swing.JComboBox<String> cboGioKT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblVND;
    private javax.swing.JPanel panelCapNhat;
    private javax.swing.JPanel panelDanhSach;
    private javax.swing.JPanel pnlTitleBarr17;
    private javax.swing.JTabbedPane tabCaLamViec;
    private javax.swing.JTable tblCaLamViec;
    private javax.swing.JTextField txtGioBatDau;
    private javax.swing.JTextField txtGioKetThuc;
    private javax.swing.JTextField txtLuong;
    private javax.swing.JTextField txtMaCa;
    private javax.swing.JTextField txtTenCa;
    // End of variables declaration//GEN-END:variables
}
