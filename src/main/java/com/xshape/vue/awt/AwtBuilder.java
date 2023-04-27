package com.xshape.vue.awt;

import com.xshape.modele.*;
import com.xshape.modele.Goupage.ToolGroupComponent;
import com.xshape.modele.Goupage.ToolGroupComposite;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private ToolBarMemento mytoolbar;
    private double prevX, prevY;

    ToolBarMemento toolbarContent;
    ToolGroupComponent whiteboardContent;


    public AwtBuilder(AwtApplication awtApplication){
        this.app = awtApplication;
        whiteboardContent = new ToolGroupComposite();
    }



    @Override
    public void toolBar() {
        AwtConcreteToolBar awtT = new AwtConcreteToolBar(this.app, 12, 82, 135, app.getHeight()-82, this);
        toolBar = awtT;
        toolBar.addMouseListener(this);
        this.app.add(awtT);
        toolbarContent = new ToolBarMemento(toolBar.renderer);
        try {
            toolbarContent.loadStateFromFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void menuBar() {
        JToolBar awtM = new AwtMenuBar(this.app, 0,23,800,60, this);
        menuBar = awtM;
        this.app.add(awtM);
    }

    @Override
    public void whiteBoard() {
        AwtConcreteWhiteBoard awtB = new AwtConcreteWhiteBoard(this.app, app.getWidth() - 650, app.getHeight() - 518, 650, 540, this);
        awtB.repaint();
        whiteBoard = awtB;
        whiteBoard.addMouseListener(this);
        this.app.add(awtB);
    }

    public void executeCommand(Command command) throws IOException {
        undoStackAwt.push(command);
        command.execute();
        redoStackAwt.clear();
        //mytoolbar.saveStateToFile();
        System.out.println("j'enregistre ds le fichier");
        toolBar.repaint();
        whiteBoard.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(selectedToolToolbar==null) {
            for (ToolGroupComponent tool : toolbarContent.getFormes().getShapes()) {
                if (tool.getShape().IsArea(e.getX(), e.getY())) {
                    selectedToolToolbar = tool;
                    prevX=selectedToolToolbar.getShape().getPositionX();
                    prevY=selectedToolToolbar.getShape().getPositionY();
                    System.out.println("aiiiiiiiiiiiiiiiiiiiiiiiiii");
                    break;
                }
            }
        }

        if(selectedToolWhiteboard==null) {
            for (ToolGroupComponent tool : whiteboardContent.getShapes()) {
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
            if(toolBar.inTrashLabel(e.getX(), e.getY())){
                selectedToolToolbar.getShape().setPosition(prevX, prevY);
                Command c = new DeleteShapeCommand(selectedToolToolbar, toolbarContent.getFormes());
                try {
                    executeCommand(c);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                toolBar.setCurrent_y(toolBar.getCurrent_y()-75);
                toolBar.repositionTools();
            }else{
                Command command = new DrawShapeCommand(selectedToolToolbar.clone().getShape(), e.getX() - toolBar.getWidthT(), e.getY(), whiteboardContent);
                try {
                    executeCommand( command);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                selectedToolToolbar.getShape().setPosition(prevX, prevY);
            }
            selectedToolToolbar = null;

        }

        if (selectedToolWhiteboard != null) {
            if (toolBar.getBounds().contains(e.getX()+toolBar.getWidthT()+10,e.getY())) {
                if(toolBar.inTrashLabel(e.getX()+toolBar.getWidthT()+10, e.getY())){
                    Command c = new DeleteShapeCommand(selectedToolWhiteboard, whiteboardContent);
                    try {
                        executeCommand(c);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else{
                    toolBar.addTool( selectedToolWhiteboard.clone());
                    whiteboardContent.remove(selectedToolWhiteboard);
                    System.out.println(toolbarContent.getFormes().getShapes().size());
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





}