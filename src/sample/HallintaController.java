package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HallintaController implements Initializable {
    
    @FXML
    private BorderPane varausNaytto;
    @FXML
    private ScrollPane varausSp;

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

    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        try {
            naytaVaraukset();
        }catch(SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getMessage();
        }
        
    }

    void naytaVaraukset() throws SQLException {
        VBox varausVb = new VBox(5);
        varausVb.setPadding(new Insets(15, 15, 15, 15));
        varausSp.setFitToWidth(true);
        varausSp.setContent(varausVb);
        ArrayList<Varaus> varaukset = SQL_yhteys.getVaraukset();
        for (Varaus v : varaukset) {
            
            VBox varauksenTiedotVb = new VBox(3);
            HBox varausBox = getVarausBox(v);
            varausVb.getChildren().add(varausBox);
        }
            

    }
    private HBox getVarausBox(Varaus v) {
        VBox varauksenTiedotVb = new VBox(7);
        HBox varausBox = new HBox(10);
        varausBox.setPadding(new Insets(5, 5, 5, 5));

        Label varausOtsikko = new Label("varausID #"+v.getVarausId());
        Label asiakasOtsikko = new Label("ASIAKAS:");
        varausOtsikko.setFont(Font.font(null, FontWeight.BOLD, 16));
        asiakasOtsikko.setFont(Font.font(null, FontWeight.SEMI_BOLD, 14));

        Label asiakasTiedot = new Label("ASIAKAS:\t        "+"ID #"+v.getAsiakasId()+", "+v.getEtunimi()+" "+v.getSukunimi());
        Label varauksenTiedot = new Label("VARATTU:\t"+v.getVarattuPvm()/*+"\nsaapumis pvm: "+v.getVarattuAlkupvm()+"\nlahto pvm: "+v.getVarattuLoppupvm()*/);
        varauksenTiedot.setFont(Font.font(null, FontWeight.SEMI_BOLD, 13));
        asiakasTiedot.setFont(Font.font(null, FontWeight.SEMI_BOLD, 13));

        Button avaaButton = new Button("avaa");
        avaaButton.setOnAction(avaaVaraus -> {
            ArrayList<VarauksenPalvelu_Hallinta> varauksenPalvelut = SQL_yhteys.getVarauksenPalvelut((int)v.getVarausId());
            HBox mokkiHBox = new HBox();
            HBox palveluHBox = new HBox();
            varausBox.getChildren().remove(avaaButton);

            Label mokkiOtsikko = new Label("MÖKKI:\t          ");
            Label mokinTiedot = new Label("mökkiID #"+v.getMokkiMokkiId()+"\n"+v.getMokkinimi()+", "+v.getKatuosoite()+", "+v.getToimintaalue()+
            "\nsaapumis pvm:\t  "+ v.getVarattuAlkupvm()+
            "\nlähtö pvm:\t  "+ v.getVarattuLoppupvm());

            Label palveluOtsikko = new Label("PALVELUT:\t  ");
            VBox palveluVbox = new VBox(2);
            for(VarauksenPalvelu_Hallinta vp : varauksenPalvelut) {
                Label lbl = new Label(vp.toString());
                palveluVbox.getChildren().add(lbl);
            }

            palveluHBox.getChildren().addAll(palveluOtsikko, palveluVbox);


            mokkiHBox.getChildren().addAll(mokkiOtsikko, mokinTiedot);
            Button suljeButton = new Button("sulje");

            suljeButton.setOnAction(suljeVaraus -> {
                varausBox.getChildren().remove(suljeButton);
                varauksenTiedotVb.getChildren().remove(mokkiHBox);
                varauksenTiedotVb.getChildren().remove(palveluHBox);
                varausBox.getChildren().add(avaaButton);
            });

            varausBox.getChildren().add(suljeButton);
            varauksenTiedotVb.getChildren().addAll(mokkiHBox, palveluHBox);
        });

        varauksenTiedotVb.getChildren().addAll(varausOtsikko, varauksenTiedot, asiakasTiedot);
        Pane pane = new Pane();
        varausBox.getChildren().addAll(varauksenTiedotVb,pane, avaaButton);
        varausBox.setHgrow(pane, Priority.ALWAYS);
        varausBox.setAlignment(Pos.BOTTOM_LEFT);

        
        
        varausBox.setStyle("-fx-border-color: aquamarine");
        return varausBox;
    }
   
    
      
    
}
