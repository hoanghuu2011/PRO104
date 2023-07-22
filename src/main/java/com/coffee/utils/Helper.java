/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coffee.utils;

import static java.awt.Color.pink;
import static java.awt.Color.red;
import static java.awt.Color.white;
import javax.swing.JEditorPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Helper {
      public static boolean checkMaNV(JTextField txt) {
        txt.setBackground(white);
        String id = txt.getText();
        String rgx = "[a-zA-Z0-9]{1,15}";
        if (id.matches(rgx)) {
            return true;
        } else {
            txt.setBackground(red);
            MsgBox.alert(txt.getRootPane(), txt.getName() + " mã nhân viên phải có đúng 5-10 kí tự\nchữ thường, chữ hoa hoặc số.");
            return false;
        }
    }
      public static boolean checkNullPass(JPasswordField txt) {
        txt.setBackground(white);
        if (txt.getPassword().length > 0) {
            return true;
        } else {
            txt.setBackground(red);
            MsgBox.alert(txt.getRootPane(), "Không được để trống " + txt.getName());
            return false;
        }
    }
       public static boolean checkPass(JPasswordField txt) {
        txt.setBackground(white);
        if (txt.getPassword().length > 2 && txt.getPassword().length < 17) {
            return true;
        } else {
            txt.setBackground(red);
            MsgBox.alert(txt.getRootPane(), txt.getName() + " mật khẩu phải có từ 3-16 kí tự.");
            return false;
        }
    }
    public static boolean checkConfirmPass(JPasswordField txt,JPasswordField txtxacnhan) {
        txt.setBackground(white);
        String txtMK = new String(txt.getPassword());
        String txtXNMK = new String(txtxacnhan.getPassword());
        if (txtMK.equals(txtXNMK)) {
            return true;
        } else {
            txtxacnhan.setBackground(red);
            MsgBox.alert(txtxacnhan.getRootPane(), txtxacnhan.getName() + " Xác nhận mật khẩu không khớp");
            return false;
        }
    }
      public static boolean checkNullText(JTextArea txt) {
        txt.setBackground(white);
        if (txt.getText().trim().length() > 0) {
            return true;
        } else {
            txt.setBackground(red);
            MsgBox.alert(txt.getRootPane(), "Không được để trống " + txt.getName());
            return false;
        }
    }
      public static boolean checkName(JTextField txt) {
        txt.setBackground(white);
        String id = txt.getText();
        String rgx = "^[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]{3,25}$";
        if (id.matches(rgx)) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBox.alert(txt.getRootPane(), txt.getName() + " tên phải là tên tiếng việt hoặc không dấu\ntừ 3-25 kí tự");
            return false;
        }
    }
       public static boolean checkNullText(JTextField txt) {
        txt.setBackground(white);
        if (txt.getText().trim().length() > 0) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBox.alert(txt.getRootPane(), "Không được để trống " + txt.getName());
            return false;
        }
    }
        public static boolean checkSDT(JTextField txt) {
        txt.setBackground(white);
        String id = txt.getText();
        String rgx = "(086|096|097|098|032|033|034|035|036|037|038|039|089|090|093|070|079|077|078|076|088|091|094|083|084|085|081|082|092|056|058|099|059)[0-9]{7}";
        if (id.matches(rgx)) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBox.alert(txt.getRootPane(), txt.getName() + " phải gồm 10 số\nđúng các đầu số của nhà mạng.");
            return false;
        }
    }
         public static boolean checkDonGia(JTextField txt) {
        txt.setBackground(white);
        try {
            double dg = Double.parseDouble(txt.getText());
            if (dg >= 0) {
                return true;
            } else {
                txt.setBackground(red);
                MsgBox.alert(txt.getRootPane(), txt.getName() + " phải là lớn hơn bằng 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            txt.setBackground(red);
            MsgBox.alert(txt.getRootPane(), txt.getName() + " phải là số thực.");
            return false;
        }
    }
         public static boolean checkMaSP(JTextField txt) {
        txt.setBackground(white);
        String id = txt.getText();
        String rgx = "[a-zA-Z0-9]{5}";
        if (id.matches(rgx)) {
            return true;
        } else {
            txt.setBackground(red);
            MsgBox.alert(txt.getRootPane(), txt.getName() + " phải có đúng 5 kí tự\nchữ thường, chữ hoa hoặc số");
            return false;
        }
    }
          public static boolean checkTenSP(JTextField txt) {
        txt.setBackground(white);
        String id = txt.getText();
        String rgx = ".{3,50}";
        if (id.matches(rgx)) {
            return true;
        } else {
            txt.setBackground(red);
            MsgBox.alert(txt.getRootPane(), txt.getName() + " phải từ 3-50 kí tự.");
            return false;
        }
    }

    //bất kì kí tự nào
    //từ 3-255 kí tự
   
}
