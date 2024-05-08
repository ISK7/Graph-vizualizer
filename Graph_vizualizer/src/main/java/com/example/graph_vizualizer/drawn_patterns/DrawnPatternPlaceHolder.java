package com.example.graph_vizualizer.drawn_patterns;

import com.example.graph_vizualizer.graph.Edge;
import com.example.graph_vizualizer.graph.Graph;
import com.example.graph_vizualizer.graph.Point;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DrawnPatternPlaceHolder implements DrawnPattern{
    final private int classHeight = 30;
    final private int classWidth = 60;
    final private int staticHeight = 20;
    final private int staticWidth = 40;
    final private int ordinaryHeight = 20;
    final private int ordinaryWidth = 20;
    final private String privateColor = "#FF0000";
    final private String protectedColor = "#0000FF";
    final private String publicColor = "#00FF00";
    final private String defaultColor = "#00FFFF";
    final private String implementsColor = "#00FF00";
    final private String containsColor = "#0000FF";
    final private String extendsColor = "#00FFFF";
    final private String classShapeStr = mxConstants.SHAPE_DOUBLE_RECTANGLE;
    final private String staticShapeStr = mxConstants.SHAPE_RECTANGLE;
    final private String ordinaryShapeStr = mxConstants.SHAPE_ELLIPSE;
    @Override
    public void drawGraph(AnchorPane pane, Graph sourceGraph) {
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        Map<Point,Object> poObj = new HashMap<>();
        ArrayList<Object> privateStyle = new ArrayList<>(), protectedStyle = new ArrayList<>(),
                publicStyle = new ArrayList<>(), defaultStyle = new ArrayList<>(),
                classShape = new ArrayList<>(), staticShape = new ArrayList<>(), ordinaryShape = new ArrayList<>();
        for(Point p : sourceGraph.getPoints()) {
            double w = getWidth(p);
            double h = getHeight(p);
            Object obj = graph.insertVertex(parent,null, p.getName(), 0.0, 0.0, w, h);
            poObj.put(p, obj);
            switch (p.getaType()) {
                case PRIVATE -> {privateStyle.add(obj);}
                case PROTECTED -> {protectedStyle.add(obj);}
                case PUBLIC -> {publicStyle.add(obj);}
                case DEFAULT -> {defaultStyle.add(obj);}
            }
            switch (p.getpType()) {
                case CLASS -> classShape.add(obj);
                case STATIC -> staticShape.add(obj);
                case ORDINARY -> ordinaryShape.add(obj);
            }
        }

        graph.setCellStyles(mxConstants.STYLE_FILLCOLOR,privateColor,privateStyle.toArray());
        graph.setCellStyles(mxConstants.STYLE_FILLCOLOR,protectedColor,protectedStyle.toArray());
        graph.setCellStyles(mxConstants.STYLE_FILLCOLOR,publicColor,publicStyle.toArray());
        graph.setCellStyles(mxConstants.STYLE_FILLCOLOR,defaultColor,defaultStyle.toArray());

        graph.setCellStyles(mxConstants.STYLE_SHAPE, classShapeStr, classShape.toArray());
        graph.setCellStyles(mxConstants.STYLE_SHAPE, staticShapeStr, staticShape.toArray());
        graph.setCellStyles(mxConstants.STYLE_SHAPE, ordinaryShapeStr, ordinaryShape.toArray());

        for(Edge e : sourceGraph.getEdges()) {
            Object edge = graph.insertEdge(parent,null,getEdgeName(e), poObj.get(e.getInput()), poObj.get(e.getOutput()));
            ((mxCell) edge).setStyle(getStyle(e));
        }

        mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
        layout.execute(parent);

        graph.setCellsEditable(false);

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setEnterStopsCellEditing(false);
        graphComponent.setInvokesStopCellEditing(false);
        SwingNode swingNode = new SwingNode();
        swingNode.setDisable(true);
        swingNode.setContent(graphComponent);
        pane.getChildren().add(swingNode);
        pane.setTopAnchor(swingNode,0.0);
        pane.setBottomAnchor(swingNode,0.0);
        pane.setLeftAnchor(swingNode,0.0);
        pane.setRightAnchor(swingNode,0.0);
    }

    private String getStyle(Edge e) {
        switch (e.getType()) {
            case CONTAIN -> {return "strokeColor=" + containsColor;}
            case IMPLEMENTS -> {return "strokeColor=" + implementsColor;}
            case EXTENDS -> {return "strokeColor=" + extendsColor;}
            default -> {return "strokeColor=" + "black";}
        }
    }

    private int getWidth(Point p) {
        switch (p.getpType()) {
            case CLASS -> {return classWidth;}
            case STATIC -> {return staticWidth;}
            default -> {return ordinaryWidth;}
        }
    }

    private int getHeight(Point p) {
        switch (p.getpType()) {
            case CLASS -> {return classHeight;}
            case STATIC -> {return staticHeight;}
            default -> {return ordinaryHeight;}
        }
    }

    protected String getEdgeName(Edge e) {
        switch (e.getType()) {
            case IMPLEMENTS -> {return "Impl";}
            case CONTAIN -> {return "Cont";}
            case EXTENDS -> {return "Extends";}
            default -> {return "";}
        }
    }

    @Override
    public String getName() {
        return "Default_v_1";
    }
}
