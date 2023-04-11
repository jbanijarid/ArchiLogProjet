package com.xshape.modele;

public interface AbstractButton {
    public AbstractButton setTitle(String text);
    public AbstractButton setSize(double width, double height);

    public AbstractButton setIconImage(String filepath,int height, int width);
}
