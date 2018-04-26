/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fujikura.it.assets.controller;

import fujikura.it.assets.dao.transaction;
import fujikura.it.assets.database.Database;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Use_Form2 extends javax.swing.JFrame {

    int a = -1;

    public Use_Form2() throws SQLException {
        initComponents();
        initComboBox();


        // Ставлю значення за замовчуванням null
        jComboBox2.setSelectedIndex(a);
        jComboBox3.setSelectedIndex(a);
        jComboBox5.setSelectedIndex(a);

        //Блокую доступ до кнопок InsiseFactory, Department, OK
        jComboBox3.setEnabled(false);
        jComboBox5.setEnabled(false);
        jButton1.setEnabled(false);
        jLabel11.setVisible(false);

    }

    // Ініціалізую змінні для зєдання з базою
    private Connection connection; // 1C ID
    private Connection connection2; // Location
    private Connection connection3; // Stock (Inssde)
    private Connection connection5; // Department

    private PreparedStatement preparedStatement;
    private PreparedStatement preparedStatement2;
    private PreparedStatement preparedStatement3;
    private PreparedStatement preparedStatement5;
    private String url = "jdbc:sqlserver://localhost:1433;databasename=stock";
    private final String User = "sa";
    private final String password = "hk7w2svu";
    private final String DBip = "localhost";
    private final String DBip2 = "192.168.0.102";

    transaction trans = new transaction();
    transaction trans2 = new transaction();

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


    // Витягує в випадаючі меню всі доступні 1С айдішки, де статус = 0
    public void initComboBox() throws SQLException {

        try {

            if (TestPort(DBip)) {
                this.url = "jdbc:jtds:sqlserver://" + DBip + ":1433;databasename=stock";
            } else {
                this.url = "jdbc:jtds:sqlserver://" + DBip2 + ":1433;databasename=stock";
            }
            connection = DriverManager.getConnection(url, User, password);
            System.out.println("Відбувся конект");
            preparedStatement = connection.prepareStatement("SELECT [1C_ID],[status] FROM [stock].[dbo].[1C_IDs] WHERE [status] = 0");
            preparedStatement.executeQuery();
            ResultSet result = preparedStatement.getResultSet();
            System.out.println("Підтягнули таблицю 1C_IDs");

            while (result.next()) {
                jComboBox1.addItem(result.getString("1C_ID"));
                System.out.println("Я в циклі");
            }
            preparedStatement.close();
            result.close();
            connection.close();

        } catch (IOException | SQLException ex) {
            Logger.getLogger(INCOMING_Form2.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Дістаю всі локації
        connection2 = DriverManager.getConnection(url, User, password);
        preparedStatement2 = connection2.prepareStatement("SELECT [location] FROM [stock].[dbo].[FactoryLoc]");
        preparedStatement2.executeQuery();
        ResultSet result2 = preparedStatement2.getResultSet();

        while (result2.next()) {
            jComboBox2.addItem(result2.getString("location"));
        }
        preparedStatement2.close();
        result2.close();
        connection2.close();


////              Дістаю всі inside factore (Office/Production)
//            connection3 = DriverManager.getConnection(url, User, password);
//            preparedStatement3 = connection3.prepareStatement("SELECT [inside] FROM [stock].[dbo].[insideFactory]");
//            preparedStatement3.executeQuery();
//            ResultSet result3 = preparedStatement3.getResultSet();
//            
//            while (result3.next()) {
//                jComboBox3.addItem(result3.getString("inside"));
//            }
//            preparedStatement3.close();
//            result3.close();
//            connection3.close();

    }

    public void initDepts() throws SQLException {
//             Дістаю всі департаменти
        String inside = (String) jComboBox3.getSelectedItem();
        connection5 = DriverManager.getConnection(url, User, password);
        preparedStatement5 = connection5.prepareStatement("SELECT [dept] FROM [stock].[dbo].[Department] WHERE [inside_f] = ?");
        preparedStatement5.setString(1, inside);
        preparedStatement5.executeQuery();
        ResultSet result5 = preparedStatement5.getResultSet();

        while (result5.next()) {
            jComboBox5.addItem(result5.getString("dept"));
        }
        preparedStatement5.close();
        result5.close();
        connection5.close();
    }

    public void selectedInsideFactory() throws IOException, SQLException {


        String inside = (String) jComboBox3.getSelectedItem();
        System.out.println("ComBox3 видав " + inside);
        Database db = new Database();
        db.selectDepts(inside);

    }


    // Перевіряє правильність вводу серійника
    public boolean CheckSN() throws IOException, SQLException, ClassNotFoundException {
        Database db = new Database();

        String serailNumber = jTextField1.getText().trim();

        if (db.SN(serailNumber) == false) {
            JOptionPane.showMessageDialog(null, "Серійний номер " + serailNumber + " не знайдено. Перевірьте правильнось вводу", "ERROR", JOptionPane.WARNING_MESSAGE);

            return false;
        }
        if (serailNumber == "") {
            return false;
        }

        return true;
    }

    public boolean checkSnWith1C() throws IOException, SQLException {
        Database db = new Database();
        String sn = jTextField1.getText().trim();

        if (db.snWithOneCId(sn) == false) {
            JOptionPane.showMessageDialog(null, "Cерійному номеру " + sn + " уже роздано 1С ID.", "ERROR", JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            System.out.println("checkSnWith1C = true");
            return true;
        }
    }

    // Метод привязує до серійника 1С айдішку та оновляє статус на 1 (використаний)
    public void update() throws IOException, SQLException {
        String serailNumber = jTextField1.getText().trim();
        System.out.println("SN = " + serailNumber);
        String OneC_ID = String.valueOf(jComboBox1.getSelectedItem());
        System.out.println("1C ID = " + OneC_ID);
        String loc = String.valueOf(jComboBox2.getSelectedItem());
        System.out.println("Location = " + loc);
        String dept = String.valueOf(jComboBox5.getSelectedItem());
        System.out.println("Department = " + dept);

        Database db = new Database();
        System.out.println("DB connect");
        trans.setSn(serailNumber);
        trans.setOneC_id(OneC_ID);
        trans.setLoc(loc);
        trans.setDept(dept);
        trans2.setOneC_id(OneC_ID);
        db.updateObject2(trans);
        db.updateStatus(trans2);


    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(95, 194, 255));
        jButton3.setText("OK");
        jButton3.setToolTipText("");
        jButton3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(95, 194, 255), 1, true));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jComboBox4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jComboBox4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(95, 194, 255)));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel8.setBackground(new java.awt.Color(204, 204, 255));
        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(95, 194, 255));
        jLabel8.setText("Select location :");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fujikura/it/assets/resource/lojo 65%.jpg"))); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jComboBox1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(95, 194, 255)));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(204, 204, 255));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(95, 194, 255));
        jLabel1.setText("Select 1C ID :");

        jLabel3.setBackground(new java.awt.Color(204, 204, 255));
        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(95, 194, 255));
        jLabel3.setText("Input serial number :");

        jTextField1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(95, 194, 255)));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(95, 194, 255));
        jButton1.setText("OK");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(95, 194, 255)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jButton2.setForeground(new java.awt.Color(95, 194, 255));
        jButton2.setText("CANCEL");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(95, 194, 255)));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Copyright by Victor Kmin");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("© 2018");

        jComboBox2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jComboBox2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(95, 194, 255)));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(204, 204, 255));
        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(95, 194, 255));
        jLabel5.setText("Select location :");

        jComboBox3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Office", "Production"}));
        jComboBox3.setSelectedIndex(-1);
        jComboBox3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(95, 194, 255)));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jLabel7.setBackground(new java.awt.Color(204, 204, 255));
        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(95, 194, 255));
        jLabel7.setText("Select stock :");

        jComboBox5.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jComboBox5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(95, 194, 255)));
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(204, 204, 255));
        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(95, 194, 255));
        jLabel9.setText("Select department :");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 255, 51));
        jLabel11.setText("SUCCESS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap(17, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(jLabel4)
                                                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addComponent(jLabel6)
                                                                                .addGap(46, 46, 46)))
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addGap(103, 103, 103)
                                                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE))
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(jLabel11))))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(jLabel1)
                                                                        .addComponent(jLabel3)
                                                                        .addComponent(jLabel5)
                                                                        .addComponent(jLabel7)
                                                                        .addComponent(jLabel9))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                .addComponent(jTextField1)
                                                                                .addComponent(jComboBox1, 0, 133, Short.MAX_VALUE))
                                                                        .addComponent(jLabel2)
                                                                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addGap(40, 40, 40))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(57, 57, 57)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel6))
                                        .addComponent(jLabel11))
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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {
        if (jComboBox2.getSelectedItem() != null) {
            jLabel11.setVisible(false);
            System.out.println("ACTION COMBO 2");
            jComboBox3.setEnabled(true);

        }
    }


    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        // КНОПКА CANCEL
        new Main_Form().setVisible(true);
        dispose();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // КНОПКА ОК
            if (CheckSN() == true) {

                if (checkSnWith1C() == true) {
                    System.out.println("checkSnWith1C() = true");
                    update();
                    jTextField1.setText("");
                    jComboBox1.removeAllItems();
                    jComboBox2.removeAllItems();
                    initComboBox();
                    jLabel11.setVisible(true);

                } else {
                    System.out.println("checkSnWith1C() = false");
                }

            } else {
                System.out.println("jButton1ActionPerformed = false");
            }
            System.out.println("jButton1ActionPerformed");
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Use_Form2.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        jLabel11.setVisible(false);
        System.out.println("ACTION TEXT 1");
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        if (jComboBox3.getSelectedItem() != null) {
            try {
//                DefaultComboBoxModel.removeAllElements()
                jComboBox5.removeAllItems();
//                jComboBox7.setSelectedItem(null);
                initDepts();
                selectedInsideFactory();
                jComboBox5.setEnabled(true);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(Use_Form2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            if (jComboBox3.getSelectedItem() != null) {
                jComboBox5.setEnabled(true);
            }
            selectedInsideFactory();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(INCOMING_Form2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        if (jComboBox5.getSelectedItem() != null) {
            jButton1.setEnabled(true);
        }
    }//GEN-LAST:event_jComboBox5ActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Use_Form2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new Use_Form2().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(Use_Form2.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
