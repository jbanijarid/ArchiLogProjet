package com.xshape.modele.awt;

import com.xshape.modele.IBuilder;
import com.xshape.vue.awt.AwtApplication;

import javax.swing.*;
import java.awt.*;

public class AwtBuilder implements IBuilder {

    AwtApplication app;

    public AwtBuilder(AwtApplication awtApplication){
        this.app = awtApplication;
    }


    @Override
    public void toolBar() {

    }

    @Override
    public void menuBar() {
        /*

        Panel awtM = new Panel();

        awtM.setBounds(0,0, 800, 92);
        awtM.setBackground(Color.GRAY);

        Button undo = new Button("Undo");
        awtM.add(undo);

        Button redo = new Button("Redo");
        awtM.add(redo);

        Button save = new Button("Save");
        awtM.add(save);

        Button load = new Button("Load");
        awtM.add(load);



        this.app.add(awtM);
         */
        JToolBar awtM = new AwtMenuBar(this.app);
        this.app.add(awtM);

    }

    @Override
    public void whiteBoard() {
        Panel awtB = new AwtWhiteBord(this.app);
        this.app.add(awtB);
    }

    public void build(){
        toolBar();
        menuBar();
        whiteBoard();
    }
}
