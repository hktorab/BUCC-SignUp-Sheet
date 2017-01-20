package BUCC;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

/**
 * Created by Torab on 06-Jan-17.
 */
public class SqliteConnection {

    protected static Connection connection;
    protected static Statement statement;

    public void getConnection() {
        // sqlite driver
        try {
            Class.forName("org.sqlite.JDBC");

            // database path, if it's new database, it will be created in the project folder
            connection = DriverManager.getConnection("jdbc:sqlite:SQLiteBuccSignUp.db");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean databaseUserTableExists() {

        try {
            statement = connection.createStatement();


            ResultSet resultSet = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='UserTable';");

            if (!( resultSet.next()))
            {

                statement.executeUpdate("CREATE  TABLE UserTable(UserName VARCHAR(50) NOT NULL , Password VARCHAR(50) NOT NULL )");
                statement.executeUpdate("CREATE  TABLE SheetNameTable(Name VARCHAR(70) NOT NULL );");
                return false;
            }
            else {


                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}