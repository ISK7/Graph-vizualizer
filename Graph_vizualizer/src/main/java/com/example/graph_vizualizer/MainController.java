package com.example.graph_vizualizer;

import com.example.graph_vizualizer.drawn_patterns.DrawnPattern;
import com.example.graph_vizualizer.graph_patterns.GraphPattern;
import com.example.graph_vizualizer.graph_patterns.GraphPatternPlaceHolder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    Menu menu_file;
    @FXML
    Menu menu_properties;
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
    Button button_visualize;


    private GraphPattern currentGraphPattern;
    private DrawnPattern currentDrawnPattern;
    private ArrayList<GraphPattern> graphPatterns;
    private ArrayList<DrawnPattern> drawnPatterns;
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

        for(DrawnPattern p: drawnPatterns) {
            MenuItem mI = new MenuItem(p.getName());
            mI.setOnAction(event -> {
                currentDrawnPattern = p;
                label_drawn.setText("Drawn pattern: " + p.getName());
            });
            menu_item_drawn_pattern.getItems().add(mI);
        }


    }
}