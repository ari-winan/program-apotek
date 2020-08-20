/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apocash;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
/**
 *
 * @author Fian_Nur
 */
public class CashObat extends javax.swing.JFrame {

    private DefaultTableModel TabModel;
    Connection conn;
    Statement st;
    ResultSet rs;
    
    /**
     * Creates new form CashObat
     */
    public CashObat() {
        initComponents();
        //object untuk menapilkan header dari table data obat
        Object header[]={"Kode Obat","Nama Obat","Stok Obat","Satuan","Harga Kontrak","Harga Jual"};
        TabModel=new DefaultTableModel(null, header);
        tObat.setModel(TabModel);
        Connection();
        siapIsi(false);
        tampilData();
    }
    
    //method untuk melakukan koneksi ke database dengan mengambil class di luar
    public void Connection(){
        Connect db = new Connect();
        db.connect();
        conn = db.conn;
        st = db.st;
    }
    
    //method untuk menampilkan kodeObat yang baru yang di ambil dari database
    private void kodeObat(){
        try {
            Connection();
            //ambil terlebih dahulu untuk nilai kode pada table
            String sql="select right (kode_obat,2)+1 from obat ";
            rs=st.executeQuery(sql);
            //jika dalam table sudah ada nilai atau kode obat maka jalankan kondisi
            //untuk menambah nilai dengan angka 1
            if(rs.next()){
                rs.last();
                String no=rs.getString(1);
                while (no.length()<3){
                    no="0"+no;
                    kObat.setText("KO"+no);
                }
            }else{
            //jika tidak ada maka tampilkan ini
                kObat.setText("KO001");
            }
        } catch (Exception e){
        }
    }
    
    //method dimana beberapa object akan dapat diakses sesuai pemanggilan methodnya
    //dengan parameter true dan false
    private void siapIsi(boolean a){
        kObat.setEnabled(a);
        nObat.setEnabled(a);
        stObat.setEnabled(a);
        sObat.setEnabled(a);
        hKontrak.setEnabled(a);
        hJual.setEnabled(a);
        Cari.setEnabled(a);
    }
    
