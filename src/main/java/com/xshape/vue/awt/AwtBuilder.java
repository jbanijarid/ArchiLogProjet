package com.xshape.vue.awt;

import com.xshape.modele.Goupage.ToolGroupComponent;
import com.xshape.modele.IBuilder;
import com.xshape.modele.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AwtBuilder implements IBuilder, MouseListener {

    AwtApplication app;

    private AwtConcreteToolBar toolBar;
    private JToolBar menuBar;
    private AwtConcreteWhiteBoard whiteBoard;
    private ToolGroupComponent selectedTool;
    private ToolGroupComponent draggedTool;


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
        for (ToolGroupComponent tool : toolBar.getTools()) {
            // Vérifie si la souris est dans la zone de la forme
            if (e.getX() >= tool.get().getPositionX() && e.getX() <= tool.get().getPositionX() + tool.get().getWidth() &&
                    e.getY() >= tool.get().getPositionY() && e.getY() <= tool.get().getPositionY() + tool.get().getHeight()) {
                // Si la forme est cliquée, elle devient le selectedtool
                this.selectedTool = tool;
                System.out.println("entreeeeeeeeeeeeeeeeeeee");
                break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (selectedTool != null) {
            draggedTool = selectedTool.clone();
            draggedTool.get().setPosition(e.getX(), e.getY());
            // Ajouter la forme au panneau blanc
            whiteBoard.add(draggedTool);
            System.out.println("wehhhhhhhhhhhhhhhhhhhhh");
            // Actualiser le panneau blanc pour afficher la nouvelle forme
        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        if (draggedTool != null) {
            // Réinitialiser la forme qui est en train d'être déplacée
            draggedTool = null;
        }
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
