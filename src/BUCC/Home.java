package BUCC;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Torab on 07-Jan-17.
 */
public class Home extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public void createNewSheet(ActionEvent actionEvent) throws IOException {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("SheetNameGenerator.fxml").openStream());

        primaryStage.setTitle("Sheet Name....");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public void showPreviousSheet(ActionEvent actionEvent) throws IOException, SQLException {





        ((Node) actionEvent.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("PreviousSheet.fxml").openStream());
        PreviousSheetController previousSheetController=loader.getController();
        previousSheetController.show();

        primaryStage.setTitle("Sheet Name....");
        primaryStage.setScene(new Scene(root, 983.0, 609));
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();


    }

    public void buttonExit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
