package com.xshape.modele;

import com.xshape.modele.IShape;

import java.util.ArrayList;
import java.util.List;

public class Composite implements IShape {

    private List<IShape>  shaps;

    public Composite() {
        this.shaps = new ArrayList<>();
    }

    public void add(IShape shape){
        this.shaps.add(shape);
    }

    public void remove(IShape shape){
        this.shaps.remove(shape);
    }


    @Override
    public void draw() {
        for(IShape shape : shaps){
            shape.draw();
        }
    }

    @Override
    public void setColor(int color) {

    }

    @Override
    public void setPosition(double x, double y) {

    }

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
    public boolean IsArea(double mousseX, double mousseY) {
        return false;
    }


    public List<IShape> getShaps(){
        return shaps;
    }
}
