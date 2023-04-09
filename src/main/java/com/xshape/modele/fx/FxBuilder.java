package com.xshape.modele.fx;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_REDPeer;
import com.xshape.modele.*;
import javafx.scene.Camera;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.Stack;

public class FxBuilder implements IBuilder, Event {

    private Composite toolbar;
    private Composite whiteboard;
    private BorderPane borderPane;
    private IRenderer _renderer;
    private Canvas _canvas;
    //IShape selectedShape = null;

    public static FxWhiteBoard _whiteBoard;
    private IFactory factory = new FxFactory();

    private static Stack<Command> undoStack = new Stack<>();
    private static Stack<Command> redoStack = new Stack<>();



    public FxBuilder(BorderPane borderPane) {
        this.toolbar = new Composite();
        //this.whiteboard = whiteboard;
        this.borderPane = borderPane;
        this._canvas = new Canvas(800, 600);
        borderPane.setBottom(_canvas);
        this._renderer = new FxRenderer(_canvas);
    }

    @Override
    public void toolBar() {
        _renderer.drawLine(100, 10, 100, 600);
        _renderer.drawLine(10, 10, 10, 600);
        _renderer.drawLine(10, 10, 100, 10);
        _renderer.drawLine(10, 600, 100, 600);
        IShape rect = factory.createRectangle(25, 40, 50, 40, _renderer);
        IShape poly = factory.createPolygone(50, 120, 30, 6, _renderer);
        toolbar.add(rect);
        toolbar.add(poly);
        toolbar.draw();
    }


    public ImageView imgView(String s){
        InputStream inputStream = getClass().getResourceAsStream(s);
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        return imageView;
    }

    @Override
    public void menuBar() {
        ToolBar toolBar = new ToolBar();

        ImageView imgUndo = imgView("/com/xshape/undo.png");

        //Boutton undo
        Button undoButton = factory.createButton("");
        undoButton.setGraphic(imgUndo);

        undoButton.setOnAction(event -> {
            if (!undoStack.empty()) {
                Command command = undoStack.pop();
                command.undo();
                redoStack.push(command);
                redrawCanvas();
            }
        });

        toolBar.getItems().add(undoButton);


        ImageView imgRedo = imgView("/com/xshape/redo.png");
        // Boutton redo
        Button redoButton = factory.createButton("");
        redoButton.setGraphic(imgRedo);

        redoButton.setOnAction(event -> {
            if (!redoStack.empty()) {
                Command command = redoStack.pop();
                command.execute();
                undoStack.push(command);
                redrawCanvas();
            }
        });

        toolBar.getItems().add(redoButton);


        ImageView imgSave = imgView("/com/xshape/save.png");
        // Boutton save
        Button saveButton = factory.createButton("");
        saveButton.setGraphic(imgSave);
        toolBar.getItems().add(saveButton);

        ImageView imgLoad = imgView("/com/xshape/load.png");
        // Boutton redo
        Button loadButton = factory.createButton("");
        loadButton.setGraphic(imgLoad);
        toolBar.getItems().add(loadButton);


        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.getChildren().addAll(undoButton,redoButton,saveButton,loadButton);
        borderPane.setTop(hBox);
    }

    @Override
    public void whiteBoard() {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    public BorderPane build(){
        toolBar();
        menuBar();
        whiteBoard();
        toolBarEvents(toolbar);

        return borderPane;
    }

    @Override
    public void groupDarggable(Composite group) {
    }

    @Override
    public void toolBarEvents(Composite toolBar) {
        _canvas.setOnMousePressed(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            for (IShape shape : toolbar.getShaps()){
                if (shape.IsArea(mouseX, mouseY)){
                    System.out.println("hellooooox");
                    _canvas.setOnMouseDragged(dragEvent -> {
                    });
                    _canvas.setOnMouseReleased(releaseEvent -> {
                        double deltaX = releaseEvent.getX() - mouseX;
                        double deltaY = releaseEvent.getY() - mouseY;
                        _canvas.setOnMouseDragged(null);
                        if (deltaX > 100){
                            //IShape newShape = shape;
                            //newShape.setPosition(releaseEvent.getX(), releaseEvent.getY());
                            //newShape.draw();
                            Command command = new DrawShapeCommand(shape, releaseEvent.getX(), releaseEvent.getY() );
                            undoStack.push(command);
                            command.execute();
                            redoStack.clear();
                        }
                    });
                }
            }
        });

    }

    @Override
    public void whiteBoardEvents(IWhiteBoard whiteBoard) {

    }

    private void redrawCanvas() {

        // Effacer le canvas
        _canvas.getGraphicsContext2D().clearRect(0, 0, 800, 600);


        // Redessiner tous les éléments de la pile undo
        for (Command command : undoStack) {
            command.execute();
        }
    }

}
