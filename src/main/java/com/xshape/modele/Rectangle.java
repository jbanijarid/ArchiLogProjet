package com.xshape.modele;

public class Rectangle extends SimpleShape {

    private double x, y, width, height;

    public Rectangle(double x, double y, double width, double height, IRenderer renderer) {
        super(renderer);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw() {
        //_renderer.drawRectangle(x, y, width, height);
        _renderer.setColor(50);

        // Ligne horizontale du haut
        _renderer.drawLine(x, y, x + width, y);

        // Ligne verticale de droite
        _renderer.drawLine(x + width, y, x + width, y + height);

        // Ligne horizontale du bas
        _renderer.drawLine(x + width, y + height, x, y + height);

        // Ligne verticale de gauche
        _renderer.drawLine(x, y + height, x, y);
    }



    @Override
    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public double getPositionX() {
        return this.x;
    }

    @Override
    public double getPositionY() {
        return this.y;
    }


}
