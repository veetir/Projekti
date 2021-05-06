package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class HallintaController {

    public void hallintaButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("hallinta.fxml", actionEvent);
    }

    public void varausButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("varaus.fxml", actionEvent);
    }

    public void raportitButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("raportit.fxml", actionEvent);
    }

    public void varausAlkuPvmOnAction(ActionEvent actionEvent) {
    }

    public void varausLoppuPvmOnAction(ActionEvent actionEvent) {
    }

    public void lisaaMokkiButtonOnAction(ActionEvent actionEvent) {
    }

    public void muokkaaMokkiaButtonOnAction(ActionEvent actionEvent) {
    }

    public void poistaMokkiButtonOnAction(ActionEvent actionEvent) {
    }

    public void lisaaAsiakasButtonOnAction(ActionEvent actionEvent) {
    }

    public void muokkaaAsiakastaButtonOnAction(ActionEvent actionEvent) {
    }

    public void poistaAsiakasButtonOnAction(ActionEvent actionEvent) {
    }

    public void muokkaaHallintaaButtonOnAction(ActionEvent actionEvent) {
    }

    public void poistaVarausButtonOnAction(ActionEvent actionEvent) {
    }

    // Toiminta-alueet -tabin toiminnallisuus
    // Tutoriaalit:
    // https://www.youtube.com/watch?v=8lR9scOLE7U (n. 10:40)
    // https://www.youtube.com/watch?v=Wc1a2KshJ4w&list=LL&index=1

    @FXML
    private VBox toimAlueVbox;
    public Label alueIdLabel, alueNimiLabel;

    FXMLLoader loader = new FXMLLoader();

    public void toimAlueTabSelected(Event event) throws SQLException {
        ArrayList<String> alueet; // Tähän taulukkolistaan ladataan olemassa olevat toim.alueet
        alueet = SQL_yhteys.getToimintaAlueet();
        for (int i = 0; i < alueet.size(); i++) {
            System.out.println(alueet.get(i));
        }
        // Tehdään node-lista, jonka alkioita voidaan lisätä VBoxiin
        Node[] alue = new Node[alueet.size()];

        // Seuraavalla silmukalla käydään läpi kaikki toim.alueet.
        // Jokainen node on alue.fxml tiedosto, johon ladataan tiedot toiminta-alueesta
        // ja lopulta node lisätään VBoxiin toimAlueetVbox
        for (int i = 0; i < alueet.size(); i++) {
            try {
                //alueIdLabel.setText(alueet.get(i));
                alue[i] = loader.load(getClass().getResource("alue.fxml"));
                //alue[i].
                // TODO: jokaisen alue.fxml -"moduulin" pitäisi näyttää oikeat tiedot

                toimAlueVbox.getChildren().add(alue[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
    public void toimAlueTabClosed(Event event) {
        toimAlueVbox = null;
        toimAlueVbox.getChildren().removeAll();
    }


    public void alueMuokkaaButtonOnAction(ActionEvent actionEvent) {
    }
}
