/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment_java3;
import data.KetNoi;
import data.SinhVien;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Admin
 */
public class QuanLySinhVien extends javax.swing.JInternalFrame {
KetNoi ketNoi;
    /**
     * Creates new form QuanLySinhVien
     */
    public QuanLySinhVien() {
        initComponents();
    try {
        layDuLieu();
    } catch (SQLException ex) {
        Logger.getLogger(QuanLySinhVien.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    public void ketnoi(){
        ketNoi=new KetNoi();
    try {
        ketNoi.KetNoi();
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(QuanLySinhVien.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
        Logger.getLogger(QuanLySinhVien.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    List<data.SinhVien> listSV=new ArrayList<>();
    public void FillTable(){
        tblSV.removeAll();
        DefaultTableModel model=(DefaultTableModel) tblSV.getModel();
        model.setRowCount(0);
        for(data.SinhVien x:listSV){
            model.addRow(new Object[]{x.getMa(),x.getTen(),x.getEmail(),x.getSdt(),x.getGioitinh(),x.getDiachi(),x.getHinh()});
        }
    }
    public void showDetail(data.SinhVien sv){
        txtMaSV.setText(sv.getMa());
        txtHoTen.setText(sv.getTen());
        txtEmail.setText(sv.getEmail());
        txtSĐT.setText(sv.getSdt());
        if(sv.getGioitinh().equals("nam")){rdoNam.setSelected(true);}
        else {rdoNu.setSelected(true);}
        txtDiaChi.setText(sv.getDiachi());
  
    }
    public void layDuLieu() throws SQLException{
        ketnoi();
        Statement stt=ketNoi.con.createStatement();
        ResultSet rs=stt.executeQuery("select *from students");
        while(rs.next()){
            String ma=rs.getString("masv");
            String ten=rs.getString("Hoten");
            String email=rs.getString("Email");
            String sdt=rs.getString("SoDT");
            String gioitinh=rs.getString("GioiTinh");
            String diachi=rs.getString("diachi");
            String hinh=rs.getString("hinh");
            listSV.add(new SinhVien(ma, ten, email, sdt, gioitinh, diachi, hinh));
        }
        FillTable();
    }
    public boolean check(){
        if(txtMaSV.getText().equals("")){
            JOptionPane.showMessageDialog(this,"khong de trong");
            txtMaSV.requestFocus();
            return false;
        }
        for(data.SinhVien sv:listSV){
            if(txtMaSV.getText().equals(sv.getMa())){
            JOptionPane.showMessageDialog(this,"da co ma nay");
            txtMaSV.requestFocus();
            return false;
            }
        }
        if(txtHoTen.getText().equals("")){
            JOptionPane.showMessageDialog(this,"khong de trong");
            txtHoTen.requestFocus();
            return false;
        }
        if(txtEmail.getText().equals("")){
            JOptionPane.showMessageDialog(this,"khong de trong");
            txtEmail.requestFocus();
            return false;
        }
        String mauEmail="\\w+@\\w+\\.\\w+";
        if(!txtEmail.getText().matches(mauEmail)){
            JOptionPane.showMessageDialog(this,"nhap dung dinh dang email");
            txtEmail.requestFocus();
            return false;
        }
        if(txtSĐT.getText().equals("")){
            JOptionPane.showMessageDialog(this,"khong de trong");
            txtSĐT.requestFocus();
            return false;
        }
        try {
            int sodt=Integer.valueOf(txtSĐT.getText());
            if(sodt<0){
                JOptionPane.showMessageDialog(this,"so dien thoai lon hon 0");
            txtSĐT.requestFocus();
            return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,"vui long nhap so");
            txtSĐT.requestFocus();
            return false;
        }
        if(!rdoNam.isSelected()&&!rdoNu.isSelected()){JOptionPane.showMessageDialog(this,"vui long chon gioi tinh");
            
            return false;}
        if(txtDiaChi.getText().equals("")){
            JOptionPane.showMessageDialog(this,"khong de trong");
            txtDiaChi.requestFocus();
            return false;
        }
        return true;
    }
    public void clear(){
       
        txtMaSV.setText("");
        txtHoTen.setText("");
        txtEmail.setText("");
        txtSĐT.setText("");
        txtDiaChi.setText("");
        rdoNam.setSelected(false);
        rdoNu.setSelected(false);
    }
    public void save() throws SQLException{
        if(check()){
            data.SinhVien sv= new SinhVien();
            sv.setMa(txtMaSV.getText());
            sv.setTen(txtHoTen.getText());
            sv.setEmail(txtEmail.getText());
            sv.setSdt(txtSĐT.getText());
            if(rdoNam.isSelected()){sv.setGioitinh("nam");
            }
            else{sv.setGioitinh("nu");}
            sv.setDiachi(txtDiaChi.getText());
            sv.setHinh("obama.png");
            listSV.add(sv);
            PreparedStatement stt=ketNoi.con.prepareCall("insert into STUDENTS VALUES (?,?,?,?,?,?,?)");
            stt.setString(1,sv.getMa());
            stt.setString(2,sv.getTen());
            stt.setString(3,sv.getEmail());
            stt.setString(4,sv.getSdt());
            stt.setString(5,sv.getGioitinh());
            stt.setString(6,sv.getDiachi());
            stt.setString(7,"obama.png");
            stt.execute();
            JOptionPane.showMessageDialog(this,"them thanh cong");
            FillTable();
        }
    }
    public void delete() throws SQLException{
        String delete=JOptionPane.showInputDialog(this,"nhap ma sv can xoa","xoa", HEIGHT);
        boolean kq=false;
        for(data.SinhVien sv:listSV){
            if(delete.equalsIgnoreCase(sv.getMa())){
                kq=true;
                int index=listSV.indexOf(sv);
                listSV.remove(index);
                PreparedStatement stt=ketNoi.con.prepareCall("delete from students where masv=?");
                stt.setString(1,sv.getMa());
                stt.execute();
                JOptionPane.showMessageDialog(this,"xoa thanh cong");
                break;
            }
        }
        if(kq==false){
            JOptionPane.showMessageDialog(this,"khong thay ma sinh vien");
        }
        FillTable();
    }
    public void update() throws SQLException{
        int index=tblSV.getSelectedRow();
        data.SinhVien sv=new SinhVien();
        sv.setMa(txtMaSV.getText());
        sv.setTen(txtHoTen.getText());
        sv.setEmail(txtEmail.getText());
        sv.setSdt(txtSĐT.getText());
        if(rdoNam.isSelected()){sv.setGioitinh("nam");
            }
            else{sv.setGioitinh("nu");}
            sv.setDiachi(txtDiaChi.getText());
            sv.setHinh("obama.png");
            listSV.set(index, sv);
            PreparedStatement stt=ketNoi.con.prepareCall("update students set MASV=?,Hoten=?,Email=?,SoDT=?,GioiTinh=?,DiaChi=?,Hinh=?");
            stt.setString(1,sv.getMa());
            stt.setString(2,sv.getTen());
            stt.setString(3,sv.getEmail());
            stt.setString(4,sv.getSdt());
            stt.setString(5,sv.getGioitinh());
            stt.setString(6,sv.getDiachi());
            stt.setString(7,"obama.png");
            stt.execute();
            JOptionPane.showMessageDialog(this,"update thanh cong");
            FillTable();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMaSV = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtSĐT = new javax.swing.JTextField();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSV = new javax.swing.JTable();
        btnUpdate1 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 51, 255));
        jLabel1.setText("QUẢN LÝ SINH VIÊN");

        jLabel2.setText("Mã SV");

        jLabel3.setText("Họ Tên");

        jLabel4.setText("Email");

        jLabel5.setText("Số ĐT");

        jLabel6.setText("Giới Tính");

        jLabel7.setText("Địa Chỉ");

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        jScrollPane1.setViewportView(txtDiaChi);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/obama.jpg"))); // NOI18N

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        tblSV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SV", "Họ Tên", "Email", "SĐT", "Giới Tính", "Địa Chỉ", "Hình"
            }
        ));
        tblSV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSVMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSV);

        btnUpdate1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit.png"))); // NOI18N
        btnUpdate1.setText("Exit");
        btnUpdate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(23, 23, 23)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtSĐT, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(rdoNam)
                                                .addGap(18, 18, 18)
                                                .addComponent(rdoNu))
                                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(36, 36, 36))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnUpdate1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtSĐT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnNew)
                                .addComponent(btnSave))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnDelete)
                                .addComponent(btnUpdate)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnUpdate1)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblSVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSVMouseClicked
        // TODO add your handling code here:
        int i=tblSV.getSelectedRow();
        data.SinhVien sv=listSV.get(i);
        showDetail(sv);
    }//GEN-LAST:event_tblSVMouseClicked

    private void btnUpdate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate1ActionPerformed
        // TODO add your handling code here:
        this.hide();
    }//GEN-LAST:event_btnUpdate1ActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
    try {
        // TODO add your handling code here:
        save();
    } catch (SQLException ex) {
        Logger.getLogger(QuanLySinhVien.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
    try {
        // TODO add your handling code here:
        delete();
    } catch (SQLException ex) {
        Logger.getLogger(QuanLySinhVien.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
    try {
        // TODO add your handling code here:
        update();
    } catch (SQLException ex) {
        Logger.getLogger(QuanLySinhVien.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_btnUpdateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUpdate1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblSV;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaSV;
    private javax.swing.JTextField txtSĐT;
    // End of variables declaration//GEN-END:variables
}
