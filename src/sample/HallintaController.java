package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


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
    // https://www.youtube.com/watch?v=Wc1a2KshJ4w&list=LL&index=1 (n. 11:00 (AlueController) ja 14:30 (alla oleva metodi))

    @FXML
    private VBox toimAlueVbox;
    public Button muokkaaToimButton, poistaToimButton;

    private String muokattava;
    int valittu = 0;

    public void toimAlueTabSelected(Event event) throws SQLException {
        toimAlueVbox.getChildren().clear();
        ArrayList<ToimintaAlue> alueet; // Tähän taulukkolistaan ladataan olemassa olevat toim.alueet
        alueet = SQL_yhteys.getToimintaAlueetX(); // Metodi palauttaa taulukkolistan, jonka alkiot ovat ToimintaAlue:ita

        // Seuraavalla silmukalla käydään läpi kaikki toiminta-alueet.
        for (int i = 0; i < alueet.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("alue.fxml"));
                Parent root = loader.load(); // root on nyt node-tyyppiä, eli se voidaan lisätä VBoxiin

                // Pitää vielä ladata AlueControllerin initData-metodin avulla oikeat tiedot (taulukkolistasta alueet)
                AlueController controller = loader.getController();
                controller.initData(alueet.get(i));
                final String k = alueet.get(i).get_nimi();
                AtomicInteger z = new AtomicInteger();

                // Värin muutos kun hiiri menee tuloksen päälle
                root.setOnMouseEntered(event1 -> {
                    if (z.get() % 2 == 0) {
                        root.setStyle("-fx-background-color: #d9f2ff");
                    }
                });
                root.setOnMouseExited(event1 -> {
                    if (z.get() % 2 == 0) {
                        root.setStyle("-fx-background-color: #f4f4f4");
                    }
                });
                root.setOnMousePressed(event1 -> {
                    z.getAndIncrement();
                    System.out.println(z + " ja muokattava " + muokattava);
                    if (z.get() % 2 == 1 & muokattava == null) {
                        root.setStyle("-fx-background-color: #ffccb3");
                        valittu++;
                        muokattava = k;
                        muokkaaToimButton.setOpacity(1);
                        muokkaaToimButton.setDisable(false);
                        poistaToimButton.setOpacity(1);
                        poistaToimButton.setDisable(false);
                    } else if (valittu == 1 & k.equals(muokattava)) {
                        muokkaaToimButton.setOpacity(0.3);
                        muokkaaToimButton.setDisable(true);
                        poistaToimButton.setOpacity(0.3);
                        poistaToimButton.setDisable(true);
                        valittu--;
                        muokattava = null;
                    } else{
                        return;
                    }

                    System.out.println(k + " ja valittuja " + valittu);
                });

                toimAlueVbox.getChildren().add(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void lisaaUusiToimAlueButton(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("uusiAlue.fxml"));
            Parent root = loader.load();

            root.setOnMouseEntered(event1 -> {
                root.setStyle("-fx-background-color: #d9f2ff");
            });
            root.setOnMouseExited(event1 -> {
                root.setStyle("-fx-background-color: #f4f4f4");
            });
            root.setOnMousePressed(event1 -> {
                root.setStyle("-fx-background-color: #ffccb3");
            });

            // Tässä käytetään add-metodia, jolla root-node saadaan laitettua tiettyyn indeksiin: tässä 0 eli alkuun
            toimAlueVbox.getChildren().add(0, root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void muokkaaToimButtonOnAction(ActionEvent actionEvent) {
    }

    public void poistaToimButtonOnAction(ActionEvent actionEvent) {
    }
}
