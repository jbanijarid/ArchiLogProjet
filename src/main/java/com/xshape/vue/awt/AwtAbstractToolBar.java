package com.xshape.vue.awt;

import java.awt.*;

class AwtAbstractToolBar extends Panel {

    public int getWidthT() {
        return widthT;
    }

    public int getHeightT() {
        return heightT;
    }

    int widthT =0;
    int heightT=0;



    public AwtAbstractToolBar(AwtApplication app, int x, int y, int width, int height) {
        setBackground(Color.CYAN);
        setBounds(x, y, width, height);
        widthT = width;
        heightT = height;
    }


    public void paint(Graphics g) {

    }

}