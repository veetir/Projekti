package sample;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;


public class UusiMokkiController implements Initializable {
    @FXML
    public Label mokkiIdLabel, mokkiTalueIdLabel, errorLabel;
    public Button lisaaUusiMokkiButton, plisaaUusiMokkiButton, poistaButton, poistaVarmaButton;

    // Muokattavat kentät
    public TextField mokkiNimiTextField;
    public TextField mokkiOsoiteTextField;
    public TextField mokkiZipTextField;
    public TextField mokkiHloMaaraTextField;
    public TextField mokkiHintaTextField;
    public TextArea mokkiVarusteluTextArea;
    public TextArea mokkiKuvausTextArea;
    public Label mokkiAlueNimiLabel;


    String paivitys = null, id = null, mokkiToimAlue;
    boolean varma = false;
    int mokkiId, mokkiTalueId;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public ChoiceBox mokkiAlueBox;

    // Tätä kutsutaan, kun mökkiä halutaan muokata. Käytetään siis alustamaan muokkauskortti muokattavalla datalla
    public void initData(Mokki mokki) {
        mokkiIdLabel.setText(String.valueOf(mokki.get_mokki_id()));
        mokkiNimiTextField.setText(mokki.get_mokkinimi());
        mokkiOsoiteTextField.setText(mokki.get_katuosoite());
        mokkiZipTextField.setText(String.valueOf(mokki.get_postinro()));
        mokkiHloMaaraTextField.setText(String.valueOf(mokki.get_henkilomaara()));
        mokkiHintaTextField.setText(String.valueOf(mokki.getHinta()));
        mokkiVarusteluTextArea.setText(mokki.get_varustelu());
        mokkiKuvausTextArea.setText(mokki.get_kuvaus());
        mokkiTalueIdLabel.setText(String.valueOf(mokki.get_toimintaalue_id()));


        mokkiAlueNimiLabel.setText(mokki.getToimintaalue_nimi());

        mokkiTalueId = mokki.get_toimintaalue_id();
        mokkiId = mokki.get_mokki_id();
        paivitys = mokki.get_mokkinimi();
        System.out.println(paivitys);
        id = String.valueOf(mokki.get_mokki_id());


        plisaaUusiMokkiButton.setDisable(false);
        plisaaUusiMokkiButton.setOpacity(1);
        poistaButton.setDisable(false);
        poistaButton.setOpacity(1);
        lisaaUusiMokkiButton.setDisable(true);
        lisaaUusiMokkiButton.setOpacity(0.1);
    }

    // Tätä kutsutaan kun halutaan lisätä täysin uusi mökki tai jos halutaan päivittää tiedot
    public void lisaaUusiMokkiButtonOnAction(ActionEvent actionEvent) throws SQLException {
        errorLabel.setText("");
        String uusiMokkiNimi = mokkiNimiTextField.getText();
        String uusiMokkiOsoite = mokkiOsoiteTextField.getText();
        String uusiMokkiZip = mokkiZipTextField.getText();
        String uusiMokkiHloMaara = mokkiHloMaaraTextField.getText();
        String uusiMokkiHinta = mokkiHintaTextField.getText();
        String uusiMokkiVarustelu = mokkiVarusteluTextArea.getText();
        String uusiMokkiKuvaus = mokkiKuvausTextArea.getText();

        int toimintaalue_id = -1;

        String valittuAlue = mokkiAlueBox.getSelectionModel().getSelectedItem().toString();
        if (!valittuAlue.equals("Uusi alue")) {
            toimintaalue_id = ToimintaAlue.findId(valittuAlue);
        } else if (mokkiAlueNimiLabel.getText() != null) {
            toimintaalue_id = ToimintaAlue.findId(mokkiAlueNimiLabel.getText());
        }

        // Huom. ei tarvitse lähettää oikeaa toim.alueen nimeä (tai nimeä ollenkaan), koska SQL-lause tekee mökin pelkän ID:n perusteella
        Mokki uusiMokki = null;
        try {
            uusiMokki = new Mokki(mokkiId, toimintaalue_id, "ei toimi",
                    uusiMokkiZip, uusiMokkiNimi, uusiMokkiOsoite,
                    uusiMokkiKuvaus, Integer.valueOf(uusiMokkiHloMaara), uusiMokkiVarustelu, Integer.valueOf(uusiMokkiHinta));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            errorLabel.setText("Kaikkia kenttiä ei täytetty oikein!");
        }


        // Ei lisätä tyhjää aluetta
        if (uusiMokki == null) {
            errorLabel.setText("Tyhjää mökkiä ei voi lisätä!");
            return;
        } else {
            ArrayList<Mokki> mokit; // Tähän taulukkolistaan ladataan olemassa olevat toim.alueet
            mokit = SQL_yhteys.getMokit();
            // Ei lisätä olemassa olevaa aluetta
            if (paivitys == null) {
                for (int i = 0; i < mokit.size(); i++) {
                    if (mokit.get(i).get_mokkinimi().equalsIgnoreCase(uusiMokki.get_mokkinimi())) { // Huom. vertailu mökkinimellä
                        errorLabel.setText("Tämän niminen mökki on jo olemassa!");
                        return;
                    }
                }
            }

            // Lähetetään tiedot. Lisäksi kerrotaan SQL_yhteys-luokan metodille onko kyseessä uusi lisäys vai päivitys
            if (paivitys == null) {
                if (uusiMokki.get_toimintaalue_id() == -1) {
                    errorLabel.setText("Valitse toiminta-alue.");
                    alert.setTitle("Tietoja puuttuu");
                    alert.setHeaderText(null);
                    alert.setContentText("Mökiltä puuttuu toiminta-alue.");
                    alert.showAndWait();
                    lisaaUusiMokkiButton.setDisable(false);
                    return;
                } else {
                    if (!SQL_yhteys.hasPostinro(uusiMokki.get_postinro())) {
                        SQL_yhteys.insertPostinro(uusiMokki.get_postinro(), ToimintaAlue.findNimi(uusiMokki.get_toimintaalue_id()));
                    }
                    SQL_yhteys.setMokit(uusiMokki, false, false, null);
                    errorLabel.setText("Mökki lisätty. Päivitä.");
                    lisaaUusiMokkiButton.setDisable(true);
                    alert.setTitle("Mökin lisäys");
                    alert.setHeaderText(null);
                    alert.setContentText("Mökki  '" + uusiMokki.get_mokkinimi() + "'  lisättiin tietokantaan.");
                    alert.showAndWait();
                }
            } else {
                if (!SQL_yhteys.hasPostinro(uusiMokki.get_postinro())) {
                    SQL_yhteys.insertPostinro(uusiMokki.get_postinro(), ToimintaAlue.findNimi(uusiMokki.get_toimintaalue_id()));
                }
                SQL_yhteys.setMokit(uusiMokki, true, false, null);
                errorLabel.setText("Mökki päivitetty. Päivitä.");
                alert.setTitle("Mökin päivitys");
                alert.setHeaderText(null);
                alert.setContentText("Mökin  '" + uusiMokki.get_mokkinimi() + "'  tiedot päivitettiin tietokannassa.");
                alert.showAndWait();
            }
        }
    }

