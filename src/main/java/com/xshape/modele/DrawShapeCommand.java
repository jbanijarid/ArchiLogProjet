package com.xshape.modele;

public class DrawShapeCommand implements Command{


    private IShape copy;
    private double x;
    private double y;

    public DrawShapeCommand(IShape shape, double newPosX, double newPosY){
        this.copy = shape;
        this.x = newPosX;
        this.y = newPosY;
    }

    @Override
    public void execute() {
        copy.setPosition(x, y);
        copy.draw();
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }

    public IShape getCopy() {
        return copy;
    }
}
