package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.sql.SQLException;

public class LaskuController {

    @FXML
    private Label laskuIdLabel;
    @FXML
    private Label laskuVarausID;
    @FXML
    private Label laskuSummaLabel;
    @FXML
    public Button printLaskuButton;

    void initData(Lasku lasku) throws SQLException {
        // Tälle voisi olla mielekkäämpi keksiä joku parempi tunnistin kuin ID:
        // voisi olla esimerkiksi joku merkkijono joka perustuu laskunsaajan nimeen, mökin/palvelun nimeen
        // ja toim.alueen nimeen
        laskuIdLabel.setText(String.valueOf(lasku.getLaskuId()));

        laskuVarausID.setText(String.valueOf(lasku.getVarausId()) + " [ "
                + Varaus.getVarauksenHlo((int) lasku.getVarausId()) + " ]");
        laskuSummaLabel.setText(String.valueOf(lasku.getSumma()) + "€");
    }


    public void printLaskuButton(ActionEvent actionEvent) {

    }
}

