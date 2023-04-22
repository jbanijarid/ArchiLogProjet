package com.xshape.modele;

import javafx.scene.control.Button;

public interface IFactory {
    public Rectangle createRectangle(double x, double y, double width, double heigth, IRenderer r);
    public Polygone createPolygone(double centerX, double centerY, double radius, double nbSides, IRenderer renderer);
}
