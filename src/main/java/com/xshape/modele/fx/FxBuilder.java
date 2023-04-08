package com.xshape.modele.fx;

import com.xshape.modele.*;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class FxBuilder implements IBuilder {

    private Composite toolbar;
    //private Composite whiteboard;
    private BorderPane borderPane;
    private IRenderer _renderer;

    //public static FxWhiteBoard _whiteBoard;
    private IFactory factory = new FxFactory();

    private Canvas _canvas;
    private double startX;
    private double startY;



    public FxBuilder(BorderPane borderPane) {
        this._canvas = new Canvas(800, 565);
        this.borderPane = borderPane;
        this.toolbar = new Composite();
        borderPane.setBottom(_canvas);
        this._renderer = new FxRenderer(_canvas);
        _canvas.setOnMousePressed(event -> {
            startX = event.getX();
            startY = event.getY();
        });
        /*
        _canvas.setOnMouseDragged(event -> {
            double endX = event.getX();
            double endY = event.getY();
            double width = Math.abs(endX - startX);
            double height = Math.abs(endY - startY);
            double x = Math.min(startX, endX);
            double y = Math.min(startY, endY);
            IShape rect = factory.createRectangle(x, y, width, height, _renderer);
            rect.draw();
        });

         */
        _canvas.setOnDragOver(event -> {
            if (event.getGestureSource() != _canvas && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        _canvas.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString() && db.getString().equals("shape")) {
                double endX = event.getX();
                double endY = event.getY();
                IShape rect = factory.createRectangle(endX, endY, 50, 40, _renderer);
                toolbar.add(rect);
                toolbar.draw();
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

    }

    @Override
    public void toolBar() {

        //GraphicsContext graphic = canvas.getGraphicsContext2D();
        _renderer.drawLine(100, 10, 100, 600);
        _renderer.drawLine(10, 10, 10, 600);
        _renderer.drawLine(10, 10, 100, 10);
        _renderer.drawLine(10, 600, 100, 600);
        IShape rect = factory.createRectangle(25, 40, 50, 40, _renderer);
        IShape poly = new Polygone(50, 120, 30, 5, _renderer);
        toolbar.add(rect);
        toolbar.add(poly);

        for (IShape shape : toolbar.getShaps()) {
          toolbar.draw();
        }

    }

    @Override
    public void menuBar() {
        ToolBar toolBar = new ToolBar();

        Button button1 = new Button("Undo");
        toolBar.getItems().add(button1);

        Button button2 = new Button("Redo");
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
