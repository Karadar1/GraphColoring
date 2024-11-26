module graphfx.graphcoloring {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires annotations;

    opens graphfx.graphcoloring to javafx.fxml;
    exports graphfx.graphcoloring;
    exports controllers;
    opens controllers to javafx.fxml;
    exports models;
    exports exceptions;
    opens models to javafx.fxml;
}