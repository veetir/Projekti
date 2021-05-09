package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class UusiAlueController implements Initializable {

    @FXML
    private CheckBox golfP;
    @FXML
    private CheckBox hiihtoP;
    @FXML
    private CheckBox marjastusP;
    @FXML
    private CheckBox hiihtokeskusP;
    @FXML
    private CheckBox maastopyorailyP;
    @FXML
    private CheckBox moottorikelkkailuP;
    @FXML
    private CheckBox retkeilyP;
    @FXML
    private CheckBox uintiP;
    @FXML
    private CheckBox tennisP;
    @FXML
    private CheckBox sienestysP;
    @FXML
    private CheckBox veneilyP;
    @FXML
    private CheckBox kalastaminenP;

    ObservableList<String> valitutPalvelut = FXCollections.observableArrayList();

    @FXML
    public Label alueIdLabel, errorLabel;
    public TextField uusiToimAlueTextField;
    public Button lisaaUusiAlueButton, plisaaUusiAlueButton, poistaButton, poistaVarmaButton;
    public TextArea ohjeTextArea;
    String paivitys = null, id = null;
    boolean varma = false;


    public void initData(ToimintaAlue alue) {
        alueIdLabel.setText(String.valueOf(alue.get_toimintaalue_id()));
        uusiToimAlueTextField.setText(alue.get_nimi());
        paivitys = alue.get_nimi();
        System.out.println(paivitys);
        id = String.valueOf(alue.get_toimintaalue_id());
        plisaaUusiAlueButton.setDisable(false);
        plisaaUusiAlueButton.setOpacity(1);
        poistaButton.setDisable(false);
        poistaButton.setOpacity(1);
        lisaaUusiAlueButton.setDisable(true);
        lisaaUusiAlueButton.setOpacity(0.1);
        ohjeTextArea.setText("Muokkaa olemassa olevaa toiminta-aluetta. \nPäivittäessä nimi vaihtuu, mutta ID pysyy samana.\n" +
                "Poistettaessa ID ja nimi poistetaan tietokannasta. ");
    }

    public void lisaaUusiAlueButtonOnAction(ActionEvent actionEvent) throws SQLException {
        // Lisätään ensin valitut palvelut tietokantaan
        //valitutPalvelut

        errorLabel.setText("");
        String uusiAlue = uusiToimAlueTextField.getText();

        // Ei lisätä tyhjää aluetta
        if (uusiAlue == "") {
            errorLabel.setText("Tyhjää aluetta ei voi lisätä!");
            return;
        } else {
            ArrayList<ToimintaAlue> alueet; // Tähän taulukkolistaan ladataan olemassa olevat toim.alueet
            alueet = SQL_yhteys.getToimintaAlueetX();
            // Ei lisätä olemassa olevaa aluetta

            if (alueIdLabel.getText().equalsIgnoreCase("id")) {
                for (int i = 0; i < alueet.size(); i++) {
                    if (alueet.get(i).get_nimi().equalsIgnoreCase(uusiAlue)) { // Huom. Vertailu equalsIGNORECASE !
                        errorLabel.setText("Alue on jo olemassa!");
                        return;
                    }
                }
            }

            if (paivitys == null) {
                SQL_yhteys.insertToiminta(uusiAlue, null, false, false);
                errorLabel.setText("Alue lisätty. Päivitä.");
                lisaaUusiAlueButton.setDisable(true);
                lisaaUusiAlueButton.setOpacity(0.1);
            } else{

                for (int i = 0; i < valitutPalvelut.size(); i++) {
                    Palvelu palvelu = new Palvelu(-1, Long.valueOf(id), valitutPalvelut.get(i), valitutPalvelut.get(i), -1);
                    SQL_yhteys.setPalvelu(palvelu, false, false);
                }

                SQL_yhteys.insertToiminta(uusiAlue, id, true, false);
                errorLabel.setText("Alue päivitetty. Päivitä.");
                plisaaUusiAlueButton.setDisable(true);
                plisaaUusiAlueButton.setOpacity(0.1);
                poistaButton.setOpacity(0.1);
                poistaButton.setDisable(true);
            }


        }
    }

    public void poistaButtonOnAction(ActionEvent actionEvent) {
        errorLabel.setText("");
        if (!varma) {
            uusiToimAlueTextField.setText(paivitys);
            uusiToimAlueTextField.setDisable(true);
            poistaButton.setText("Peru");
            varma = true;
            errorLabel.setText("Oletko varma?");
            plisaaUusiAlueButton.setDisable(true);
            plisaaUusiAlueButton.setOpacity(0.1);
            poistaVarmaButton.setDisable(false);
            poistaVarmaButton.setOpacity(1);
            poistaVarmaButton.setVisible(true);
        } else {
            varma = false;
            plisaaUusiAlueButton.setDisable(false);
            plisaaUusiAlueButton.setOpacity(1);
            uusiToimAlueTextField.setDisable(false);
            poistaVarmaButton.setDisable(true);
            poistaVarmaButton.setOpacity(0.1);
            poistaVarmaButton.setVisible(false);
            poistaButton.setText("Poista");
        }

    }

    public void poistaVarmaButtonOnAction(ActionEvent actionEvent) throws SQLException {
        SQL_yhteys.insertToiminta(paivitys, id, false, true);
        errorLabel.setText("Alue poistettu. Päivitä.");
        poistaButton.setVisible(false);
        plisaaUusiAlueButton.setVisible(false);
        poistaVarmaButton.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        golfP.setOnAction(e -> {
            valitutPalvelut.add(golfP.getText());
        });
        hiihtoP.setOnAction(e -> {
            valitutPalvelut.add(hiihtoP.getText());
        });
        marjastusP.setOnAction(e -> {
            valitutPalvelut.add(marjastusP.getText());
        });
        hiihtokeskusP.setOnAction(e -> {
            valitutPalvelut.add(hiihtokeskusP.getText());
        });
        maastopyorailyP.setOnAction(e -> {
            valitutPalvelut.add(maastopyorailyP.getText());
        });
        moottorikelkkailuP.setOnAction(e -> {
            valitutPalvelut.add(moottorikelkkailuP.getText());
        });
        retkeilyP.setOnAction(e -> {
            valitutPalvelut.add(retkeilyP.getText());
        });
        uintiP.setOnAction(e -> {
            valitutPalvelut.add(uintiP.getText());
        });
        tennisP.setOnAction(e -> {
            valitutPalvelut.add(tennisP.getText());
        });
        sienestysP.setOnAction(e -> {
            valitutPalvelut.add(sienestysP.getText());
        });
        veneilyP.setOnAction(e -> {
            valitutPalvelut.add(veneilyP.getText());
        });
        kalastaminenP.setOnAction(e -> {
            valitutPalvelut.add(kalastaminenP.getText());
        });
    }
}