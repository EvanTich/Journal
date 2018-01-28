package com.evan.tichenor;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class Controller{

    // the path the journals will be saved to.
    // maybe will make it dynamic. maybe. TODO?
    private static final File saveFolder = new File("journals/");

    @FXML
    private TextField title; // or subject, one line, short

    @FXML
    private TextField description; // short, one line

    @FXML
    private TextArea body; // multi-line

    @FXML
    private Text saved; // near buttons

    @FXML
    private Text failed; // near buttons

    /**
     * Initializes all the good looking stuff.
     * @param stage the primary stage
     */
    public void init(Stage stage) {
        title.prefWidthProperty().bind(stage.widthProperty().subtract(16));
        description.prefWidthProperty().bind(stage.widthProperty().subtract(16));
        body.prefWidthProperty().bind(stage.widthProperty().subtract(16));

        String style = "-fx-font-family: 'Inconsolata', monospace; -fx-font-smoothing-type: lcd;";
        title.setStyle(style);
        description.setStyle(style);
        body.setStyle(style);

        if(!saveFolder.exists())
            saveFolder.mkdir();

        body.prefHeightProperty().bind(stage.heightProperty());
    }

    public TextField getTitle() {
        return title;
    }

    /**
     * Saves the journal entry.
     * Don't spam this. You'll die.
     * @return an unused value
     */
    @FXML
    public boolean save() {
        try {
            File file = new File(makeFile());

            file.createNewFile(); // should work. :)

            PrintWriter out = new PrintWriter(new FileWriter(file, true));
            out.append(getDate("yyyy/MM/dd HH:mm:ss")).println();
            out.append("# ").append(title.getText()).println();
            out.append(description.getText()).append("\n\n");
            out.append(body.getText()).println();
            out.println("END");
            out.close();

            // show that it saved
            saved.setVisible(true);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    saved.setVisible(false);
                }
            }, 5000);

            return true;
        } catch (IOException e) {
            // show that it failed
            failed.setVisible(true);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    failed.setVisible(false);
                }
            }, 10000);
            return false;
        }
    }

    /**
     * @return the file path and name
     */
    private String makeFile() {
        String title = this.title.getText();

        return saveFolder.getName() + "/" + getDate("yyyy.MM.dd - ") + title + ".md";
    }

    /**
     * Gets the local date from the pattern
     * @param pattern pattern for the date-machine
     * @return the formatted date
     */
    private String getDate(String pattern) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime now = LocalDateTime.now();
        return format.format(now);
    }

    /**
     * Shows the Info box.
     */
    @FXML
    public void info() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Information");
        info.setHeaderText("Program Information");
        info.setContentText(
                "This journal program was made by Evan Tichenor (evan.tichenor@gmail.com). Thanks for using it!"
        );

        info.showAndWait();
    }

    /**
     * Shows the Help box.
     */
    @FXML
    public void help() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Help");
        info.setHeaderText("Program Help");
        info.setContentText("Use Markdown in the body!"); // not that it does anything, something TODO maybe

        // I'd like to add a hyperlink, but that's not going to happen. Sadly.

        info.showAndWait();
    }

    /**
     * Clears the text boxes
     */
    @FXML
    public void clear() {
        title.clear();
        description.clear();
        body.clear();
    }
}
