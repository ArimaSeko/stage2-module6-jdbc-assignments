package jdbc;

import java.sql.*;
public class CustomConnector {
    public Connection getConnection(String url) {
        Connection connection= null;
        try  {
            connection = DriverManager.getConnection(url);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Connection getConnection(String url, String user, String password)  {
        Connection connection= null;
        try  {
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
