package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LaskuController {

    @FXML
    private Label laskuIdLabel;
    @FXML
    private Label laskuVarausID;
    @FXML
    private Label laskuSummaLabel;

    void initData(Lasku lasku) {
        // Tälle voisi olla mielekkäämpi keksiä joku parempi tunnistin kuin ID:
        // voisi olla esimerkiksi joku merkkijono joka perustuu laskunsaajan nimeen, mökin/palvelun nimeen
        // ja toim.alueen nimeen
        laskuIdLabel.setText(String.valueOf(lasku.getLaskuId()));

        laskuVarausID.setText(String.valueOf(lasku.getVarausId()));
        laskuSummaLabel.setText(String.valueOf(lasku.getSumma()));
    }
}
