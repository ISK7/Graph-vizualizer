module com.example.graph_vizualizer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.google.gson;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.swing;
    requires com.github.vlsi.mxgraph.jgraphx;

    opens com.example.graph_vizualizer to javafx.fxml;
    exports com.example.graph_vizualizer;
}