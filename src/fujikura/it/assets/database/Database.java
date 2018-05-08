/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fujikura.it.assets.database;

import fujikura.it.assets.dao.Assets;
import fujikura.it.assets.dao.ExcelConstructor;
import fujikura.it.assets.dao.transaction;

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

public class Database {

    private String DBip = "localhost";
    private String DBip2 = "192.168.0.102";
    private String url = "jdbc:sqlserver://localhost:1433;databasename=stock";
    
    private String User =  "sa"; 
    private String password  = "hk7w2svu";
    
    private Connection connection;
    private PreparedStatement preparedStatement;
    
    public Database() {
         try {
             if (TestPort(DBip)){
                 this.url="jdbc:jtds:sqlserver://"+DBip+":1433;databasename=stock";
             }else{
                 this.url="jdbc:jtds:sqlserver://"+DBip2+":1433;databasename=stock";
             }
         } catch (IOException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

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

    public boolean Version(String u) throws SQLException {
        boolean valid = false;
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, User, password);
        preparedStatement = connection.prepareStatement("SELECT [version] FROM [stock].[dbo].[version]");
        preparedStatement.executeQuery();
        ResultSet result = preparedStatement.getResultSet();

        System.out.println("version: " + u);

        if (result.next()) {
            String db_version = result.getString("version");
            System.out.println("db_version: " + db_version);
            double a = Double.parseDouble(u);
            double b = Double.parseDouble(db_version);
            if (a == b) {
                valid = true;
                System.out.println("OK");
            } else
                valid = false;
        }
        preparedStatement.close();
        result.close();
        connection.close();

        return valid;
    }

    public boolean SN(String u) throws SQLException, ClassNotFoundException {
        boolean valid = false;
        connection = DriverManager.getConnection(url, User, password);
        preparedStatement = connection.prepareStatement("SELECT[sn] FROM [stock].[dbo].[Object] where [sn] = ?");
        preparedStatement.setString(1, u);
        preparedStatement.executeQuery();
        ResultSet result = preparedStatement.getResultSet();

        System.out.println("serial number: " + u);

        if (result.next()) {
            valid = true;
            System.out.println("serial number: " + result.getString("sn"));
        }
        preparedStatement.close();
        result.close();
        connection.close();

        return valid;
    }

    public int id_action_stock(String u) throws SQLException, ClassNotFoundException {
        int valid = 0;
        connection = DriverManager.getConnection(url, User, password);
        preparedStatement = connection.prepareStatement("SELECT[id] FROM [stock].[dbo].[Object] where [sn] = ?");
        preparedStatement.setString(1, u);
        preparedStatement.executeQuery();
        ResultSet result = preparedStatement.getResultSet();

        System.out.println("serial number: " + u);

        if (result.next()) {
            valid = result.getInt("id");
            System.out.println("id: " + result.getString("id"));
        }
        preparedStatement.close();
        result.close();
        connection.close();

        return valid;
    }

    public boolean Login(String u, String p) throws SQLException, ClassNotFoundException {
        boolean valid = false;
        connection = DriverManager.getConnection(url, User, password);
        preparedStatement = connection.prepareStatement("SELECT [name],[pass] FROM [stock].[dbo].[Program_Users] where [name] = ? and [pass] = ?");
        preparedStatement.setString(1, u);
        preparedStatement.setString(2, p);
        preparedStatement.executeQuery();
        ResultSet result = preparedStatement.getResultSet();

        System.out.println("name: " + u);
        System.out.println("pass: " + p);

        if (result.next()) {
            valid = true;
            System.out.println("result pass: " + result.getString("pass"));
        }
        preparedStatement.close();
        result.close();
        connection.close();

        return valid;
    }

    public int Login_rights(String username) throws SQLException, ClassNotFoundException {
        int rights = 0;
        connection = DriverManager.getConnection(url, User, password);
        preparedStatement = connection.prepareStatement("SELECT [name],[pass],[rights] FROM [stock].[dbo].[Program_Users] where name = ?");
        preparedStatement.setString(1, username);
        preparedStatement.executeQuery();
        ResultSet result = preparedStatement.getResultSet();

        if (result.next()) {
            rights = result.getInt("rights");
            System.out.println(rights);
        }


        preparedStatement.close();
        result.close();
        connection.close();

        return rights;
    }

    public boolean insertStock(Assets trans) throws SQLException {
        connection = DriverManager.getConnection(url, User, password);
        preparedStatement = connection.prepareStatement("INSERT INTO [stock].[dbo].[Object]([sn],[mod],[sup],[loc],[warranty],[tm],[dept])VALUES(?,?,?,?,?,?,?)");
        preparedStatement.setString(1, trans.getSn());
        preparedStatement.setString(2, trans.getModel());
        preparedStatement.setString(3, trans.getSupplier());
        preparedStatement.setString(4, trans.getLocation());
        preparedStatement.setInt(5, trans.getWarranty());
        preparedStatement.setString(6, trans.getTm());
        preparedStatement.setString(7, trans.getDepartment());

        if (preparedStatement.executeUpdate() == 0) {
            return false;
        } else {
            preparedStatement.close();
            connection.close();
            return true;
        }
    }


    public boolean OneCCheck(String oneC_id) throws SQLException {
        boolean valid = false;
        connection = DriverManager.getConnection(url, User, password);
        preparedStatement = connection.prepareStatement("SELECT [1C_ID],[status] FROM [stock].[dbo].[1C_IDs] WHERE [1C_ID] = ?");
        preparedStatement.setString(1, oneC_id);
        preparedStatement.executeQuery();
        ResultSet resultSet = preparedStatement.getResultSet();
        System.out.println("1C id = " + oneC_id);
        if (resultSet.next()) {
            valid = true;
            System.out.println("1CID : " + resultSet.getString("1C_ID"));
        }
        preparedStatement.close();
        resultSet.close();
        connection.close();

        return valid;
    }


    public boolean insertOneC(transaction trans) throws SQLException {
        connection = DriverManager.getConnection(url, User, password);
        preparedStatement = connection.prepareStatement("INSERT INTO [stock].[dbo].[1C_IDs]([1C_ID],[status] )VALUES(?,0)");
        preparedStatement.setString(1, trans.getOneC_id());
        if (preparedStatement.executeUpdate() == 0) {
            System.out.println("Метод insertOneC return false");
            return false;

        } else {
            preparedStatement.close();
            connection.close();
            System.out.println("Метод insertOneC return true");
            return true;
        }
    }

    public boolean updateObject2(transaction trans) throws SQLException {
        System.out.println("Я в методі updateObject1C");
        connection = DriverManager.getConnection(url, User, password);
        preparedStatement = connection.prepareStatement("UPDATE stock.dbo.Object SET [id_1CId] = ?, [loc] = ?, [dept] = ? WHERE [sn] = ?");
        preparedStatement.setString(1, trans.getOneC_id());
        preparedStatement.setString(2, trans.getLoc());
        preparedStatement.setString(3, trans.getDept());
        preparedStatement.setString(4, trans.getSn());
        System.out.println("Метод updateObject2 оновив базу");
        if (preparedStatement.executeUpdate() == 0) {
            System.out.println("Метод updateObject2 return false");
            return false;

        } else {
            preparedStatement.close();
            connection.close();
            System.out.println("Метод updateObject2 return true");
            return true;
        }
    }

    public boolean updateStatus(transaction trans2) throws SQLException {
        connection = DriverManager.getConnection(url, User, password);
        preparedStatement = connection.prepareStatement("UPDATE [stock].[dbo].[1C_IDs] SET [status] = 1 WHERE [1C_ID] = ? ");
        preparedStatement.setString(1, trans2.getOneC_id());

        if (preparedStatement.executeUpdate() == 0) {
            System.out.println("Метод updateObject1C return false");
            return false;

        } else {
            preparedStatement.close();
            connection.close();
            System.out.println("Метод updateObject1C return true");
            return true;
        }
    }

    public boolean snWithOneCId(String sn) throws SQLException {

        boolean valid = false;
        connection = DriverManager.getConnection(url, User, password);
        preparedStatement = connection.prepareStatement("SELECT [sn],[id_1CId] FROM [stock].[dbo].[Object] WHERE [sn] = ? AND [id_1CId] IS NULL");
        preparedStatement.setString(1, sn);
        preparedStatement.executeQuery();
        ResultSet resultSet = preparedStatement.getResultSet();
        System.out.println("SerialNumber324 = " + sn);
        if (resultSet.next()) {
            valid = true;
            System.out.println("SerialNumber327 : " + resultSet.getString("sn"));
        }
        preparedStatement.close();
        resultSet.close();
        connection.close();

        return valid;
    }

    public boolean selectDepts(String inside) throws SQLException {

        boolean valid = false;
        connection = DriverManager.getConnection(url, User, password);
        preparedStatement = connection.prepareStatement("SELECT [dept] FROM [stock].[dbo].[Department] WHERE [inside_f] = ?");
        preparedStatement.setString(1, inside);
        preparedStatement.executeQuery();
        ResultSet resultSet = preparedStatement.getResultSet();
        System.out.println("selectDepts345 = " + inside);
        if (inside != null) {
            System.out.println("Я в циклі selectDepts");
            while (resultSet.next()) {
                valid = true;
            }
        }
        preparedStatement.close();
        resultSet.close();
        connection.close();

        return valid;

    }
    
    public ArrayList<ExcelConstructor> getObjects() throws SQLException {
          ArrayList<ExcelConstructor> list = new ArrayList<>();
          ExcelConstructor object;

          connection = DriverManager.getConnection(url,User,password);
          preparedStatement = connection.prepareStatement("SELECT [sn],[sup],[loc],[warranty] ,[tm],[id_1CId],[mod],[dept] FROM [stock].[dbo].[Object]");
          preparedStatement.executeQuery();
          ResultSet result = preparedStatement.getResultSet(); 
          
          int a = 0;
            while (result.next()) {   
                    object = new ExcelConstructor(result.getString("sn"),result.getString("sup"), 
                        result.getString("loc"),result.getInt("warranty"),result.getString("tm"),
                        result.getString("id_1CId"),result.getString("mod"),result.getString("dept"));
                    list.add(object);
                     System.out.println(object + "  цикл " + a++);
                }

           
          preparedStatement.close();
          result.close(); 
          connection.close();        

          return list;
    }
    
    public ArrayList<ExcelConstructor> searchBySN(String serial) throws SQLException{

          ArrayList<ExcelConstructor> list = new ArrayList<>();
          ExcelConstructor object;

          connection = DriverManager.getConnection(url,User,password);
          preparedStatement = connection.prepareStatement("SELECT [sn],[sup],[loc],[warranty] ,[tm],[id_1CId],[mod],[dept] FROM [stock].[dbo].[Object] WHERE [sn] = ?");
          preparedStatement.setString(1, serial);
          preparedStatement.executeQuery();
          ResultSet result = preparedStatement.getResultSet(); 
          
          int a = 0;
            while (result.next()) {   
                    object = new ExcelConstructor(result.getString("sn"),result.getString("sup"), 
                        result.getString("loc"),result.getInt("warranty"),result.getString("tm"),
                        result.getString("id_1CId"),result.getString("mod"),result.getString("dept"));
                    list.add(object);
                     System.out.println(object + "  цикл " + a++);
                } 
          preparedStatement.close();
          result.close(); 
          connection.close();        

          return list;

    }
    
    
    public ArrayList<ExcelConstructor> searchByLocation(String location) throws SQLException{
        
          ArrayList<ExcelConstructor> list = new ArrayList<>();
          ExcelConstructor object;

          connection = DriverManager.getConnection(url,User,password);
          preparedStatement = connection.prepareStatement("SELECT [sn],[sup],[loc],[warranty] ,[tm],[id_1CId],[mod],[dept] FROM [stock].[dbo].[Object] WHERE [loc] = ?");
          preparedStatement.setString(1, location);
          preparedStatement.executeQuery();
          ResultSet result = preparedStatement.getResultSet(); 
          
          int a = 0;
            while (result.next()) {   
                    object = new ExcelConstructor(result.getString("sn"),result.getString("sup"), 
                        result.getString("loc"),result.getInt("warranty"),result.getString("tm"),
                        result.getString("id_1CId"),result.getString("mod"),result.getString("dept"));
                    list.add(object);
                     System.out.println(object + "  цикл " + a++);
                }

           
          preparedStatement.close();
          result.close(); 
          connection.close();        

          return list;

    }
    
    // Вносить обєкти на сервіс
     public boolean insertService(transaction trans) throws SQLException {
        connection = DriverManager.getConnection(url, User, password);
        preparedStatement = connection.prepareStatement("INSERT INTO [stock].[dbo].[Service]([sn],[supplier],[comment],[time])VALUES(?,?,?,?)");
        preparedStatement.setString(1, trans.getSn());
        preparedStatement.setString(2, trans.getSup());
        preparedStatement.setString(3, trans.getComment());
        preparedStatement.setString(4, trans.getTm());
        if (preparedStatement.executeUpdate() == 0) {
            System.out.println("Метод insertService return false");
            return false;

        } else {
            preparedStatement.close();
            connection.close();
            System.out.println("Метод insertService return true");
            return true;
        }
    }
     
       public boolean removeFromService(transaction trans2) throws SQLException {
        connection = DriverManager.getConnection(url, User, password);
        preparedStatement = connection.prepareStatement("DELETE FROM [stock].[dbo].[Service] WHERE [sn] = ?");
        preparedStatement.setString(1, trans2.getSn());
        if (preparedStatement.executeUpdate() == 0) {
            return false;

        } else {
            preparedStatement.close();
            connection.close();
            return true;
        }
    }
     
      public boolean updateLocation(transaction trans2) throws SQLException {
        connection = DriverManager.getConnection(url, User, password);
        preparedStatement = connection.prepareStatement("UPDATE [stock].[dbo].[Object] SET [dept] = ?, [loc] = ? WHERE [sn] = ? ");
        preparedStatement.setString(1, trans2.getDept());
        preparedStatement.setString(2, trans2.getLoc());
        preparedStatement.setString(3, trans2.getSn());

        if (preparedStatement.executeUpdate() == 0) {
            return false;

        } else {
            preparedStatement.close();
            connection.close();
            return true;
        }
    }
      
      public boolean updateFromService(transaction trans) throws SQLException {
        connection = DriverManager.getConnection(url, User, password);
        preparedStatement = connection.prepareStatement("UPDATE [stock].[dbo].[Object] SET [dept] = ? WHERE [sn] = ? ");
        preparedStatement.setString(1, trans.getLoc());
        preparedStatement.setString(2, trans.getSn());

        if (preparedStatement.executeUpdate() == 0) {
            return false;

        } else {
            preparedStatement.close();
            connection.close();
            return true;
        }

    }

        public boolean trans1C(transaction trans2) throws SQLException {
        connection = DriverManager.getConnection(url, User, password);
        preparedStatement = connection.prepareStatement("INSERT INTO [stock].[dbo].[transactions]([username],[sn],[action],[id_action],[tm])VALUES(?,?,?,?,?)");
        preparedStatement.setString(1, trans2.getUser());
        preparedStatement.setString(2, "inputOneC");
        preparedStatement.setString(3, "inputOneC");
        preparedStatement.setInt(4, trans2.getId_action());
        preparedStatement.setString(5, trans2.getTm());

        if (preparedStatement.executeUpdate() == 0) {
            return false;
        } else {
            preparedStatement.close();
            connection.close();
            return true;
        }
    }


    public boolean transaction(transaction trans2) throws SQLException {
        connection = DriverManager.getConnection(url, User, password);
        preparedStatement = connection.prepareStatement("INSERT INTO [stock].[dbo].[transactions]([username],[sn],[action],[id_action],[tm])VALUES(?,?,?,?,?)");
        preparedStatement.setString(1, trans2.getUser());
        preparedStatement.setString(2, trans2.getSn());
        preparedStatement.setString(3, trans2.getAction());
        preparedStatement.setInt(4, trans2.getId_action());
        preparedStatement.setString(5, trans2.getTm());

        if (preparedStatement.executeUpdate() == 0) {
            return false;
        } else {
            preparedStatement.close();
            connection.close();
            return true;
            
        }
    }
}
