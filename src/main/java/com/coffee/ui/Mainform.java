/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.coffee.ui;

import com.coffee.dao.NhanVienDAO;
import com.coffee.entity.NhanVien;

import com.coffee.utils.Auth;
import com.coffee.utils.MsgBox;
import com.coffee.utils.XImage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;


/**
 *
 *
 */
public class Mainform extends javax.swing.JFrame {
  
    private nhanvienform nv;
    private BanForm bf;
    private formdoimk mk;
    private LoaiSPForm lsp;
    private sanphamform spf;
    private Nguoidungform nd;
    private Calamform ca;
    private luongform luong;
    private hoadonform hd;
    private banhangform bh;
    private Hoadonctform hdct;
    private thongkeform tkf;
    // private Quanlisanpham sp;

    /**
     * Creates new form Mainform
     */
    public Mainform() {
        initComponents();
        customDesing();

        //setSize(1650, 1080);
        openDN();
        dongHo();
        setExtendedState(getExtendedState()|JFrame.MAXIMIZED_BOTH);     
        setLocationRelativeTo(null);
        setIconImage(XImage.getAppIcon());
        setTitle("Quản Lý Cà Phê ");
       //nv = new nhanvienform();
        //tabbMain.addTab("quản lí nhân viên", nv);
       // tabbMain.setSelectedComponent(nv);
        //int vt = tabbMain.getSelectedIndex();
       // tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));

    }

    public void dongHo() {
        new Timer(1000, new ActionListener() {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss-a");

            @Override
            public void actionPerformed(ActionEvent e) {
                lblDongHo2.setText(format.format(new Date()));
            }
        }).start();

    }

    void openDoiMatKhau() {
        if (Auth.isLogin()) {
            new formdoimk();
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập!");
        }

    }

    public void openDN() {
       
        new DangNhap(this, true).setVisible(true);
        
        //if (!Auth.isLogin()) {
        // lbltaikhoan.setText("Bạn chưa đăng nhập !");
        //} else if (Auth.getManager() != null && Auth.isLogin()) {

        //  lbltaikhoan.setText("Xin chào, " + Auth.getUsername());
        //  lblTaiKhoan.setText(""+Auth.getManager());
        // }
        if (!Auth.isLogin()) {
            lbltaikhoan.setText("Bạn chưa đăng nhập !");
            this.dispose();
        } else {
            if (Auth.isLogin()) {
               
                String maND = Auth.getUsername();
                NhanVienDAO daoNV = new NhanVienDAO();
                List<NhanVien> list = daoNV.selectByKeyword3(maND);
                for (NhanVien nv : list) {
                   
                    
                    lbltaikhoan.setText("Hello: " + nv.getTenNV());
                    lblchucvu.setText("" + nv.getChucVu());
                   
                    if(nv.getChucVu().equals("Quản lý ")){
                       
                        btntaikhoan.setEnabled(false);
                        btnthongke.setEnabled(false);
                        menuthongke.setEnabled(false);
                       //jPanel2.removeAll();
                         ///tabbMain.removeAll();
                    }else// if(nv.getChucVu().equals("Admin")){
                    {
                   
                        btntaikhoan.setEnabled(true);
                       btnthongke.setEnabled(true);
                       menuthongke.setEnabled(true);
                        ///tabbMain.removeAll();
                    }
                }
                 
            }
        }
        
    }
    private void customDesing() {

       // pnlhethong.setVisible(true);
        pnlquanli.setVisible(true);
        //  pnlthongke.setVisible(true);
        pnlsanpham.setVisible(true);

    }
    private void hideSubMenu(){
        if(pnlquanli.isVisible() == true){
            pnlquanli.setVisible(false);
        }
        //if(pnlhethong.isVisible() == true){
          // pnlhethong.setVisible(true);
       // }
       // if(pnlthongke.isVisible() == true){
          //  pnlthongke.setVisible(true);
       // }
        if(pnlsanpham.isVisible()==true){
            pnlsanpham.setVisible(true);
        }
    }
   
    
    private void showSubMenu(JPanel panel){
        if(panel.isVisible() == false){
            hideSubMenu();
            panel.setVisible(true);
        }else{
            panel.setVisible(false);
        }
    }

