package com.xshape.modele.fx;

import com.xshape.modele.Composite;
import com.xshape.modele.Event;
import com.xshape.modele.IWhiteBoard;
import com.xshape.modele.IShape;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Shape;

public class FxWhiteBoard extends BorderPane implements IWhiteBoard {

    Composite canvasElements = null;
    private Event libraryEvents;

    public FxWhiteBoard(Composite canvasElements, double width, double height,  Event libraryEvents){
        super();
        setHeight(width);
        setWidth(height);
        this.canvasElements = canvasElements;
        this.libraryEvents = libraryEvents;
        //_libraryEvents.whiteBoardEvents(this);

    }

    @Override
    public void addGroupComponent(IShape rec) {
         canvasElements.add(rec);
         getChildren().add((Shape) rec);
    }
}
