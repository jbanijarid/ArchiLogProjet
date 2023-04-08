package com.xshape.vue.fx;

import com.xshape.modele.*;
import com.xshape.modele.fx.FxBuilder;
import com.xshape.modele.fx.FxFactory;
import com.xshape.modele.fx.FxRenderer;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FXApplication extends Application {

    //public static Composite toolBarElements = new Composite();
    //public static Composite menuElements = new Composite();
    //public static Composite whiteBoardElements = new Composite();
    public static BorderPane _root = new BorderPane();
    public static FxBuilder fxBuilder = null;

    @Override
    public void start(Stage stage) throws IOException {
        //Group root = new Group();
        //Canvas fxCanvas = new Canvas(800, 600);
        //_root.getChildren().add(fxCanvas);
        //FxRenderer fxRenderer = new FxRenderer(fxCanvas);

        //IShape rect = new Rectangle(100, 100, 200, 150, fxRenderer);
        //rect.draw();

        //IShape poly = new Polygone(400, 300, 100, 8, fxRenderer);
        //poly.draw();

        /*
        IFactory factory = new FxFactory();
        IShape rect = factory.createRectangle(100, 100, 20, 150, fxRenderer);
        rect.draw();


        IShape poly = new Polygone(400, 300, 100, 8, fxRenderer);
        poly.draw();


         */
        fxBuilder = new FxBuilder(_root);
        _root = fxBuilder.build();

        Scene scene = new Scene(_root,800,635);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}