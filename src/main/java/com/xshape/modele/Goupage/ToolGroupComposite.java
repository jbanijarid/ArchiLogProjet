package com.xshape.modele.Goupage;

import com.xshape.modele.IShape;

import java.util.ArrayList;

public class ToolGroupComposite implements ToolGroupComponent, Cloneable {

    ArrayList<ToolGroupComponent> tools = new ArrayList<>();


    @Override
    public void add(ToolGroupComponent tool) {
        tools.add(tool);
    }

    @Override
    public void remove(ToolGroupComponent tool) {
        tools.remove(tool);
    }

    ArrayList<ToolGroupComponent> getTools(){
        return this.tools;
    }

    @Override
    public ToolGroupComponent clone() {
        ToolGroupComposite clone = new ToolGroupComposite();
        for (ToolGroupComponent tool : tools) {
            clone.add(tool.clone());
        }
        return clone;
    }

    @Override
    public IShape getShape() {
        return null;
    }


    @Override
    public ArrayList<ToolGroupComponent> getShapes(){
        return tools;
    }

    @Override
    public boolean contains(IShape shape) {
        for (ToolGroupComponent c: tools){
            if(c.getShape() == shape) return true;
        }
        return false;
    }

    @Override
    public void draw() {
        for (ToolGroupComponent tool : tools) {
            tool.draw();
        }
    }


}
