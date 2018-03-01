package fullFile;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;

public class Controller {

    FileReader fileReader;
    BufferedReader bufferedReader;
    private String finis;
   // private final String outPath = "C:\\Users\\DELL\\Desktop\\FullFileTest\\FindRaport.csv";
   private final String outPath = "FindRaport.csv";
    private boolean allSelected = false;
    private ArrayList<CheckBox> layoutMarketCheckBox;
    private ArrayList<String> marketShortcut;
    private int marketCount, lineCount;

    @FXML
    CheckBox axCheck, bxCheck, chCheck, csCheck, dkCheck, dxCheck, exCheck, fxCheck, gbCheck, grCheck,
            hxCheck, irCheck, ixCheck, nlCheck, nxCheck, plCheck, pxCheck, ruCheck, roCheck, sfCheck, sxCheck;
    @FXML
    Button findButton, restartButton, exitButton, selectAll;
    @FXML
    TextField finisTextField;

    @FXML
    public void initialize() {
        createList();
    }

    /**
     * Method creates new file with column labels
     */
    public void createNewFile() {
        try {
            FileWriter fileWriter = new FileWriter("FindRaport.csv");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("Searched finis:" + "," + finis);
            bufferedWriter.newLine();
            bufferedWriter.write("Vehicle Line" + "," + "FCJ Menu Number" + "," + "Operation (EN)" + "," + "Year Dates   " + ","
                    + "Variant (EN)" + "," + "Part Description (EN)" + "," + "FINIS" + "," + "MLI" + ","
                    + "Parts Quantity" + "," + "Parts Unit Price excl. VAT" + "," + "Parts Retail Excl. VAT" + ","
                    + "Parts Retail Incl. VAT" + "," + "Labour Time" + "," + "Labour Cost Excl. VAT " + ","
                    + "Labour Cost Incl. VAT " + "," + "Repair Retail Excl. VAT" + "," + "Total Repair Incl. VAT " + ","
                    + "Fleet National Pricing Region 1" + "," + "Fleet National Pricing Region 2" + ","
                    + "Fleet National Pricing Region 3" + "," + "Fleet National Pricing Region 4" + ","
                    + "Ford Motorcraft 4+(Retail)" + "," + "Recommended Fitted Price(Retail)");
            bufferedWriter.newLine();
            bufferedWriter.close();

        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
    }

    /**
     * Method search thru selected market files and marks label on Green when file exists and on Red when file is unavailable
     */
    public void readSelectedMarkets() {
        checkIfFileExist();

        if ((finisTextField.getText()) != "") {
            for (int i = 0; i < layoutMarketCheckBox.size(); i++) {
                if (layoutMarketCheckBox.get(i).isSelected()) {
                    layoutMarketCheckBox.get(i).setDisable(false);
                    readFile(marketShortcut.get(i) + ".csv", layoutMarketCheckBox.get(i));
                } else
                    layoutMarketCheckBox.get(i).setDisable(true);
            }
            showAlert("Lines checked: " + lineCount + "\n" + "Occurrences of part: " + marketCount);
            lineCount = 0;
            marketCount = 0;
        } else
            showAlert("Specify part number first");
    }

    /**
     * Method initialize process of searching through price file
     */
    public void readFile(String path, CheckBox layoutMarketCheckBox) {
        finis = finisTextField.getText().toString();

        String row = "";

        try {
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            FileWriter fileWriter = new FileWriter("FindRaport.csv", true);
            BufferedWriter buff2 = new BufferedWriter(fileWriter);

            while ((row = bufferedReader.readLine()) != null) {
                lineCount++;
                if (row.toUpperCase().contains(finis.toUpperCase())) {
                    buff2.append(row);
                    buff2.newLine();
                    buff2.flush();
                    marketCount++;
                }
            }
            layoutMarketCheckBox.setTextFill(Color.GREEN);
            bufferedReader.close();
            buff2.close();
        } catch (Exception e1) {
            layoutMarketCheckBox.setTextFill(Color.RED);
            e1.printStackTrace();
        }
    }

    /**
     * Method checks whether previous raport exists and informs user about its deletion
     */
    public void checkIfFileExist() {
        File file = new File(outPath);
        if (file.exists()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK);
            alert.setHeaderText("Previous raport exist. Click OK to delete it and proceed with new search");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                file.delete();
                alert.close();
                createNewFile();
            }
        } else {
            createNewFile();
        }
    }

    /**
     * Method allows to show information alerts
     */
    public void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setHeaderText(text);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }

    /**
     * Method creates ArrayList of market labels (javafx elements) and ArrayList with markets shortcuts
     */
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

    /**
     * Method allows to select/deselect all markets for search
     */
    public void selectAllMarkets(){

        if(allSelected!=true){
            for(int i=0;i<layoutMarketCheckBox.size();i++){
                layoutMarketCheckBox.get(i).setSelected(true);
            }
            allSelected=true;
        }
        else{
            for(int i=0;i<layoutMarketCheckBox.size();i++){
                layoutMarketCheckBox.get(i).setSelected(false);
            }
            allSelected=false;
        }

    }

    /**
     * Method allows to restart app
     */
    public void restartApp() throws IOException {
        Stage stage = (Stage) restartButton.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("fullFileLayout.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Part Finder v.1.0 2017");
        primaryStage.setScene(new Scene(root, 800, 200));
        primaryStage.show();

    }

    /**
     * Method allows to close app
     */
    public void closeApp() throws IOException {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}



