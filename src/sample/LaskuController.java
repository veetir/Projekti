package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.sql.SQLException;

public class LaskuController {

    @FXML
    public HBox laskuHBox;
    @FXML
    public Label laskuIdLabel;
    @FXML
    public Label laskuVarausID;
    @FXML
    public Label laskuSummaLabel;
    @FXML
    public Button printLaskuButton;

    public Lasku printLasku = null;

    void initData(Lasku lasku) throws SQLException, IOException {
        // Tälle voisi olla mielekkäämpi keksiä joku parempi tunnistin kuin ID:
        // voisi olla esimerkiksi joku merkkijono joka perustuu laskunsaajan nimeen, mökin/palvelun nimeen
        // ja toim.alueen nimeen
        laskuIdLabel.setText(String.valueOf(lasku.getLaskuId()));
        Asiakas asiakas = SQL_yhteys.findAsiakas(String.valueOf(lasku.getVarausId()));
        laskuVarausID.setText(lasku.getVarausId() + " [ "
                + asiakas.getEtunimi() + " " + asiakas.getSukunimi() + " ]");
        laskuSummaLabel.setText(lasku.getSumma() + "€");
        printLasku = lasku;
    }


    public void printLaskuButton(ActionEvent actionEvent) throws IOException, SQLException {
        System.out.println("Laskun tiedot: " + printLasku.getLaskuId() + " ja "
                + printLasku.getSumma() + " ja " + " ....");
        // https://stackoverflow.com/a/47581179
        PrinterJob job = PrinterJob.createPrinterJob();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("printLasku.fxml"));
        Parent root = loader.load();
        printLaskuController controller = loader.getController();
        controller.initData(printLasku);

        if (job != null) {
            Printer printer = Printer.getDefaultPrinter().getDefaultPrinter();
            PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
            // TODO: Selvitä ja korjaa minkä takia koko kuvaa ei printata
            job.showPrintDialog(((Node) actionEvent.getSource()).getScene().getWindow());
            job.printPage(pageLayout, root);
            job.endJob();
        }
    }
}

