package com.xshape.modele;

public class HeightRectangleCommand implements Command{
    private IShape shape;
    private double oldH;
    private double newH;
    public HeightRectangleCommand(IShape shape, double newH) {
        this.shape = shape;
        this.oldH = shape.getHeight();
        this.newH = newH;
    }
    @Override
    public void execute() {
        shape.setHeight(newH);
    }
    @Override
    public void undo() {
        shape.setHeight(oldH);
    }
    @Override
    public void redo() {
        execute();
    }
}
