package PollManagerLib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBConnection {
    static String JDBC_DRIVER = null;
    static String DB_URL = null;
    static String DB_NAME = null;
    static String DB_USER = null;
    static String DB_PASSWORD = null;
    public static Connection connection = null;

    public static Connection getConnection(){
        Properties properties = new Properties();
        InputStream inputStream = null;
        try{
            inputStream = new FileInputStream("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\resources\\config.properties");
            properties.load(inputStream);
            JDBC_DRIVER = properties.getProperty("jdbc_driver");
            DB_URL = properties.getProperty("db_url");
            DB_NAME = properties.getProperty("db_name");
            DB_USER = properties.getProperty("db_user");
            DB_PASSWORD = properties.getProperty("db_password");
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
        } catch(SQLException e){
            throw new RuntimeException("Error connecting to database", e);
        } catch(ClassNotFoundException e){
            throw new RuntimeException("Error Class Not Found", e);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(inputStream != null){
                try{
                    inputStream.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if(connection != null)
            connection.close();
    }
}
