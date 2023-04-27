package com.xshape.modele;

public class ColorShapeCommand implements Command {

    private IShape shape;
    private int oldColor;
    private int newColor;

    public ColorShapeCommand(IShape shape, int newColor) {
        this.shape = shape;
        this.oldColor = shape.getColor();
        this.newColor = newColor;
    }

    @Override
    public void execute() {
        shape.setColor(newColor);
    }

    @Override
    public void undo() {
        shape.setColor(oldColor);
    }

    @Override
    public void redo() {
        shape.setColor(newColor);
    }
}
