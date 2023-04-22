package com.xshape.vue.awt;

import com.xshape.modele.Command;
import com.xshape.modele.DrawShapeCommand;
import com.xshape.modele.Goupage.ToolGroupComponent;
import com.xshape.modele.IBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;

public class AwtBuilder implements IBuilder, MouseListener {

    AwtApplication app;
    protected AwtConcreteToolBar toolBar;
    protected JToolBar menuBar;
    protected AwtConcreteWhiteBoard whiteBoard;
    private ToolGroupComponent selectedToolToolbar;
    private ToolGroupComponent selectedToolWhiteboard;
    protected static Stack<Command> undoStackAwt = new Stack<>();
    protected static Stack<Command> redoStackAwt = new Stack<>();

    public AwtBuilder(AwtApplication awtApplication){
        this.app = awtApplication;
    }

    public AwtConcreteToolBar getToolBar() {
        return toolBar;
    }

    public AwtConcreteWhiteBoard getWhiteBoard() {
        return whiteBoard;
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
        JToolBar awtM = new AwtMenuBar(this.app, 0,23,800,60, this);
        menuBar = awtM;
        this.app.add(awtM);
    }

    @Override
    public void whiteBoard() {
        AwtConcreteWhiteBoard awtB = new AwtConcreteWhiteBoard(this.app, app.getWidth() - 650, app.getHeight() - 518, 650, 540);
        awtB.update(undoStackAwt, redoStackAwt);
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
    }


    @Override
    public void mouseDragged(MouseEvent e) {
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if(selectedToolToolbar==null) {
            for (ToolGroupComponent tool : toolBar.getTools().getShapes()) {
                if (tool.getShape().IsArea(e.getX(), e.getY())) {
                    selectedToolToolbar = tool;
                    break;
                }
            }
        }
        if(selectedToolWhiteboard==null) {
            for (ToolGroupComponent tool : whiteBoard.getContentWhiteBoard().getShapes()) {
                if (tool.getShape().IsArea(e.getX(), e.getY())) {
                    selectedToolWhiteboard = tool;
                    break;
                }
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (selectedToolToolbar != null) {
                Command command = new DrawShapeCommand(selectedToolToolbar.clone().getShape(),e.getX()-toolBar.getWidthT(), e.getY(), whiteBoard.getContentWhiteBoard());
                undoStackAwt.push(command);
                command.execute();
                selectedToolToolbar = null;
                redoStackAwt.clear();
                whiteBoard.update(undoStackAwt, redoStackAwt);
        }
        if (selectedToolWhiteboard != null) {
            System.out.println("yoyoyooooooooooooooo1111111111111111111111");
            if (toolBar.getBounds().contains(e.getPoint())) {
                System.out.println("yoyoyooooooooooooooo");
                toolBar.addTool(selectedToolWhiteboard);
                toolBar.repaint();
            }
        }
    }



    public void build() {
        toolBar();
        whiteBoard();
        menuBar();
    }




    public JToolBar getMenuBar() {
        return menuBar;
    }

}