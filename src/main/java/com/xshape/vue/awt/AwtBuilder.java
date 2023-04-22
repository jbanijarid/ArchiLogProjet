package com.xshape.vue.awt;

import com.xshape.modele.Command;
import com.xshape.modele.DrawShapeCommand;
import com.xshape.modele.Goupage.ToolGroupComponent;
import com.xshape.modele.IBuilder;
import com.xshape.modele.IShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Stack;

public class AwtBuilder implements IBuilder, MouseListener {

    AwtApplication app;
    protected AwtConcreteToolBar toolBar;
    protected JToolBar menuBar;
    protected AwtConcreteWhiteBoard whiteBoard;
    private ToolGroupComponent selectedTool;
    protected static Stack<Command> undoStackAwt = new Stack<>();
    protected static Stack<Command> redoStackAwt = new Stack<>();

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
        if(selectedTool==null) {
            for (ToolGroupComponent tool : toolBar.getTools().getShapes()) {
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
        if (selectedTool != null) {
                Command command = new DrawShapeCommand(selectedTool.clone().getShape(),e.getX()-toolBar.getWidthT(), e.getY(), whiteBoard.getContentWhiteBoard());
                undoStackAwt.push(command);
                command.execute();
                selectedTool = null;
                redoStackAwt.clear();
                whiteBoard.update(undoStackAwt, redoStackAwt);

        }
    }



    public void build() {
        toolBar();
        whiteBoard();
        menuBar();
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