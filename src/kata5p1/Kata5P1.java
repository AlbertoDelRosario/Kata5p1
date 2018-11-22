package kata5p1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Kata5P1 {
    private Connection conn;
   
    public static void main(String[] args) {
        MailListReader m = new MailListReader("emails.txt");
        List<String> emails = m.read();
        String url = "jdbc:sqlite:KATA5.db";
        Kata5P1 kt = new Kata5P1();
        kt.connect(url);
        kt.selectAll("PEOPLE");
        //kt.createNewTable(); --> Descomentar si no se ha creado la tabla con anterioridad.
        for (String email : emails) {
            kt.insert("direcc_email", email);
        }
        
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
    
    public void createNewTable() {
        String sql = "CREATE TABLE IF NOT EXISTS direcc_email (\n"
        + " id integer PRIMARY KEY AUTOINCREMENT,\n"
        + " direccion text NOT NULL);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla creada");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void insert(String table, String email) {
        String sql = "INSERT INTO " + table + "(direccion) VALUES(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
