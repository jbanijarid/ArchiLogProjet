package com.xshape.modele.Goupage;

import com.xshape.modele.IShape;

import java.util.ArrayList;

public class ToolGroupComposite implements ToolGroupComponent {

    ArrayList<ToolGroupComponent> tools = new ArrayList<>();


    @Override
    public double getPositionX() {
        return 0;
    }

    @Override
    public double getPositionY() {
        return 0;
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }

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
    public void setPosition(double x, double y) {

    }

    @Override
    public void setColor(int color) {
        for(ToolGroupComponent tool : tools){
            tool.setColor(color);
        }
    }

    public ArrayList<ToolGroupComponent> getTools(){
        return this.tools;
    }



}
