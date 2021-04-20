package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VarausController {

    public void suodatin3SetOnAction(ActionEvent actionEvent) {
    }

    public void suodatin2SetOnAction(ActionEvent actionEvent) {
    }

    public void suodatin1SetOnAction(ActionEvent actionEvent) {
    }


    public void mokinlisaykseenButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent toiseenNakymaan = FXMLLoader.load(getClass().getResource("mokki.fxml"));
        Scene toinenScene = new Scene(toiseenNakymaan);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(toinenScene);
        window.show();
    }


    public void kotinakymaanButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent toiseenNakymaan = FXMLLoader.load(getClass().getResource("hallinta.fxml"));
        Scene toinenScene = new Scene(toiseenNakymaan);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow(); // ks. 11:30 videolta
        window.setScene(toinenScene);
        window.show();
    }

    public void asiakkaanlisaysButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent toiseenNakymaan = FXMLLoader.load(getClass().getResource("asiakas.fxml"));
        Scene toinenScene = new Scene(toiseenNakymaan);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(toinenScene);
        window.show();
    }
}
