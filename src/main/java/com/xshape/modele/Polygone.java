package com.xshape.modele;

public class Polygone extends SimpleShape {

    private double centerX;
    double centerY;
    double radius;
    double nbSides;

    public Polygone(double centerX, double centerY, double radius, double nbSides, IRenderer renderer) {
        super(renderer);
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.nbSides = nbSides;
    }

    @Override
    public void draw() {
        double angle = 2 * Math.PI / 5; // Angle entre deux sommets consécutifs du pentagone
        double x, y;

        // Coordonnées du premier sommet
        x = centerX + radius * Math.cos(0);
        y = centerY + radius * Math.sin(0);

        // Dessine les cinq côtés du pentagone
        for (int i = 1; i <= 5; i++) {
            double nextX = centerX + radius * Math.cos(i * angle);
            double nextY = centerY + radius * Math.sin(i * angle);

            _renderer.drawLine(x, y, nextX, nextY);

            x = nextX;
            y = nextY;
        }
    }

}