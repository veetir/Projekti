package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;


public class UusiAlueController implements Initializable {

    @FXML
    private VBox palveluBox;
    @FXML
    private Button lisaaPalveluButton;
    @FXML
    private Button peruutaPalveluButton;

    @FXML
    public Label alueIdLabel, errorLabel;
    public TextField uusiToimAlueTextField;
    public Button lisaaUusiAlueButton, plisaaUusiAlueButton, poistaButton, poistaVarmaButton;

    String paivitys = null, id = null;
    boolean varma = false;

    private Palvelu muokattavaPalvelu = null;
    boolean valittu, lisays;
    ToimintaAlue tamaAlue;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public void alusta(ToimintaAlue alue) throws SQLException {
        /**
         * Ladataan valitun alueen palvelut-listaan kaikki sen alueen palvelut.
         * Listan on tarkoitus toimia samalla tavalla kuin toiminta-alueiden listan toim.alueiden hallinnassa
         */
        tamaAlue = alue;

        palveluBox.getChildren().clear();
        ArrayList<Palvelu> palvelut; // Tähän taulukkolistaan ladataan olemassa olevat toim.alueet
        palvelut = SQL_yhteys.getAlueenPalvelut(alue.get_toimintaalue_id()); // Metodi palauttaa taulukkolistan, jonka alkiot ovat ToimintaAlue:ita

        // Seuraavalla silmukalla käydään läpi kaikki palvelut, jos niitä on, ja ladataan ne
        if (palvelut != null) {
            for (int i = 0; i < palvelut.size(); i++) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("palvelu.fxml"));
                    Parent root = loader.load(); // root on nyt node-tyyppiä, eli se voidaan lisätä VBoxiin

                    // Pitää vielä ladata AlueControllerin initData-metodin avulla oikeat tiedot (taulukkolistasta alueet)
                    PalveluController controller = loader.getController();
                    controller.initData(palvelut.get(i));
                    final Palvelu k = palvelut.get(i);
                    AtomicInteger z = new AtomicInteger();

                    // Värin muutos kun hiiri menee tuloksen päälle

                    root.setOnMousePressed(event1 -> {
                        z.getAndIncrement();
                        if (z.get() % 2 == 1 & muokattavaPalvelu == null) {
                            lisaaPalveluButton.setText("Muokkaa");
                            root.setStyle("-fx-background-color: #dbd9ff; " +
                                    "-fx-border-color: #40424a; -fx-border-width: 3");
                            valittu = true;
                            muokattavaPalvelu = k;
                        } else if (valittu == true & k.equals(muokattavaPalvelu)) {
                            lisaaPalveluButton.setText("Lisää");
                            root.setStyle("-fx-background-color: #f4f4f4; " +
                                    "-fx-border-color:  #dbd9ff; -fx-border-width: 1");
                            valittu = false;
                            muokattavaPalvelu = null;
                        } else {
                            return;
                        }
                    });
                    palveluBox.getChildren().add(root);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void initData(ToimintaAlue alue) throws SQLException {
        lisaaPalveluButton.setDisable(false);
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
        alusta(alue);
    }

    public void lisaaUusiAlueButtonOnAction(ActionEvent actionEvent) throws SQLException {
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
                int alueenId = ToimintaAlue.findId(uusiToimAlueTextField.getText());
                errorLabel.setText("Alue lisätty. Päivitä.");
                lisaaUusiAlueButton.setDisable(true);
                lisaaUusiAlueButton.setOpacity(0.1);
                alert.setTitle("Alueen lisäys");
                alert.setHeaderText(null);
                alert.setContentText("Toiminta-alue lisättiin tietokantaan.");
                alert.showAndWait();
            } else {
                SQL_yhteys.insertToiminta(uusiAlue, id, true, false);
                errorLabel.setText("Alue päivitetty. Päivitä.");
                plisaaUusiAlueButton.setDisable(true);
                plisaaUusiAlueButton.setOpacity(0.1);
                poistaButton.setOpacity(0.1);
                poistaButton.setDisable(true);
                alert.setTitle("Alueen päivitys");
                alert.setHeaderText(null);
                alert.setContentText("Toiminta-alueen tiedot päivitettiin tietokannassa.");
                alert.showAndWait();
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
        alert.setTitle("Alueen poisto");
        alert.setHeaderText(null);
        alert.setContentText("Toiminta-alueen tiedot poistettiin tietokannasta.");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void lisaaPalveluButtonOnAction(ActionEvent actionEvent) {
        if (!lisays) {
            try {
                lisaaPalveluButton.setDisable(true);
                peruutaPalveluButton.setDisable(false);
                peruutaPalveluButton.setOpacity(1);
                lisays = true;
                lisaaPalveluButton.setText("Lisää palvelu");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("uusiPalvelu.fxml"));
                Parent root = loader.load();
                UusiPalveluController controller = loader.getController();

                if (muokattavaPalvelu != null) {
                    controller.initData(muokattavaPalvelu);
                } else {
                    controller.sendAlue(tamaAlue);
                }

                // Tässä käytetään add-metodia, jolla root-node saadaan laitettua tiettyyn indeksiin: tässä 0 eli alkuun
                palveluBox.getChildren().clear();
                palveluBox.getChildren().add(0, root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void peruutaPalveluButtonOnAction(ActionEvent actionEvent) throws SQLException {
        palveluBox.getChildren().remove(0);
        lisays = false;
        peruutaPalveluButton.setDisable(true);
        peruutaPalveluButton.setOpacity(0.1);
        lisaaPalveluButton.setDisable(false);
        alusta(tamaAlue);
        valittu = false;
        lisays = false;
        muokattavaPalvelu = null;
    }
}