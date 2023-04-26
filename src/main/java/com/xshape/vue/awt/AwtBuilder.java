package com.xshape.vue.awt;

import com.xshape.modele.Command;
import com.xshape.modele.DeleteShapeCommand;
import com.xshape.modele.DrawShapeCommand;
import com.xshape.modele.Goupage.ToolGroupComponent;
import com.xshape.modele.Goupage.ToolGroupComposite;
import com.xshape.modele.IBuilder;

import javax.swing.*;
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
    private double prevX, prevY;


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
                    prevX=selectedToolToolbar.getShape().getPositionX();
                    prevY=selectedToolToolbar.getShape().getPositionY();
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

    private void executeCommand(Command command){
        undoStackAwt.push(command);
        command.execute();
        redoStackAwt.clear();
        toolBar.repaint();
        whiteBoard.update(undoStackAwt, redoStackAwt);
    }



    @Override
    public void mouseReleased(MouseEvent e) {
        if (selectedToolToolbar != null) {
            if(toolBar.inTrashLabel(e.getX(), e.getY())){
                Command c = new DeleteShapeCommand(selectedToolToolbar, toolBar.getTools());
                executeCommand(c);
                toolBar.setCurrent_y(toolBar.getCurrent_y()-75);
                toolBar.repositionTools();
            }else{
                Command command = new DrawShapeCommand(selectedToolToolbar.clone().getShape(), e.getX() - toolBar.getWidthT(), e.getY(), whiteBoard.getContentWhiteBoard());
                executeCommand( command);
                selectedToolToolbar.getShape().setPosition(prevX, prevY);
            }
            selectedToolToolbar = null;

        }

        if (selectedToolWhiteboard != null) {
            if (toolBar.getBounds().contains(e.getX()+toolBar.getWidthT()+10,e.getY())) {
                if(toolBar.inTrashLabel(e.getX()+toolBar.getWidthT()+10, e.getY())){
                    Command c = new DeleteShapeCommand(selectedToolWhiteboard, whiteBoard.getContentWhiteBoard());
                    executeCommand(c);
                }
                else{
                    toolBar.addTool( selectedToolWhiteboard.clone());
                    whiteBoard.getContentWhiteBoard().remove(selectedToolWhiteboard);
                    System.out.println(toolBar.tools.getShapes().size());
                }

            }

            selectedToolWhiteboard = null;



        }

        toolBar.repaint();
        whiteBoard.repaint();

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