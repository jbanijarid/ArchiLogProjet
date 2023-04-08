package com.xshape.modele.fx;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_REDPeer;
import com.xshape.modele.*;
import javafx.scene.Camera;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class FxBuilder implements IBuilder, Event {

    private Composite toolbar;
    private Composite whiteboard;
    private BorderPane borderPane;
    private IRenderer _renderer;
    private Canvas _canvas;
    //IShape selectedShape = null;

    public static FxWhiteBoard _whiteBoard;
    private IFactory factory = new FxFactory();



    public FxBuilder(BorderPane borderPane) {
        this.toolbar = new Composite();
        //this.whiteboard = whiteboard;
        this.borderPane = borderPane;
        this._canvas = new Canvas(800, 600);
        borderPane.setBottom(_canvas);
        this._renderer = new FxRenderer(_canvas);

        /*
        _canvas.setOnDragOver(event -> {
            if (event.getGestureSource() != _canvas && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        _canvas.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                if (db.getString().equals("Rectangle")) {
                    double x = event.getX();
                    double y = event.getY();
                    factory = new FxFactory();
                    IShape rect = factory.createRectangle(100, 200, 50, 40, _renderer);
                    rect.draw();
                    System.out.println("helloo");
                    //Command drawCommand = new DrawRectangleCommand(_factory, x, y);
                    //undoStack.push(drawCommand);
                    //drawCommand.execute();
                    //redoStack.clear();
                    success = true;
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

         */
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

    @Override
    public void menuBar() {
        ToolBar toolBar = new ToolBar();

        Button button1 = factory.createButton("Undo");
        toolBar.getItems().add(button1);

        Button button2 = factory.createButton("Redo");
        toolBar.getItems().add(button2);

        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.getChildren().addAll(button1,button2);
        borderPane.setTop(hBox);
    }

    @Override
    public void whiteBoard() {

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
                            //shape.setPosition(shape.getPositionX() + deltaX, shape.getPositionY() + deltaY);
                            IShape newShape = shape;
                            newShape.setPosition(shape.getPositionX() + deltaX, shape.getPositionY() + deltaY);
                            System.out.println("helloobbbbbbbbbbbbbb");
                            newShape.draw();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void whiteBoardEvents(IWhiteBoard whiteBoard) {

    }
}
