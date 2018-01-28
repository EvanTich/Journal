package com.evan.tichenor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("app.fxml"));

        Parent root = loader.load();
        primaryStage.setTitle("Journal");

        Controller control = loader.getController();
        control.init(primaryStage);

        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.getScene().getStylesheets().add("style.css");

        // these don't even work, but I have them here anyways (just pets I guess)
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(300);

        primaryStage.titleProperty().bind(control.getTitle().textProperty().concat(pokeFun()));

        primaryStage.setOnCloseRequest(e -> {
            System.gc(); // won't exit until garbage is collected, just like any serious parent.
            Platform.exit();
        });
        primaryStage.show();
    }

    /**
     * Don't think about these too much, it's just poking fun.
     * @return some fun
     */
    private static String pokeFun() {
        return " - " + new String[] {
                "don't die edition",
                "now with cats!",
                "made with love",
                "made with hate",
                "uhh, yeaaaah..." // shout out to physics class
        }[(int)(Math.random() * 5)];
    }

    public static void main(String[] args) {
        launch(args);
    }
}
