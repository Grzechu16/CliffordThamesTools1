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
    private String finisInRow;
    private String marketInRow;
    private ArrayList<CheckBox> layoutMarketCheckBox;
    private ArrayList<String> marketShortcut;

    @FXML
    CheckBox axCheck, bxCheck, chCheck, csCheck, dkCheck, dxCheck, exCheck, fxCheck, gbCheck, grCheck,
            hxCheck, irCheck, ixCheck, nlCheck, nxCheck, plCheck, pxCheck, ruCheck, roCheck, sfCheck, sxCheck;
    @FXML
    Button findButton, restartButton, exitButton;
    @FXML
    TextField finisTextField;

    /** Method checks if finis/path textFields are empty */
    public void checkIfEmpty() throws IOException {
        if ((finisTextField.getText().equals("")) || (pathTextField.getText().equals(""))) {
            showAlert("Specify part number and path to price file first");
        } else
            readFile();
    }

    /** Method initialize process of searching through price file */
    public void readFile() throws IOException {
        finis = finisTextField.getText().toString();
        createList();
        createNewFile();

        String row = "";
        boolean isFinisFound = false;

        try {
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            while ((row = bufferedReader.readLine()) != null) {
                readFinisFromRow(row);
                if (finisInRow.equals(finis)) {
                    isFinisFound = true;
                    for (int i = 0; i < layoutMarketLabels.size(); i++) {
                        readMarketFromRow(row);
                        if (marketInRow.contains(marketShortcut.get(i))) {
                            layoutMarketLabels.get(i).setTextFill(javafx.scene.paint.Paint.valueOf(String.valueOf(Color.GREEN)));
                            layoutMarketLabels.remove(i);
                            marketShortcut.remove(i);
                        } else
                            layoutMarketLabels.get(i).setTextFill(javafx.scene.paint.Paint.valueOf(String.valueOf(Color.RED)));
                    }
                }
            }
            if (!isFinisFound) {
                showAlert("Part not found in price file");
            }
            bufferedReader.close();
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
        String[] marketTab = {"AX", "BX", "CH", "CS", "DK", "DX", "ED", "EX", "FX", "GB", "GR", "HX", "IR", "IX",
                "NL", "NX", "PL", "PX", "RU", "RO", "SF", "SX"};

        for (CheckBox l : checkBoxes)
            layoutMarketCheckBox.add(l);
        for (String s : marketTab)
            marketShortcut.add(s);
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









    // /** Method searches through first seven characters in row (first seven characters always represents FINIS number) */
    public void readFinisFromRow(String row) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            stringBuilder.append(String.valueOf(row.charAt(i)));
        }
        finisInRow = stringBuilder.toString();
        stringBuilder.setLength(0);
    }

    //  /** Method searches through characters 9-11 in row (characters 9-11 always represent market shortcut) */
    public void readMarketFromRow(String row) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 8; i < 10; i++) {
            stringBuilder.append(String.valueOf(row.charAt(i)));
        }
        marketInRow = stringBuilder.toString();
        stringBuilder.setLength(0);
    }


