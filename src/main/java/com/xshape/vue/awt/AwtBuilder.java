package com.xshape.vue.awt;

import com.xshape.modele.Goupage.ToolGroupComponent;
import com.xshape.modele.IBuilder;
import com.xshape.modele.IShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class AwtBuilder implements IBuilder, MouseListener {

    AwtApplication app;
    private AwtConcreteToolBar toolBar;
    private JToolBar menuBar;
    private AwtConcreteWhiteBoard whiteBoard;
    private ToolGroupComponent selectedTool;



    public AwtBuilder(AwtApplication awtApplication){
        this.app = awtApplication;
    }


    @Override
    public void toolBar() {
        AwtConcreteToolBar awtT = new AwtConcreteToolBar(this.app, 12, 82, 135, app.getHeight()-82);
        toolBar = awtT;
        toolBar.addMouseListener(this);
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
        AwtConcreteWhiteBoard awtB = new AwtConcreteWhiteBoard(this.app, app.getWidth() - 650, app.getHeight() - 540, 650, 540);
        whiteBoard = awtB;
        whiteBoard.addMouseListener(this);
        this.app.add(awtB);
    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        /*
        for (ToolGroupComponent tool : toolBar.getTools()) {
            if (tool.getShape().IsArea(e.getX(), e.getY())) {
                selectedTool = tool;
                break;
            }
        }

         */
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        /*
        if (selectedShape != null) {
            int dx = e.getX() - lastMouseX;
            int dy = e.getY() - lastMouseY;
            selectedShape.move(dx, dy);
            whiteBoard.repaint();
            lastMouseX = e.getX();
            lastMouseY = e.getY();
        }

         */
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if(selectedTool==null) {
            for (ToolGroupComponent tool : toolBar.getTools()) {
                if (tool.getShape().IsArea(e.getX(), e.getY())) {
                    selectedTool = tool;
                    break;
                }
            }
        }
        if (selectedTool != null) {
            System.out.println(selectedTool.getShape());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println(selectedTool+" 111111111111111111111");
        if (selectedTool != null) {
            System.out.println("222222222222222222222");
                // Si la forme est survolée, ajoute la forme à la position de la souris
                //whiteBoard.add(selectedTool.get().cloneAt(e.getX(), e.getY()));
                ToolGroupComponent clone = selectedTool.clone();
                clone.getShape().setPosition(e.getX()-toolBar.getWidthT(), e.getY());
                whiteBoard.addShape(clone.getShape());

            selectedTool = null;
        }
    }



    public void build() {
        toolBar();
        menuBar();
        whiteBoard();
        //whiteBoard.setBuilder(this);
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