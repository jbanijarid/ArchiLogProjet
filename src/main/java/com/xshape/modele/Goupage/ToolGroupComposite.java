package com.xshape.modele.Goupage;

import com.xshape.modele.IShape;

import java.util.ArrayList;

public class ToolGroupComposite implements ToolGroupComponent {

    ArrayList<ToolGroupComponent> tools = new ArrayList<>();


    @Override
    public void add(ToolGroupComponent tool) {
        tools.add(tool);
    }

    @Override
    public void remove(ToolGroupComponent tool) {
        tools.remove(tool);
    }

    @Override
    public void draw() {
        for(ToolGroupComponent tool : tools){
            tool.draw();
        }
    }

    @Override
    public IShape get() {
        return null;
    }


    @Override
    public ToolGroupComponent clone() {
        return null;
    }

    public ArrayList<ToolGroupComponent> getTools(){
        return this.tools;
    }



}
