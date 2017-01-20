package BUCC;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LogInController extends Application {




    public Label userNamePasswordMismatch;
    public TextField textFieldUserNameLogin;
    public PasswordField textFieldPassWordLogin;


    public Label labelUserCreationPopUpMsg;


    @Override
    public void start(Stage primaryStage) throws Exception {
    }







//For Creating UserName and Password



    public void buttonLogInToSignUpSheetCreator(ActionEvent actionEvent) throws IOException, SQLException {
        boolean authenticationPass=false;

        LogInInfoChecker logInInfoChecker=new LogInInfoChecker();
        if( logInInfoChecker.userNameCheck(textFieldUserNameLogin.getText()))
        {
            if(logInInfoChecker.passWordCheck(textFieldPassWordLogin.getText()))
            {
            ResultSet resultSet=SqliteConnection.statement.executeQuery("Select * from UserTable;");

            if (resultSet.getString(1).equals(textFieldUserNameLogin.getText()) && resultSet.getString(2).equals(textFieldPassWordLogin.getText()) ){
                authenticationPass=true;
            }else {
                authenticationPass=false;
            }
            }else {
                authenticationPass=false;
            }
        }else {

            authenticationPass=false;
        }


        if(authenticationPass) {

            ((Node) actionEvent.getSource()).getScene().getWindow().hide();

            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("Home.fxml").openStream());

            primaryStage.setTitle("Home");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.setResizable(false);
            primaryStage.sizeToScene();
            primaryStage.show();
        }else {
            userNamePasswordMismatch.setText("Wrong username or password");
        }
    }


    public void hyperLinkForgetMyPassword(ActionEvent actionEvent) throws IOException {

        ((Node) actionEvent.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("forgetPassword.fxml").openStream());

        primaryStage.setTitle("Update password....");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }



}

