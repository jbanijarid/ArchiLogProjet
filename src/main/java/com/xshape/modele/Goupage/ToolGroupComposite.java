package com.xshape.modele.Goupage;

import com.xshape.modele.IShape;

import java.util.ArrayList;

public class ToolGroupComposite implements ToolGroupComponent {

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
