package com.xshape.modele;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Rectangle extends SimpleShape {

    private double x, y, width, height;
    private int color=0;


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
        renderer.setColor(color);

        // Ligne horizontale du haut
        /*renderer.drawLine(x, y, x + width, y);

        // Ligne verticale de droite
        renderer.drawLine(x + width, y, x + width, y + height);

        // Ligne horizontale du bas
        renderer.drawLine(x + width, y + height, x, y + height);

        // Ligne verticale de gauche
        renderer.drawLine(x, y + height, x, y);

        double[] xPoints = {x, x + width, x + width, x};
        double[] yPoints = {y, y, y + height, y + height};*/
        //renderer.fillPolygon(xPoints, yPoints, 4);

        renderer.fillRect(x, y, width, height);
    }

    @Override
    public void setColor(int color) {
        this.color =color;
    }

    @Override
    public int getColor() {
        return color;
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
        return 4;
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
        renderer = r;
    }

    @Override
    public IRenderer getIRenderer() {
        return renderer;
    }

    @Override
    public void translate(double dx, double dy) {
        x += dx;
        y += dy;
    }

}

