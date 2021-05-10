package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class PalveluController implements Initializable {

    @FXML
    private Label alueIdLabel;
    @FXML
    private Label alueNimiLabel;
    @FXML
    private Label palveluHintaLabel;
    @FXML
    private Label palveluKuvausLabel;

    public void initData(Palvelu palvelu) {
        alueIdLabel.setText(String.valueOf(palvelu.getPalveluId()));
        alueNimiLabel.setText(palvelu.getNimi());
        palveluHintaLabel.setText(String.valueOf(palvelu.getHinta()));
        palveluKuvausLabel.setText(palvelu.getKuvaus());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
