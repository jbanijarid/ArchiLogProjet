package com.xshape;

import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Shape;

public class FxWhiteBoard extends BorderPane implements IWhiteBoard{

    Composite _canvasElements = null;
    private Event _libraryEvents;

    public FxWhiteBoard(Composite canvasElements, double width, double height,  Event libraryEvents){
        super();
        setHeight(width);
        setWidth(height);
        _canvasElements = canvasElements;
        _libraryEvents = libraryEvents;
        _libraryEvents.manageIWhiteBoardEvents(this);

    }

    @Override
    public void addGroupComponent(IShape rec) {
         _canvasElements.add(rec);
         getChildren().add((Shape) rec);
    }
}
