/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.coffee.ui;

import com.coffee.dao.NguoiDungDAO;
import com.coffee.dao.NhanVienDAO;
import com.coffee.entity.NguoiDung;
import com.coffee.entity.NhanVien;
import com.coffee.utils.MsgBox;
import com.coffee.utils.XDate;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

/**
 *
 *
 */
public class nhanvienform extends javax.swing.JPanel {

    /**
     * Creates new form nhanvienform
     */
    public nhanvienform() {
        initComponents();
        init();
    }
    NhanVienDAO dao = new NhanVienDAO();
    List<com.coffee.entity.NhanVien> list = dao.selectAll();
    NguoiDungDAO daoND = new NguoiDungDAO();
    List<NguoiDung> listND = daoND.selectAll();
    int mpX, mpY;
    int index;

    /*  public void getAdmin(int i) {

        if (tblNhanVien.getValueAt(i, 0).equals("admin")) {
           
           
        } else {
          
        }

    }*/
    public void init() {

        fillTable();
    }

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        String tenNV = txtTimKiem.getText();

        list = dao.selectByKeyword2(tenNV);
//        list = dao.selectAll();
        for (com.coffee.entity.NhanVien nv : list) {
            Object[] rows = new Object[]{nv.getMaNV(), nv.getTenNV(), nv.getGioiTinh2(),
                nv.getSdt(), nv.getEmail(), nv.getDiaChi(), nv.getChucVu(), nv.getNgayVaoLam2()

            };
            model.addRow(rows);
        }
    }

    public NhanVien readForm() {

        NhanVien nv = new NhanVien();

        nv.setMaNV(txtMaNV.getText());
        nv.setTenNV(txtTenNV.getText());
        if (cboGioiTinh.getSelectedItem().equals("Nam")) {
            nv.setGioiTinh("1");
        } else {
            nv.setGioiTinh("0");
        }

        nv.setSdt(txtSDT.getText());
        nv.setEmail(txtEmail.getText());
        nv.setDiaChi(txtDiaChi.getText());
        nv.setChucVu(cboChucVu.getSelectedItem().toString());

        String ngayNhap = txtNgayNhap.getText();
        Date ngayNhap2 = XDate.toDate(ngayNhap, "dd-MM-yyyy");
        nv.setNgayVaoLam(XDate.toString(ngayNhap2, "yyyy-MM-dd"));

        return nv;
    }

    public void writeForm() {
        int i = tblNhanVien.getSelectedRow();
        // getAdmin(i);

        txtMaNV.setEnabled(false);
        tabNhanVien.setSelectedIndex(0);
        int index = tblNhanVien.getSelectedRow();

        txtMaNV.setText(tblNhanVien.getValueAt(index, 0).toString());
        txtTenNV.setText(tblNhanVien.getValueAt(index, 1).toString());
        cboGioiTinh.setSelectedItem(tblNhanVien.getValueAt(index, 2).toString());
        txtSDT.setText(tblNhanVien.getValueAt(index, 3).toString());
        txtEmail.setText(tblNhanVien.getValueAt(index, 4).toString());
        txtDiaChi.setText(tblNhanVien.getValueAt(index, 5).toString());
        cboChucVu.setSelectedItem(tblNhanVien.getValueAt(index, 6).toString());
        txtNgayNhap.setText(tblNhanVien.getValueAt(index, 7).toString());

    }

    public void clearForm() {

        txtEmail.setBackground(Color.white);
        txtSDT.setBackground(Color.white);

        txtMaNV.setEnabled(true);
        int index = tblNhanVien.getSelectedRow();
        txtMaNV.setText("");
        txtTenNV.setText("");
        cboGioiTinh.setSelectedItem("");
        txtSDT.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
        cboChucVu.setSelectedItem("");
        cboGioiTinh.setSelectedIndex(0);
        cboChucVu.setSelectedIndex(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        txtNgayNhap.setText(sdf.format(now) + "");

    }

    public boolean checkNhanVien() {
        String maNV = txtMaNV.getText();
        String tenNV = txtTenNV.getText();
        String sdt = txtSDT.getText();
        String email = txtEmail.getText();
        String diaChi = txtDiaChi.getText();
        String loi = "";
        if (maNV.isEmpty() || tenNV.isEmpty() || sdt.isEmpty() || email.isEmpty() || diaChi.isEmpty()) {
            loi += "Không được bỏ trống !\n";

            String sdtDinhDang = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
            if (sdt.matches(sdtDinhDang)) {
                txtSDT.setBackground(Color.white);
            } else {

                loi += "Định dạng số điện thoại không hợp lệ ! \n";
                txtSDT.setBackground(Color.orange);
                txtSDT.requestFocus();

            }

        }
        if (cboGioiTinh.getSelectedIndex() == 0) {
            loi += "Vui lòng chọn giới tính !\n";
        }
        if (cboChucVu.getSelectedIndex() == 0) {
            loi += "Vui lòng chọn chức vụ !\n";
        }

        if (loi.length() > 0) {
            MsgBox.alert(this, loi);
            return false;
        }
        return true;
    }

    public boolean checkTrung() {
        String maNV = txtMaNV.getText();
        List<NhanVien> nv = dao.selectByKeyword(maNV);
        String email = txtEmail.getText();
        String sdt = txtSDT.getText();
        String loi = "";
        for (int i = 0; i < nv.size(); i++) {

            if (maNV.equalsIgnoreCase(nv.get(i).getMaNV())) {

                loi += "Nhân viên đã tồn tại! \n";

            }
            if (email.equals(nv.get(i).getEmail())) {

                loi += "Email đã tồn tại! \n";

            }
            if (sdt.equals(nv.get(i).getSdt())) {

                loi += "Số điện thoại đã tồn tại! \n";

            }
        }
        if (loi.length() != 0) {
            MsgBox.alert(this, loi);
            return false;
        }
        return true;
    }

    public void insert() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        txtNgayNhap.setText(sdf.format(now) + "");

        NhanVien nv = this.readForm();
        dao.insert(nv);
        fillTable();
        clearForm();
        MsgBox.alert(this, "Thêm thành công !?");
    }

    public void delete() {
        int viTri = tblNhanVien.getSelectedRow();
        boolean chon = MsgBox.confirm(this, "Bạn có chắc chắn muốn xóa nhân viên này ?!"), xoa = true;
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        String maNV = tblNhanVien.getValueAt(viTri, 0).toString();
        if (chon) {
            for (int i = 0; i < listND.size(); i++) {
                if (listND.get(i).getMaND().equals(maNV)) {
                    MsgBox.alert(this, "Nhân viên này đã có tài khoản không được xóa");
                    xoa = false;
                    break;
                }

            }
            if (xoa) {
                dao.delete(maNV);
                clearForm();
                fillTable();
                MsgBox.alert(this, "Đã xóa thành công");
            }

        }

    }

    public void update() {
        NhanVien nv = this.readForm();
        dao.update(nv);
        fillTable();
        clearForm();
        MsgBox.alert(this, "Cập nhật thành công !");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTitleBar = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        pnlTablePane = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tabNhanVien = new javax.swing.JTabbedPane();
        pnlDiaChi = new javax.swing.JPanel();
        lblMaNV = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtTenNV = new javax.swing.JTextField();
        lblTenNv = new javax.swing.JLabel();
        lblGioiTinh = new javax.swing.JLabel();
        cboGioiTinh = new javax.swing.JComboBox<>();
        txtSDT = new javax.swing.JTextField();
        lblSDT = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        lblDiaChi = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        lblNgayVaoLam = new javax.swing.JLabel();
        txtNgayNhap = new javax.swing.JTextField();
        cboChucVu = new javax.swing.JComboBox<>();
        lblChucVu = new javax.swing.JLabel();
        btnLamMoi = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        pnlDanhSach = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        pnlTimKiem = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();

        pnlTitleBar.setBackground(new java.awt.Color(81, 145, 255));
        pnlTitleBar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlTitleBarMouseDragged(evt);
            }
        });
        pnlTitleBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlTitleBarMousePressed(evt);
            }
        });

        javax.swing.GroupLayout pnlTitleBarLayout = new javax.swing.GroupLayout(pnlTitleBar);
        pnlTitleBar.setLayout(pnlTitleBarLayout);
        pnlTitleBarLayout.setHorizontalGroup(
            pnlTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlTitleBarLayout.setVerticalGroup(
            pnlTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 2, 2, 2, new java.awt.Color(81, 145, 255)));

        pnlTablePane.setBackground(new java.awt.Color(255, 255, 255));
        pnlTablePane.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(255, 255, 255)));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Quản Lý Nhân Viên");

        javax.swing.GroupLayout pnlTablePaneLayout = new javax.swing.GroupLayout(pnlTablePane);
        pnlTablePane.setLayout(pnlTablePaneLayout);
        pnlTablePaneLayout.setHorizontalGroup(
            pnlTablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlTablePaneLayout.setVerticalGroup(
            pnlTablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        tabNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        tabNhanVien.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabNhanVien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tabNhanVien.setOpaque(true);

        pnlDiaChi.setBackground(new java.awt.Color(255, 255, 255));

        lblMaNV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMaNV.setText("MÃ NHÂN VIÊN :");

        txtMaNV.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMaNV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        txtTenNV.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTenNV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        lblTenNv.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTenNv.setText("TÊN NHÂN VIÊN :");

        lblGioiTinh.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblGioiTinh.setText("GIỚI TÍNH :");

        cboGioiTinh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Chọn giới tính---", "Nam", "Nữ" }));

        txtSDT.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtSDT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });

        lblSDT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSDT.setText("SỐ ĐIỆN THOẠI :");

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtEmail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        lblEmail.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEmail.setText("EMAIL :");

        lblDiaChi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDiaChi.setText("ĐỊA CHỈ :");

        txtDiaChi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtDiaChi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtDiaChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiaChiActionPerformed(evt);
            }
        });

        lblNgayVaoLam.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNgayVaoLam.setText("NGÀY VÀO LÀM :");

        txtNgayNhap.setEditable(false);
        txtNgayNhap.setBackground(new java.awt.Color(255, 255, 255));
        txtNgayNhap.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtNgayNhap.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        cboChucVu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Chọn chức vụ---", "Quản lý ", "Nhân Viên", "admin", " ", " " }));
        cboChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChucVuActionPerformed(evt);
            }
        });

        lblChucVu.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblChucVu.setText("Chức vụ :");

        btnLamMoi.setText("Làm mới");
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

        btnXoa.setBackground(new java.awt.Color(255, 0, 0));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDiaChiLayout = new javax.swing.GroupLayout(pnlDiaChi);
        pnlDiaChi.setLayout(pnlDiaChiLayout);
        pnlDiaChiLayout.setHorizontalGroup(
            pnlDiaChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDiaChiLayout.createSequentialGroup()
                .addGroup(pnlDiaChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlDiaChiLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnlDiaChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDiaChiLayout.createSequentialGroup()
                                .addGroup(pnlDiaChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtNgayNhap, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaNV, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTenNV, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDiaChi)
                                    .addComponent(txtSDT))
                                .addGap(20, 20, 20))
                            .addGroup(pnlDiaChiLayout.createSequentialGroup()
                                .addGroup(pnlDiaChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNgayVaoLam)
                                    .addComponent(lblDiaChi)
                                    .addComponent(lblChucVu)
                                    .addComponent(cboChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblEmail)
                                    .addComponent(lblMaNV)
                                    .addComponent(lblTenNv)
                                    .addComponent(lblSDT)
                                    .addGroup(pnlDiaChiLayout.createSequentialGroup()
                                        .addComponent(lblGioiTinh)
                                        .addGap(54, 54, 54)
                                        .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(pnlDiaChiLayout.createSequentialGroup()
                        .addContainerGap(450, Short.MAX_VALUE)
                        .addComponent(btnLamMoi)
                        .addGap(18, 18, 18)
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSua)
                        .addGap(14, 14, 14)
                        .addComponent(btnXoa)))
                .addContainerGap())
        );
        pnlDiaChiLayout.setVerticalGroup(
            pnlDiaChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDiaChiLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblMaNV)
                .addGap(1, 1, 1)
                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(lblTenNv)
                .addGap(1, 1, 1)
                .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDiaChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGioiTinh)
                    .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(lblSDT)
                .addGap(7, 7, 7)
                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(lblEmail)
                .addGap(1, 1, 1)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(lblDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(lblChucVu)
                .addGap(1, 1, 1)
                .addComponent(cboChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(lblNgayVaoLam)
                .addGap(1, 1, 1)
                .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(pnlDiaChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLamMoi)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa))
                .addContainerGap())
        );

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        txtNgayNhap.setText(sdf.format(now) + "");

        tabNhanVien.addTab("Cập Nhật", pnlDiaChi);

        pnlDanhSach.setBackground(new java.awt.Color(255, 255, 255));

        tblNhanVien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "Giới tính", "Số điện thoại", "Email", "Địa chỉ", "Chức vụ", "Ngày vào làm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.setRowHeight(25);
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        pnlTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        pnlTimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        txtTimKiem.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        txtTimKiem.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        txtTimKiem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTimKiemFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTimKiemFocusLost(evt);
            }
        });

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
                .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTimKiem))
        );
        pnlTimKiemLayout.setVerticalGroup(
            pnlTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTimKiemLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(pnlTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem)))
        );

        javax.swing.GroupLayout pnlDanhSachLayout = new javax.swing.GroupLayout(pnlDanhSach);
        pnlDanhSach.setLayout(pnlDanhSachLayout);
        pnlDanhSachLayout.setHorizontalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlDanhSachLayout.setVerticalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabNhanVien.addTab("Danh Sách", pnlDanhSach);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTablePane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tabNhanVien))
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnlTablePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tabNhanVien)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitleBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTitleBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void pnlTitleBarMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleBarMouseDragged

    }//GEN-LAST:event_pnlTitleBarMouseDragged

    private void pnlTitleBarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleBarMousePressed
        // TODO add your handling code hee:

    }//GEN-LAST:event_pnlTitleBarMousePressed

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDTActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtDiaChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiaChiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiaChiActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (checkTrung() && checkNhanVien()) {
            insert();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (checkNhanVien()) {
            update();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        if (evt.getClickCount() == 2) {
            writeForm();
        }

    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void txtTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusGained
        // TODO add your handling code here:
        //        if (txtTimKiem.getText().equals("Nhập để tìm kiếm ...")) {
        //            txtTimKiem.setText("");
        //        }
    }//GEN-LAST:event_txtTimKiemFocusGained

    private void txtTimKiemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusLost
        // TODO add your handling code here:
        //        if (txtTimKiem.getText().equals("")) {
        //            txtTimKiem.setText("Nhập để tìm kiếm ...");
        //        }
    }//GEN-LAST:event_txtTimKiemFocusLost

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        fillTable();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void cboChucVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChucVuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboChucVuActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboChucVu;
    private javax.swing.JComboBox<String> cboGioiTinh;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblChucVu;
    private javax.swing.JLabel lblDiaChi;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblNgayVaoLam;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblTenNv;
    private javax.swing.JPanel pnlDanhSach;
    private javax.swing.JPanel pnlDiaChi;
    private javax.swing.JPanel pnlTablePane;
    private javax.swing.JPanel pnlTimKiem;
    private javax.swing.JPanel pnlTitleBar;
    private javax.swing.JTabbedPane tabNhanVien;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNgayNhap;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
