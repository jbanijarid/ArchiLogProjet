package com.xshape.modele.fx;

import com.xshape.modele.*;
import javafx.scene.Camera;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class FxBuilder implements IBuilder {

    private Composite toolbar;
    private Composite whiteboard;
    private BorderPane borderPane;
    private IRenderer _renderer;
    private Canvas _canvas;

    public static FxWhiteBoard _whiteBoard;
    private IFactory factory = new FxFactory();



    public FxBuilder(BorderPane borderPane) {
        this.toolbar = new Composite();
        //this.whiteboard = whiteboard;
        this.borderPane = borderPane;
        this._canvas = new Canvas(800, 600);
        borderPane.setBottom(_canvas);
        this._renderer = new FxRenderer(_canvas);

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
                    IShape rect = factory.createRectangle(25, 40, 50, 40, _renderer);
                    rect.draw();
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

        return borderPane;
    }
}
