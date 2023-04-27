package com.xshape.modele;

public class WidthRectangleCommand implements Command{

    private IShape shape;
    private double oldW;
    private double newW;


    public WidthRectangleCommand(IShape shape, double newW) {
        this.shape = shape;
        this.oldW = shape.getWidth();
        this.newW = newW;
    }

    @Override
    public void execute() {
        shape.setWidth(newW);
    }

    @Override
    public void undo() {
        shape.setWidth(oldW);
    }

    @Override
    public void redo() {

    }
}