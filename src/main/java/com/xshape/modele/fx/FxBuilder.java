package com.xshape.modele.fx;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_REDPeer;
import com.xshape.modele.*;
import com.xshape.modele.Goupage.Tool;
import com.xshape.modele.Goupage.ToolGroupComponent;
import com.xshape.modele.Goupage.ToolGroupComposite;
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

    private ToolGroupComponent toolbar;

    private ToolGroupComponent whiteBoard;
    private Composite whiteboard;
    private BorderPane borderPane;
    private IRenderer _renderer;
    private Canvas _canvas;
    IShape selectedShape = null;

    public static FxWhiteBoard _whiteBoard;
    private IFactory factory = new FxFactory();

    private static Stack<Command> undoStack = new Stack<>();
    private static Stack<Command> redoStack = new Stack<>();



    public FxBuilder(BorderPane borderPane) {
        //this.toolbar = new Composite();
        this.toolbar = new ToolGroupComposite();
        //this.whiteboard = whiteboard;
        this.borderPane = borderPane;
        this._canvas = new Canvas(800, 600);
        borderPane.setBottom(_canvas);
        this._renderer = new FxRenderer(_canvas);
        this.whiteBoard = new ToolGroupComposite();
    }

    @Override
    public void toolBar() {

        _renderer.drawLine(100, 10, 100, 600);
        _renderer.drawLine(10, 10, 10, 600);
        _renderer.drawLine(10, 10, 100, 10);
        _renderer.drawLine(10, 600, 100, 600);
        IShape rect = factory.createRectangle(25, 40, 50, 40, _renderer);
        IShape poly = factory.createPolygone(50, 120, 30, 6, _renderer);
        ToolGroupComponent rectTool = new Tool(rect);
        ToolGroupComponent polyTool = new Tool(poly);
        toolbar.add(rectTool);
        toolbar.add(polyTool);
        toolbar.draw();
        //toolbar.add(rect);
        //toolbar.add(poly);
        //toolbar.draw();
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
        toolBarEvents();

        return borderPane;
    }

    @Override
    public void groupDarggable(Composite group) {
    }


    @Override
    public void toolBarEvents() {
        _canvas.setOnMousePressed(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            if (selectedShape == null){
                for (ToolGroupComponent shape: this.toolbar.getShapes()) {
                    if (shape.getShape().IsArea(mouseX, mouseY)) {
                        System.out.println("hellooooox");
                        selectedShape = shape.clone().getShape();
                        System.out.println(selectedShape);
                        System.out.println(shape);
                        System.out.print("shape is selectet");
                        break;
                    }
                }
            }
            System.out.println("fin 1");
        });
        _canvas.setOnMouseDragged(dragEvent -> {
        });
        _canvas.setOnMouseReleased(releaseEvent -> {
            if (selectedShape != null){
                double deltaX = releaseEvent.getX();
                double deltaY = releaseEvent.getY();
                if (deltaX > 100){
                    System.out.println("Je dessine le new shape");
                    //newShape.draw();
                    ToolGroupComponent selectedTool = new Tool(selectedShape);
                    whiteBoard.add(selectedTool);
                    Command command = new DrawShapeCommand(selectedShape, releaseEvent.getX(), releaseEvent.getY());
                    undoStack.push(command);
                    command.execute();
                    selectedShape = null;
                    System.out.println("je met selected shape a null");
                    _canvas.setOnMouseDragged(null);
                    redoStack.clear();
                }
            }
        });
    }

    @Override
    public void whiteBoardEvents() {

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
