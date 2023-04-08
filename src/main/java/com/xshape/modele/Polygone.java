package com.xshape.modele;

public class Polygone extends SimpleShape {

    double centerX;
    double centerY;
    double radius;
    double nbSides;
    private int color=50;

    public Polygone(double centerX, double centerY, double radius, double nbSides, IRenderer renderer) {
        super(renderer);
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.nbSides = nbSides;
    }

    @Override
    public void draw() {
        _renderer.setColor(this.color);
        double angle = 2 * Math.PI / nbSides; // Angle entre deux sommets consécutifs du pentagone
        double x, y;

        // Coordonnées du premier sommet
        x = centerX + radius * Math.cos(0);
        y = centerY + radius * Math.sin(0);

        // Dessine les cinq côtés du pentagone
        for (int i = 1; i <= nbSides; i++) {
            double nextX = centerX + radius * Math.cos(i * angle);
            double nextY = centerY + radius * Math.sin(i * angle);

            _renderer.drawLine(x, y, nextX, nextY);

            x = nextX;
            y = nextY;
        }
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public void setPosition(double x, double y){
        this.centerX = x;
        this.centerY = y;
    }

    @Override
    public double getPositionX() {
        return this.centerX;
    }

    @Override
    public double getPositionY() {
        return this.centerY;
    }

    @Override
    public double getWidth() {
        double angle = Math.PI / nbSides; // angle entre le centre, un sommet et le sommet adjacent
        double sideLength = 2 * radius * Math.sin(angle); // longueur d'un côté
        return sideLength;
    }

    @Override
    public double getHeight() {
        double angle = Math.PI / nbSides; // angle entre le centre, un sommet et le sommet adjacent
        double apothem = radius * Math.cos(angle); // apothème du polygone (distance entre le centre et le milieu d'un côté)
        return 2 * apothem;
    }

}