package com.xshape.modele.toolbar;

import com.xshape.modele.IRenderer;
import com.xshape.modele.IShape;

import java.util.ArrayList;

public class ToolBarComposite implements ToolBarComponent{

    ArrayList<IShape> tools = new ArrayList<>();

    @Override
    public IShape get() {
        return null;
    }

    @Override
    public void add(IShape shape) {
        tools.add(shape);
    }

    @Override
    public void remove(IShape shape) {
        tools.remove(shape);
    }

    public ArrayList<IShape> getTools(){
        return this.tools;
    }



}
