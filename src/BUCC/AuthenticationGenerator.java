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
import java.sql.SQLException;

/**
 * Created by Torab on 07-Jan-17.
 */
public class AuthenticationGenerator extends Application {

    public TextField textFieldUserName;
    public PasswordField textFieldPassword;
    public PasswordField textFieldPasswordMismatch;
    public Label labelPasswordError;
    public Label labelUserNameError;
    public Label labelPasswordMismatchError;


    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public void buttonSetNamUserPass(ActionEvent actionEvent) {



        LogInInfoChecker logInInfoChecker=new LogInInfoChecker();
        String userName="";
        String password="";

        try {
            if (logInInfoChecker.userNameCheck(textFieldUserName.getText())){
                userName=textFieldUserName.getText();

                //Password
                if(logInInfoChecker.passWordCheck(textFieldPassword.getText()))
                {
                    password=textFieldPassword.getText();

                    //passwordMismatch
                    if(logInInfoChecker.passwordMismatch(textFieldPassword.getText(),textFieldPasswordMismatch.getText()))
                    {
                        SqliteConnection.statement.executeUpdate("insert into UserTable values('"+userName+"','"+password+"');");


                        ((Node) actionEvent.getSource()).getScene().getWindow().hide();

                        Stage primaryStage = new Stage();
                        FXMLLoader loader = new FXMLLoader();
                        Pane root = loader.load(getClass().getResource("LogIn.fxml").openStream());
                        LogInController logInController= loader.getController();
                        logInController.labelUserCreationPopUpMsg.setText("User Created Successfully");

                        primaryStage.setTitle("Log In");
                        primaryStage.setScene(new Scene(root, 600, 400));


                        primaryStage.setResizable(false);
                        primaryStage.sizeToScene();
                        primaryStage.show();
                    }
                    else {
                        labelPasswordMismatchError.setText(logInInfoChecker.errorMsg);
                    }
                }
                else {
                    labelPasswordError.setText(logInInfoChecker.errorMsg);
                }
            }
            else {
                labelUserNameError.setText(logInInfoChecker.errorMsg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
