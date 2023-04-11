package com.xshape.modele.fx;

import com.xshape.modele.*;
import com.xshape.modele.Goupage.Tool;
import com.xshape.modele.Goupage.ToolGroupComponent;
import com.xshape.modele.Goupage.ToolGroupComposite;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.Stack;

public class FxBuilder implements IBuilder, Event {

    private ToolGroupComponent toolbar;

    private ToolGroupComponent whiteBoard;
    private Composite whiteboard;
    private BorderPane borderPane;
    private IRenderer renderer;
    private Canvas canvas;
    IShape selectedShape = null;

    public static FxWhiteBoard _whiteBoard;
    private IFactory factory = new Factory();
    private IButton factoryButton = new FxFactoryButton();
    private boolean fromToolBar = false;

    private static Stack<Command> undoStack = new Stack<>();
    private static Stack<Command> redoStack = new Stack<>();



    public FxBuilder(BorderPane borderPane) {
        //this.toolbar = new Composite();
        this.toolbar = new ToolGroupComposite();
        //this.whiteboard = whiteboard;
        this.borderPane = borderPane;
        this.canvas = new Canvas(800, 600);
        borderPane.setBottom(canvas);
        this.renderer = new FxRenderer(canvas);
        this.whiteBoard = new ToolGroupComposite();
    }

    @Override
    public void toolBar() {

        renderer.drawLine(100, 10, 100, 600);
        renderer.drawLine(10, 10, 10, 600);
        renderer.drawLine(10, 10, 100, 10);
        renderer.drawLine(10, 600, 100, 600);
        IShape rect = factory.createRectangle(25, 40, 50, 40, renderer);
        IShape poly = factory.createPolygone(50, 120, 30, 6, renderer);
        ToolGroupComponent rectTool = new Tool(rect);
        ToolGroupComponent polyTool = new Tool(poly);
        toolbar.add(rectTool);
        toolbar.add(polyTool);
        toolbar.draw();
        //toolbar.add(rect);
        //toolbar.add(poly);
        //toolbar.draw();
    }

    @Override
    public void menuBar() {
        ToolBar toolBar = new ToolBar();

        //Boutton undo
        FxAdapterButton undoButton = (FxAdapterButton) factoryButton.createButton("","/com/xshape/undo.png",24,24);
        undoButton.setOnAction(event -> {
            if (!undoStack.empty()) {
                Command command = undoStack.pop();
                command.undo();
                redoStack.push(command);
                redrawCanvas();
            }
        });

        // Boutton redo
        FxAdapterButton redoButton = (FxAdapterButton)factoryButton.createButton("","/com/xshape/redo.png",24,24);
        redoButton.setOnAction(event -> {
            if (!redoStack.empty()) {
                Command command = redoStack.pop();
                command.execute();
                undoStack.push(command);
                redrawCanvas();
            }
        });

        // Boutton save
        FxAdapterButton saveButton = (FxAdapterButton) factoryButton.createButton("","/com/xshape/save.png",24,24);
        //saveButton.setGraphic(imgSave);

        // Boutton load
        FxAdapterButton loadButton = (FxAdapterButton)factoryButton.createButton("","/com/xshape/load.png",24,24);

        toolBar.getItems().addAll(undoButton,redoButton,saveButton,loadButton);
        borderPane.setTop(toolBar);
    }

