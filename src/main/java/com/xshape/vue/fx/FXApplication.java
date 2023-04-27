package com.xshape.vue.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FXApplication extends Application {
    public static BorderPane _root = new BorderPane();
    public static FxBuilder fxBuilder = null;

    @Override
    public void start(Stage stage) throws IOException {
        fxBuilder = new FxBuilder(_root);
        _root = fxBuilder.build();
        Scene scene = new Scene(_root,800,635);
        stage.setTitle("Mini Editeur de Formes");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}