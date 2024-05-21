package com.example.graph_vizualizer;

import com.example.graph_vizualizer.drawn_patterns.DrawnPattern;
import com.example.graph_vizualizer.drawn_patterns.DrawnPatternPlaceHolder;
import com.example.graph_vizualizer.graph_patterns.GraphPattern;
import com.example.graph_vizualizer.graph_patterns.GraphPatternDefaultStructure;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    byte[] graph;
    private GraphPattern currentGraphPattern;
    private DrawnPattern currentDrawnPattern;

    protected void visualize() {
        if (!folder.exists()) {
            showMessageDialog(null, "Folder does not exist!");
            return;
        }
        if (currentDrawnPattern == null) {
            showMessageDialog(null, "Choose drawn pattern!");
            return;
        }
        if (currentGraphPattern == null) {
            showMessageDialog(null, "Choose graph pattern!");
            return;
        }
        graph_pane.getChildren().clear();
        graph = null;
        graph = currentGraphPattern.newGraph(folder);
        if(graph == null) {
            showMessageDialog(null, "Serialization error!");
            return;
        }
        if(!currentDrawnPattern.drawGraph(graph_pane, graph)) showMessageDialog(null, "Visualisation error!");;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<GraphPattern> graphPatterns;
        ArrayList<DrawnPattern> drawnPatterns;
        graphPatterns = new ArrayList<>();
        graphPatterns.add(new GraphPatternDefaultStructure());
        for (GraphPattern p : graphPatterns) {
            MenuItem mI = new MenuItem(p.getName());
            mI.setOnAction(event -> {
                currentGraphPattern = p;
                label_graph.setText("Graph pattern: " + p.getName());
            });
            menu_item_graph_pattern.getItems().add(mI);
        }
        drawnPatterns = new ArrayList<>();
        drawnPatterns.add(new DrawnPatternPlaceHolder());
        for (DrawnPattern p : drawnPatterns) {
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
        menu_item_open.setOnAction(v -> onFileChooseClick());
        menu_item_saveas.setOnAction(v -> onSaveClick());
        button_visualize.setOnMouseClicked(v -> visualize());
        draw_pane.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            updateGraphComponent(newValue.getWidth(), newValue.getHeight());
        });
    }

    private void onFileChooseClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("xml files", "*.xml"),
                new FileChooser.ExtensionFilter("java files", "*.java")
        );

        folder = fileChooser.showOpenDialog(null);

        if (folder != null) {
            label_folder.setText(folder.getAbsolutePath());
        }
    }

    private void onSaveClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as");

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("xml files", "*.xml")
        );

        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile != null) {
            try {
                Path path = Paths.get(selectedFile.getAbsolutePath());
                Files.write(path, graph);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onDirectoryChooseClick() {
        DirectoryChooser fc = new DirectoryChooser();
        folder = fc.showDialog(null);
        if (folder != null) label_folder.setText(folder.getAbsolutePath());
    }
    private void updateGraphComponent(double width, double height) {
        graph_pane.resize(width,height);
    }
}