    @Override
    public void whiteBoard() {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    public BorderPane build(){
        toolBar();
        menuBar();
        whiteBoard();
        toolBarEvents();

        return borderPane;
    }

    @Override
    public void groupDarggable(Composite group) {
    }


    @Override
    public void toolBarEvents() {
        canvas.setOnMousePressed(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            if (selectedShape == null){
                for (ToolGroupComponent shape: this.toolbar.getShapes()) {
                    if (shape.getShape().IsArea(mouseX, mouseY)) {
                        System.out.println("shape from toolbar");
                        fromToolBar = true;
                        selectedShape = shape.clone().getShape();
                        System.out.println(selectedShape);
                        System.out.println(shape);
                        System.out.print("shape is selectet");
                        break;
                    }
                }
                for (ToolGroupComponent shape: this.whiteBoard.getShapes()) {
                    if (shape.getShape().IsArea(mouseX, mouseY)) {
                        System.out.println("shape from white board");
                        selectedShape = shape.getShape();
                        whiteBoard.remove(shape);
                        fromToolBar = false;
                        System.out.println(selectedShape);
                        System.out.println(shape);
                        System.out.print("shape is selectet");
                        break;
                    }
                }
            }
            System.out.println("fin 1");
        });
        canvas.setOnMouseDragged(dragEvent -> {
        });
        canvas.setOnMouseReleased(releaseEvent -> {
            if (selectedShape != null){
                double deltaX = releaseEvent.getX();
                double deltaY = releaseEvent.getY();
                if (deltaX > 100 && fromToolBar) {
                    System.out.println("Je dessine le new shape sur la white board");
                    //newShape.draw();
                    //ToolGroupComponent selectedTool = new Tool(selectedShape);
                    //whiteBoard.add(selectedTool);
                    Command command = new DrawShapeCommand(selectedShape, releaseEvent.getX(), releaseEvent.getY(), whiteBoard);
                    undoStack.push(command);
                    command.execute();
                    selectedShape = null;
                    System.out.println("je met selected shape a null");
                    canvas.setOnMouseDragged(null);
                    redoStack.clear();
                    redraw();
                } else if (deltaX > 100 && !fromToolBar){
                    //ToolGroupComponent selectedTool = new Tool(selectedShape);
                    //whiteBoard.add(selectedTool);
                    System.out.println("la j'ai selectionne un shape du white board");
                    Command command = new DrawShapeCommand(selectedShape, releaseEvent.getX(), releaseEvent.getY(), whiteBoard);
                    undoStack.push(command);
                    command.execute();
                    redoStack.clear();
                    //selectedShape.setPosition(releaseEvent.getX(), releaseEvent.getY());
                    //_canvas.getGraphicsContext2D().clearRect(0, 0, 800, 600);
                    System.out.println("Je supprime tout, et je redessine");
                    //whiteBoard.draw();
                    redraw();
                    canvas.setOnMouseDragged(null);
                    //toolBar();
                    selectedShape = null;
                } else {
                    //ToolGroupComponent selectedTool = new Tool(selectedShape);
                    //toolbar.add(selectedTool);
                    Command command = new DrawShapeCommand(selectedShape, 50, releaseEvent.getY(), toolbar);
                    undoStack.push(command);
                    command.execute();
                    selectedShape = null;
                    //drawToolBar();
                    //_canvas.getGraphicsContext2D().clearRect(0, 0, 800, 600);
                    //redrawCanvas();
                    redraw();
                    System.out.println("je met selected shape a null");
                    canvas.setOnMouseDragged(null);
                    redoStack.clear();
                }
                fromToolBar = false;
            }
        });
    }

    void redraw(){
        canvas.getGraphicsContext2D().clearRect(0, 0, 800, 600);
        renderer.drawLine(100, 10, 100, 600);
        renderer.drawLine(10, 10, 10, 600);
        renderer.drawLine(10, 10, 100, 10);
        renderer.drawLine(10, 600, 100, 600);
        drawToolBar();
        whiteBoard.draw();
    }


    void drawToolBar(){
        double current_y = 40;
        for(ToolGroupComponent tool: this.toolbar.getShapes()){
            tool.getShape().setPosition(50,  current_y);
            tool.getShape().draw();
            current_y += 75;
        }
    }




    @Override
    public void whiteBoardEvents() {

    }

    private void redrawCanvas() {

        // Effacer le canvas
        canvas.getGraphicsContext2D().clearRect(0, 0, 800, 600);
        toolBar();

        // Redessiner tous les éléments de la pile undo
        for (Command command : undoStack) {
            command.execute();
        }
    }

}