    // Kutsutaan tätä kun halutaan poistaa mökki. Tällä ei varsinaisesti vielä poisteta: se tehdään poistaVarmaButtonilla
    // jotta ei vahingossa poisteta aluetta.
    public void poistaButtonOnAction(ActionEvent actionEvent) throws SQLException {
        errorLabel.setText("");
        if (!varma) {
            mokkiNimiTextField.setVisible(false);
            mokkiOsoiteTextField.setVisible(false);
            mokkiZipTextField.setVisible(false);
            mokkiHloMaaraTextField.setVisible(false);
            mokkiHintaTextField.setVisible(false);
            mokkiVarusteluTextArea.setVisible(false);
            mokkiKuvausTextArea.setVisible(false);

            poistaButton.setText("Peru");
            varma = true;
            errorLabel.setText("Oletko varma? Poistetaanko mökki, jolla on tämä mökki ID?");

            plisaaUusiMokkiButton.setDisable(true);
            plisaaUusiMokkiButton.setOpacity(0.1);
            poistaVarmaButton.setDisable(false);
            poistaVarmaButton.setOpacity(1);
            poistaVarmaButton.setVisible(true);
        } else {
            varma = false;

            mokkiNimiTextField.setVisible(true);
            mokkiOsoiteTextField.setVisible(true);
            mokkiZipTextField.setVisible(true);
            mokkiHloMaaraTextField.setVisible(true);
            mokkiHintaTextField.setVisible(true);
            mokkiVarusteluTextArea.setVisible(true);
            mokkiKuvausTextArea.setVisible(true);

            poistaVarmaButton.setDisable(true);
            poistaVarmaButton.setOpacity(0.1);
            poistaVarmaButton.setVisible(false);
            poistaButton.setText("Poista");

            plisaaUusiMokkiButton.setDisable(false);
            plisaaUusiMokkiButton.setOpacity(1);
        }
    }

    public void poistaVarmaButtonOnAction(ActionEvent actionEvent) throws SQLException {
        SQL_yhteys.setMokit(null, false, true, String.valueOf(mokkiId));
        errorLabel.setText("Alue poistettu. Päivitä.");
        poistaButton.setVisible(false);
        plisaaUusiMokkiButton.setVisible(false);
        poistaVarmaButton.setVisible(false);
        alert.setTitle("Mökin poisto");
        alert.setHeaderText(null);
        alert.setContentText("Mökin tiedot poistettiin tietokannasta.");
        alert.showAndWait();
    }

    //https://stackoverflow.com/a/36436243
    UnaryOperator<TextFormatter.Change> filter = change -> {
        String text = change.getText();
        if (text.matches("[0-9]*")) {
            return change;
        }
        return null;
    };
    // Näitä formattereja käyttävät tekstikentät eivät ota vastaan muita kuin numeroita
    TextFormatter<String> textFormatter = new TextFormatter<>(filter);
    TextFormatter<String> textFormatter1 = new TextFormatter<>(filter);
    TextFormatter<String> textFormatter2 = new TextFormatter<>(filter);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> toimintaAlueet = FXCollections.observableArrayList();
        try {
            ArrayList<String> alueet = SQL_yhteys.getToimintaAlueet();
            toimintaAlueet.add("Uusi alue");
            for (String alue : alueet) {
                toimintaAlueet.add(alue);
            }
            mokkiAlueBox.setItems(toimintaAlueet);
            mokkiAlueBox.getSelectionModel().selectFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mokkiHintaTextField.setTextFormatter(textFormatter);
        mokkiZipTextField.setTextFormatter(textFormatter1);
        mokkiHloMaaraTextField.setTextFormatter(textFormatter2);
        mokkiZipTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() != 5) {
                errorLabel.setText("Tarkista postinumero!");
                mokkiZipTextField.setStyle("-fx-border-color: #cf1b1b; -fx-background-color: #FFF0F0; -fx-border-width: 2");
            } else if (newValue.length() == 5) {
                errorLabel.setText("");
                mokkiZipTextField.setStyle("");
            }
        });
    }
}