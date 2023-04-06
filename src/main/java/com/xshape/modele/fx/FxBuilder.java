package com.xshape.modele.fx;

import com.xshape.modele.Composite;
import com.xshape.modele.IBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class FxBuilder implements IBuilder {

    private Composite toolbar;
    private Composite whiteboard;
    private BorderPane borderPane;

    public static FxWhiteBoard _whiteBoard;



    public FxBuilder(Composite toolbar, Composite whiteboard, BorderPane borderPane) {
        this.toolbar = toolbar;
        this.whiteboard = whiteboard;
        this.borderPane = borderPane;
    }

    @Override
    public void toolBar() {

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
