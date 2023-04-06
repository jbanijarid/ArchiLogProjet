package com.xshape;

import javafx.scene.control.Button;

public interface IFactory {
    public Rectangle createRectangle(double x, double y, double width, double heigth,IRenderer r);
    /**
     * @return
     */
    public Polygone createPolygon( double centerX, double centerY, double radius, double nbSides, IRenderer renderer);

    /**
     * @return A voir
     */
    public Button createButton(String title, double width, double height);


    public IToolBar createToolBar(Composite  toolBarElements, Composite canvasElements);
    public IWhiteBoard createWhiteBoard(Composite canvasElements, double width, double height);
}
