package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    public TextField moknimiTextField;

    public void varausButtonOnAction(ActionEvent actionEvent) {
    }

    public void takaisinButtonOnAction(ActionEvent actionEvent) {
    }

    public void mokvButtonOnAction(ActionEvent actionEvent) {
    }

    public void astietButtonOnAction(ActionEvent actionEvent) {
    }

    public void toimalueButtonOnAction(ActionEvent actionEvent) {
    }

    public void palvhalButtonOnAction(ActionEvent actionEvent) {
    }

    public void suodatin3SetOnAction(ActionEvent actionEvent) {
    }

    public void suodatin2SetOnAction(ActionEvent actionEvent) {
    }

    public void suodatin1SetOnAction(ActionEvent actionEvent) {
    }

    public void lisaamokkiButtonOnAction(ActionEvent actionEvent) {
        String nimi = moknimiTextField.getText();
        System.out.println(nimi);
    }

    public void moklisaustakaisinButtonOnAction(ActionEvent actionEvent) {
    }
}
