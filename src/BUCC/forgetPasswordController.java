package BUCC;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by Torab on 07-Jan-17.
 */
public class forgetPasswordController extends Application {


    public Label labelForgetPasswordError;


    public TextField textFieldForgetUserName;
    public PasswordField textFieldForgetPasswordMismatch;
    public Label labelForgetPasswordMismatchError;
    public Label labelForgetUserNameError;
    public PasswordField textFieldForgetPassword;



    @Override
    public void start(Stage primaryStage) throws Exception {

    }




    public void buttonForgetUpdate(ActionEvent actionEvent) throws SQLException, IOException {
        LogInInfoChecker logInInfoChecker = new LogInInfoChecker();

        ResultSet resultSet=SqliteConnection.statement.executeQuery("Select UserName from UserTable;");

        String userName;
        String password;

        if ((logInInfoChecker.userNameCheck(textFieldForgetUserName.getText()))&&(textFieldForgetUserName.getText().equals(resultSet.getString(1))))
        {
            userName=textFieldForgetUserName.getText();

            labelForgetPasswordError.setText("");
            labelForgetPasswordMismatchError.setText("");
            //Password
            if (logInInfoChecker.passWordCheck(textFieldForgetPassword.getText())) {
                labelForgetPasswordMismatchError.setText("");

                //passwordMismatch
                if (logInInfoChecker.passwordMismatch(textFieldForgetPasswordMismatch.getText() , textFieldForgetPassword.getText())) {
                    password= textFieldForgetPassword.getText();
                    SqliteConnection.statement.executeUpdate("delete from UserTable where UserName='"+userName+"';");
                    SqliteConnection.statement.executeUpdate("insert into UserTable values('"+userName+"','"+password+"');");



                    ((Node) actionEvent.getSource()).getScene().getWindow().hide();

                    Stage primaryStage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    Pane root = loader.load(getClass().getResource("LogIn.fxml").openStream());

                    LogInController logInController=loader.getController();
                    logInController.labelUserCreationPopUpMsg.setText("Password Updated");

                    Scene scene = new Scene(root,600,400);

                    primaryStage.setTitle("Sign In....");
                    primaryStage.setScene(scene);


                    primaryStage.setResizable(false);
                    primaryStage.sizeToScene();

                    primaryStage.show();
                }
                else {
                    labelForgetPasswordMismatchError.setText("Password does not match");
                }

            } else {
                labelForgetPasswordError.setText("password too short");
            }
        }

        else{
            labelForgetUserNameError.setText("User Name does not match");
        }
    }

    public void buttonBack(ActionEvent actionEvent) throws IOException {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("LogIn.fxml").openStream());
        Scene scene = new Scene(root,600,400);
        primaryStage.setTitle("Sign In....");
        primaryStage.setScene(scene);


        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        primaryStage.show();
    }
}
