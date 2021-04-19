package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HallintaController {


    public void mokvButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent varaustenHallintaan = FXMLLoader.load(getClass().getResource("varaushallinta.fxml"));
        Scene varaushallintaScene = new Scene(varaustenHallintaan);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(varaushallintaScene);
        window.show();
    }

    public void astietButtonOnAction(ActionEvent actionEvent) {
    }

    public void toimalueButtonOnAction(ActionEvent actionEvent) {
    }

    public void palvhalButtonOnAction(ActionEvent actionEvent) {
    }

    public void hallinnastakotiinButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent toiseenNakymaan = FXMLLoader.load(getClass().getResource("kotinakyma.fxml"));
        Scene toinenScene = new Scene(toiseenNakymaan);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow(); // ks. 11:30 videolta
        window.setScene(toinenScene);
        window.show();
    }
}
