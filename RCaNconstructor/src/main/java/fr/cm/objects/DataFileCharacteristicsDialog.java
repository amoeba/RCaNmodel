package fr.cm.objects;

import fr.cm.Main.Context;
import fr.cm.preferences.ColorsAndFormats;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.Optional;

public class DataFileCharacteristicsDialog extends Dialog <ButtonType> {
    double width = Context.getWindowWidth();
    Label middleTitle, labelShortName, labelMetaInformation, labelPathLabel, labelPathStill, labelPathStillOK, labelOwner;
    TextField fieldShortName, fieldOwner;
    TextArea areaMetaInformation;
    Text textPath;
    DataFile selectedDataFile;
    VBox  middleBox = new VBox();

    int middleWidth = (int)(0.3 * width);

    public DataFileCharacteristicsDialog(DataFile selectedDataFile){
        this.selectedDataFile = selectedDataFile;
        middleTitle = new Label("Meta information about selected file");
        labelShortName = new Label("Short name of datafile");
        fieldShortName = new TextField("Short name");
        labelMetaInformation = new Label("Information about file");
        areaMetaInformation = new TextArea(" Some meta information here.");
        textPath = new Text("--");
        textPath.wrappingWidthProperty().set(middleWidth);
        labelPathLabel = new Label("Original address on disk");
        labelPathStill = new Label("Address");
        labelPathStillOK = new Label("Yes");
        labelOwner = new Label("Owner");
        fieldOwner = new TextField("--");
        middleTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

        GridPane.setHalignment(labelShortName, HPos.RIGHT);
        GridPane.setHalignment(labelOwner, HPos.RIGHT);
        GridPane.setHalignment(labelMetaInformation, HPos.RIGHT);
        GridPane.setHalignment(labelPathLabel, HPos.RIGHT);
        GridPane.setHalignment(labelPathStill, HPos.RIGHT);

        GridPane grid = new GridPane();
        grid.addRow(0,  labelShortName, fieldShortName);
        grid.addRow(1,  labelOwner, fieldOwner);
        grid.addRow(2,  labelMetaInformation, areaMetaInformation);
        grid.addRow(3,  labelPathLabel, textPath);
        grid.addRow(4,  labelPathStill, labelPathStillOK);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(8);
        grid.setVgap(15);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(25);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(75);
        grid.getColumnConstraints().addAll(column1, column2);

        middleBox.getChildren().addAll(middleTitle, grid);
        if(selectedDataFile != null) {
            middleTitle.setText("Selected data file");
            middleTitle.setFont(ColorsAndFormats.titleFont);
            fieldShortName.setText(selectedDataFile.getShortName());
            areaMetaInformation.setText(selectedDataFile.getMetaInformation());
            textPath.setText(selectedDataFile.getFullFileName());
            labelPathStill.setText("The file is : ");
            if(selectedDataFile.isStillExisting()){
                labelPathStillOK.setText(" still at this address on disk");
            } else {
                labelPathStillOK.setText(" not now at this address on disk ");
            }
        }

        this.setTitle("Characteristics of file "+ selectedDataFile.getShortName());
        this.getDialogPane().setContent(middleBox);
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        this.getDialogPane().setStyle(ColorsAndFormats.font);

        Optional<ButtonType> result = this.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                try{
                    selectedDataFile.setShortName(fieldShortName.getText());
                    selectedDataFile.setFullFileName(textPath.getText());
                    selectedDataFile.setOwner(fieldOwner.getText());
                    selectedDataFile.setMetaInformation(areaMetaInformation.getText());
                    Context.setTextAreaContent("True");
                }
                catch(Exception ex){
                    Context.setTextAreaContent("False");
                }
            }
        }
    }



}

