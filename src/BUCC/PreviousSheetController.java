package BUCC;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Torab on 11-Jan-17.
 */
public class PreviousSheetController extends Application {
    public Hyperlink firstSheet;
    public Hyperlink secondSheet;
    public Hyperlink thirdSheet;

    public TextArea textAreaName;
    public TextArea textAreaId;
    public TextArea textAreaEmail;
    public TextArea textAreaPhone;
    public TextArea textAreaDept;
    public TextArea textAreaCount;
    public TextArea textAreaShowSheetName;
    public TextField textfieldSheetName;
    public Label textfieldSheetNameError;
    public Label textfieldSheetCreatedSuccesful;
    public Label sheetError;

    ArrayList <String> arrayList = new ArrayList<>();
    @Override
    public void start(Stage primaryStage) throws Exception {

    }
    protected String extractSheet="";
    protected String editSheetName="";


    String name="";
    String id="";
    String dept="";
    String phone="";
    String email="";
    String c="";
    int count=1;






    protected void show() throws SQLException {



        textAreaCount.setEditable(false);
        textAreaDept.setEditable(false);
        textAreaEmail.setEditable(false);
        textAreaName.setEditable(false);
        textAreaId.setEditable(false);
        textAreaPhone.setEditable(false);
        textAreaShowSheetName.setEditable(false);

        String showSheetName="";
        ResultSet resultSet= SqliteConnection.statement.executeQuery("Select * from SheetNameTable;");

        if (!resultSet.next())
        {
            showSheetName="No Sheet Exists";
            sheetError.setText("No sheet Available");
            System.out.println("Doesnot exists");
        }
        else {

            do
            {
                System.out.println("Hello");
                String xx=resultSet.getString(1);
                System.out.println(xx);
                arrayList.add(xx);
                showSheetName=showSheetName+ xx+System.lineSeparator();

            }while (resultSet.next());
        }
        textAreaShowSheetName.setText(showSheetName);

    }



    public void showSheetValue (ActionEvent actionEvent) throws SQLException {


        ResultSet resultSet= SqliteConnection.statement.executeQuery("Select * from "+editSheetName+" ;");


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






    public void buttonExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void buttonBack(ActionEvent actionEvent) throws IOException {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("Home.fxml").openStream());
        Scene scene = new Scene(root,600,400);
        primaryStage.setTitle("Home");
        primaryStage.setScene(scene);


        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        primaryStage.show();
    }

    public void buttonEdit(ActionEvent actionEvent) throws IOException {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("SignUpSheet.fxml").openStream());
        SignUpSheetController signUpSheetController = loader.getController();
        signUpSheetController.tableNameSelector(editSheetName);
        Scene scene = new Scene(root,600,400);
        primaryStage.setTitle(editSheetName);
        primaryStage.setScene(scene);


        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        primaryStage.show();
    }



    public void buttonShowExecuteSheet(ActionEvent actionEvent) throws SQLException {

        name="";
        id="";
        dept="";
        phone="";
        c="";
        email="";
        count=1;

        boolean sheetValue=false;
        for (int c=0;c<arrayList.size();c++)
        {
            if (textfieldSheetName.getText().equals(arrayList.get(c)))
            {
                textfieldSheetNameError.setText("");
                editSheetName=textfieldSheetName.getText();
                showSheetValue(actionEvent);
                sheetValue=true;
                extractSheet=textfieldSheetName.getText();
                break;

            }

        }
        if (!sheetValue){
            textfieldSheetNameError.setText("No Sheet Exists");
            textAreaCount.setText("");
            textAreaDept.setText("");
            textAreaEmail.setText("");
            textAreaName.setText("");
            textAreaId.setText("");
            textAreaPhone.setText("");

        }
    }

    public String sheetName="";
    public void buttonExtract(ActionEvent actionEvent) throws WriteException, IOException, BiffException, SQLException {



        if(!extractSheet.isEmpty())
        {
            sheetName=extractSheet+".xls";
            File file=new File(sheetName);
            if(!file.exists())
            {
                create();
                ExcelSheetWriting();

            }
            ExcelSheetWriting();
        }else {

            textfieldSheetNameError.setText("Sheet Doesn't Exists");
        }



    }

    private void ExcelSheetWriting() throws IOException, BiffException, WriteException, SQLException {

        Workbook aWorkBook = Workbook.getWorkbook(new File(sheetName));
        WritableWorkbook aCopy = Workbook.createWorkbook(new File(sheetName), aWorkBook);

        WritableSheet aCopySheet = aCopy.getSheet(0);

        int row=0;
        int count=1;

        jxl.write.Label anotherWritableCellNameCount =  new jxl.write.Label(0,row,""+"Serial");
        jxl.write.Label anotherWritableCellName =  new jxl.write.Label(1,row,"Name");
        jxl.write.Label anotherWritableCellId =  new jxl.write.Label(2,row,"ID");
        jxl.write.Label anotherWritableCellPhone =  new jxl.write.Label(3,row,"Phone");
        jxl.write.Label anotherWritableCellDept =  new jxl.write.Label(4,row,"Department");

        jxl.write.Label anotherWritableCellEmail =  new jxl.write.Label(5,row,"Email");
        aCopySheet.addCell(anotherWritableCellNameCount);

        aCopySheet.addCell(anotherWritableCellName);
        aCopySheet.addCell(anotherWritableCellId);
        aCopySheet.addCell(anotherWritableCellPhone);
        aCopySheet.addCell(anotherWritableCellDept);

        aCopySheet.addCell(anotherWritableCellEmail);

        row++;



        ResultSet resultSet= SqliteConnection.statement.executeQuery("Select * from "+extractSheet+" ;");


        while (resultSet.next()){


            anotherWritableCellNameCount =  new jxl.write.Label(0,row,""+count);
            anotherWritableCellName =  new jxl.write.Label(1,row,resultSet.getString(1));
            anotherWritableCellId =  new jxl.write.Label(2,row,resultSet.getString(2));
            anotherWritableCellPhone =  new jxl.write.Label(3,row,resultSet.getString(4));
            anotherWritableCellDept =  new jxl.write.Label(4,row,resultSet.getString(5));

            anotherWritableCellEmail =  new jxl.write.Label(5,row,resultSet.getString(3));




            aCopySheet.addCell(anotherWritableCellNameCount);

            aCopySheet.addCell(anotherWritableCellName);
            aCopySheet.addCell(anotherWritableCellId);
            aCopySheet.addCell(anotherWritableCellPhone);
            aCopySheet.addCell(anotherWritableCellDept);

            aCopySheet.addCell(anotherWritableCellEmail);
            count++;
            row++;

        }





        textfieldSheetCreatedSuccesful.setText("Excel Sheet Created");
        aCopy.write();
        aCopy.close();

    }

    private void create() throws IOException, BiffException, WriteException {

        WritableWorkbook writableWorkbook = Workbook.createWorkbook(new File(sheetName));
        writableWorkbook.createSheet(sheetName,0);

        writableWorkbook.write();
        writableWorkbook.close();
    }
}
