/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.coffee.ui;

import com.coffee.dao.BanDAO;
import com.coffee.dao.HoaDonCTDAO;
import com.coffee.dao.HoaDonDAO;
import com.coffee.dao.NhanVienDAO;
import com.coffee.dao.SanPhamDAO;
import com.coffee.entity.Ban;
import com.coffee.entity.HoaDon;
import com.coffee.entity.HoaDonCT;
import com.coffee.entity.NhanVien;
import com.coffee.entity.SanPham;
import com.coffee.utils.Auth;
import com.coffee.utils.MsgBox;
import com.coffee.utils.XDate;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HOANG HUU
 */
public class banhangform extends javax.swing.JPanel {

    /**
     * Creates new form banhangform
     */
    public banhangform() {
        initComponents();
         fillComboBoxBan();
        getNguoiDung();
        fillComboBoxSanPham();
        fillTableDanhSachSP();
        fillSanPham();
        setTongTien();
    }
      
    int hdCheck = 0;
    String maHDCT = "";
    String maHD = "";
    BanDAO daoBan = new BanDAO();
    SanPhamDAO daoSP = new SanPhamDAO();
    HoaDonDAO daoHD = new HoaDonDAO();
    HoaDonCTDAO daoHDCT = new HoaDonCTDAO();
    SanPham sanPham;

   

    public void eventButtonEnable(JButton button) {
        button.setEnabled(false);
        button.setBackground(Color.GRAY);
    }

    public void eventButton(JButton button, Color color) {
        button.setEnabled(true);
        button.setBackground(color);
    }

    public void check() {
        int row = tblSanPham.getRowCount();
        int viTri = tblSanPham.getSelectedRow();
        if (row == 0) {
            eventButtonEnable(btnThanhToan);
            eventButtonEnable(btnHuy);
           

          
        } else {
            eventButton(btnThanhToan, new Color(255,153,0));
            eventButton(btnHuy, new Color(255, 51, 51));
          

        }
        if (viTri < 0) {
            eventButtonEnable(btnXoa);
            eventButtonEnable(btnCapNhat);

          
        }
        if (txtTrangThaiHD.getText().equals("Chưa tạo hóa đơn")) {
            eventButtonEnable(btnThem);
            eventButtonEnable(btnXoa);
            eventButtonEnable(btnCapNhat);

            
            txtSoLuong.setEnabled(false);
            txtGiamGia.setEnabled(false);

        } else {
            eventButton(btnThem, new Color(255, 0, 0));
         
            txtSoLuong.setEnabled(true);
            txtGiamGia.setEnabled(true);
        }

    }

    public void checkSanPham() {
        int row = tblSanPham.getRowCount();
        int so = 0;
        if (cboSanPham.getSelectedItem() != null) {
            SanPham sanPham = (SanPham) cboSanPham.getSelectedItem();
            SanPham list = daoSP.selectById(sanPham.getMaSP());
            List<String> sp = new ArrayList<>();
            for (int i = 0; i < row; i++) {
                String maSP = tblSanPham.getValueAt(i, 0).toString();
                String soLuong = tblSanPham.getValueAt(0, 3).toString();
                sp.add(maSP);
                if (String.valueOf(list.getMaSP()).equals(sp.get(i))) {
                    so = 1;
                    String giamGia = String.valueOf(tblSanPham.getValueAt(i, 4));
                    String giamGia2 = giamGia.substring(0, giamGia.indexOf(" VND"));

                    txtGiamGia.setText(giamGia2);
                    String thanhTien = String.valueOf(tblSanPham.getValueAt(i, 5));
                    String thanhTien2 = thanhTien.substring(0, thanhTien.indexOf(" VND"));
                    txtThanhTien.setText(thanhTien2);
                    txtSoLuong.setText(soLuong);
                    tblSanPham.setRowSelectionInterval(i, i);
                }

            }

        }
        if (so == 1) {
            eventButtonEnable(btnThem);
         

            eventButton(btnXoa, new Color(0, 204, 52));
        
            eventButton(btnCapNhat, new Color(0, 102, 204));
          
        } else {
            if (!txtTrangThaiHD.getText().equals("Chưa tạo hóa đơn")) {
                eventButton(btnThem, new Color(255, 0, 0));
             
                eventButtonEnable(btnXoa);
                eventButtonEnable(btnCapNhat);

               
            }

        }

    }

