package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MokkiController implements Initializable {
    @FXML
    public Label mokkiHloMaaraLabel;
    public Label mokkiHintaLabel;
    public Label mokkiVarusteluLabel;
    public Label mokkiKuvausLabel;
    public Label mokkiTalueIdLabel;
    public Label mokkiZipLabel;
    public Label mokkiOsoiteLabel;
    public Label mokkiNimiLabel;
    public Label mokkiIdLabel;




    public void initData(Mokki mokki){
        mokkiHloMaaraLabel.setText(String.valueOf(mokki.get_henkilomaara()));
        mokkiHintaLabel.setText(String.valueOf(mokki.getHinta())); ;
        mokkiVarusteluLabel.setText(mokki.get_varustelu());
        mokkiKuvausLabel.setText(mokki.get_kuvaus());
        mokkiTalueIdLabel.setText(String.valueOf(mokki.get_toimintaalue_id()));
        mokkiZipLabel.setText(mokki.get_postinro());
        mokkiOsoiteLabel.setText(mokki.get_katuosoite());
        mokkiNimiLabel.setText(mokki.get_mokkinimi());
        mokkiIdLabel.setText(String.valueOf(mokki.get_mokki_id()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
