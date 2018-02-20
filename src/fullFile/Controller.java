package fullFile;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;

public class Controller {

    JFileChooser fileChooser;
    FileNameExtensionFilter extensionFilter;
    FileReader fileReader;
    BufferedReader bufferedReader;
    private String finis;
    private final String outPath = "C:\\Users\\DELL\\Desktop\\FullFileTest\\FindRaport.csv";
    private String finisInRow;
    private String marketInRow;
    public CheckBox[] checkBoxes;
    public  String[] marketTab;
    private ArrayList<CheckBox> layoutMarketCheckBox;
    private ArrayList<String> marketShortcut;

    @FXML
    CheckBox axCheck, bxCheck, chCheck, csCheck, dkCheck, dxCheck, exCheck, fxCheck, gbCheck, grCheck,
            hxCheck, irCheck, ixCheck, nlCheck, nxCheck, plCheck, pxCheck, ruCheck, roCheck, sfCheck, sxCheck;
    @FXML
    Button findButton, restartButton, exitButton;
    @FXML
    TextField finisTextField;

    @FXML
    public void initialize() {
        createList();
    }

    /** Method checks if finis textField are empty */
    public void checkIfEmpty() throws IOException {
        if ((finisTextField.getText().equals(""))) {
            showAlert("Specify part number first");
        }

    }

    /** Method initialize process of searching through price file */
    public void readFile(String path) throws IOException {
        finis = finisTextField.getText().toString();
        createNewFile();

        String row = "";
        boolean isFinisFound = false;

        try {
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            FileWriter fileWriter = new FileWriter("C:\\Users\\DELL\\Desktop\\FullFileTest\\FindRaport.csv");
            BufferedWriter buff2 = new BufferedWriter(fileWriter);

            while ((row = bufferedReader.readLine()) != null) {
                if (row.toUpperCase().contains(finis.toUpperCase())) {
                buff2.write(row);
                buff2.newLine();
                }
            }
            bufferedReader.close();
            buff2.close();
        } catch (Exception e1) {
            showAlert("Invalid or missing file");
            e1.printStackTrace();
        }
    }

    public void createNewFile(){
        try {
            FileWriter fileWriter = new FileWriter("C:\\Users\\DELL\\Desktop\\FullFileTest\\FindRaport.csv");
            BufferedWriter buff2 = new BufferedWriter(fileWriter);
            buff2.write("Searched finis:" + "," + finis);
            buff2.newLine();
            buff2.write("Vehicle Line" + "," + "FCJ Menu Number" + "," + "Operation (EN)" + "," + "Year Dates   " + ","
                    + "Variant (EN)" + "," + "Part Description (EN)" + "," + "FINIS" + "," + "MLI" + ","
                    + "Parts Quantity" + "," + "Parts Unit Price excl. VAT" + "," + "Parts Retail Excl. VAT" + ","
                    + "Parts Retail Incl. VAT" + "," + "Labour Time" + "," + "Labour Cost Excl. VAT " + ","
                    + "Labour Cost Incl. VAT " + "," + "Repair Retail Excl. VAT" + "," + "Total Repair Incl. VAT " + ","
                    + "Fleet National Pricing Region 1" + "," + "Fleet National Pricing Region 2" + ","
                    + "Fleet National Pricing Region 3" + "," + "Fleet National Pricing Region 4" + ","
                    + "Ford Motorcraft 4+(Retail)" + "," + "Recommended Fitted Price(Retail)");
            buff2.newLine();
            buff2.close();

        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
    }

    /** Method allows to show information alerts */
    public void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setHeaderText(text);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }

    /** Method creates ArrayList of market labels (javafx elements) and ArrayList with markets shortcuts */
    private void createList() {
        layoutMarketCheckBox = new ArrayList<>();
        marketShortcut = new ArrayList<>();

        CheckBox[] checkBoxes = {axCheck, bxCheck, chCheck, csCheck, dkCheck, dxCheck, exCheck, fxCheck,
                gbCheck, grCheck, hxCheck, irCheck, ixCheck, nlCheck, nxCheck, plCheck, pxCheck,
                ruCheck, roCheck, sfCheck, sxCheck};
        String[] marketTab = {"FMPAX1", "FMPBX1", "FMPCH1", "FMPCS1", "FMPDK1", "FMPDX1", "FMPEX1", "FMPFX1", "FMPGB1", "FMPGR1", "FMPHX1", "FMPIR1", "FMPIX1",
                "FMPNL1", "FMPNX1", "FMPPL1", "FMPPX1", "FMPRU1", "FMPRO1", "FMPSF1", "FMPSX1"};

        for (CheckBox l : checkBoxes)
            layoutMarketCheckBox.add(l);
        for (String s : marketTab)
            marketShortcut.add(s);
    }

    public void checkSelectedMarkets() throws IOException {
        if((finisTextField.getText())!=""){
            createList();
            createNewFile();
            for(int i=0 ; i<layoutMarketCheckBox.size();i++){
                if(layoutMarketCheckBox.get(i).isSelected()){
                    layoutMarketCheckBox.get(i).setDisable(false);
                    readFile("C:\\Users\\DELL\\Desktop\\FullFileTest\\"+marketShortcut.get(i)+".csv");
                }
                else
                    layoutMarketCheckBox.get(i).setDisable(true);
            }
        }
        else
            showAlert("Type finis first!");

    }

    /** Method allows to restart app */
    public void restartApp() throws IOException {
        Stage stage = (Stage) restartButton.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("fullFileLayout.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Part Finder v.1.0 2017");
        primaryStage.setScene(new Scene(root, 800, 200));
        primaryStage.show();

    }

    /** Method allows to close app */
    public void closeApp() throws IOException {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}