    public void fillComboBoxBan() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboMaBan.getModel();
        model.removeAllElements();
        List<Ban> list = daoBan.selectAll();
        for (Ban ban : list) {
            model.addElement(ban);
        }
        fillBan();

    }

    public void fillBan() {
        if (cboMaBan.getSelectedItem() != null) {

            Ban ban = (Ban) cboMaBan.getSelectedItem();
            Ban list = daoBan.selectById(String.valueOf(ban.getMaBan()));
            if (list.isTrangThai() == true) {
                cboTrangThai.setSelectedItem("Có khách");
                cboTrangThai.setEnabled(false);
                fillHDBan();
                hdCheck = 0;
            } else {
                cboTrangThai.setSelectedItem("Trống");
                cboTrangThai.setEnabled(true);
                txtMaHD.setText("");
            }
            if (!cboTrangThai.getSelectedItem().equals("Có khách")) {
                fillHoaDon();
                hdCheck = 1;
            }
        }

    }

    public void updateBan(boolean trangThai) {
        if (cboMaBan.getSelectedItem() != null) {
            Ban ban1 = (Ban) cboMaBan.getSelectedItem();
            Ban list2 = daoBan.selectById(String.valueOf(ban1.getMaBan()));

            list2.setTrangThai(trangThai);

            daoBan.updateTrangThai(list2);
        }
    }

    public HoaDon readFormHD(boolean trangThai, boolean Huy, boolean DangBan) {

        Ban ban = (Ban) cboMaBan.getSelectedItem();
        HoaDon hd = new HoaDon();
        hd.setMaHD(txtMaHD.getText());
        Date ngayTao = XDate.toDate(txtNgayTao.getText(), "dd-MM-yyyy");
        String nt = XDate.toString(ngayTao, "yyyy-MM-dd");
        hd.setNgayTao(nt);
        hd.setTenDN(txtTenDN.getText());
        hd.setTrangThai(trangThai);
        hd.setHuy(Huy);
        hd.setBan(ban.getMaBan());
        hd.setDangBan(DangBan);

        return hd;
    }

    public void insertHD() {
        HoaDon hd = this.readFormHD(false, false, true);
        HoaDon list = daoHD.selectById(hd.getMaHD());

        if (list == null) {
            daoHD.insert(hd);
            fillBan();
            MsgBox.alert(this, "Đã tạo hóa đơn " + hd.getMaHD() + "!!");
        }

    }

    public void fillHoaDon() {

        List<Object> list = daoHD.selectMaxMaHD();
        String ma = "";
        int hdd = 0;
        if (list != null) {
            for (Object hd : list) {

                hdd = Integer.parseInt(hd.toString().substring(2, hd.toString().length()));

                if (hdd >= 99 && hdd < 999) {
                    hdd = Integer.parseInt(hd.toString().substring(2, hd.toString().length()));
                    ma += "HD" + hdd;

                } else if (hdd < 9) {
                    hdd = Integer.parseInt(hd.toString().substring(4, hd.toString().length()));
                    ma += "HD00" + (hdd + 1);
                } else if (hdd >= 9) {
                    hdd = Integer.parseInt(hd.toString().substring(3, hd.toString().length()));
                    ma += "HD0" + (hdd + 1);
                }
            }
        }
        txtMaHD.setText(ma);
        txtTrangThaiHD.setText("Chưa tạo hóa đơn");
        txtTrangThaiHD.setForeground(Color.blue);

    }

    public void fillHDBan() {
        if (cboMaBan.getSelectedItem() != null) {
            Ban ban = (Ban) cboMaBan.getSelectedItem();
            List<HoaDon> hd = daoHD.selectByKeyword(ban.getMaBan());

            if (hd != null) {
                for (HoaDon hoadon : hd) {

                    txtMaHD.setText(hoadon.getMaHD());
                    txtTrangThaiHD.setForeground(Color.red);
                    if (hoadon.getTrangThai()) {
                        txtTrangThaiHD.setText("Chưa thanh toán");
                    } else if (hoadon.isDangBan()) {
                        txtTrangThaiHD.setText("Đang bán");
                    } else {
                        txtTrangThaiHD.setText("");
                    }
                }
            }

        }
    }

    public void getNguoiDung() {
        if (Auth.isLogin()) {
            String maND = Auth.getUsername();
            txtTenDN.setText(maND);
            NhanVienDAO daoNV = new NhanVienDAO();
            List<NhanVien> list = daoNV.selectByKeyword3(maND);
            for (NhanVien nv : list) {
                txtTenNguoiTao.setText(nv.getTenNV());
            }

        } else {
            txtTenDN.setText("vvhh123");
            txtTenNguoiTao.setText("Võ Văn Hoàng Hữu");
        }
    }

    public void fillComboBoxSanPham() {

        DefaultComboBoxModel model = (DefaultComboBoxModel) cboSanPham.getModel();
        model.removeAllElements();
        List<SanPham> list = daoSP.selectAll();
        for (SanPham sp : list) {
            model.addElement(sp);

        }

    }

    public void fillSanPham() {
        if (cboSanPham.getSelectedItem() != null) {

            int viTri = tblSanPham.getSelectedRow();
            SanPham sanPham = (SanPham) cboSanPham.getSelectedItem();
            SanPham list = daoSP.selectById(sanPham.getMaSP());
            txtSoLuongTon.setText(String.valueOf(list.getSoLuong()));
            txtSoLuong.setText("0");
            txtGiamGia.setText("0");
            String donGia = getNum(String.valueOf(list.getGiaBan()));
            txtDonGia.setText(donGia);
            txtThanhTien.setText("0");
            if (viTri < 0) {
                eventButtonEnable(btnXoa);
                eventButtonEnable(btnCapNhat);

                           }

        }
    }

    public void fillTableDanhSachSP() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        List<Object[]> list = daoHDCT.selectHDCT(txtMaHD.getText());
        if (list != null) {
            for (Object[] rowData : list) {

                String donGia = getNum(rowData[2].toString());
                String giamGia = getNum(rowData[4].toString());
                String thanhTien = getNum(rowData[5].toString());

                Object[] data = new Object[]{rowData[0],
                    rowData[1], donGia, rowData[3], giamGia, thanhTien
                };
                model.addRow(data);
            }
            setTongTien();
        }
    }

    public String getNum(String num) {

        return num.substring(0, num.indexOf(".")) + " VND";
    }

    public HoaDonCT readFormSanPham() {
        fillHoaDonCT();
        SanPham sp = (SanPham) cboSanPham.getSelectedItem();
        HoaDonCT hdct = new HoaDonCT();
        hdct.setMaHD(txtMaHD.getText());
        hdct.setMaHDCT(maHDCT);
        hdct.setMaSP(sp.getMaSP());
        hdct.setSoLuong(Integer.valueOf(txtSoLuong.getText()));
        hdct.setGiamGia(Double.valueOf(txtGiamGia.getText()));
        hdct.setDonGia(Double.valueOf(txtThanhTien.getText()));
        return hdct;

    }
    String maHDCT2 = "";

    public HoaDonCT readFormSanPhamUP() {
        getMaHDCT();
        HoaDonCT hdct = new HoaDonCT();
        hdct.setMaHDCT(maHDCT2);
        hdct.setSoLuong(Integer.valueOf(txtSoLuong.getText()));
        hdct.setGiamGia(Double.valueOf(txtGiamGia.getText()));
        hdct.setDonGia(Double.valueOf(txtThanhTien.getText()));

        return hdct;

    }

    public void getMaHDCT() {
        int viTri = tblSanPham.getSelectedRow();

        String maSP = tblSanPham.getValueAt(viTri, 0).toString();
        String maHD = txtMaHD.getText();

        List<HoaDonCT> list = daoHDCT.selectByKeyword(maHD, maSP);
        if (list != null) {
            for (HoaDonCT hdc : list) {
                maHDCT2 = hdc.getMaHDCT();
            }

        }

    }

    public void insertHDCT() {
        HoaDonCT hdct = this.readFormSanPham();
        daoHDCT.insert(hdct);
        updateSP("Thêm");
        fillTableDanhSachSP();
        //System.out.println("Thêm mã hóa đơn thành công");
        MsgBox.alert(this, "Đã thêm sản phẩm !");

    }

    public void updateHDCT() {
        int viTri = tblSanPham.getSelectedRow();
        if (viTri < 0) {
            MsgBox.alert(this, "Vui lòng chọn sản phẩm cần sửa!");
            return;
        }
        HoaDonCT hdct = this.readFormSanPhamUP();
        daoHDCT.update(hdct);
        updateSP("Cập nhật");
        fillTableDanhSachSP();
        MsgBox.alert(this, "Cập nhật thành công !");
    }

    public void removeSanPham() {
        int viTri = tblSanPham.getSelectedRow();
        if (viTri < 0) {
            MsgBox.alert(this, "Vui lòng chọn sản phẩm cần xóa!");
            return;
        }
        String maSP = tblSanPham.getValueAt(viTri, 0).toString();
        String maHD = txtMaHD.getText();
        String maHDCT = "";
        List<HoaDonCT> list = daoHDCT.selectByKeyword(maHD, maSP);
        if (list != null) {
            for (HoaDonCT hdct : list) {
                maHDCT = hdct.getMaHDCT();
            }

        }
        boolean chon = MsgBox.confirm(this, "Bạn có chắc chắn muốn xóa ");
        if (chon) {
            daoHDCT.delete(maHDCT);
            updateSP("Xóa");
            fillTableDanhSachSP();
            MsgBox.alert(this, "Đã xóa sản phẩm !");

        }

    }

    public void fillHoaDonCT() {

        List<Object> list = daoHDCT.selectMaxMaHDCT();
        maHDCT = "";
        if (list != null) {
            for (Object hd : list) {

                int hdd = Integer.parseInt(hd.toString().substring(4, hd.toString().length()));

                if (hdd >= 99 && hdd < 999) {
                    hdd = Integer.parseInt(hd.toString().substring(4, hd.toString().length()));
                    maHDCT += "HDCT" + (hdd + 1);
                } else if (hdd < 9) {
                    hdd = Integer.parseInt(hd.toString().substring(4, hd.toString().length()));
                    maHDCT += "HDCT00" + (hdd + 1);
                } else if (hdd >= 9) {
                    hdd = Integer.parseInt(hd.toString().substring(4, hd.toString().length()));
                    maHDCT += "HDCT0" + (hdd + 1);
                }
            }

        }
    }

    public void setTongTien() {

        int j = tblSanPham.getRowCount();
        int tongTien = 0;
        String thanhTien = "";
        if (j != 0) {
            for (int i = 0; i < j; i++) {
                thanhTien = tblSanPham.getValueAt(i, 5).toString();
                int thanhT = Integer.valueOf(thanhTien.substring(0, thanhTien.indexOf(" VND")));
                tongTien += thanhT;
//                System.out.println(tongTien);
            }
            lblTongTien.setText(String.valueOf(tongTien) + " VND");
          

        } else {
            lblTongTien.setText(tongTien + " VND");
           
        }

    }

    public void setThanhTien() {
        String donG = txtDonGia.getText();
        int donGia = Integer.valueOf(donG.substring(0, donG.indexOf(" VND")));
        int soLuong = 0;
        int giamGia = 0;
        if (txtSoLuong.getText().isEmpty()) {
            txtSoLuong.setText("0");
        }
        if (txtGiamGia.getText().isEmpty()) {
            txtGiamGia.setText("0");
        }

        try {
            soLuong = Integer.valueOf(txtSoLuong.getText());
            if (soLuong > 500) {
                MsgBox.alert(this, "Số lượng phải từ 500 trở xuống!!");
                txtSoLuong.setText("0");
                return;
            }
        } catch (NumberFormatException e) {
//            MsgBox.alert(this, "Số lượng vui lòng nhập số!");
            txtSoLuong.setText("");
            txtSoLuong.setText("0");

        }

        try {

            giamGia = Integer.valueOf(txtGiamGia.getText());
            if (soLuong == 0 && giamGia > 0) {

                MsgBox.alert(this, "Vui lòng nhập số lượng trước!!");
                txtGiamGia.setText("0");

                return;
            }
            if (giamGia > 5000) {

                MsgBox.alert(this, "Giá giảm phải từ 5000 trở xuống!!");
                txtGiamGia.setText("0");
                return;
            }
        } catch (Exception e) {

//            MsgBox.alert(this, "Giảm giá vui lòng nhập số!");
            txtGiamGia.setText("");
            txtGiamGia.setText("0");

        }

        int ThanhTien = (donGia * soLuong) - giamGia;

        txtThanhTien.setText(String.valueOf(ThanhTien));

    }

    public void writeFormSP() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboSanPham.getModel();
        SanPham sp = (SanPham) cboSanPham.getSelectedItem();
        int viTri = tblSanPham.getSelectedRow();
        String maSP = tblSanPham.getValueAt(viTri, 0).toString();

        eventButton(btnXoa, new Color(0, 204, 52));
      
        eventButton(btnCapNhat, new Color(0, 102, 204));
       

        List<SanPham> list1 = daoSP.selectSanPham(maSP);
        if (!list1.isEmpty()) {
            for (SanPham sp1 : list1) {
                if (sp1.getMaSP().equals(maSP)) {
                    model.setSelectedItem(sp1);

                }

            }
        }

        SanPham list = daoSP.selectById(maSP);

        txtSoLuongTon.setText(String.valueOf(list.getSoLuong()));
        String donGia = getNum(String.valueOf(list.getGiaBan()));
        txtDonGia.setText(donGia);

        txtSoLuong.setText(String.valueOf(tblSanPham.getValueAt(viTri, 3)));
        String giamGia = String.valueOf(tblSanPham.getValueAt(viTri, 4));
        String giamGia2 = giamGia.substring(0, giamGia.indexOf(" VND"));

        txtGiamGia.setText(giamGia2);
        String thanhTien = String.valueOf(tblSanPham.getValueAt(viTri, 5));
        String thanhTien2 = thanhTien.substring(0, thanhTien.indexOf(" VND"));
        txtThanhTien.setText(thanhTien2);

    }

    public void updateSP(String cn) {
        int viTri = tblSanPham.getSelectedRow();
        SanPham sp = new SanPham();

        int soLuong2 = Integer.valueOf(txtSoLuong.getText());
        int soLuongTon = Integer.valueOf(txtSoLuongTon.getText());

        int soLuongCapNhat = 0;
        if (cn.equals("Cập nhật")) {
            int soLuong = Integer.valueOf(tblSanPham.getValueAt(viTri, 3).toString());
            sp.setMaSP(tblSanPham.getValueAt(viTri, 0).toString());
            soLuongCapNhat = soLuongTon + soLuong - soLuong2;
        } else if (cn.equals("Xóa")) {
            int soLuong = Integer.valueOf(tblSanPham.getValueAt(viTri, 3).toString());
            sp.setMaSP(tblSanPham.getValueAt(viTri, 0).toString());
            soLuongCapNhat = soLuongTon + soLuong;
        } else {
            SanPham sanPham = (SanPham) cboSanPham.getSelectedItem();
            SanPham list = daoSP.selectById(sanPham.getMaSP());
            sp.setMaSP(String.valueOf(list.getMaSP()));
            soLuongCapNhat = soLuongTon - soLuong2;
        }
        sp.setSoLuong(soLuongCapNhat);
        daoSP.updateSoLuong(sp);
        fillSanPham();

    }

    public boolean checkBoTrong() {
        int soLuong = 0;
        int soLuongTon = Integer.valueOf(txtSoLuongTon.getText());
        int giamGia = 0;
        try {
            soLuong = Integer.valueOf(txtSoLuong.getText());
            if (soLuong > 500) {
                MsgBox.alert(this, "Số lượng phải từ 500 trở xuống!!");
                txtSoLuong.setText("0");
                return false;
            }
        } catch (NumberFormatException e) {
            MsgBox.alert(this, "Số lượng vui lòng nhập số!");
            txtSoLuong.setText("0");
            return false;

        }
        try {

            giamGia = Integer.valueOf(txtGiamGia.getText());
            if (soLuong == 0 && giamGia > 0) {

                MsgBox.alert(this, "Vui lòng nhập số lượng trước!!");
                txtGiamGia.setText("0");

                return false;
            }
            if (giamGia > 5000) {

                MsgBox.alert(this, "Giá giảm phải từ 5000 trở xuống!!");
                txtGiamGia.setText("0");
                return false;
            }
        } catch (Exception e) {

            MsgBox.alert(this, "Giảm giá vui lòng nhập số!");
            txtGiamGia.setText("0");
            return false;

        }
        if (soLuongTon == 0) {
            MsgBox.alert(this, "Sản phẩm này đã hết hàng!!");
            txtSoLuong.setText("0");
            return false;
        }
        if (soLuong == 0) {
            MsgBox.alert(this, "Vui lòng nhập số lượng !!");
            return false;
        }
        int viTri = tblSanPham.getSelectedRow();

        if (viTri >= 0) {
            int sl = Integer.valueOf(tblSanPham.getValueAt(viTri, 3).toString());
            if (soLuong > (soLuongTon + sl)) {
                MsgBox.alert(this, "Số lượng gọi món không thể \nvượt quá số lượng tồn!!");
                txtSoLuong.setText("0");
                return false;
            }
        } else if (viTri < 0) {
            if (soLuong > soLuongTon) {
                MsgBox.alert(this, "Số lượng gọi món không thể \nvượt quá số lượng tồn!!");
                txtSoLuong.setText("0");
                return false;
            }
        }

        return true;
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
        panelThongTinChung = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cboMaBan = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cboTrangThai = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNgayTao = new javax.swing.JTextField();
        txtTenDN = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTenNguoiTao = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtTrangThaiHD = new javax.swing.JTextField();
        panelThongTinSP = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtSoLuongTon = new javax.swing.JTextField();
        txtDonGia = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtThanhTien = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtGiamGia = new javax.swing.JTextField();
        cboSanPham = new javax.swing.JComboBox<>();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        panelBanHang = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        pnlTitleBar = new javax.swing.JPanel();
        lblTongTien = new javax.swing.JLabel();
        btnHuy = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 2, 2, 2, new java.awt.Color(51, 102, 255)));

        panelThongTinChung.setBackground(new java.awt.Color(255, 255, 255));
        panelThongTinChung.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 48, 12)), "THÔNG TIN CHUNG", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(255, 48, 12))); // NOI18N
        panelThongTinChung.setOpaque(false);
        panelThongTinChung.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 48, 12)), "BÀN\n", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(255, 48, 12))); // NOI18N
        jPanel2.setOpaque(false);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Mã Bàn:");

        cboMaBan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboMaBan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "000003", "000002", "000001" }));
        cboMaBan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboMaBanItemStateChanged(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Trạng thái:");

        cboTrangThai.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Trống", "Có khách" }));
        cboTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel17.setText("<html>Chú ý: Để lưu thông tin hóa đơn vui lòng <br> chuyển trạng thái bàn từ trống sang có khách</html>");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboTrangThai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cboMaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboMaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        panelThongTinChung.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 360, 240));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 48, 12)), "HÓA ĐƠN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(255, 48, 12))); // NOI18N
        jPanel3.setOpaque(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Mã hóa đơn :");

        txtMaHD.setEditable(false);
        txtMaHD.setBackground(new java.awt.Color(255, 255, 255));
        txtMaHD.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtMaHD.setForeground(new java.awt.Color(255, 102, 0));
        txtMaHD.setText("HD001");
        txtMaHD.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Ngày tạo :");

        txtNgayTao.setEditable(false);
        txtNgayTao.setBackground(new java.awt.Color(255, 255, 255));
        txtNgayTao.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtNgayTao.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        txtTenDN.setEditable(false);
        txtTenDN.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTenDN.setText("admin");
        txtTenDN.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Tên ND:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Người tạo :");

        txtTenNguoiTao.setEditable(false);
        txtTenNguoiTao.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTenNguoiTao.setText("Võ Văn Hoàng Hữu");
        txtTenNguoiTao.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Trạng thái:");

        txtTrangThaiHD.setEditable(false);
        txtTrangThaiHD.setBackground(new java.awt.Color(255, 255, 255));
        txtTrangThaiHD.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtTrangThaiHD.setForeground(new java.awt.Color(204, 0, 0));
        txtTrangThaiHD.setText("Chưa thanh toán");
        txtTrangThaiHD.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel16)))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenNguoiTao, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                    .addComponent(txtTenDN)
                    .addComponent(txtNgayTao)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTrangThaiHD, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTenDN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTrangThaiHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        txtNgayTao.setText(sdf.format(now) + "");

        panelThongTinChung.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 370, 250));

        panelThongTinSP.setBackground(new java.awt.Color(255, 255, 255));
        panelThongTinSP.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 48, 12)), "SẢN PHẨM", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(255, 48, 12))); // NOI18N
        panelThongTinSP.setOpaque(false);
        panelThongTinSP.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Mã sản phẩm:");
        panelThongTinSP.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 120, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Số lượng tồn:");
        panelThongTinSP.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Giảm giá :");
        panelThongTinSP.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 90, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Thành tiền :");
        panelThongTinSP.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 180, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Số lượng:");
        panelThongTinSP.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 110, 80, -1));

        txtSoLuongTon.setEditable(false);
        txtSoLuongTon.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtSoLuongTon.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        panelThongTinSP.add(txtSoLuongTon, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 40, 170, -1));

        txtDonGia.setEditable(false);
        txtDonGia.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtDonGia.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        panelThongTinSP.add(txtDonGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 240, -1));

        txtSoLuong.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtSoLuong.setText("0");
        txtSoLuong.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtSoLuong.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSoLuongFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSoLuongFocusLost(evt);
            }
        });
        txtSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoLuongKeyReleased(evt);
            }
        });
        panelThongTinSP.add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 110, 180, -1));

        txtThanhTien.setEditable(false);
        txtThanhTien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtThanhTien.setText("0");
        txtThanhTien.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        panelThongTinSP.add(txtThanhTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 180, 170, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Đơn giá :");
        panelThongTinSP.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 80, -1));

        txtGiamGia.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtGiamGia.setText("0");
        txtGiamGia.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtGiamGia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtGiamGiaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGiamGiaFocusLost(evt);
            }
        });
        txtGiamGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiamGiaKeyReleased(evt);
            }
        });
        panelThongTinSP.add(txtGiamGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 230, -1));

        cboSanPham.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboSanPham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SP001" }));
        cboSanPham.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboSanPhamItemStateChanged(evt);
            }
        });
        panelThongTinSP.add(cboSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 240, -1));

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        panelThongTinSP.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, -1, -1));

        btnCapNhat.setText("Sửa");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        panelThongTinSP.add(btnCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, -1, -1));

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        panelThongTinSP.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 250, -1, -1));

        panelBanHang.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 3, 3, 3, new java.awt.Color(255, 48, 12)));

        tblSanPham.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Đơn giá", "Số lượng", "Giảm giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.setFocusable(false);
        tblSanPham.setRowHeight(25);
        tblSanPham.setSelectionBackground(new java.awt.Color(255, 0, 51));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        panelBanHang.setViewportView(tblSanPham);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel12.setText("Tổng tiền: ");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 48, 12));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("QUẢN LÝ BÁN HÀNG");

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

        lblTongTien.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblTongTien.setForeground(new java.awt.Color(255, 0, 0));
        lblTongTien.setText("0");

        btnHuy.setText("Hủy Hóa Đơn");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        btnThanhToan.setText("Thanh Toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitleBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelThongTinChung, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(231, 231, 231)
                        .addComponent(btnHuy)
                        .addGap(18, 18, 18)
                        .addComponent(btnThanhToan)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelThongTinSP, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
                            .addComponent(panelBanHang))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlTitleBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelThongTinSP, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(panelBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnHuy)
                                    .addComponent(btnThanhToan))
                                .addGap(23, 23, 23))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(lblTongTien))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(panelThongTinChung, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                        .addContainerGap())))
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

    private void cboMaBanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboMaBanItemStateChanged
        fillBan();
        fillTableDanhSachSP();
        check();
        fillComboBoxSanPham();
        checkSanPham();
    }//GEN-LAST:event_cboMaBanItemStateChanged

    private void cboTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThaiActionPerformed
        test();
        check();
    }//GEN-LAST:event_cboTrangThaiActionPerformed

    private void txtSoLuongFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSoLuongFocusGained
        if (txtSoLuong.getText().equals("0")) {
            txtSoLuong.setText("");
        }
    }//GEN-LAST:event_txtSoLuongFocusGained

    private void txtSoLuongFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSoLuongFocusLost
        setThanhTien();
    }//GEN-LAST:event_txtSoLuongFocusLost

    private void txtSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongKeyReleased
        setThanhTien();
    }//GEN-LAST:event_txtSoLuongKeyReleased

    private void txtGiamGiaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGiamGiaFocusGained
        if (txtGiamGia.getText().equals("0")) {
            txtGiamGia.setText("");
        }
    }//GEN-LAST:event_txtGiamGiaFocusGained

    private void txtGiamGiaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGiamGiaFocusLost
        setThanhTien();
    }//GEN-LAST:event_txtGiamGiaFocusLost

    private void txtGiamGiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiamGiaKeyReleased
        setThanhTien();
    }//GEN-LAST:event_txtGiamGiaKeyReleased

    private void cboSanPhamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboSanPhamItemStateChanged

        fillSanPham();
        check();
        checkSanPham();
    }//GEN-LAST:event_cboSanPhamItemStateChanged

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (checkBoTrong()) {
            insertHDCT();
            check();
            checkSanPham();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        if (checkBoTrong()) {
            updateHDCT();
            check();
            checkSanPham();
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        removeSanPham();
        check();
        checkSanPham();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        writeFormSP();
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void pnlTitleBarMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleBarMouseDragged

    }//GEN-LAST:event_pnlTitleBarMouseDragged

    private void pnlTitleBarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleBarMousePressed

    }//GEN-LAST:event_pnlTitleBarMousePressed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        huyHoaDon();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        thanhToanHD();
    }//GEN-LAST:event_btnThanhToanActionPerformed
        public void test() {
        if (cboTrangThai.getSelectedItem().equals("Có khách")) {
            Ban ban = (Ban) cboMaBan.getSelectedItem();
            Ban list = daoBan.selectById(String.valueOf(ban.getMaBan()));
            List<HoaDon> hd = daoHD.selectByKeyword(ban.getMaBan());
            if (hd.isEmpty()) {
                if (cboTrangThai.isEnabled() == false && list.isTrangThai()) {

                } else {
                    updateBan(true);
                    insertHD();
                }
            }

        }
    }

    public void thanhToanHD() {
        String maHD = txtMaHD.getText();
        boolean chon = MsgBox.confirm(this, "Bạn muốn thanh toán hóa đơn " + maHD + "!!");
        if (chon) {
            updateBan(false);
            HoaDon hd = this.readFormHD(true, false, false);
            daoHD.update(hd);
            fillComboBoxBan();
            fillTableDanhSachSP();
            MsgBox.alert(this, "Hóa đơn " + maHD + " đã thanh toán!");

        }

    }

    public void huyHoaDon() {
        String maHD = txtMaHD.getText();
        boolean chon = MsgBox.confirm(this, "Bạn chắc chắn muốn hủy hóa đơn " + maHD + " !");
        if (chon) {
            updateBan(false);
            HoaDon hd = this.readFormHD(false, true, false);
            daoHD.update(hd);
            fillComboBoxBan();
            fillTableDanhSachSP();
            MsgBox.alert(this, "Đã hủy hóa đơn " + maHD + " !!");

        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboMaBan;
    private javax.swing.JComboBox<String> cboSanPham;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JScrollPane panelBanHang;
    private javax.swing.JPanel panelThongTinChung;
    private javax.swing.JPanel panelThongTinSP;
    private javax.swing.JPanel pnlTitleBar;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtGiamGia;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtSoLuongTon;
    private javax.swing.JTextField txtTenDN;
    private javax.swing.JTextField txtTenNguoiTao;
    private javax.swing.JTextField txtThanhTien;
    private javax.swing.JTextField txtTrangThaiHD;
    // End of variables declaration//GEN-END:variables
}
