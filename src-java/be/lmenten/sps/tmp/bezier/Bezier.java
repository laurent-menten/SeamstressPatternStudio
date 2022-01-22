package be.lmenten.sps.tmp.bezier;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import java.util.LinkedList;

public class Bezier {
    private LinkedList<Node> nodes = new LinkedList<>();
    private LinkedList<Line> connections = new LinkedList<>();
    private LinkedList<Line> intermediateLines = new LinkedList<>();
    private LinkedList<Line> bezierCurve = new LinkedList<>();
    private LinkedList<Point> curvePoints = new LinkedList<>();

    private Group bezierGroup = new Group();
    private int numOfNodes;
    private double nodeStartX,nodeStartY;
    private int smoothness = 2;
    private int currentPoint = 0;
    private boolean guidesOn = true;
    double opacityNodes = 1,opacityIntermediate = 0.1,opacityConnectors = 0.1;
    private Color curveColor = Color.RED;
    public Bezier(double nodeX,double nodeY){
        this.nodeStartX = nodeX;
        this.nodeStartY = nodeY;

        for(numOfNodes =0; numOfNodes< 3; numOfNodes++){
            nodes.add(new Node(nodeX,nodeY,numOfNodes,opacityNodes));
        }
        connectNodes();

        buildGroup();
        actionHandler();
    }

    //Making Connections
    private void constructIntermediateLines(LinkedList<Line> lines) {
        LinkedList<Line> newLines = new LinkedList();

        //Base Case
        if (lines.size() == 1) {
            return;
        }

        for (int i = 0; i < lines.size() - 1; i++) {
            double startX = lines.get(i).getStartX() + ((lines.get(i).getEndX() - lines.get(i).getStartX()) * currentPoint / smoothness);
            double startY = lines.get(i).getStartY() + ((lines.get(i).getEndY() - lines.get(i).getStartY()) * currentPoint/ smoothness);
            double endX = lines.get(i + 1).getStartX() + ((lines.get(i + 1).getEndX() - lines.get(i + 1).getStartX()) * currentPoint/ smoothness);
            double endY = lines.get(i + 1).getStartY() + ((lines.get(i + 1).getEndY() - lines.get(i + 1).getStartY()) * currentPoint/ smoothness);
            Line line = new Line(startX, startY, endX, endY);
            line.setOpacity(opacityIntermediate);
            intermediateLines.add(line);
            newLines.add(line);
        }
        constructIntermediateLines(newLines);
    }

    private void connectNodes(){
        connections.clear();
        intermediateLines.clear();
        for(int i  = 0; i < nodes.size() -1; i++){
            Line line = new Line(nodes.get(i).getX(),nodes.get(i).getY(),nodes.get(i+1).getX(),nodes.get(i+1).getY());
            line.setOpacity(opacityConnectors);
            connections.add(line);
        }
        constructIntermediateLines(connections);
        constructCurve();
    }

    private void constructCurve() {
        curvePoints.clear();
        bezierCurve.clear();

        for (int i = 0; i <= currentPoint; i++) {
            int tempCurrent = currentPoint;
            currentPoint = i;
            updateConnections();
            currentPoint = tempCurrent;

            double x = intermediateLines.getLast().getStartX() + ((intermediateLines.getLast().getEndX() - intermediateLines.getLast().getStartX()) * i / smoothness);
            double y = intermediateLines.getLast().getStartY() + ((intermediateLines.getLast().getEndY() - intermediateLines.getLast().getStartY()) * i / smoothness);
            curvePoints.add(new Point(x, y));
        }

        for (int i = 0; i < curvePoints.size() - 1; i++) {
            double startX = curvePoints.get(i).x;
            double startY = curvePoints.get(i).y;
            double endX = curvePoints.get(i + 1).x;
            double endY = curvePoints.get(i + 1).y;

            Line line = new Line(startX, startY, endX, endY);
            line.setStroke(curveColor);
            bezierCurve.add(line);
        }

        buildGroup();
    }

    private void updateCurve() {
        for (int i = 0; i <= currentPoint; i++) {
            int tempCurrent = currentPoint;
            currentPoint = i;
            updateConnections();
            currentPoint = tempCurrent;

            double x = intermediateLines.getLast().getStartX() + ((intermediateLines.getLast().getEndX() - intermediateLines.getLast().getStartX()) * i / smoothness);
            double y = intermediateLines.getLast().getStartY() + ((intermediateLines.getLast().getEndY() - intermediateLines.getLast().getStartY()) * i / smoothness);
            curvePoints.get(i).x = x;
            curvePoints.get(i).y = y;
        }

        for (int i = 0; i < curvePoints.size() - 1; i++) {
            double startX = curvePoints.get(i).x;
            double startY = curvePoints.get(i).y;
            double endX = curvePoints.get(i + 1).x;
            double endY = curvePoints.get(i + 1).y;

            bezierCurve.get(i).setStartX(startX);
            bezierCurve.get(i).setStartY(startY);
            bezierCurve.get(i).setEndX(endX);
            bezierCurve.get(i).setEndY(endY);
        }
    }

