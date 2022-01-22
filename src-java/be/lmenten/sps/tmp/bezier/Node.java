package be.lmenten.sps.tmp.bezier;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

public class Node {
    private Group node = new Group();
    private Circle circle;
    private Color color = Color.BLACK;
    private int nodeNum;
    private Text text;
    private double textOffset;
    private double X,Y;
    private int radius = 5;

    public Node(double x, double y, int nodeNum, double opacity) {
        AppController.count++;
        this.nodeNum = nodeNum;

        //Circle Setup
        this.circle = new Circle(x, y, radius);
        this.circle.setFill(color);
        this.circle.setOpacity(opacity);
        //Text Setup
        this.text = new Text("" + nodeNum);
        this.text.setBoundsType(TextBoundsType.VISUAL);
        this.text.setFont(Font.font(14));
        this.text.setTextOrigin(VPos.CENTER);
        this.text.setTextAlignment(TextAlignment.LEFT);

        X = circle.centerXProperty().get();
        Y = circle.centerYProperty().get();

        setTextOffset();

        this.node.getChildren().addAll(circle);
    }

    private void setTextOffset(){
        this.textOffset = 4;

        if(nodeNum>=10){
            this.textOffset +=4;
        }
        if(nodeNum >= 100){
            this.textOffset +=3;
            this.text.setFont(Font.font(12));
        }
        if(nodeNum >= 1000){
            this.text.setFont(Font.font(9));
        }

        this.text.xProperty().set(this.circle.centerXProperty().get() - textOffset);
        this.text.yProperty().set(this.circle.centerYProperty().get());
    }

    public void updateNode(MouseEvent e){
        this.circle.centerXProperty().set(e.getSceneX());
        this.circle.centerYProperty().set(e.getSceneY());
        clipToScreen();
        this.text.xProperty().set(this.circle.centerXProperty().get() - textOffset);
        this.text.yProperty().set(this.circle.centerYProperty().get());
        X = circle.centerXProperty().get();
        Y = circle.centerYProperty().get();
    }

    public void clipToScreen() {
        double locX = this.circle.centerXProperty().get();
        double locY = this.circle.centerYProperty().get();
        if (locX < radius) {
            this.circle.centerXProperty().set(radius);
        }
        if (locX > AppController.WIDTH -radius*2) {
            this.circle.centerXProperty().set(AppController.WIDTH - radius);
        }
        if (locY < radius) {
            this.circle.centerYProperty().set(radius);
        }
        if (locY > AppController.HEIGHT - radius) {
            this.circle.centerYProperty().set(AppController.HEIGHT -radius);
        }
        X = circle.centerXProperty().get();
        Y = circle.centerYProperty().get();
    }

    public int getNodeNum(){
        return nodeNum;
    }

    public Group getNode(){
        return node;
    }

    public double getX(){
        return X;
    }

    public double getY() {
        return Y;
    }
}