    //method yang digunakan untuk menampilkan data obat pada table
    private void tampilData(){
        try{
            Connection();
            String sql = "select * from obat";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                Object[] klm = new Object[6];
                klm[0] = rs.getString("kode_obat");
                klm[1] = rs.getString("nama_obat");
                klm[2] = rs.getString("stok");
                klm[3] = rs.getString("satuan");
                klm[4] = rs.getString("harga_kontrak");
                klm[5] = rs.getString("harga_jual");
                TabModel.addRow(klm);
            }
            tObat.setModel(TabModel);
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Kesalahan Query");
        }
    }
    
    //method yang digunakan untuk melakukan reset pada TextField berikut
    private void clearData(){
        kObat.setText("Kode Obat");
        nObat.setText("Nama Obat");
        stObat.setSelectedItem("--Satuan--");
        sObat.setText("Stok Obat");
        hKontrak.setText("Harga Kontrak");
        hJual.setText("Harga Jual");
        Cari.setText("Cari");
    }
    
    //method yang digunakan untuk melakukan insert data obat ke dalam table obat
    private void tambahData(){
        String satuan = (String)stObat.getSelectedItem();
        try{
            Connection();
            String sql = "insert into obat values('"+kObat.getText()+"','"+nObat.getText()+
                    "','"+satuan+"','"+sObat.getText()+"','"+hKontrak.getText()+"','"+hJual.getText()+"')";
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Data Berhasil Ditambahkan");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data Gagal Ditambahkan");
        }
    }
    
    //method yang digunakan untuk melakukan update data obat pada table obat
    private void updateData(){
        String satuan = (String)stObat.getSelectedItem();
        try{
            Connection();
            String sql = "update obat set nama_obat='"+nObat.getText()+
                    "',satuan='"+satuan+"',stok='"+sObat.getText()+"',harga_kontrak='"+hKontrak.getText()+"',harga_jual='"+hJual.getText()+
                    "' where kode_obat='"+kObat.getText()+"'";
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Data Berhasil Diupdate");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data Gagal Diupdate");
        }
    }
    
    //method yang digunakan untuk melakukan delete data obat pada table obat
    private void hapusData(){
        String satuan = (String)stObat.getSelectedItem();
        try{
            Connection();
            String sql = "delete from obat where kode_obat='"+kObat.getText()+"'";
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
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

        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        bClose2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        kObat = new javax.swing.JTextField();
        nObat = new javax.swing.JTextField();
        sObat = new javax.swing.JTextField();
        hKontrak = new javax.swing.JTextField();
        hJual = new javax.swing.JTextField();
        Cari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tObat = new javax.swing.JTable();
        Tambah = new javax.swing.JButton();
        Update = new javax.swing.JButton();
        Hapus = new javax.swing.JButton();
        stObat = new javax.swing.JComboBox<>();
        Batal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel5.setBackground(new java.awt.Color(0, 51, 204));

        bClose2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/close.png"))); // NOI18N
        bClose2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bClose2MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ApoCash");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bClose2))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bClose2)
            .addComponent(jLabel3)
        );

        kObat.setText("Kode Obat");
        kObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                kObatKeyTyped(evt);
            }
        });

        nObat.setText("Nama Obat");
        nObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nObatMouseClicked(evt);
            }
        });
        nObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nObatKeyTyped(evt);
            }
        });

        sObat.setText("Stok Obat");
        sObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sObatMouseClicked(evt);
            }
        });
        sObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sObatActionPerformed(evt);
            }
        });
        sObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sObatKeyTyped(evt);
            }
        });

        hKontrak.setText("Harga Kontrak");
        hKontrak.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hKontrakMouseClicked(evt);
            }
        });
        hKontrak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                hKontrakKeyTyped(evt);
            }
        });

        hJual.setText("Harga Jual");
        hJual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hJualMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hJualMouseEntered(evt);
            }
        });
        hJual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                hJualKeyTyped(evt);
            }
        });

        Cari.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Cari.setText("Cari");
        Cari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CariMouseClicked(evt);
            }
        });
        Cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CariKeyPressed(evt);
            }
        });

        tObat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tObatMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tObat);

        Tambah.setBackground(new java.awt.Color(51, 204, 0));
        Tambah.setText("Tambah");
        Tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TambahActionPerformed(evt);
            }
        });

        Update.setBackground(new java.awt.Color(0, 51, 204));
        Update.setText("Update");
        Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateActionPerformed(evt);
            }
        });

        Hapus.setBackground(new java.awt.Color(255, 0, 0));
        Hapus.setText("Hapus");
        Hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HapusActionPerformed(evt);
            }
        });

        stObat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Satuan--", "Botol", "Kaplet", "Bungkus" }));
        stObat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                stObatItemStateChanged(evt);
            }
        });
        stObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stObatActionPerformed(evt);
            }
        });

        Batal.setText("Batal");
        Batal.setEnabled(false);
        Batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BatalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(kObat, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Cari))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(Tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Update, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Batal, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(nObat, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                                .addComponent(sObat, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                                .addComponent(hKontrak, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                                .addComponent(hJual, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE))
                            .addComponent(stObat, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Cari, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(kObat))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(nObat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(stObat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sObat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(hKontrak, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(hJual, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Update, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Batal, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //fungsi button close menuju menu utama
    private void bClose2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bClose2MouseClicked
        // TODO add your handling code here:
        CashUtama CU = new CashUtama();
        CU.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_bClose2MouseClicked

    //button tambah data
    private void TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TambahActionPerformed
        // TODO add your handling code here:
        //jika button bertuliskan tambah maka ketika diklik akan menjadi tulisan tambah 
        //akan berubah menjadi simpan
        if(Tambah.getText().equalsIgnoreCase("Tambah")){
            Tambah.setText("Simpan");
            kodeObat();
            siapIsi(true);
            Batal.setEnabled(true);
            Update.setEnabled(false);
            Hapus.setEnabled(false);
            
        }else{
            //jika tulisan selain tambah maka ubah tulisan menjadi tambah
            //simpan data dengan memanggil method simpan
            Tambah.setText("Tambah");
            tambahData();
            siapIsi(false);
            Batal.setEnabled(false);
            TabModel.getDataVector().removeAllElements();
            TabModel.fireTableDataChanged();
            tampilData();
            clearData();
            Update.setEnabled(true);
            Hapus.setEnabled(true);
        }
    }//GEN-LAST:event_TambahActionPerformed

    private void UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateActionPerformed
        // TODO add your handling code here:
        //jika button bertuliskan update maka ketika diklik akan menjadi tulisan tambah 
        //akan berubah menjadi simpan
        if(Update.getText().equalsIgnoreCase("Update")){
            Update.setText("Simpan");
            siapIsi(true);
            Batal.setEnabled(true);
            Tambah.setEnabled(false);
            Hapus.setEnabled(false);
        }else{
            Update.setText("Update");
            updateData();
            siapIsi(false);
            Batal.setEnabled(false);
            TabModel.getDataVector().removeAllElements();
            TabModel.fireTableDataChanged();
            tampilData();
            clearData();
            Tambah.setEnabled(true);
            Hapus.setEnabled(true);
        }
    }//GEN-LAST:event_UpdateActionPerformed

    private void HapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HapusActionPerformed
        // TODO add your handling code here:
        if(Hapus.getText().equalsIgnoreCase("Hapus")){
            Hapus.setText("Simpan");
            siapIsi(true);
            Batal.setEnabled(true);
            Tambah.setEnabled(false);
            Update.setEnabled(false);
        }else{
            Hapus.setText("Hapus");
            hapusData();
            siapIsi(false);
            Batal.setEnabled(false);
            TabModel.getDataVector().removeAllElements();
            TabModel.fireTableDataChanged();
            tampilData();
            clearData();
            Tambah.setEnabled(true);
            Update.setEnabled(true);
        }
    }//GEN-LAST:event_HapusActionPerformed

    private void nObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nObatMouseClicked
        // TODO add your handling code here:
        nObat.setText("");
    }//GEN-LAST:event_nObatMouseClicked

    private void sObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sObatMouseClicked
        // TODO add your handling code here:
        sObat.setText("");
    }//GEN-LAST:event_sObatMouseClicked

    private void hKontrakMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hKontrakMouseClicked
        // TODO add your handling code here:
        hKontrak.setText("");
    }//GEN-LAST:event_hKontrakMouseClicked

    private void hJualMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hJualMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_hJualMouseEntered

    private void hJualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hJualMouseClicked
        // TODO add your handling code here:
        hJual.setText("");
    }//GEN-LAST:event_hJualMouseClicked

    private void stObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stObatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stObatActionPerformed

    private void stObatItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_stObatItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_stObatItemStateChanged

    private void tObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tObatMouseClicked
        // TODO add your handling code here:
        int baris = tObat.getSelectedRow();
        kObat.setText(tObat.getModel().getValueAt(baris, 0).toString());
        nObat.setText(tObat.getModel().getValueAt(baris, 1).toString());
        sObat.setText(tObat.getModel().getValueAt(baris, 2).toString());
        stObat.setSelectedItem(tObat.getModel().getValueAt(baris, 3).toString());
        hKontrak.setText(tObat.getModel().getValueAt(baris, 4).toString());
        hJual.setText(tObat.getModel().getValueAt(baris, 5).toString());
    }//GEN-LAST:event_tObatMouseClicked

    private void CariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CariMouseClicked
        // TODO add your handling code here:
        Cari.setText("");
    }//GEN-LAST:event_CariMouseClicked

    private void CariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CariKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Object header[]={"Kode Obat","Nama Obat","Stok Obat","Satuan","Harga Kontrak","Harga Jual"};
            DefaultTableModel data=new DefaultTableModel(null,header);
            tObat.setModel(data);

            String sql="Select * from obat where kode_obat like '%" + Cari.getText() + "%'" + "or nama_obat like '%" + Cari.getText()+ "%'";
            try {
                rs = st.executeQuery(sql);
                while (rs.next()){
                    String kolom1=rs.getString(1);
                    String kolom2=rs.getString(2);
                    String kolom3=rs.getString(3);
                    String kolom4=rs.getString(4);
                    String kolom5=rs.getString(5);
                    String kolom6=rs.getString(6);
                    String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6};
                    data.addRow(kolom);
                }
            }catch (SQLException e){
            }
        }
    }//GEN-LAST:event_CariKeyPressed

    private void BatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BatalActionPerformed
        // TODO add your handling code here:
        Tambah.setText("Tambah");
        Update.setText("Update");
        Hapus.setText("Hapus");
        Tambah.setEnabled(true);
        Update.setEnabled(true);
        Hapus.setEnabled(true);
        siapIsi(false);
        Batal.setEnabled(false);
        clearData();
    }//GEN-LAST:event_BatalActionPerformed

    private void sObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sObatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sObatActionPerformed

    private void sObatKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sObatKeyTyped
        // TODO add your handling code here:
        char karakter = evt.getKeyChar();
        if (!(Character.isDigit(karakter) || karakter==KeyEvent.VK_BACK_SPACE))
        {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_sObatKeyTyped

    private void hKontrakKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hKontrakKeyTyped
        // TODO add your handling code here:
        char karakter = evt.getKeyChar();
        if (!(Character.isDigit(karakter) || karakter==KeyEvent.VK_BACK_SPACE))
        {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_hKontrakKeyTyped

    private void hJualKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hJualKeyTyped
        // TODO add your handling code here:
        char karakter = evt.getKeyChar();
        if (!(Character.isDigit(karakter) || karakter==KeyEvent.VK_BACK_SPACE))
        {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_hJualKeyTyped

    private void nObatKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nObatKeyTyped
        // TODO add your handling code here:
        char karakter = evt.getKeyChar();
        if (!(Character.isAlphabetic(karakter) || karakter==KeyEvent.VK_BACK_SPACE))
        {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_nObatKeyTyped

    private void kObatKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kObatKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_kObatKeyTyped

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
            java.util.logging.Logger.getLogger(CashObat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CashObat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CashObat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CashObat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CashObat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Batal;
    private javax.swing.JTextField Cari;
    private javax.swing.JButton Hapus;
    private javax.swing.JButton Tambah;
    private javax.swing.JButton Update;
    private javax.swing.JLabel bClose2;
    private javax.swing.JTextField hJual;
    private javax.swing.JTextField hKontrak;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField kObat;
    private javax.swing.JTextField nObat;
    private javax.swing.JTextField sObat;
    private javax.swing.JComboBox<String> stObat;
    private javax.swing.JTable tObat;
    // End of variables declaration//GEN-END:variables
}
