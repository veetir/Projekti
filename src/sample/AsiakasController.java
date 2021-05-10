package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AsiakasController implements Initializable {
    @FXML
    public Label etunimiLabel;
    public Label sukunimiLabel;
    public Label osoiteLabel;
    public Label postinroLabel;
    public Label puhnroLabel;
    public Label asiakasIdLabel;
    public Label spostiLabel;


    public void initData(Asiakas asiakas){

        asiakasIdLabel.setText(String.valueOf(asiakas.getAsiakasId()));
        etunimiLabel.setText(String.valueOf(asiakas.getEtunimi()));
        sukunimiLabel.setText(String.valueOf(asiakas.getSukunimi()));
        osoiteLabel.setText(String.valueOf(asiakas.getLahiosoite()));
        postinroLabel.setText(String.valueOf(asiakas.getPostinro()));
        puhnroLabel.setText(String.valueOf(asiakas.getPuhelinnro()));
        spostiLabel.setText(String.valueOf(asiakas.getEmail()));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
