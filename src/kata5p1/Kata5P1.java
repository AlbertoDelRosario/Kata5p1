
package kata5p1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


public class Kata5P1 {
    private Connection conn;
   
    public static void main(String[] args) {
        String url = "jdbc:sqlite:KATA5.db";
        Kata5P1 kt = new Kata5P1();
        kt.connect(url);
        kt.selectAll("PEOPLE");
    }
    
    private Connection connect(String url) {
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Conexión con SQLite establecida");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            conn = null;
        }
        return conn;
    }

    public void selectAll(String table){
        String sql = "SELECT * FROM " + table;
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    System.out.print(rs.getString(rsmd.getColumnName(i)) + "\t");
                }
                System.out.println("");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    } 
}