    public class DemoCustomTab extends JPanel {

        Mainform mainform;

        /**
         * JPanel contain a JLabel and a JButton to close
         */
        public DemoCustomTab(Mainform mainform) {
            this.mainform = mainform;
            //setLayout(new FlowLayout(FlowLayout.RIGHT, 0 , 0));
            // setBorder(new EmptyBorder(0, 2, 2, 2));
            setOpaque(false);

            addLabel();
            add(new CustomButton("x"));
        }

        private void addLabel() {
            JLabel label = new JLabel() {
                
                public String getText() {
                    int index = mainform.tabbMain.indexOfTabComponent(DemoCustomTab.this);
                    if (index != -1) {
                        return mainform.tabbMain.getTitleAt(index);
                    }
                    return null;
                }
            };
            /**
             * tao khoang cach giu chu va nut
             */
            label.setBorder(new EmptyBorder(0, 0, 0, 10));
            label.setFont(new Font("segoe UI", Font.BOLD, 12));
            add(label);
        }

        class CustomButton extends JButton implements MouseListener {

            public CustomButton(String text) {
                int size = 10;
                setText(text);
                /**
                 * set size for button close
                 */
                setPreferredSize(new Dimension(size, size));

                setToolTipText("Đống Tab");

                /**
                 * set transparent
                 */
                setContentAreaFilled(false);

                /**
                 * set border for button
                 */
                setBorder(new EtchedBorder());
                /**
                 * don't show border
                 */
                setBorderPainted(true);

                setFocusable(false);

                //setFont(new Font("segoe UI", Font.BOLD, 14));
                /**
                 * add event with mouse
                 */
                addMouseListener(this);

            }

