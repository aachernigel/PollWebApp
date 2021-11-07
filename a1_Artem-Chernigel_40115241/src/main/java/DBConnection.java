import java.sql.*;

public class DBConnection {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/";
    static final String DB_NAME ="polldb?useSSL=false";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "obobyandex0919";
    static Connection conn = null;

    public static Connection getConnection(){
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
            return conn;
        } catch(SQLException e){
            throw new RuntimeException("Error connecting to database", e);
        } catch(ClassNotFoundException e){
            throw new RuntimeException("Error Class Not Found", e);
        }
    }

    public static void closeConnection() throws SQLException {
        if(conn != null)
            conn.close();
    }
}
