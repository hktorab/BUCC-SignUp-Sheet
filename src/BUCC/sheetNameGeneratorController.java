package BUCC;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Torab on 08-Jan-17.
 */
public class sheetNameGeneratorController implements Initializable {
    public TextField textfieldSheetName;
    public TextField textFieldYear;
    public ComboBox comboBoxSemesterSelect;
    public Label labelSheetNameErrorMsg;

    ObservableList<String> semester = FXCollections.observableArrayList("Spring","Summer","Fall");




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBoxSemesterSelect.setItems(semester);
    }
    protected String query;



    public void buttonSheetNameNext(ActionEvent actionEvent) throws SQLException, IOException {
        if (  (!(textfieldSheetName.getText().isEmpty())) && (!(textFieldYear.getText().isEmpty())) &&
                ((comboBoxSemesterSelect.getValue()!=null)) ){

            query=textfieldSheetName.getText()+comboBoxSemesterSelect.getValue()+ textFieldYear.getText();

            ResultSet resultSet= SqliteConnection.statement.executeQuery("SELECT * from SheetNameTable;");



            boolean isThisTableExists=false;
            while(resultSet.next())
            {


                if(query.equalsIgnoreCase(resultSet.getString(1)))
                {
                    isThisTableExists=true;
                    break;
                }
            }
            if(isThisTableExists)
            {

                labelSheetNameErrorMsg.setText("Table Exits or Field Empty.. try again");
                query="";
            }
            else {

                SqliteConnection.statement.executeUpdate("insert into SheetNameTable values('"+query+"');");


                SqliteConnection.
                        statement.executeUpdate
                        ("CREATE  TABLE "+query+"(Name VARCHAR(50) NOT NULL  ," +
                                "Id int NOT NULL,Department varchar(50) NOT NULL , Phone_Number varchar(50) NOT NULL,Email VARCHAR(70) NOT NULL)");

                SignUpSheetController signUpSheetController=new SignUpSheetController();
                signUpSheetController.tableNameSelector(query);



                ((Node) actionEvent.getSource()).getScene().getWindow().hide();

                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("SignUpSheet.fxml").openStream());


                primaryStage.setTitle(query);
                primaryStage.setScene(new Scene(root, 600, 450));
                primaryStage.setResizable(false);
                primaryStage.sizeToScene();
                primaryStage.show();


            }
        }
        else {
            labelSheetNameErrorMsg.setText("Table Exits or Field Empty.. try again");
        }
    }

    public void buttonBack(ActionEvent actionEvent) throws IOException {




        ((Node) actionEvent.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("Home.fxml").openStream());


        primaryStage.setTitle("Home");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}