    //Connections Updating

    private void updateIntermediateLines(LinkedList<Line> lines,int index){
        LinkedList<Line> newLines = new LinkedList();
        int addToIndex = 0;
        //Base Case
        if (lines.size() == 1) {
            return;
        }

        for(int i = 0; i <lines.size() -1; i++){
            intermediateLines.get(i + index).setStartX(lines.get(i).getStartX() + ((lines.get(i).getEndX() - lines.get(i).getStartX()) * currentPoint / smoothness));
            intermediateLines.get(i + index).setStartY(lines.get(i).getStartY() + ((lines.get(i).getEndY() - lines.get(i).getStartY()) * currentPoint / smoothness));
            intermediateLines.get(i + index).setEndX(lines.get(i + 1).getStartX() + ((lines.get(i + 1).getEndX() - lines.get(i + 1).getStartX()) * currentPoint / smoothness));
            intermediateLines.get(i + index).setEndY(lines.get(i + 1).getStartY() + ((lines.get(i + 1).getEndY() - lines.get(i + 1).getStartY()) * currentPoint / smoothness));
            intermediateLines.get(i + index).setOpacity(opacityIntermediate);
            Line line = new Line(intermediateLines.get(i+index).getStartX(),intermediateLines.get(i+index).getStartY(),
                    intermediateLines.get(i+index).getEndX(), intermediateLines.get(i+index).getEndY());
            newLines.add(line);
            addToIndex++;
        }
        index+=addToIndex;
        updateIntermediateLines(newLines,index);
    }

    private void updateConnections(){
        for(int i  = 0; i < connections.size(); i++){
            connections.get(i).setStartX(nodes.get(i).getX());
            connections.get(i).setStartY(nodes.get(i).getY());
            connections.get(i).setEndX(nodes.get(i + 1).getX());
            connections.get(i).setEndY(nodes.get(i + 1).getY());
            connections.get(i).setOpacity(opacityConnectors);
        }
        updateIntermediateLines(connections, 0);

    }

    //Building Group
    private void buildGroup(){
        bezierGroup.getChildren().clear();

        for(int i = 0;i<intermediateLines.size();i++){
            bezierGroup.getChildren().add(intermediateLines.get(i));
        }

        for(int i = 0;i<connections.size();i++){
            bezierGroup.getChildren().add(connections.get(i));
        }

        for(int i = 0; i<bezierCurve.size();i++){
            bezierGroup.getChildren().add(bezierCurve.get(i));
        }

        for(int i = 0;i<nodes.size();i++){
            bezierGroup.getChildren().add(nodes.get(i).getNode());
        }
    }

    //Handling Updates
    private void actionHandler() {
        for(Node n: nodes){
            n.getNode().setOnMouseDragged(e ->{
                n.updateNode(e);
                updateConnections();
                updateCurve();
            });
        }
    }

    public void clipBezier(){
        for(Node n: nodes){
            n.clipToScreen();
        }
        updateConnections();
        updateCurve();
        buildGroup();
    }

    //Adding and Removing Nodes
    public void addNode(double x, double y){
        nodes.add(new Node(x,y,numOfNodes,opacityNodes));
        numOfNodes++;
        connectNodes();
        buildGroup();
        actionHandler();
    }

    public void removeNode(){
        numOfNodes--;
        for (Node n: nodes) {
            if(n.getNodeNum() == numOfNodes){
             nodes.remove(n);
             break;
            }
        }
        connectNodes();
        buildGroup();
    }

    //Getters and Setters
    public int getNumOfNodes(){
        return numOfNodes;
    }

    public Group getBezierGroup(){
        return bezierGroup;
    }

    public void setSmoothness(int smoothness){
       this.smoothness = smoothness;
       updateConnections();
       constructCurve();
    }

    public void setCurrentPoint(int currentPoint){
        this.currentPoint = currentPoint;
        updateConnections();
        constructCurve();
    }

    public void toggleGuides(){
        if(guidesOn) {
            opacityNodes = 0;
            opacityIntermediate = 0;
            opacityConnectors = 0;
            guidesOn = false;
        } else{
            opacityNodes = 1;
            opacityIntermediate = 0.1;
            opacityConnectors = 0.2;
            guidesOn = true;
        }

        for(int i =0 ; i<nodes.size(); i++){
            nodes.get(i).getNode().getChildren().get(0).setOpacity(opacityNodes);
        }
        updateConnections();
    }
}