            /**
             * when click button, tab will close
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = mainform.tabbMain.indexOfTabComponent(DemoCustomTab.this);
                if (index != -1) {
                    //tabbMain.removeAll();
                    mainform.tabbMain.remove(index);
                    nv = null;
                    bf = null;
                    lsp = null;
                    spf = null;
                    nd = null;
                    ca = null;
                    luong = null;
                    //kh = null;
                    mk = null;
                    hd = null;
                    bh = null;
                    hdct = null;
                    tkf = null;
//                sp = null;

                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            /**
             * show border button when mouse hover
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorderPainted(true);

                setForeground(Color.red);
            }

            /**
             * hide border when mouse not hover
             */
            @Override
            public void mouseExited(MouseEvent e) {
                setBorderPainted(false);
                setForeground(Color.BLACK);
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

        jPanel2 = new javax.swing.JPanel();
        btnhethongquanli = new javax.swing.JButton();
        pnlquanli = new javax.swing.JPanel();
        btnNhanvien = new javax.swing.JButton();
        btnquanliluong = new javax.swing.JButton();
        btncalam = new javax.swing.JButton();
        btnquanli = new javax.swing.JButton();
        pnlsanpham = new javax.swing.JPanel();
        btnLoaiSP = new javax.swing.JButton();
        btnSanpham = new javax.swing.JButton();
        SanPham = new javax.swing.JButton();
        btnban = new javax.swing.JButton();
        btnhoadon = new javax.swing.JButton();
        btnbanhang = new javax.swing.JButton();
        pnlhethong = new javax.swing.JPanel();
        btndoimk = new javax.swing.JButton();
        btntaikhoan = new javax.swing.JButton();
        btnDangXuat = new javax.swing.JButton();
        btnthongke = new javax.swing.JButton();
        btnHoadonchitiet = new javax.swing.JButton();
        tabbMain = new javax.swing.JTabbedPane();
        lblDongHo2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbltaikhoan = new javax.swing.JLabel();
        lblchucvu = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem7 = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jMenu4 = new javax.swing.JMenu();
        menuthongke = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 153, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 153, 0));
        jPanel2.setAutoscrolls(true);
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel2.setDoubleBuffered(false);
        jPanel2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        btnhethongquanli.setBackground(new java.awt.Color(255, 153, 0));
        btnhethongquanli.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnhethongquanli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/coffee/icon/home-icon.png"))); // NOI18N
        btnhethongquanli.setText("hệ Thống");
        btnhethongquanli.setToolTipText("");
        btnhethongquanli.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnhethongquanli.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnhethongquanli.setDefaultCapable(false);
        btnhethongquanli.setDisabledIcon(null);
        btnhethongquanli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnhethongquanli.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnhethongquanli.setMargin(new java.awt.Insets(2, 20, 3, 14));
        btnhethongquanli.setPreferredSize(new java.awt.Dimension(160, 35));
        btnhethongquanli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhethongquanliActionPerformed(evt);
            }
        });

        pnlquanli.setBackground(new java.awt.Color(255, 153, 0));
        pnlquanli.setForeground(new java.awt.Color(255, 255, 255));
        pnlquanli.setPreferredSize(new java.awt.Dimension(160, 100));

        btnNhanvien.setBackground(new java.awt.Color(255, 153, 0));
        btnNhanvien.setText("Quản lí nhân viên");
        btnNhanvien.setBorder(null);
        btnNhanvien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanvienActionPerformed(evt);
            }
        });

        btnquanliluong.setBackground(new java.awt.Color(255, 153, 0));
        btnquanliluong.setText("Quản lí lương");
        btnquanliluong.setBorder(null);
        btnquanliluong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnquanliluongActionPerformed(evt);
            }
        });

        btncalam.setBackground(new java.awt.Color(255, 153, 0));
        btncalam.setText("Ca làm");
        btncalam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncalamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlquanliLayout = new javax.swing.GroupLayout(pnlquanli);
        pnlquanli.setLayout(pnlquanliLayout);
        pnlquanliLayout.setHorizontalGroup(
            pnlquanliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlquanliLayout.createSequentialGroup()
                .addGroup(pnlquanliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnNhanvien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                    .addComponent(btnquanliluong, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btncalam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlquanliLayout.setVerticalGroup(
            pnlquanliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlquanliLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(btnNhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(btncalam, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(btnquanliluong, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

        btnNhanvien.getAccessibleContext().setAccessibleName("");
        btnquanliluong.getAccessibleContext().setAccessibleName("");

        btnquanli.setBackground(new java.awt.Color(255, 153, 0));
        btnquanli.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnquanli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/coffee/icon/User group.png"))); // NOI18N
        btnquanli.setText("Quản Lí ");
        btnquanli.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnquanli.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnquanli.setDefaultCapable(false);
        btnquanli.setDisabledIcon(null);
        btnquanli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnquanli.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnquanli.setPreferredSize(new java.awt.Dimension(160, 35));
        btnquanli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnquanliActionPerformed(evt);
            }
        });

        pnlsanpham.setBackground(new java.awt.Color(255, 153, 0));
        pnlsanpham.setForeground(new java.awt.Color(255, 255, 255));
        pnlsanpham.setPreferredSize(new java.awt.Dimension(160, 100));

        btnLoaiSP.setBackground(new java.awt.Color(255, 153, 0));
        btnLoaiSP.setText("Loại sản phẩm");
        btnLoaiSP.setBorder(null);
        btnLoaiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoaiSPActionPerformed(evt);
            }
        });

        btnSanpham.setBackground(new java.awt.Color(255, 153, 0));
        btnSanpham.setText("Sản Phẩm");
        btnSanpham.setBorder(null);
        btnSanpham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSanphamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlsanphamLayout = new javax.swing.GroupLayout(pnlsanpham);
        pnlsanpham.setLayout(pnlsanphamLayout);
        pnlsanphamLayout.setHorizontalGroup(
            pnlsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlsanphamLayout.createSequentialGroup()
                .addGroup(pnlsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSanpham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLoaiSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(1, 1, 1))
        );
        pnlsanphamLayout.setVerticalGroup(
            pnlsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlsanphamLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(btnLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(btnSanpham, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
        );

        SanPham.setBackground(new java.awt.Color(255, 153, 0));
        SanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        SanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/coffee/icon/Box.png"))); // NOI18N
        SanPham.setText("SanPham");
        SanPham.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        SanPham.setDefaultCapable(false);
        SanPham.setDisabledIcon(null);
        SanPham.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SanPham.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        SanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SanPhamActionPerformed(evt);
            }
        });

        btnban.setBackground(new java.awt.Color(255, 153, 0));
        btnban.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnban.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/coffee/icon/icons8_table_45px.png"))); // NOI18N
        btnban.setText("Bàn");
        btnban.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnban.setDefaultCapable(false);
        btnban.setDisabledIcon(null);
        btnban.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnban.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbanActionPerformed(evt);
            }
        });

        btnhoadon.setBackground(new java.awt.Color(255, 153, 0));
        btnhoadon.setText("Hóa Đơn");
        btnhoadon.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnhoadon.setDefaultCapable(false);
        btnhoadon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhoadonActionPerformed(evt);
            }
        });

        btnbanhang.setBackground(new java.awt.Color(255, 153, 0));
        btnbanhang.setText("Bán Hàng");
        btnbanhang.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnbanhang.setDefaultCapable(false);
        btnbanhang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbanhangActionPerformed(evt);
            }
        });

        pnlhethong.setBackground(new java.awt.Color(255, 153, 0));

        btndoimk.setBackground(new java.awt.Color(255, 153, 0));
        btndoimk.setText("Đổi mật khẩu");
        btndoimk.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        btndoimk.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btndoimk.setDefaultCapable(false);
        btndoimk.setName(""); // NOI18N
        btndoimk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndoimkActionPerformed(evt);
            }
        });

        btntaikhoan.setBackground(new java.awt.Color(255, 153, 0));
        btntaikhoan.setText("Quản lí tài khoản");
        btntaikhoan.setBorder(null);
        btntaikhoan.setBorderPainted(false);
        btntaikhoan.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btntaikhoan.setDefaultCapable(false);
        btntaikhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntaikhoanActionPerformed(evt);
            }
        });

        btnDangXuat.setBackground(new java.awt.Color(255, 153, 0));
        btnDangXuat.setText("Đăng xuất");
        btnDangXuat.setBorder(null);
        btnDangXuat.setBorderPainted(false);
        btnDangXuat.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDangXuat.setDefaultCapable(false);
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlhethongLayout = new javax.swing.GroupLayout(pnlhethong);
        pnlhethong.setLayout(pnlhethongLayout);
        pnlhethongLayout.setHorizontalGroup(
            pnlhethongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btndoimk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btntaikhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDangXuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlhethongLayout.setVerticalGroup(
            pnlhethongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlhethongLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(btndoimk, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(btntaikhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btndoimk.getAccessibleContext().setAccessibleName("");
        btntaikhoan.getAccessibleContext().setAccessibleName("");
        btnDangXuat.getAccessibleContext().setAccessibleName("");

        btnthongke.setBackground(new java.awt.Color(255, 153, 0));
        btnthongke.setText("Tổng hợp thống kê");
        btnthongke.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthongkeActionPerformed(evt);
            }
        });

        btnHoadonchitiet.setBackground(new java.awt.Color(255, 153, 0));
        btnHoadonchitiet.setText("Hóa Đơn Chi Tiết");
        btnHoadonchitiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoadonchitietActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnthongke, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnbanhang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlhethong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnhethongquanli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnquanli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnHoadonchitiet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(pnlquanli, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnban, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnhoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlsanpham, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                    .addComponent(SanPham, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(1, 1, 1))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(btnhethongquanli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(pnlhethong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnquanli, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(pnlquanli, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(btnban, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(SanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(pnlsanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(btnhoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(btnbanhang, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(btnHoadonchitiet, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(btnthongke, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
        );

        tabbMain.setBackground(new java.awt.Color(255, 255, 255));
        tabbMain.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblDongHo2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblDongHo2.setForeground(new java.awt.Color(255, 51, 51));
        lblDongHo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/coffee/icon/DongHo.png"))); // NOI18N
        lblDongHo2.setText("00:00:00");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 51, 51));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/coffee/icon/User.png"))); // NOI18N
        jLabel11.setText("Hệ Thống quản lí cafe");

        lbltaikhoan.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lbltaikhoan.setText("Xin chào");

        lblchucvu.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblchucvu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/coffee/icon/Unknown person.png"))); // NOI18N
        lblchucvu.setText("Chức vụ");
        lblchucvu.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jMenuBar1.setBackground(new java.awt.Color(255, 51, 51));

        jMenu2.setText("Hệ Thống");
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/coffee/icon/Log out.png"))); // NOI18N
        jMenuItem2.setText("Đăng xuất");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);
        jMenu2.add(jSeparator1);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/coffee/icon/Refresh.png"))); // NOI18N
        jMenuItem1.setText("Đổi mật khẩu");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndoimkActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);
        jMenu2.add(jSeparator6);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Quản lí");

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/coffee/icon/Users.png"))); // NOI18N
        jMenuItem3.setText("Quản lí nhân viên");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanvienActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);
        jMenu3.add(jSeparator2);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/coffee/icon/Clien list.png"))); // NOI18N
        jMenuItem4.setText("Ca làm");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncalamActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);
        jMenu3.add(jSeparator3);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/coffee/icon/Coin.png"))); // NOI18N
        jMenuItem5.setText("Lương nhân viên");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnquanliluongActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);
        jMenu3.add(jSeparator4);

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/coffee/icon/Remove from basket.png"))); // NOI18N
        jMenuItem6.setText("Quản lí bán hàng");
        jMenu3.add(jMenuItem6);
        jMenu3.add(jSeparator5);

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/coffee/icon/Full basket.png"))); // NOI18N
        jMenuItem7.setText("Quản lí sản phẩm");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSanphamActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);
        jMenu3.add(jSeparator7);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Thống kê");

        menuthongke.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/coffee/icon/Bar chart.png"))); // NOI18N
        menuthongke.setText("Tổng hợp thống kê");
        menuthongke.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthongkeActionPerformed(evt);
            }
        });
        jMenu4.add(menuthongke);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabbMain)
                .addGap(1, 1, 1)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(147, 147, 147)
                .addComponent(lbltaikhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(lblchucvu, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 138, Short.MAX_VALUE)
                .addComponent(lblDongHo2, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(tabbMain, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbltaikhoan, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                            .addComponent(lblchucvu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDongHo2)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btndoimkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndoimkActionPerformed
       if (Auth.isLogin()) {
            if (mk == null) {
            try {
                mk = new formdoimk();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Quản lí đổi mật khẩu", mk);
        }
        tabbMain.setSelectedComponent(mk);
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập!");
        }
        

    }//GEN-LAST:event_btndoimkActionPerformed

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        openDN();
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void btnbanhangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbanhangActionPerformed
         if (Auth.isLogin()) {
             if (bh == null) {
            try {
                bh = new banhangform();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Quản lí bán hàng", bh);
        }
        tabbMain.setSelectedComponent(bh);
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập!");
        }
       
        
    }//GEN-LAST:event_btnbanhangActionPerformed

    private void btnhoadonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhoadonActionPerformed
         if (Auth.isLogin()) {
             if (hd == null) {
            try {
                hd = new hoadonform();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Quản lí hóa đơn", hd);
        }
        tabbMain.setSelectedComponent(hd);
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
           
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập!");
        }

        
    }//GEN-LAST:event_btnhoadonActionPerformed

    private void btnbanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbanActionPerformed
         if (Auth.isLogin()) {
             if (bf == null) {
            try {
                bf = new BanForm();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Quản lí bàn", bf);
        }
        tabbMain.setSelectedComponent(bf);
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập!");
        }
       
    }//GEN-LAST:event_btnbanActionPerformed

    private void SanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SanPhamActionPerformed
        showSubMenu(pnlsanpham);
    }//GEN-LAST:event_SanPhamActionPerformed

    private void btnSanphamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSanphamActionPerformed
         if (Auth.isLogin()) {
             if (spf == null) {
            try {
                spf = new sanphamform();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Quản lí sản phẩm", spf);
        }
        tabbMain.setSelectedComponent(spf);
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập!");
        }
       
    }//GEN-LAST:event_btnSanphamActionPerformed

    private void btnLoaiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoaiSPActionPerformed
        if (Auth.isLogin()) {
            if (lsp == null) {
            try {
                lsp = new LoaiSPForm();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Quản lí loại sản phẩm", lsp);
        }
        tabbMain.setSelectedComponent(lsp);
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập!");
        }
        
    }//GEN-LAST:event_btnLoaiSPActionPerformed

    private void btntaikhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntaikhoanActionPerformed
         if (Auth.isLogin()) {
             if (nd == null) {
            try {
                nd = new Nguoidungform();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Quản lí người dùng", nd);
        }
        tabbMain.setSelectedComponent(nd);
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập!");
        }
       
    }//GEN-LAST:event_btntaikhoanActionPerformed

    private void btnquanliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnquanliActionPerformed
      showSubMenu(pnlquanli);
    }//GEN-LAST:event_btnquanliActionPerformed

    private void btncalamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncalamActionPerformed
         if (Auth.isLogin()) {
             if (ca == null) {
            try {
                ca = new Calamform();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Quản lí ca làm", ca);
        }
        tabbMain.setSelectedComponent(ca);
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập!");
        }
       
    }//GEN-LAST:event_btncalamActionPerformed

    private void btnquanliluongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnquanliluongActionPerformed
         if (Auth.isLogin()) {
             if (luong == null) {
            try {
                luong = new luongform();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Quản lí luong", luong);
        }
        tabbMain.setSelectedComponent(luong);
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập!");
        }
       
    }//GEN-LAST:event_btnquanliluongActionPerformed

    private void btnNhanvienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanvienActionPerformed
         if (Auth.isLogin()) {
            if (nv == null) {
            try {
                nv = new nhanvienform();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Quản lí nhân viên", nv);
        }
        tabbMain.setSelectedComponent(nv);
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập!");
        }
        
    }//GEN-LAST:event_btnNhanvienActionPerformed

    private void btnhethongquanliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhethongquanliActionPerformed
        //showSubMenu(pnlhethong);
    }//GEN-LAST:event_btnhethongquanliActionPerformed

    private void btnthongkeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthongkeActionPerformed
        if (Auth.isLogin()) {
            if (tkf == null) {
            try {
                tkf = new thongkeform();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Quản lí thống kê", tkf);
        }
        tabbMain.setSelectedComponent(tkf);
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
            
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập!");
        }
        
    }//GEN-LAST:event_btnthongkeActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    private void btnHoadonchitietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoadonchitietActionPerformed
         if (Auth.isLogin()) {
            if (hdct == null) {
            try {
                hdct = new Hoadonctform();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Quản lí hóa đơn chi tiết", hdct);
        }
        tabbMain.setSelectedComponent(hdct);
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập!");
        }
        
    }//GEN-LAST:event_btnHoadonchitietActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Mainform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Mainform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Mainform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Mainform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Mainform().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SanPham;
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnHoadonchitiet;
    private javax.swing.JButton btnLoaiSP;
    private javax.swing.JButton btnNhanvien;
    private javax.swing.JButton btnSanpham;
    private javax.swing.JButton btnban;
    private javax.swing.JButton btnbanhang;
    private javax.swing.JButton btncalam;
    private javax.swing.JButton btndoimk;
    private javax.swing.JButton btnhethongquanli;
    private javax.swing.JButton btnhoadon;
    private javax.swing.JButton btnquanli;
    private javax.swing.JButton btnquanliluong;
    private javax.swing.JButton btntaikhoan;
    private javax.swing.JButton btnthongke;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JLabel lblDongHo2;
    private javax.swing.JLabel lblchucvu;
    private javax.swing.JLabel lbltaikhoan;
    private javax.swing.JMenuItem menuthongke;
    private javax.swing.JPanel pnlhethong;
    private javax.swing.JPanel pnlquanli;
    private javax.swing.JPanel pnlsanpham;
    private javax.swing.JTabbedPane tabbMain;
    // End of variables declaration//GEN-END:variables
}
