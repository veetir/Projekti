package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Tyokalu {

    // Idea otettu ao. videosta. Katso käyttöesimerkki luokasta HallintaController.
    // https://www.youtube.com/watch?v=XCgcQTQCfJQ
    public static void vaihdaIkkuna(String fxml, ActionEvent ikkuna) throws IOException {
        Parent toiseenNakymaan = FXMLLoader.load(Tyokalu.class.getResource(fxml));
        Scene toinenScene = new Scene(toiseenNakymaan);
        Stage window = (Stage) ((Node) ikkuna.getSource()).getScene().getWindow();
        window.setScene(toinenScene);
        window.show();
    }

}
