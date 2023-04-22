package com.xshape.modele;

public class TranslateshapeCommand implements Command{

    private IShape rectangle;
    private int oldX;
    private int oldY;
    private int newX;
    private int newY;

    public TranslateshapeCommand(IShape rectangle, int newX, int newY) {
        this.rectangle = rectangle;
        this.oldX = (int) rectangle.getPositionX();
        this.oldY = (int) rectangle.getPositionY();
        this.newX = newX;
        this.newY = newY;
    }

    @Override
    public void execute() {
        rectangle.setPosition(newX,newY);
    }

    @Override
    public void undo() {
        rectangle.setPosition(oldX,oldY);
    }

    @Override
    public void redo() {

    }
}
