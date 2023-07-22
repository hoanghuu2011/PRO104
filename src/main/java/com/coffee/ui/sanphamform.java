/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.coffee.ui;

import com.coffee.dao.HoaDonCTDAO;
import com.coffee.dao.LoaiSanPhamDAO;
import com.coffee.dao.SanPhamDAO;
import com.coffee.entity.HoaDonCT;
import com.coffee.entity.LoaiSanPham;
import com.coffee.entity.SanPham;
import com.coffee.utils.MsgBox;
import com.coffee.utils.XDate;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.plaf.synth.SynthTableHeaderUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * 
 */
public class sanphamform extends javax.swing.JPanel {

    /**
     * Creates new form sanphamform
     */
    public sanphamform() {
       
        initComponents();
        fillTable();
         txtNgayNhap.setText(sdf.format(now) + "");
        viTri = -1;
        fillCombobox();
        
    }
     SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    SanPhamDAO sanPhamDao = new SanPhamDAO();
    List<SanPham> list = sanPhamDao.selectAll();
    LoaiSanPhamDAO loaiDao = new LoaiSanPhamDAO();
    List<LoaiSanPham> listLoai = loaiDao.selectAll();
    HoaDonCTDAO daoHDCT = new HoaDonCTDAO();
    List<HoaDonCT> listHDCT = daoHDCT.selectAll();
    
    Date now = new Date();
    int viTri;
    

