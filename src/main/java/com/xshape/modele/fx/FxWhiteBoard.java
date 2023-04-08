package com.xshape.modele.fx;

import com.xshape.modele.Composite;
import com.xshape.modele.Event;
import com.xshape.modele.IWhiteBoard;
import com.xshape.modele.IShape;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Shape;

public class FxWhiteBoard extends BorderPane implements IWhiteBoard {

    Composite _canvasElements = null;
    private Event _libraryEvents;

    public FxWhiteBoard(Composite canvasElements, double width, double height,  Event libraryEvents){
        super();
        setHeight(width);
        setWidth(height);
        _canvasElements = canvasElements;
        _libraryEvents = libraryEvents;
        _libraryEvents.whiteBoardEvents(this);

    }

    @Override
    public void addGroupComponent(IShape rec) {
         _canvasElements.add(rec);
         getChildren().add((Shape) rec);
    }
}
