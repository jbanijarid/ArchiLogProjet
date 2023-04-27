package com.xshape.modele;

public class SlidePolygoneCommand implements Command{

    private IShape rectangle;
    private double oldSlide;
    private double newSlide;

    public SlidePolygoneCommand(IShape rectangle, double newSlide ){
        this.rectangle = rectangle;
        this.oldSlide = (int) rectangle.getNbSides();
        this.newSlide = newSlide;
    }

    @Override
    public void execute() {
        rectangle.setNbSides(newSlide);
    }

    @Override
    public void undo() {
        rectangle.setNbSides(oldSlide);
    }

    @Override
    public void redo() {

    }
}