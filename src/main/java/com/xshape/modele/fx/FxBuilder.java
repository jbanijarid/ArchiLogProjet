package com.xshape.modele.fx;

import com.xshape.modele.*;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class FxBuilder implements IBuilder {

    private Composite toolbar;
    private Composite whiteboard;
    private BorderPane borderPane;
    private IRenderer _renderer;

    public static FxWhiteBoard _whiteBoard;
    private IFactory factory = new FxFactory();



    public FxBuilder(Composite toolbar, Composite whiteboard, IRenderer renderer, BorderPane borderPane) {
        this.toolbar = toolbar;
        this.whiteboard = whiteboard;
        this.borderPane = borderPane;
        this._renderer = renderer;
    }

    @Override
    public void toolBar() {
        //GraphicsContext graphic = canvas.getGraphicsContext2D();
        _renderer.drawLine(100, 25, 100, 600);
        //toolbar.getShaps().size();

        // VBox vbox = new VBox();
        //Canvas v = new Canvas();
        //vbox.getChildren().add(v);
        //borderPane.setLeft(vbox);
        IShape rect = factory.createRectangle(25, 40, 50, 40, _renderer);
        IShape poly = new Polygone(45, 120, 30, 5, _renderer);
        toolbar.add(rect);
        toolbar.add(poly);
        ToolBar tool = new ToolBar();

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
