package com.xshape.modele;

import javafx.scene.control.Button;

public interface IFactory {
    public Rectangle createRectangle(double x, double y, double width, double heigth, IRenderer r);
    public Polygone createPolygone(double centerX, double centerY, double radius, double nbSides, IRenderer renderer);

    public Button createButton(String title);


    public IToolBar createToolBar(Composite toolBarElements, Composite canvasElements);
    public IWhiteBoard createWhiteBoard(Composite canvasElements, double width, double height);
}
