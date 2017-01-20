package BUCC;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    SqliteConnection sqliteConnection = new SqliteConnection();
    @Override
    public void start(Stage primaryStage) throws Exception{



        try{

            sqliteConnection.getConnection();


            if(!(SqliteConnection.connection==null)) {
                sqliteConnection = new SqliteConnection();
                boolean databaseExist = sqliteConnection.databaseUserTableExists();


                if (databaseExist) {

                    Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
                    primaryStage.setTitle("Log In");
                    primaryStage.setScene(new Scene(root, 600, 400));
                    primaryStage.show();







                } else {

                    Parent root = FXMLLoader.load(getClass().getResource("AuthenticationGenerator.fxml"));
                    primaryStage.setTitle("Creating Database");
                    primaryStage.setScene(new Scene(root, 600, 400));
                    primaryStage.show();
                }
            }
            else {

                //  connectionEstablishing.setText("Database not found");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }







    public static void main(String[] args) {

        launch(args);


    }


}
