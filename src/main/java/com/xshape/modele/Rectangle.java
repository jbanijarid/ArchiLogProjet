package com.xshape.modele;

public class Rectangle extends SimpleShape {

    private double x, y, width, height;
    private int color=200;

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
        _renderer.setColor(this.color);

        // Ligne horizontale du haut
        _renderer.drawLine(x, y, x + width, y);

        // Ligne verticale de droite
        _renderer.drawLine(x + width, y, x + width, y + height);

        // Ligne horizontale du bas
        _renderer.drawLine(x + width, y + height, x, y + height);

        // Ligne verticale de gauche
        _renderer.drawLine(x, y + height, x, y);

        double[] xPoints = {x, x + width, x + width, x};
        double[] yPoints = {y, y, y + height, y + height};
        _renderer.fillPolygon(xPoints, yPoints, 4);
    }

    @Override
    public void setColor(int color) {
        this.color =color;
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

    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    @Override
    public double getRadius() {
        return 0;
    }

    @Override
    public double getNbSides() {
        return 0;
    }

    @Override
    public boolean IsArea(double mouseX, double mouseY) {
        if (mouseX < this.x || mouseX > this.x + this.width || mouseY < this.y || mouseY > this.y + this.height) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void setRenderer(IRenderer r) {
        _renderer = r;
    }

    @Override
    public IRenderer getIRenderer() {
        return _renderer;
    }

    @Override
    public void translate(double dx, double dy) {
        x += dx;
        y += dy;
    }


}
