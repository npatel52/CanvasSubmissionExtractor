import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class CanvasSubmissionExtractor extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // Set title for GUI
        primaryStage.setTitle("Canvas Submission Extractor");

        // For removing default focus from text field
        final BooleanProperty onInit = new SimpleBooleanProperty(true);

        // Adding components a text area and a button

        // Text area to enter section number
        TextField sectionInput = new TextField();
        sectionInput.setPromptText("Enter your section number. Example: 11H4");
        sectionInput.setPrefWidth(250);
        sectionInput.setEditable(true);


        // Button to perform extraction
        Button extract = new Button("Extract");

        // Horizontal view with twp children one text field and one button
        HBox hBox = new HBox(sectionInput,extract);
        hBox.setPadding(new Insets(10  ,0,10,0));
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);

        // Horizontal scene
        Scene scene = new Scene(hBox,400,100);

        // To remove default focus from text field
        sectionInput.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if(onInit.get() && newValue){
                hBox.requestFocus();
                onInit.setValue(false); // Change to false after first load
            }
        }));

        // adding scene to stage and showing it
        primaryStage.setScene(scene);
        primaryStage.show();

        extract.setOnAction(event -> {
            onExtractClick(sectionInput.getText());
        });
    }

    /**
     * Handles button click
     */
    public static void onExtractClick(String section) {
        if (isValidSection(section)) {
            /*
            Once section is partially validated, there must be two files in the current directory
            an excel file and a submission file.
            */


            // Submission file must be name submissions
            File submissionFile = new File(System.getProperty("user.dir") + File.separator + "submissions" + File.separator);

            if(!submissionFile.exists()) {
                showAlertDialog("File Access Error", "Submission file is missing!",Alert.AlertType.ERROR);
            }else{
                File excelFile = null;
                if(findExcelFilePath() != null) {
                    excelFile = new File(findExcelFilePath());
                    // Both files found so, extract the section

                    // Extract section number
                    ExtractSections es = new ExtractSections(section, excelFile.getAbsolutePath());

                    // names of student in the section
                    List<String> studentList = es.ExtractSection();

                    // File ordering
                    FileOrdering fo = new FileOrdering(section, studentList);

                    // Unzip java files only
                    fo.unzipFiles(".java");

                    showAlertDialog("Task Completed", "", Alert.AlertType.INFORMATION);
                }else{
                    showAlertDialog("File Access Error", "Excel file is missing!",Alert.AlertType.ERROR);
                }
            }
        }else{
            showAlertDialog("Invalid Section", "Enter a valid section, e.g. 11H4",Alert.AlertType.ERROR);
        }
    }

    public static String findExcelFilePath(){

        File currrentDirectory = new File(System.getProperty("user.dir"));
        // All files in the current directory
        File[] filesInDirectory = currrentDirectory.listFiles();

        for (File file : filesInDirectory) {
                // Excel file extension .xlsx
                if (file.getName().contains(".xlsx")) {
                    return file.getAbsolutePath();
                }
        }
        return null;
    }

    /**
     * Validates section number
     * @param section
     * @return
     */
    public static boolean isValidSection(String section){
        // Section number must be non-empty string with length at least 4
        if(section == null || section.trim().isEmpty())
            return false;
        if(section.length() > 0 && section.length() < 4)
            return false;
        return true;
    }
    /**
     * Pops error dialog
     * @param title
     * @param message
     */
    public static void showAlertDialog(String title, String message, Alert.AlertType at){
        Alert alert = new Alert(at);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
