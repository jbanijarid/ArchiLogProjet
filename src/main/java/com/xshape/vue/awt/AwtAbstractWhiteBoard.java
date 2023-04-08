package com.xshape.vue.awt;

import java.awt.*;

class AwtAbstractWhiteBoard extends Panel {




    public AwtAbstractWhiteBoard(AwtApplication app, int x, int y, int width, int height) {

        setBackground(Color.white);
        setBounds(x, y, width, height);

    }



    public void paint(Graphics g) {


    }
}