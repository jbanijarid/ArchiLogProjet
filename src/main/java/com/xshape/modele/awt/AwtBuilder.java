package com.xshape.modele.awt;

import com.xshape.modele.IBuilder;
import com.xshape.vue.awt.AwtApplication;

import javax.swing.*;
import java.awt.*;

public class AwtBuilder implements IBuilder {

    AwtApplication app;

    private Panel toolBar;
    private JToolBar menuBar;
    private Panel whiteBoard;

    public AwtBuilder(AwtApplication awtApplication){
        this.app = awtApplication;
    }


    @Override
    public void toolBar() {
        Panel awtT = new AwtToolBar(this.app, 12, 82, 135, app.getHeight()-60);
        toolBar = awtT;
        this.app.add(awtT);
    }

    @Override
    public void menuBar() {
        JToolBar awtM = new AwtMenuBar(this.app, 0,23,800,60);
        menuBar = awtM;
        this.app.add(awtM);

    }

    @Override
    public void whiteBoard() {
        Panel awtB = new AwtWhiteBord(this.app, app.getWidth() - 650, app.getHeight() - 540, 650, 540);
        whiteBoard = awtB;
        this.app.add(awtB);
    }

    public void build(){
        toolBar();
        menuBar();
        whiteBoard();
    }

    public Panel getToolBar() {
        return toolBar;
    }

    public JToolBar getMenuBar() {
        return menuBar;
    }

    public Panel getWhiteBoard() {
        return whiteBoard;
    }
}
