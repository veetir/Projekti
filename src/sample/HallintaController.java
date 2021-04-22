package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.*;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;

public class HallintaController {

    @FXML
    private VBox tietoVBox;


    

    public void hallinnastakotiinButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent toiseenNakymaan = FXMLLoader.load(getClass().getResource("kotinakyma.fxml"));
        Scene toinenScene = new Scene(toiseenNakymaan);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow(); // ks. 11:30 videolta
        window.setScene(toinenScene);
        window.show();
    }

    public void mokkiButtonOnAction(ActionEvent actionEvent) {
        tietoVBox.getChildren().clear();
        try  {
            ArrayList<Mokki> mokit = SQL_yhteys.getMokit();

            for(Mokki mokki : mokit) {
                String tiedot = mokki.toString();
                Label teksti = new Label(tiedot);
                tietoVBox.getChildren().add(teksti);
                

            }
        }catch(SQLException e) {
            e.getMessage();
        }


    }

    public void asiakasButtonOnAction(ActionEvent actionEvent) {
    }

    public void varausButtonOnAction(ActionEvent actionEvent) {
    }

    public void palveluButtonOnAction(ActionEvent actionEvent) {
    }
}
