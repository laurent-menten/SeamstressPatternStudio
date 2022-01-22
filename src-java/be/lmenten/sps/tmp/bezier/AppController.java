package be.lmenten.sps.tmp.bezier;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AppController {
    @FXML
    private Pane scenePane;
    @FXML
    private Slider smoothnessSlider;
    @FXML
    private Slider currentPointSlider;
    @FXML
    private Button minus,full;

    private Stage stage;

    static int WIDTH = 900;
    static int HEIGHT = 450;
    static int count = 0;
    Bezier bezier = new Bezier(WIDTH /2.0, HEIGHT / 2.0);

    public void initialize() {
        smoothnessSlider.valueProperty().addListener(observable -> {
            bezier.setSmoothness((int) smoothnessSlider.getValue());
            currentPointSlider.setMax(smoothnessSlider.getValue());
        });

        currentPointSlider.valueProperty().addListener(observable -> {
            bezier.setCurrentPoint((int) currentPointSlider.getValue());
        });

        Pane pane = new Pane();
        pane.setPrefSize(WIDTH,HEIGHT);

        Group root = new Group(pane,bezier.getBezierGroup());

        scenePane.getChildren().add(new SubScene(root, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED));
        Background background = new Background(new BackgroundFill(Color.web("183D42"), CornerRadii.EMPTY, Insets.EMPTY));
        scenePane.setBackground(background);

        pane.setOnMouseClicked(event -> addNodeLocation(event.getSceneX(),event.getSceneY()));

        full.setOnAction(event ->{
            stage.setFullScreen(!stage.isFullScreen());
            WIDTH = (int)scenePane.getWidth();
            HEIGHT = (int)scenePane.getHeight();
            updateScreen();
        });
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void addNode() {
        bezier.addNode(scenePane.getWidth()/2.0,scenePane.getHeight()/2.0);
        if (bezier.getNumOfNodes() > 3) {
            minus.setDisable(false);
        }
    }

    public void addNodeLocation(double x, double y) {
        bezier.addNode(x,y);
        if (bezier.getNumOfNodes() > 3) {
            minus.setDisable(false);
        }
    }

    public void removeNode() {
        if (bezier.getNumOfNodes() > 3) {
            bezier.removeNode();
        }
        if (bezier.getNumOfNodes() == 3) {
            minus.setDisable(true);
        }
    }

    public void toggleGuides(){
        bezier.toggleGuides();
    }

    public void addResizeListener() {
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            WIDTH = (int)scenePane.getWidth();
            updateScreen();
        });
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            HEIGHT = (int)scenePane.getHeight();
            updateScreen();
        });
    }

    //After resize, this is called to fix clipping
    private void updateScreen(){
        ((SubScene)scenePane.getChildren().get(0)).setWidth(WIDTH);
        ((SubScene)scenePane.getChildren().get(0)).setHeight(HEIGHT);
        ((Pane)((SubScene)scenePane.getChildren().get(0)).getRoot().getChildrenUnmodifiable().get(0)).setPrefSize(WIDTH,HEIGHT);
        bezier.clipBezier();
    }
}
