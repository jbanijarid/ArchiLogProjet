package com.xshape.modele;

public class RadiusPolygoneCommand implements Command{

    private IShape rectangle;
    private double oldR;
    private double newR;

    public RadiusPolygoneCommand(IShape rectangle, double newR ){
        this.rectangle = rectangle;
        this.oldR = (int) rectangle.getRadius();
        this.newR = newR;
    }

    @Override
    public void execute() {
        rectangle.setRadius(newR);
    }

    @Override
    public void undo() {
        rectangle.setRadius(oldR);
    }

    @Override
    public void redo() {

    }
}
