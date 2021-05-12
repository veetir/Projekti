package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RaportitController implements Initializable {

    @FXML
    private BorderPane varausNaytto;

    @FXML
    private TableView<Raportit> tableVaraukset;

    @FXML
    private TableColumn<Raportit, Integer> columnVarausnumero;

    @FXML
    private TableColumn<Raportit, Integer> columnAsiakasnumero;

    @FXML
    private TableColumn<Raportit, Integer> columnMokki;

    @FXML
    private TableColumn<Raportit, Date> columnVarauspaiva;

    public void raportitButtonOnAction(ActionEvent event) {

    }

    public void varausButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("varaus.fxml", actionEvent);
    }

    public void hallintaButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("hallinta.fxml", actionEvent);
    }

    ObservableList<Raportit> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            Connection conn= SQL_yhteys.getYhteys();

            ResultSet rs = conn.createStatement().executeQuery("select * from varaus");

            while (rs.next()) {
                oblist.add(new Raportit(rs.getInt("varaus_id"), rs.getInt("asiakas_id"), rs.getInt("mokki_mokki_id"), rs.getDate("varattu_pvm")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RaportitController.class.getName()).log(Level.SEVERE, null, ex);
        }

        columnVarausnumero.setCellValueFactory(new PropertyValueFactory<>("varaus_id"));
        columnAsiakasnumero.setCellValueFactory(new PropertyValueFactory<>("asiakas_id"));
        columnMokki.setCellValueFactory(new PropertyValueFactory<>("mokki_mokki_id"));
        columnVarauspaiva.setCellValueFactory(new PropertyValueFactory<>("varattu_pvm"));

        tableVaraukset.setItems(oblist);
    }






















 /*   private ObservableList<Raportit>data;
    private SQL_yhteys dc;



   @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dc = new SQL_yhteys();
    }

    @FXML
    private void loadDataFromDatabase(ActionEvent event) {
        Connection conn=dc.Connect();
        data=FXCollections.observableArrayList();
        ResultSet rs=conn.createStatement().executeQuery("select * from varaus");
        while (rs.next()) {
            data.add(new Raportit(rs.getInt("varausnumero"), rs.getInt("asiakanumero"), rs.getInt("mökki"), rs.getDate("pvm")));
        }






    } catch (SQLException ex)  {
       System.err.println("Error"+ ex);
    }

    columnVarausnumero.setCellValueFactory







    /*public static ObservableList<Raportit> getVarausraportit() {
        Connection conn = SQL_yhteys.getYhteys();
        ObservableList<Raportit> lista = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from varaus");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                lista.add(new Raportit(rs.getInt("varausnumero"), rs.getInt("asiakanumero"), rs.getInt("mökki"), rs.getDate("pvm")));
            }
        }  catch (SQLException e){

        }



        return lista;
    }
*/










 /*   private ToimintaAlue muokattava;
    boolean valittu, lisays;

    public void toimAlueTabSelected(Event event) throws SQLException {
        valittu = false;
        lisays = false;
        muokattava = null;
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
                final ToimintaAlue k = alueet.get(i);
                AtomicInteger z = new AtomicInteger();

                // Värin muutos kun hiiri menee tuloksen päälle

                root.setOnMousePressed(event1 -> {
                    z.getAndIncrement();
                    if (z.get() % 2 == 1 & muokattava == null) {
                        lisaaUusiToimAlueButton.setText("Muokkaa");
                        root.setStyle("-fx-background-color: #dbd9ff; " +
                                "-fx-border-color: #40424a; -fx-border-width: 3");
                        valittu = true;
                        muokattava = k;
                    } else if (valittu == true & k.equals(muokattava)) {
                        lisaaUusiToimAlueButton.setText("Lisää");
                        root.setStyle("-fx-background-color: #f4f4f4; " +
                                "-fx-border-color:  #dbd9ff; -fx-border-width: 1");
                        valittu = false;
                        muokattava = null;
                    } else {
                        return;
                    }
                });
                toimAlueVbox.getChildren().add(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
}