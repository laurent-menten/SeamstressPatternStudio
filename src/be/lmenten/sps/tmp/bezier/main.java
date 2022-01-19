package be.lmenten.sps.tmp.bezier;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource( "app.fxml" ));
        Parent root = loader.load();
        AppController controller = loader.getController();
        controller.setStage(primaryStage);
        controller.addResizeListener();
        primaryStage.setMinHeight(536);
        primaryStage.setMinWidth(923);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
