package com.xshape.vue.fx;

import com.xshape.modele.fx.JavaFXRenderer;
import com.xshape.modele.Polygone;
import com.xshape.modele.Rectangle;
import com.xshape.modele.Shape;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.IOException;

public class FXApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();
        Canvas fxCanvas = new Canvas(800, 600);
        root.getChildren().add(fxCanvas);
        JavaFXRenderer fxRenderer = new JavaFXRenderer(fxCanvas);

        Shape rect = new Rectangle(100, 100, 200, 150, fxRenderer);
        rect.draw();

        Shape poly = new Polygone(400, 300, 100, 8, fxRenderer);
        poly.draw();

        Scene scene = new Scene(root);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}