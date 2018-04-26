/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fujikura.it.assets.controller;

import fujikura.it.assets.dao.ExcelConstructor;
import fujikura.it.assets.database.Database;
import fujikura.it.assets.excel.Excel;
import fujikura.it.assets.excel.OSDetector;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * @author GlobalRoot
 */
public class Object_Tabale extends javax.swing.JFrame {

    /**
     * Creates new form Object_Tabale
     */
    public Object_Tabale() {
        initComponents();
        initDepts();
        jComboBox1.setSelectedIndex(-1);
    }

    private String DBip = "localhost";
    private String DBip2 = "192.168.0.102";
    private String url = "jdbc:sqlserver://localhost:1433;databasename=stock";
    private final String User = "sa";
    private final String password = "hk7w2svu";

    private Connection connection;
    private PreparedStatement preparedStatement;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{

                }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fujikura/it/assets/resource/lojo 65%.jpg"))); // NOI18N

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(95, 194, 255));
        jButton1.setText("REPORT");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(95, 194, 255));
        jButton2.setText("Download Excel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Copyright by Victor Kmin");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("© 2018");

        jTextField1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(95, 194, 255));
        jButton3.setText("Search");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Search by serial number:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Search by factory:");

        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(95, 194, 255));
        jButton4.setText("Search");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(298, 298, 298)
                                                .addComponent(jLabel6)))
                                .addContainerGap())
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(129, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(119, 119, 119))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel4)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jButton4))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel2)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jButton3)))
                                                .addGap(121, 121, 121))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(266, 266, 266))))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(45, 45, 45)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addContainerGap())
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
    }// </editor-fold>//GEN-END:initComponents


    public boolean TestPort(String ip) throws IOException {
        //test port
        try {
            Socket test = new Socket();
            test.setSoTimeout(200);
            test.connect(new InetSocketAddress(ip, 1433), 55);

            test.close();
            return true;
        } catch (UnknownHostException e) {
            System.out.println("Невідовий хост " + ip);
            // unknown host
        } catch (IOException e) {
            System.out.println("Порт недоступний " + ip);
            // io exception, service probably not running
        }
        return false;
    }

    public void initDepts() {

        try {
            if (TestPort(DBip)) {
                this.url = "jdbc:jtds:sqlserver://" + DBip + ":1433;databasename=stock";
            } else {
                this.url = "jdbc:jtds:sqlserver://" + DBip2 + ":1433;databasename=stock";
            }

            connection = DriverManager.getConnection(url, User, password);
            preparedStatement = connection.prepareStatement("SELECT [location] FROM [stock].[dbo].[FactoryLoc]");
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                jComboBox1.addItem(resultSet.getString("location"));
            }
            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(INCOMING_Form2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private TableModel tableBuilder() throws IOException, SQLException, ClassNotFoundException {

        Database db = new Database();

        // Створюю масив стрінгів
        String[] columnNames = {"SerialNumber", "Model", "Factory", "Department", "1C ID", "Supplier", "Warranty", "Time"};
        // Дістаю ліст з всіма обєктами
        ArrayList<ExcelConstructor> objectList = new ArrayList<>(db.getObjects());

        // Створюю новий ліст з масивом стрінгів
        ArrayList<String[]> data_list = new ArrayList<>();

        // приствоюю через цикл дані з objectList в data_list
        for (int i = 0; i < objectList.size(); i++) {
            data_list.add(new String[]{objectList.get(i).getSn(), objectList.get(i).getMod(),
                    objectList.get(i).getLoc(), objectList.get(i).getDept(), objectList.get(i).getId_1CId(),
                    objectList.get(i).getSup(), String.valueOf(objectList.get(i).getWarranty()), objectList.get(i).getTm()});
        }

        //Ставлю можель таблиці
        TableModel tableModel = new DefaultTableModel(data_list.toArray(new Object[][]{}), columnNames);

        return tableModel;

    }

    // ЗРОБИТИ УНІВЕРСАЛЬНИМ
    private TableModel tableBuilderBySerial() throws IOException, SQLException, ClassNotFoundException {

        String serial = jTextField1.getText();

        Database db = new Database();

        // Створюю масив стрінгів
        String[] columnNames = {"SerialNumber", "Model", "Factory", "Department", "1C ID", "Supplier", "Warranty", "Time"};
        // Дістаю ліст з всіма обєктами
        ArrayList<ExcelConstructor> objectList = new ArrayList<>(db.searchBySN(serial));

        // Створюю новий ліст з масивом стрінгів
        ArrayList<String[]> data_list = new ArrayList<>();

        // приствоюю через цикл дані з objectList в data_list
        for (int i = 0; i < objectList.size(); i++) {
            data_list.add(new String[]{objectList.get(i).getSn(), objectList.get(i).getMod(),
                    objectList.get(i).getLoc(), objectList.get(i).getDept(), objectList.get(i).getId_1CId(),
                    objectList.get(i).getSup(), String.valueOf(objectList.get(i).getWarranty()), objectList.get(i).getTm()});
        }

        //Ставлю можель таблиці
        TableModel tableModel = new DefaultTableModel(data_list.toArray(new Object[][]{}), columnNames);

        return tableModel;
    }

    private TableModel tableBuilderByLocation() throws IOException, SQLException, ClassNotFoundException {

        String location = jComboBox1.getSelectedItem().toString();

        Database db = new Database();

        // Створюю масив стрінгів
        String[] columnNames = {"SerialNumber", "Model", "Factory", "Department", "1C ID", "Supplier", "Warranty", "Time"};
        // Дістаю ліст з всіма обєктами
        ArrayList<ExcelConstructor> objectList = new ArrayList<>(db.searchByLocation(location));

        // Створюю новий ліст з масивом стрінгів
        ArrayList<String[]> data_list = new ArrayList<>();

        // приствоюю через цикл дані з objectList в data_list
        for (int i = 0; i < objectList.size(); i++) {
            data_list.add(new String[]{objectList.get(i).getSn(), objectList.get(i).getMod(),
                    objectList.get(i).getLoc(), objectList.get(i).getDept(), objectList.get(i).getId_1CId(),
                    objectList.get(i).getSup(), String.valueOf(objectList.get(i).getWarranty()), objectList.get(i).getTm()});
        }

        //Ставлю можель таблиці
        TableModel tableModel = new DefaultTableModel(data_list.toArray(new Object[][]{}), columnNames);

        return tableModel;

    }

    // Кнопка EXCEL
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            Database db = new Database();
            // Заповняю ArrayList даними з методу getObject
            ArrayList<ExcelConstructor> list = new ArrayList<>(db.getObjects());
            // виводжу ліст який був створений в консольку
            System.out.println(list);
            String tmp_dir = "";

            Excel export = new Excel();

            export.ExportCount(tmp_dir + "excel.xlsx", list);

            new OSDetector().open(new File(tmp_dir + "excel.xlsx"));

        } catch (IOException | SQLException ex) {
            Logger.getLogger(Main_Form.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            // Присвоюю таблиі модель з методу вище
            jTable1.setModel(tableBuilder());
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Object_Tabale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    // Пошук по серійнику кнопка Search
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            // Присвоюю таблиі модель з методу вище
            jTable1.setModel(tableBuilderBySerial());
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Object_Tabale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            // Присвоюю таблиі модель з методу вище
            jTable1.setModel(tableBuilderByLocation());
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Object_Tabale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
            java.util.logging.Logger.getLogger(Object_Tabale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Object_Tabale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Object_Tabale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Object_Tabale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Object_Tabale().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
