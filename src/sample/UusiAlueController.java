package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class UusiAlueController {

    @FXML
    public Label alueIdLabel;
    public TextField uusiToimAlueTextField;

    public void lisaaUusiAlueButtonOnAction(ActionEvent actionEvent) throws SQLException {
        String uusiAlue = uusiToimAlueTextField.getText();
        SQL_yhteys.insertToiminta(uusiAlue);
    }
}
