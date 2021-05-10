package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("varaus.fxml"));
        primaryStage.setTitle("Projekti");
        primaryStage.setScene(new Scene(root, 1024, 768));
        //root.getScene().getStylesheets().add("Stylesheets/testi.css"); // lisää css
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
