package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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
    // https://www.youtube.com/watch?v=Wc1a2KshJ4w&list=LL&index=1 (n. 11:00 (AlueController) ja 14:30 (alla oleva metodi))

    @FXML
    private VBox toimAlueVbox;

    public static void restart(){
    };

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

                // Värin muutos kun hiiri menee tuloksen päälle
                root.setOnMouseEntered(event1 -> {
                    root.setStyle("-fx-background-color: #d9f2ff");
                });
                root.setOnMouseExited(event1 -> {
                    root.setStyle("-fx-background-color: #f4f4f4");
                });
                root.setOnMousePressed(event1 -> {
                    root.setStyle("-fx-background-color: #ffccb3");
                });

                toimAlueVbox.getChildren().add(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void lisaaUusiToimAlueButton(ActionEvent actionEvent) {
        System.out.println("mikä meni vikaan?");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("uusiAlue.fxml"));
            System.out.println("in");
            Parent root = loader.load();
            System.out.println("tänne");

            root.setOnMouseEntered(event1 -> {
                root.setStyle("-fx-background-color: #d9f2ff");
            });
            root.setOnMouseExited(event1 -> {
                root.setStyle("-fx-background-color: #f4f4f4");
            });
            root.setOnMousePressed(event1 -> {
                root.setStyle("-fx-background-color: #ffccb3");
            });

            toimAlueVbox.getChildren().add(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
