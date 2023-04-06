package com.xshape.modele.fx;

import com.xshape.modele.Composite;
import com.xshape.modele.IFactory;
import com.xshape.modele.IToolBar;
import com.xshape.modele.IWhiteBoard;
import com.xshape.modele.IRenderer;
import com.xshape.modele.Polygone;
import com.xshape.modele.Rectangle;
import javafx.scene.control.Button;

public class FxFactory implements IFactory {
    @Override
    public Rectangle createRectangle(double x, double y, double width, double height, IRenderer r) {
        return new Rectangle(x,y,width,height,r);
    }

    @Override
    public Polygone createPolygon(double centerX, double centerY, double radius, double nbSides, IRenderer renderer) {
        return new Polygone(centerX,centerY,radius,nbSides,renderer);
    }

    @Override
    public Button createButton(String title, double width, double height) {
        Button b = new Button(title);
        b.setPrefSize(width,height);
        return b;
    }

    @Override
    public IToolBar createToolBar(Composite toolBarElements, Composite canvasElements) {
        return null;
    }

    @Override
    public IWhiteBoard createWhiteBoard(Composite canvasElements, double width, double height) {
        return null;
    }
}
