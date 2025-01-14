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
    requires java.sql;

    opens graphfx.graphcoloring.test to javafx.fxml;
    exports graphfx.graphcoloring.test;
    exports controllers;
    opens controllers to javafx.fxml;
    exports exceptions;
}