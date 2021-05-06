package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AlueController implements Initializable {

    private ToimintaAlue toimAlue;

    @FXML
    public Label alueIdLabel, alueNimiLabel;

    public void initData(ToimintaAlue alue){
        toimAlue = alue;
        //alueIdLabel = ;
        alueNimiLabel.setText(alue.get_nimi());
        System.out.println(alue.get_nimi());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    public void alueMuokkaaButtonOnAction(ActionEvent actionEvent) {
    }


}
