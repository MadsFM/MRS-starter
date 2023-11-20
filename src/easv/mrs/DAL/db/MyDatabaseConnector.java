package easv.mrs.DAL.db;


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class MyDatabaseConnector {


    //Class will easv.mrs.be included when we start working on DATABASES

    private static String PROP_FILE = "Config/config.settings";
    private SQLServerDataSource dataSource;

    public MyDatabaseConnector() throws IOException {

        Properties databaseProp = new Properties();
        databaseProp.load(new FileInputStream(new File(PROP_FILE)));

        dataSource = new SQLServerDataSource();
        dataSource.setServerName(databaseProp.getProperty("Server"));
        dataSource.setDatabaseName(databaseProp.getProperty("Database"));
        dataSource.setUser(databaseProp.getProperty("User"));
        dataSource.setPassword(databaseProp.getProperty("Password"));
        dataSource.setPortNumber(1433);
        dataSource.setTrustServerCertificate(true);
    }

    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }


    public static void main(String[] args) throws SQLException, IOException {

        MyDatabaseConnector databaseConnector = new MyDatabaseConnector();

        try (Connection connection = databaseConnector.getConnection()) {

            System.out.println("Is it open? " + !connection.isClosed());

        } //Connection gets closed here
    }


}
