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
    requires java.xml.bind;

    opens com.example.graph_vizualizer to javafx.fxml, java.xml.bind;
    opens com.example.graph_vizualizer.graph to java.xml.bind;
    exports com.example.graph_vizualizer;
    exports com.example.graph_vizualizer.graph to java.xml.bind;
}