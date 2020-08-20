/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apocash;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.*;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Fian_Nur
 */
public final class CashTransaksi extends javax.swing.JFrame {

    private DefaultTableModel TabModel;
    Connection conn;
    Statement st;
    ResultSet rs;
    String sql;
    JasperReport JasRep;
    JasperPrint JasPri;
    Map param  = new HashMap();
    JasperDesign JasDes;

    /**
     * Creates new form CashTransaksi
     */
    public CashTransaksi() {
        initComponents();
        kObat2.setVisible(false);
        hStok.setVisible(false);
        Stok.setVisible(false);
        subTotal.setVisible(false);
        tabelTrans();
        siapIsi(false);
        tampilData();
    }

    public void Connection() {
        Connect db = new Connect();
        db.connect();
        conn = db.conn;
        st = db.st;
    }

    public void getDate() {
        ActionListener taskperformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                SimpleDateFormat tgl = new SimpleDateFormat("EEEE-dd-MMM-YYYY");
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                Date dt = new Date();
                int nilai_jam = dt.getHours();
                int nilai_menit = dt.getMinutes();
                int nilai_detik = dt.getSeconds();
                if (nilai_jam <= 9) {
                    nol_jam = "0";
                }
                if (nilai_menit <= 9) {
                    nol_menit = "0";
                }
                if (nilai_detik <= 9) {
                    nol_detik = "0";
                }
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                Jam.setText("" + jam + " : " + menit + " : " + detik + "");
                Tgl.setText(tgl.format(dt));
            }
        };
        new javax.swing.Timer(10, taskperformer).start();
    }

    private void kodeTransaksi() {
        try {
            Connection();
            String sql = "select right (kode_transaksi,2)+1 from transaksi ";
            rs = st.executeQuery(sql);
            if (rs.next()) {
                rs.last();
                String no = rs.getString(1);
                while (no.length() < 3) {
                    no = "0" + no;
                    kdTrans.setText("KT" + no);
                }
            } else {
                kdTrans.setText("KT001");
            }
        } catch (Exception e) {
        }
    }

    private void clearButton() {
        kdTrans.setText("Kode Transakasi");
        Tgl.setText("Tanggal");
        Jam.setText("Jam");
        kObat.setText("Kode Obat");
        nObat.setText("Nama Obat");
        hObat.setText("Harga Obat");
        jObat.setText("Jumlah Beli");
        Cari.setText("Cari");
        Total.setText("Total");
        Bayar.setText("Bayar");
        Kembali.setText("Kembali");
    }

    private void siapIsi(boolean a) {
        kdTrans.setEnabled(a);
        Tgl.setEnabled(a);
        Jam.setEnabled(a);
        kObat.setEnabled(a);
        nObat.setEnabled(a);
        hObat.setEnabled(a);
        jObat.setEnabled(a);
        add.setEnabled(a);
        Cari.setEnabled(a);
        Total.setEnabled(a);
        Bayar.setEnabled(a);
        Kembali.setEnabled(a);
    }

    private void tabelTrans() {
        Object header[] = {"Kode Obat", "Nama Obat", "Harga Jual", "Jumlah", "Subtotal"};
        TabModel = new DefaultTableModel(null, header);
        tTrans.setModel(TabModel);
    }

    private void tampilData() {
        DefaultTableModel setTable = new DefaultTableModel();
        setTable.addColumn("Kode Obat");
        setTable.addColumn("Nama Obat");
        setTable.addColumn("Stok");
        setTable.addColumn("Harga Satuan");

        try {
            Connection();
            String sql = "select * from obat";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Object[] klm = new Object[4];
                klm[0] = rs.getString("kode_obat");
                klm[1] = rs.getString("nama_obat");
                klm[2] = rs.getString("stok");
                klm[3] = rs.getString("harga_jual");
                setTable.addRow(klm);
            }
            tStok.setModel(setTable);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Kesalahan Query");
        }
    }

    public void ambilData() {
        try {
            String kolom1 = kObat.getText();
            String kolom2 = nObat.getText();
            String kolom3 = hObat.getText();
            String kolom4 = jObat.getText();
            String kolom5 = subTotal.getText();
            String kolom[] = {kolom1, kolom2, kolom3, kolom4, kolom5};
            TabModel.addRow(kolom);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan");
        }
    }

    private void simpanData() {
        try {
            Connection();
            Date skrg = new Date();
            SimpleDateFormat frm = new SimpleDateFormat("yyyy-MM-dd");
            String tanggal = frm.format(skrg);
            int t = tTrans.getRowCount();
            for (int x = 0; x < t; x++) {
                String kodeobat = tTrans.getValueAt(x, 0).toString();
                String namaobat = tTrans.getValueAt(x, 1).toString();
                int hrg = Integer.parseInt(tTrans.getValueAt(x, 2).toString());
                int jmlh = Integer.parseInt(tTrans.getValueAt(x, 3).toString());
                int subtot = Integer.parseInt(tTrans.getValueAt(x, 4).toString());

                String sql = "insert into transaksi values('" + kdTrans.getText() + "','" + tanggal + "','" + kodeobat + "','" + namaobat + "','"
                        + hrg + "','" + jmlh + "','" + subtot + "','" + Total.getText() + "','" + Bayar.getText() + "','" + Kembali.getText() + "')";
                st.executeUpdate(sql);
            }
            JOptionPane.showMessageDialog(null, "Transaksi Berhasil");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Transaksi Gagal");
        }
    }

    private void cetakData() {
        try {
            Connection();
            Date skrg = new Date();
            SimpleDateFormat frm = new SimpleDateFormat("yyyy-MM-dd");
            String tanggal = frm.format(skrg);
            int t = tTrans.getRowCount();

            String sql = "delete from cetak_bon";
            st.executeUpdate(sql);

            for (int x = 0; x < t; x++) {
                String kodeobat = tTrans.getValueAt(x, 0).toString();
                String namaobat = tTrans.getValueAt(x, 1).toString();
                int hrg = Integer.parseInt(tTrans.getValueAt(x, 2).toString());
                int jmlh = Integer.parseInt(tTrans.getValueAt(x, 3).toString());
                int subtot = Integer.parseInt(tTrans.getValueAt(x, 4).toString());

                sql = "insert into cetak_bon values('" + kdTrans.getText() + "','" + tanggal + "','" + kodeobat + "','" + namaobat + "','"
                        + hrg + "','" + jmlh + "','" + subtot + "')";
                st.executeUpdate(sql);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Transaksi Gagal");
        }
    }

    private void dataBayar() {
        try {
            Connection();
            String sql = "delete from bayar";
            st.executeUpdate(sql);
            sql = "insert into bayar values('" + Total.getText() + "','" + Bayar.getText() + "','" + Kembali.getText() + "')";
            st.executeUpdate(sql);
        } catch (Exception e) {

        }
    }

    private void updateData() {
        try {
            Connection();
            String sql = "update obat set stok='" + hStok.getText() + "' where kode_obat='" + kObat2.getText() + "'";
            st.executeUpdate(sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan Update");
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
        jPanel2 = new javax.swing.JPanel();
        bClose = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        add = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tTrans = new javax.swing.JTable();
        jObat = new javax.swing.JTextField();
        Tambah = new javax.swing.JButton();
        Simpan = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tStok = new javax.swing.JTable();
        Cari = new javax.swing.JTextField();
        kObat2 = new javax.swing.JTextField();
        hStok = new javax.swing.JTextField();
        Stok = new javax.swing.JTextField();
        subTotal = new javax.swing.JTextField();
        Jam = new javax.swing.JTextField();
        Tgl = new javax.swing.JTextField();
        kdTrans = new javax.swing.JTextField();
        hObat = new javax.swing.JTextField();
        nObat = new javax.swing.JTextField();
        kObat = new javax.swing.JTextField();
        Total = new javax.swing.JTextField();
        Bayar = new javax.swing.JTextField();
        Kembali = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBackground(new java.awt.Color(0, 51, 204));

        bClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/close.png"))); // NOI18N
        bClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bCloseMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ApoCash");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bClose))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bClose)
            .addComponent(jLabel1)
        );

        add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add_new.png"))); // NOI18N
        add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addMouseClicked(evt);
            }
        });

        tTrans.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tTrans);

        jObat.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jObat.setText("Jumlah Beli");
        jObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jObatMouseClicked(evt);
            }
        });
        jObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jObatKeyTyped(evt);
            }
        });

        Tambah.setBackground(new java.awt.Color(0, 51, 153));
        Tambah.setText("Tambah");
        Tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TambahActionPerformed(evt);
            }
        });

        Simpan.setBackground(new java.awt.Color(51, 204, 0));
        Simpan.setText("Simpan");
        Simpan.setEnabled(false);
        Simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SimpanActionPerformed(evt);
            }
        });

        tStok.setModel(new javax.swing.table.DefaultTableModel(
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
        tStok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tStokMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tStok);

        Cari.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Cari.setText("Cari Obat");
        Cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CariKeyPressed(evt);
            }
        });

        kObat2.setEditable(false);
        kObat2.setBackground(new java.awt.Color(255, 255, 255));
        kObat2.setDisabledTextColor(new java.awt.Color(255, 255, 255));

        hStok.setEditable(false);
        hStok.setBackground(new java.awt.Color(255, 255, 255));
        hStok.setDisabledTextColor(new java.awt.Color(255, 255, 255));

        Stok.setEditable(false);
        Stok.setBackground(new java.awt.Color(255, 255, 255));
        Stok.setDisabledTextColor(new java.awt.Color(255, 255, 255));

        subTotal.setEditable(false);
        subTotal.setBackground(new java.awt.Color(255, 255, 255));
        subTotal.setDisabledTextColor(new java.awt.Color(255, 255, 255));

        Jam.setEditable(false);
        Jam.setBackground(new java.awt.Color(255, 255, 255));
        Jam.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Jam.setText("Jam");

        Tgl.setEditable(false);
        Tgl.setBackground(new java.awt.Color(255, 255, 255));
        Tgl.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Tgl.setText("Tanggal");

        kdTrans.setEditable(false);
        kdTrans.setBackground(new java.awt.Color(255, 255, 255));
        kdTrans.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        kdTrans.setText("Kode Transaksi");

        hObat.setEditable(false);
        hObat.setBackground(new java.awt.Color(255, 255, 255));
        hObat.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        hObat.setText("Harga");

        nObat.setEditable(false);
        nObat.setBackground(new java.awt.Color(255, 255, 255));
        nObat.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        nObat.setText("Nama Obat");

        kObat.setEditable(false);
        kObat.setBackground(new java.awt.Color(255, 255, 255));
        kObat.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        kObat.setText("Kode Obat");

        Total.setEditable(false);
        Total.setBackground(new java.awt.Color(255, 255, 255));
        Total.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Total.setText("Total");

        Bayar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Bayar.setText("Bayar");
        Bayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BayarMouseClicked(evt);
            }
        });
        Bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BayarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                BayarKeyTyped(evt);
            }
        });

        Kembali.setEditable(false);
        Kembali.setBackground(new java.awt.Color(255, 255, 255));
        Kembali.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Kembali.setText("Kembali");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(kObat)
                            .addComponent(kdTrans, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Tgl)
                            .addComponent(nObat, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(hObat, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jObat, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(kObat2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(hStok, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Stok, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(subTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Jam, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Kembali, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                            .addComponent(Bayar)
                            .addComponent(Total))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                    .addComponent(Cari))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Cari)
                    .addComponent(Tgl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(kdTrans)
                    .addComponent(Jam, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kObat2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hStok, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Stok, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(subTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(kObat, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(nObat)
                            .addComponent(add, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jObat, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(hObat, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(9, 9, 9)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(Tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Total))
                        .addGap(18, 18, 18)
                        .addComponent(Bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bCloseMouseClicked
        // TODO add your handling code here:
        this.dispose();
        CashUtama CS = new CashUtama();
        CS.setVisible(true);
    }//GEN-LAST:event_bCloseMouseClicked

    private void TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TambahActionPerformed
        // TODO add your handling code here:
        if (Tambah.getText().equalsIgnoreCase("Tambah")) {
            Tambah.setText("Clear");
            siapIsi(true);
            Total.setText("0");
            getDate();
            kodeTransaksi();
        } else {
            Tambah.setText("Tambah");
            clearButton();
            siapIsi(false);
            TabModel.getDataVector().removeAllElements();
            TabModel.fireTableDataChanged();
            Simpan.setEnabled(false);
        }
    }//GEN-LAST:event_TambahActionPerformed

    private void tStokMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tStokMouseClicked
        // TODO add your handling code here:
        int baris = tStok.getSelectedRow();
        kObat.setText(tStok.getModel().getValueAt(baris, 0).toString());
        kObat2.setText(tStok.getModel().getValueAt(baris, 0).toString());
        nObat.setText(tStok.getModel().getValueAt(baris, 1).toString());
        hObat.setText(tStok.getModel().getValueAt(baris, 3).toString());
        Stok.setText(tStok.getModel().getValueAt(baris, 2).toString());
    }//GEN-LAST:event_tStokMouseClicked

    private void addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseClicked
        // TODO add your handling code here:
        int hrg = Integer.parseInt(hObat.getText());
        int jmlh = Integer.parseInt(jObat.getText());
        int stk = Integer.parseInt(Stok.getText());
        int total = Integer.parseInt(Total.getText());

        if (jmlh > stk) {
            JOptionPane.showMessageDialog(null, "Stok Tidak Mencukupi");
        } else {
            int subtot = hrg * jmlh;
            subTotal.setText(Integer.toString(subtot));

            int hasilstok = stk - jmlh;
            hStok.setText(Integer.toString(hasilstok));

            int totbay = total + (hrg * jmlh);
            Total.setText(Integer.toString(totbay));

            ambilData();
            updateData();
            kObat.setText("Kode Obat");
            nObat.setText("Nama Obat");
            hObat.setText("Harga Obat");
            jObat.setText("Jumlah Beli");
        }
    }//GEN-LAST:event_addMouseClicked

    private void jObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jObatMouseClicked
        // TODO add your handling code here:
        jObat.setText("");
    }//GEN-LAST:event_jObatMouseClicked

    private void SimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SimpanActionPerformed
        // TODO add your handling code here:
        if (kdTrans.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Lengkapi Data");
        } else {
            simpanData();
            cetakData();
            dataBayar();
            tampilData();
            try{
                File report = new File(getClass().getResource("CashBon.jrxml").getPath());
                JasDes = JRXmlLoader.load(report);
                param.clear();
                JasRep = JasperCompileManager.compileReport(JasDes);
                JasPri = JasperFillManager.fillReport(JasRep, param, conn);
                JasperViewer.viewReport(JasPri, false);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_SimpanActionPerformed

    private void BayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BayarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int total = Integer.parseInt(Total.getText());
            int bayar = Integer.parseInt(Bayar.getText());
            if (bayar < total) {
                JOptionPane.showMessageDialog(null, "Jumlah bayar tidak mencukupi");
                Bayar.requestFocus();
            } else {
                int kembali = bayar - total;
                Kembali.setText(Integer.toString(kembali));
                Simpan.setEnabled(true);
            }
        }
    }//GEN-LAST:event_BayarKeyPressed

    private void CariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CariKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Object header[] = {"Kode Obat", "Nama Obat", "Stok", "Harga Satuan"};
            DefaultTableModel data = new DefaultTableModel(null, header);
            tStok.setModel(data);

            String sql = "Select kode_obat,nama_obat,stok,harga_jual from obat where kode_obat like '%" + Cari.getText() + "%'" + "or nama_obat like '%" + Cari.getText() + "%'";
            try {
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    String kolom1 = rs.getString(1);
                    String kolom2 = rs.getString(2);
                    String kolom3 = rs.getString(3);
                    String kolom4 = rs.getString(4);
                    String kolom[] = {kolom1, kolom2, kolom3, kolom4};
                    data.addRow(kolom);
                }
            } catch (SQLException e) {
            }
        }
    }//GEN-LAST:event_CariKeyPressed

    private void BayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BayarMouseClicked
        // TODO add your handling code here:
        Bayar.setText("");
    }//GEN-LAST:event_BayarMouseClicked

    private void jObatKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jObatKeyTyped
        // TODO add your handling code here:
        char karakter = evt.getKeyChar();
        if (!(Character.isDigit(karakter) || karakter==KeyEvent.VK_BACK_SPACE))
        {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_jObatKeyTyped

    private void BayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BayarKeyTyped
        // TODO add your handling code here:
        char karakter = evt.getKeyChar();
        if (!(Character.isDigit(karakter) || karakter==KeyEvent.VK_BACK_SPACE))
        {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_BayarKeyTyped

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
            java.util.logging.Logger.getLogger(CashTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CashTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CashTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CashTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CashTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Bayar;
    private javax.swing.JTextField Cari;
    private javax.swing.JTextField Jam;
    private javax.swing.JTextField Kembali;
    private javax.swing.JButton Simpan;
    private javax.swing.JTextField Stok;
    private javax.swing.JButton Tambah;
    private javax.swing.JTextField Tgl;
    private javax.swing.JTextField Total;
    private javax.swing.JLabel add;
    private javax.swing.JLabel bClose;
    private javax.swing.JTextField hObat;
    private javax.swing.JTextField hStok;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jObat;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField kObat;
    private javax.swing.JTextField kObat2;
    private javax.swing.JTextField kdTrans;
    private javax.swing.JTextField nObat;
    private javax.swing.JTextField subTotal;
    private javax.swing.JTable tStok;
    private javax.swing.JTable tTrans;
    // End of variables declaration//GEN-END:variables
}
