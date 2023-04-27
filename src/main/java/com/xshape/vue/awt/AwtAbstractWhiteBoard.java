package com.xshape.vue.awt;

import com.xshape.modele.IShape;
import com.xshape.modele.awt.AwtRenderer;

import java.awt.*;

class AwtAbstractWhiteBoard extends Panel {




    public AwtAbstractWhiteBoard(AwtApplication app, int x, int y, int width, int height) {

        setBackground(Color.WHITE);
        setBounds(x, y, width, height);

    }





    public void paint(Graphics g) {


    }
}