package com.example.graph_vizualizer;

import com.example.graph_vizualizer.drawn_patterns.DrawnPattern;
import com.example.graph_vizualizer.drawn_patterns.DrawnPatternPlaceHolder;
import com.example.graph_vizualizer.graph.Graph;
import com.example.graph_vizualizer.graph_patterns.GraphPattern;
import com.example.graph_vizualizer.graph_patterns.GraphPatternPlaceHolder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javax.swing.JOptionPane.showMessageDialog;

public class MainController implements Initializable {
    @FXML
    Menu menu_file;
    @FXML
    Menu menu_properties;
    @FXML
    MenuItem menu_item_choose;
    @FXML
    MenuItem menu_item_open;
    @FXML
    MenuItem menu_item_saveas;
    @FXML
    Menu menu_item_graph_pattern;
    @FXML
    Menu menu_item_drawn_pattern;
    @FXML
    Label label_folder;
    @FXML
    Label label_graph;
    @FXML
    Label label_drawn;
    @FXML
    AnchorPane graph_pane;
    @FXML
    Pane draw_pane;
    @FXML
    Button button_visualize;

    File folder;

    private GraphPattern currentGraphPattern;
    private DrawnPattern currentDrawnPattern;
    private ArrayList<GraphPattern> graphPatterns;
    private ArrayList<DrawnPattern> drawnPatterns;

    protected void visualize() {
        if(!folder.exists()) {
            showMessageDialog(null, "Folder does not exist!");
            return;
        }
        if(!folder.isDirectory()) {
            showMessageDialog(null, "Choose folder!");
            return;
        }
        if(currentDrawnPattern == null) {
            showMessageDialog(null, "Choose drawn pattern!");
            return;
        }
        if(currentGraphPattern == null) {
            showMessageDialog(null, "Choose graph pattern!");
            return;
        }
        graph_pane.getChildren().clear();
        Graph graph = currentGraphPattern.newGraph(folder);
        currentDrawnPattern.drawGraph(graph_pane, graph);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        graphPatterns = new ArrayList<>();
        graphPatterns.add(new GraphPatternPlaceHolder());
        for(GraphPattern p: graphPatterns) {
            MenuItem mI = new MenuItem(p.getName());
            mI.setOnAction(event -> {
                currentGraphPattern = p;
                label_graph.setText("Graph pattern: " + p.getName());
            });
            menu_item_graph_pattern.getItems().add(mI);
        }
        drawnPatterns = new ArrayList<>();
        drawnPatterns.add(new DrawnPatternPlaceHolder());
        for(DrawnPattern p: drawnPatterns) {
            MenuItem mI = new MenuItem(p.getName());
            mI.setOnAction(event -> {
                currentDrawnPattern = p;
                label_drawn.setText("Drawn pattern: " + p.getName());
            });
            menu_item_drawn_pattern.getItems().add(mI);
        }

        graph_pane.prefWidthProperty().bind(draw_pane.widthProperty());
        graph_pane.prefHeightProperty().bind(draw_pane.heightProperty());

        menu_item_choose.setOnAction(v -> onDirectoryChooseClick());
        button_visualize.setOnMouseClicked(v -> visualize());
    }
    public void onDirectoryChooseClick() {
        DirectoryChooser fc = new DirectoryChooser();
        folder = fc.showDialog(null);
        label_folder.setText(folder.getAbsolutePath());
    }
}