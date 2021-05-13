package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class printLaskuController {
    @FXML
    private Label laskuIdLabel;
    @FXML
    private Label varausIdLabel;
    @FXML
    private Label varausSummaLabel;
    @FXML
    private Label erapaivaLabel;
    @FXML
    private VBox ostetutVBox;
    @FXML
    private Label saajanNimiLabel;
    @FXML
    private Label saajanOsoiteLabel;
    @FXML
    private Label saajanPaikkakuntaLabel;

    // TODO: ostetutVBox pitäisi täyttää palveluilla (ja mökillä) siten, että näkyy hinta, ostettujen palveluiden lkm.

    void initData(Lasku lasku) throws SQLException {
        // TODO: Eräpäivä tms. puuttuu. Pitäsi koettaa tehdä normaalin laskun näköinen
        // Lisäksi jossain voisi olla maininta asiakkaan nimestä
        laskuIdLabel.setText(String.valueOf(lasku.getLaskuId()));
        varausIdLabel.setText(String.valueOf(lasku.getVarausId()));
        varausSummaLabel.setText(String.valueOf(lasku.getSumma()));
        Asiakas asiakas = SQL_yhteys.findAsiakas(String.valueOf(lasku.getVarausId()));
        saajanNimiLabel.setText(asiakas.getEtunimi() + " " + asiakas.getSukunimi());
        saajanOsoiteLabel.setText(asiakas.getLahiosoite());
        // saajanPaikkakuntaLabel.setText(asiakas.get); TODO: pitäisi saada myös saajan paikkakunta ja postinro
    }
}
