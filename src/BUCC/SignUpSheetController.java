package BUCC;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Torab on 08-Jan-17.
 */
public class SignUpSheetController extends Application {
    public TextField textFieldName;
    public TextField textFieldId;
    public TextField textFieldEmail;
    public TextField textFieldPhone;
    public TextField textFieldDept;
    public Label labelNameFieldError;
    public Label labelIdError;
    public Label labelEmailError;
    public Label labelPhoneNumberEror;
    public Label labelDeptError;
    private static String tableName="";
    public Label labelGiveThanks;
    public TextArea textAreaCount;
    public TextArea textAreaName;
    public TextArea textAreaId;
    public TextArea textAreaDept;
    public TextArea textAreaPhone;
    public TextArea textAreaEmail;

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
    protected void tableNameSelector(String tName){
        tableName=tName;
        System.out.println(tableName);
    }


    private String query="";
    public void buttonUpdateName(ActionEvent actionEvent) throws SQLException {

        if(!(textFieldName.getText().isEmpty())){

            labelNameFieldError.setText("");
            labelEmailError.setText("");
            labelIdError.setText("");
            labelPhoneNumberEror.setText("");
            labelDeptError.setText("");

            if(IdCheck(textFieldId.getText()) )
            {

                labelEmailError.setText("");
                labelIdError.setText("");
                labelPhoneNumberEror.setText("");
                labelDeptError.setText("");


                if(EmailCheck(textFieldEmail.getText())){


                    labelPhoneNumberEror.setText("");
                    labelEmailError.setText("");
                    labelDeptError.setText("");

                    if (!(textFieldPhone.getText().isEmpty())){



                        labelPhoneNumberEror.setText("");
                        labelDeptError.setText("");



                        if(!(textFieldDept.getText().isEmpty()))
                        {

                            makeShowNull();

                            labelDeptError.setText("");
                            query="insert into "+tableName+" values('"+textFieldName.getText()+"',"+textFieldId.getText()+",'"+textFieldEmail.getText()+"','"+textFieldPhone.getText()+"','"+textFieldDept.getText()+"');" ;

                            SqliteConnection.statement.executeUpdate(query);

                            textFieldName.setText("");
                            textFieldId.setText("");
                            textFieldEmail.setText("");
                            textFieldPhone.setText("");
                            textFieldDept.setText("");

                            System.out.println(query);
                            labelGiveThanks.setText("Thanks For Sharing Your Information");

                            show(tableName);



                        }
                        else {
                            labelDeptError.setText("Error in Dept Field");
                        }
                    }
                    else {
                        labelPhoneNumberEror.setText("Error in Phone number");
                    }
                }else{
                    labelEmailError.setText("Error in Email");
                }
            }else {
                labelIdError.setText("Error in ID");
            }
        }else {
            labelNameFieldError.setText("Error in Name");
        }

    }

    private void makeShowNull() {
        textAreaCount.setText("");
        textAreaDept.setText("");
        textAreaEmail.setText("");
        textAreaName.setText("");
        textAreaId.setText("");
        textAreaPhone.setText("");

    }


    private boolean IdCheck(String text) throws SQLException {
        if(text.isEmpty() || text.length()<8 || text.length()>8)
            return false;

        SqliteConnection sqliteConnection =new SqliteConnection();
        ResultSet resultSet= sqliteConnection.statement.executeQuery("select * from "+tableName+";");
        while (resultSet.next())
        {
            System.out.println(resultSet.getString(2));
            if(text.equals(resultSet.getString(2))){

                return false;

            }
        }

        return true;
    }


    private boolean EmailCheck(String msg){
        if (msg.isEmpty())
            return false;
        String [] email=msg.split("");

        for (int c=0;c<email.length;c++)
        {
            if (email[c].equals("@"))
            {
                return  true;
            }
        }

        return false;
    }


    public void buttonExit(ActionEvent actionEvent) throws SQLException {
        SqliteConnection.connection.close();

        System.exit(0);
    }

    String name="";
    String id="";
    String dept="";
    String phone="";
    String email="";
    String c="";
    int count=1;

    private void show(String tableName) throws SQLException {

        name="";
        id="";
        dept="";
        phone="";
        email="";
        c="";
        count=1;


        ResultSet resultSet= SqliteConnection.statement.executeQuery("Select * from "+tableName+" ;");


        while (resultSet.next()){
            c=c+System.lineSeparator()+count;
            name=name+System.lineSeparator() +resultSet.getString(1);
            id=id+System.lineSeparator()+resultSet.getString(2);
            dept=dept+System.lineSeparator()+resultSet.getString(5);
            phone=phone+System.lineSeparator()+resultSet.getString(4);
            email=email+System.lineSeparator()+resultSet.getString(3);
            count++;
        }

        textAreaCount.setText(c);
        textAreaDept.setText(dept);
        textAreaEmail.setText(email);
        textAreaName.setText(name);
        textAreaId.setText(id);
        textAreaPhone.setText(phone);


    }

}