   /* public String anh() {
        JFileChooser chooser = new JFileChooser();
        if (TruocDo != null) {
            chooser.setCurrentDirectory(new File(TruocDo));//Set thư mục đã chọn trc đó
        } else {
            String userDir = System.getProperty("user.home");// Lấy tên PC
            chooser = new JFileChooser(userDir + "/Downloads");

        }
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        if (f != null) {
            InputStream is = null;
            OutputStream os = null;
            try {
                is = new FileInputStream(new File(f.getPath()));
                os = new FileOutputStream(new File("src\\Image\\" + f.getName()));
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SanPhamJFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SanPhamJFrame.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    is.close();
                    os.close();
                } catch (IOException ex) {
                    Logger.getLogger(SanPhamJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (f != null) {
            TruocDo = f.getPath();
            int cuoi = TruocDo.lastIndexOf("\\");
            TruocDo = TruocDo.substring(0, cuoi);
            ImageIcon hinhAnh = new ImageIcon(new ImageIcon("src\\Image\\" + f.getName()).getImage().getScaledInstance(lblHinhAnh.getWidth(), lblHinhAnh.getHeight(), Image.SCALE_SMOOTH));
            lblHinhAnh.setIcon(hinhAnh);
            duongDanAnh = f.getName();
            txtHinhAnh.setText(duongDanAnh);
        }
        return TruocDo;
    }
    /*void Anh() {
        if (jFileChooser1.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser1.getSelectedFile();
            XImage.save(file);
            ImageIcon icon = new ImageIcon(XImage.read("./logos/" + file.getName(),197, 220));
            lblHinhAnh.setIcon(icon);
            lblHinhAnh.setToolTipText(file.getName());
        }
    }*/

   

    
    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        list = sanPhamDao.selectByKeyword(txtTimKiem.getText());
        String trangThai = "";
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isTrangThai()) {
                trangThai = "Kinh Doanh";
            } else {
                trangThai = "Ngừng Kinh Doanh";
            }
            Object[] rows = new Object[]{list.get(i).getMaSP(), list.get(i).getTenSP(), list.get(i).getLoaiSP(),
                list.get(i).getSoLuong(), list.get(i).getGiaNhap(), list.get(i).getGiaBan(), list.get(i).getNgayNhap(),
                list.get(i).getMoTa(), trangThai
            };
            model.addRow(rows);
        }
    }

    public void fillCombobox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboLoai.getModel();
        model.removeAllElements();
        listLoai = loaiDao.selectAll();
        model.addElement("---Chọn Loại---");
        for (int i = 0; i < listLoai.size(); i++) {
            model.addElement(listLoai.get(i).getTenLoai());
        }

    }

    SanPham readForm() {
        SanPham sanPham = new SanPham();
        sanPham.setMaSP(txtMaSP.getText());
        sanPham.setTenSP(txtTenSP.getText());
        for (int i = 0; i < listLoai.size(); i++) {
            if (cboLoai.getSelectedItem().equals(listLoai.get(i).getTenLoai())) {
                sanPham.setLoaiSP(listLoai.get(i).getMaLoai());
                break;
            }
        }
        sanPham.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        sanPham.setGiaNhap(Double.parseDouble(txtGiaNhap.getText()));
        sanPham.setGiaBan(Double.parseDouble(txtGiaBan.getText()));
        sanPham.setNgayNhap(XDate.toString(XDate.toDate(txtNgayNhap.getText(), "dd-MM-yyyy"), "yyyy-MM-dd"));
        sanPham.setMoTa(txtMoTa.getText());
//       sanPham.setHinhAnh(txtHinhAnh.getText());
        if (btnThem.getBackground() != Color.LIGHT_GRAY) {
            sanPham.setTrangThai(true);
        } else {
            if (cboTrangThai.getSelectedIndex() == 0) {
                sanPham.setTrangThai(true);
            } else {
                sanPham.setTrangThai(false);
            }
        }
        return sanPham;
    }

    public void writeForm(SanPham sanPham) {
        txtMaSP.setText(sanPham.getMaSP());
        txtTenSP.setText(sanPham.getTenSP());
        for (int i = 0; i < listLoai.size(); i++) {
            if (sanPham.getLoaiSP().equals(listLoai.get(i).getMaLoai())) {
                cboLoai.setSelectedItem(listLoai.get(i).getTenLoai());
                break;
            }
        }
        txtGiaNhap.setText(sanPham.getGiaNhap() + "");
        txtGiaBan.setText(sanPham.getGiaBan() + "");
        txtSoLuong.setText(sanPham.getSoLuong() + "");
        txtMoTa.setText(sanPham.getMoTa());
//       txtHinhAnh.setText(sanPham.getHinhAnh());
       // ImageIcon hinhAnh = new ImageIcon(new ImageIcon("src\\Image\\" + sanPham.getHinhAnh()).getImage().getScaledInstance(244, 316, Image.SCALE_SMOOTH));
      //  lblHinhAnh.setIcon(hinhAnh);
        txtNgayNhap.setText(sanPham.getNgayNhap());
        if (sanPham.isTrangThai()) {
            cboTrangThai.setSelectedIndex(0);
        } else {
            cboTrangThai.setSelectedIndex(1);
        }
    }

    public void clear() {
        SanPham sanPham = new SanPham();
        txtMaSP.setText("");
        txtGiaBan.setText("");
        txtTenSP.setText("");
        txtGiaNhap.setText("");
        txtSoLuong.setText("");
        cboLoai.setSelectedIndex(0);
       // lblHinhAnh.setIcon(null);
        txtNgayNhap.setText(sdf.format(now) + "");
        cboTrangThai.setSelectedIndex(0);
//        pnlTrangThai.setVisible(false);
        viTri = -1;
        txtMoTa.setText("");
//      txtHinhAnh.setText("");
//        buttonSh();
        txtMaSP.setEditable(true);
    }

    public void insert() {
        SanPham sanPham = this.readForm();
        sanPhamDao.insert(sanPham);
        MsgBox.alert(this, "Thêm thành công");
        fillTable();
        clear();
    }

    public void update() {
        SanPham sanPham = this.readForm();
        sanPhamDao.update(sanPham);
        MsgBox.alert(this, "Sửa thành công");
        fillTable();
        viTri = -1;
//        buttonSh();
        clear();
    }

    public void showTable() {
        viTri = tblSanPham.getSelectedRow();
        SanPham sanPham = list.get(viTri);
        writeForm(sanPham);
        tabSanPham.setSelectedIndex(0);

        txtMaSP.setEditable(false);
    }

    public void remove() {
        viTri = tblSanPham.getSelectedRow();
        boolean chon = MsgBox.confirm(this, "Bạn có chắc chắn xóa bàn này"), xoa = true;
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        String maSanPham = txtMaSP.getText();
        if (chon) {
            for (int i = 0; i < listHDCT.size(); i++) {
                if (listHDCT.get(i).getMaSP().equals(maSanPham)) {
                    MsgBox.alert(this, "Sản phẩm này không được xóa!!!");
                    xoa = false;
                    break;
                }
            }
            if (xoa) {
                sanPhamDao.delete(maSanPham);
                MsgBox.alert(this, "Đã xóa thành công");
                fillTable();
                clear();
            }

        } else {
            MsgBox.alert(this, "Chưa được xóa");
        }
        viTri = -1;
    }

    public boolean validateSanPham() {
        String maSanPham = txtMaSP.getText();
        String tenSanPham = txtTenSP.getText();
        String soLuong = txtSoLuong.getText();
        String giaNhap = txtGiaNhap.getText();
        String giaBan = txtGiaBan.getText();
        String loi = "";
        if (btnThem.getBackground() != Color.LIGHT_GRAY) {
            if (maSanPham.equals("")) {
                loi += "Vui lòng nhập mã sản phẩm\n";
            } else {
                for (int i = 0; i < list.size(); i++) {
                    if (maSanPham.equals(list.get(i).getMaSP())) {
                        loi += "Mã đã tồn tại\n";
                        break;
                    }
                }
            }
        }
        if (tenSanPham.equals("")) {
            loi += "Vui lòng nhập tên sản phẩm\n";
        }
        try {
            int so = Integer.parseInt(soLuong);
            if (so <= 0) {
                loi += "Số lượng phải lớn hơn 0\n";

            } else if (so > 2147483647) {
                loi += "Số lượng quá lớn \n";
            }

        } catch (NumberFormatException e) {
            loi += "Vui lòng nhập số lượng là số\n";
        }
        try {
            Double gia = Double.parseDouble(giaNhap);
            if (gia <= 0) {
                loi += "Giá nhập phải lớn hơn 0\n";

            }

        } catch (NumberFormatException e) {
            loi += "Vui lòng nhập giá nhập là số\n";
        }
        try {
            Double gia = Double.parseDouble(giaBan);
            if (gia <= 0) {
                loi += "Giá bán phải lớn hơn 0\n";

            }

        } catch (NumberFormatException e) {
            loi += "Vui lòng nhập giá bán là số\n";
        }
        if (cboLoai.getSelectedIndex() == 0) {
            loi += "Vui lòng chọn loại\n";
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
        pnlTablePane = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tabSanPham = new javax.swing.JTabbedPane();
        pnlCapNhat = new javax.swing.JPanel();
        lblMaSP = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        txtTenSP = new javax.swing.JTextField();
        lblTenSP = new javax.swing.JLabel();
        lblGiaBan = new javax.swing.JLabel();
        cboLoai = new javax.swing.JComboBox<>();
        lblLoai = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        lblSoLuong = new javax.swing.JLabel();
        txtGiaBan = new javax.swing.JTextField();
        lblGiaNhap = new javax.swing.JLabel();
        txtGiaNhap = new javax.swing.JTextField();
        lblNgayNhap = new javax.swing.JLabel();
        lblMoTa = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        txtNgayNhap = new javax.swing.JTextField();
        btnReNew = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        lblTrangThai = new javax.swing.JLabel();
        cboTrangThai = new javax.swing.JComboBox<>();
        pnlDanhSach = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        pnlTimKiem = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimkiem = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 2, 2, 2, new java.awt.Color(204, 255, 255)));

        pnlTablePane.setBackground(new java.awt.Color(255, 255, 255));
        pnlTablePane.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(255, 255, 255)));

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Quản Lí Sản Phẩm");

        tabSanPham.setBackground(new java.awt.Color(255, 255, 255));
        tabSanPham.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabSanPham.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tabSanPham.setOpaque(true);
        tabSanPham.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabSanPhamStateChanged(evt);
            }
        });

        pnlCapNhat.setBackground(new java.awt.Color(255, 255, 255));

        lblMaSP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMaSP.setText("MÃ SẢN PHẨM:");

        txtMaSP.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMaSP.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 0, 0)));

        txtTenSP.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTenSP.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 0, 0)));

        lblTenSP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTenSP.setText("TÊN SẢN PHẨM :");

        lblGiaBan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblGiaBan.setText("GIÁ BÁN :");

        cboLoai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Chọn Lọai---", " ", " " }));
        cboLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiActionPerformed(evt);
            }
        });

        lblLoai.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblLoai.setText("LOẠI :");

        txtSoLuong.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtSoLuong.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 0, 0)));
        txtSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongActionPerformed(evt);
            }
        });

        lblSoLuong.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSoLuong.setText("SỐ LƯỢNG :");

        txtGiaBan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtGiaBan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 0, 0)));

        lblGiaNhap.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblGiaNhap.setText("GIÁ NHẬP :");

        txtGiaNhap.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtGiaNhap.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 0, 0)));
        txtGiaNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaNhapActionPerformed(evt);
            }
        });

        lblNgayNhap.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNgayNhap.setText("NGÀY NHẬP :");

        lblMoTa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMoTa.setText("MÔ TẢ:");

        txtMoTa.setColumns(20);
        txtMoTa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMoTa.setLineWrap(true);
        txtMoTa.setRows(5);
        txtMoTa.setWrapStyleWord(true);
        txtMoTa.setBorder(null);
        jScrollPane2.setViewportView(txtMoTa);

        txtNgayNhap.setEditable(false);
        txtNgayNhap.setBackground(new java.awt.Color(255, 255, 255));
        txtNgayNhap.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtNgayNhap.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 0, 0)));
        txtNgayNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayNhapActionPerformed(evt);
            }
        });

        btnReNew.setText("Mới");
        btnReNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReNewActionPerformed(evt);
            }
        });

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 51, 255));
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

        lblTrangThai.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTrangThai.setText("TRẠNG THÁI:");

        cboTrangThai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kinh Doanh", "Ngừng Khinh Doanh" }));
        cboTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCapNhatLayout = new javax.swing.GroupLayout(pnlCapNhat);
        pnlCapNhat.setLayout(pnlCapNhatLayout);
        pnlCapNhatLayout.setHorizontalGroup(
            pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                .addComponent(lblNgayNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1))
            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addComponent(lblLoai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblMoTa))
                .addGap(109, 109, 109)
                .addComponent(lblTrangThai)
                .addGap(12, 12, 12)
                .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addComponent(lblSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(846, 846, 846))
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addComponent(lblMaSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(818, 818, 818))
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addComponent(lblGiaNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(249, 249, 249))
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addComponent(lblGiaBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(249, 249, 249))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCapNhatLayout.createSequentialGroup()
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNgayNhap, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtGiaNhap)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtGiaBan, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(1, 1, 1))
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 815, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 806, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btnReNew)
                                .addGap(18, 18, 18)
                                .addComponent(btnThem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSua)
                                .addGap(18, 18, 18)
                                .addComponent(btnXoa)))
                        .addGap(1, 1, 1))))
        );
        pnlCapNhatLayout.setVerticalGroup(
            pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMaSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTenSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSoLuong)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(lblGiaNhap)
                .addGap(1, 1, 1)
                .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(lblGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(lblNgayNhap)
                .addGap(1, 1, 1)
                .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLoai)
                            .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTrangThai))))
                .addGap(1, 1, 1)
                .addComponent(lblMoTa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoa)
                    .addComponent(btnSua)
                    .addComponent(btnThem)
                    .addComponent(btnReNew)))
        );

        tabSanPham.addTab("Cập Nhật", pnlCapNhat);

        pnlDanhSach.setBackground(new java.awt.Color(255, 255, 255));

        tblSanPham.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Tên loại ", "Số lượng", "Giá nhập", "Giá Bán", "Ngày nhập", "Mô tả", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.setFocusable(false);
        tblSanPham.setRowHeight(25);
        tblSanPham.setSelectionBackground(new java.awt.Color(0, 102, 215));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        pnlTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        pnlTimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        txtTimKiem.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        txtTimKiem.setText("Nhập để tìm kiếm ...");
        txtTimKiem.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        txtTimKiem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTimKiemFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTimKiemFocusLost(evt);
            }
        });
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        btnTimkiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/coffee/icon/Search.png"))); // NOI18N
        btnTimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimkiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTimKiemLayout = new javax.swing.GroupLayout(pnlTimKiem);
        pnlTimKiem.setLayout(pnlTimKiemLayout);
        pnlTimKiemLayout.setHorizontalGroup(
            pnlTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTimKiemLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(txtTimKiem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTimkiem)
                .addContainerGap())
        );
        pnlTimKiemLayout.setVerticalGroup(
            pnlTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTimKiemLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(pnlTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimkiem)))
        );

        javax.swing.GroupLayout pnlDanhSachLayout = new javax.swing.GroupLayout(pnlDanhSach);
        pnlDanhSach.setLayout(pnlDanhSachLayout);
        pnlDanhSachLayout.setHorizontalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1224, Short.MAX_VALUE)
        );
        pnlDanhSachLayout.setVerticalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE))
        );

        tabSanPham.addTab("Danh Sách", pnlDanhSach);

        javax.swing.GroupLayout pnlTablePaneLayout = new javax.swing.GroupLayout(pnlTablePane);
        pnlTablePane.setLayout(pnlTablePaneLayout);
        pnlTablePaneLayout.setHorizontalGroup(
            pnlTablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTablePaneLayout.createSequentialGroup()
                .addComponent(tabSanPham)
                .addGap(1, 1, 1))
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlTablePaneLayout.setVerticalGroup(
            pnlTablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTablePaneLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(tabSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTablePane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTablePane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLoaiActionPerformed

    private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongActionPerformed

    private void txtGiaNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaNhapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaNhapActionPerformed

    private void txtNgayNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayNhapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayNhapActionPerformed

    private void cboTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTrangThaiActionPerformed

    private void btnReNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReNewActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnReNewActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (btnThem.getBackground() != Color.LIGHT_GRAY) {
            if (validateSanPham()) {
                insert();
                fillTable();
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed

        update();

    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        if (btnXoa.getBackground() != Color.LIGHT_GRAY) {
            remove();
        } else {

        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        showTable();
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void txtTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusGained
        // TODO add your handling code here:
        if (txtTimKiem.getText().equals("Nhập để tìm kiếm ...")) {
            txtTimKiem.setText("");
        }
    }//GEN-LAST:event_txtTimKiemFocusGained

    private void txtTimKiemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusLost
        // TODO add your handling code here:
        if (txtTimKiem.getText().equals("")) {
            txtTimKiem.setText("Nhập để tìm kiếm ...");
        }
    }//GEN-LAST:event_txtTimKiemFocusLost

    private void tabSanPhamStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabSanPhamStateChanged
        // TODO add your handling code here:
        txtTimKiem.setText("");
        fillTable();
    }//GEN-LAST:event_tabSanPhamStateChanged

    private void btnTimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimkiemActionPerformed
       fillTable();
    }//GEN-LAST:event_btnTimkiemActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReNew;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimkiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboLoai;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblGiaBan;
    private javax.swing.JLabel lblGiaNhap;
    private javax.swing.JLabel lblLoai;
    private javax.swing.JLabel lblMaSP;
    private javax.swing.JLabel lblMoTa;
    private javax.swing.JLabel lblNgayNhap;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblTenSP;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JPanel pnlCapNhat;
    private javax.swing.JPanel pnlDanhSach;
    private javax.swing.JPanel pnlTablePane;
    private javax.swing.JPanel pnlTimKiem;
    private javax.swing.JTabbedPane tabSanPham;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtNgayNhap;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
