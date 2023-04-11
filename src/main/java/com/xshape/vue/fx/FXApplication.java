package com.xshape.vue.fx;

import com.xshape.modele.fx.FxBuilder;

import javafx.application.Application;
import javafx.scene.Scene;
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

        // Créer un rectangle
        /*javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(50, 50, 100, 100);
        rect.setFill(Color.RED);

        // Créer un menu contextuel
        ContextMenu menu = new ContextMenu();

        MenuItem item1 = new MenuItem("Modifier");
        item1.setOnAction(event -> {
            // Code pour modifier les propriétés de l'objet
            System.out.println("Modifier");
        });

        MenuItem item2 = new MenuItem("Supprimer");
        item2.setOnAction(event -> {
            // Code pour supprimer l'objet
            System.out.println("Supprimer");
        });

        menu.getItems().addAll(item1, item2);

        // Ajouter un écouteur de clic droit sur le rectangle
        rect.setOnContextMenuRequested(event -> {
            menu.show(rect, event.getScreenX(), event.getScreenY());
        });

        // Afficher le rectangle dans une scène
        StackPane root = new StackPane();
        root.getChildren().add(rect);
        Scene scene = new Scene(root, 300, 250);*/

        Scene scene = new Scene(_root,800,635);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}