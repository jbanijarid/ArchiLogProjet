package com.xshape.modele.fx;

import com.xshape.modele.*;
import com.xshape.modele.Goupage.Tool;
import com.xshape.modele.Goupage.ToolGroupComponent;
import com.xshape.modele.Goupage.ToolGroupComposite;
import com.xshape.vue.fx.FXApplication;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

public class FxBuilder implements IBuilder, Event {
    private ToolGroupComponent toolbar;
    private ToolGroupComponent whiteBoard;
    private BorderPane borderPane;
    private IRenderer renderer;
    private Canvas canvas;
    IShape selectedShape = null;
    private IFactory factory = new Factory();
    private IButtonFactory factoryButton = new FxButtonFactory();
    private boolean fromToolBar = false;
    private Image image;
    private ToolGroupComponent selectedTool;
    private static Stack<Command> undoStack = new Stack<>();
    private static Stack<Command> redoStack = new Stack<>();

    private ToolGroupComponent group;

    public FxBuilder(BorderPane borderPane) {
        this.toolbar = new ToolGroupComposite();
        this.borderPane = borderPane;
        this.canvas = new Canvas(800, 600);
        borderPane.setBottom(canvas);
        this.renderer = new FxRenderer(canvas);
        this.whiteBoard = new ToolGroupComposite();
        this.group = new ToolGroupComposite();
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

        /*IShape rect1 = factory.createRectangle(150, 150, 50, 40, renderer);
        IShape poly1 = factory.createPolygone(450, 420, 30, 6, renderer);
        TranslateshapeCommand c = new TranslateshapeCommand(poly1,300, 300);
        ColorShapeCommand co = new ColorShapeCommand(rect1,0);

        c.execute();
        //c.undo();
        co.execute();
        rect1.draw();
        poly1.draw();*/

        toolbar.add(rectTool);
        toolbar.add(polyTool);
        toolbar.draw();

        image = new Image("/com/xshape/delete.png");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(image, 25, 500, 50, 50);
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
                redraw();
            }
        });

        // Boutton redo
        FxAdapterButton redoButton = (FxAdapterButton)factoryButton.createButton("","/com/xshape/redo.png",24,24);
        redoButton.setOnAction(event -> {
            if (!redoStack.empty()) {
                Command command = redoStack.pop();
                command.redo();
                undoStack.push(command);
                redraw();
            }
        });

        // Boutton save
        FxAdapterButton saveButton = (FxAdapterButton) factoryButton.createButton("","/com/xshape/save.png",24,24);

        saveButton.setOnAction(event -> {
            Stage dialog = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sauvegarder le fichier");
            File file = fileChooser.showSaveDialog(dialog);
            if (file != null){
                StrategyManager saveManager = new StrategyManager(new TextStrategy());
                try {
                    saveManager.save(this.toolbar, this.whiteBoard, file.getAbsolutePath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Boutton load
        FxAdapterButton loadButton = (FxAdapterButton)factoryButton.createButton("","/com/xshape/load.png",24,24);
        loadButton.setOnAction(event -> {
            Stage dialog = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("charger un fichier");
            File file = fileChooser.showOpenDialog(dialog);
            toolbar.clear();
            if (file != null){
                StrategyManager loadManager = new StrategyManager(new TextStrategy());
                try {
                    loadManager.load(this.toolbar, this.whiteBoard, renderer, file.getAbsolutePath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                redraw();
            }
        });
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

    private void executeCommand(Command command){
        undoStack.push(command);
        command.execute();
        selectedShape = null;
        System.out.println(compter());
        canvas.setOnMouseDragged(null);
        redoStack.clear();
        redraw();
    }

    @Override
    public void toolBarEvents() {
        /*
        // Ajouter un événement de souris au whiteboard
        canvas.setOnMousePressed(event -> {
            // Si le bouton de la souris est appuyé et l'utilisateur a appuyé sur la touche CTRL, alors il a sélectionné un objet.
            if (event.isPrimaryButtonDown() && event.isControlDown()) {
                // Code pour ajouter l'objet sélectionné à une liste de sélection.
                System.out.println("debut de la selection");
            }
        });

        canvas.setOnMouseDragged(event -> {
            // Si l'utilisateur fait un rectangle de sélection
            if (event.isPrimaryButtonDown()) {
                // Code pour dessiner le rectangle de sélection.
                System.out.println("en cours de selection");
            }
        });

        canvas.setOnMouseReleased(event -> {
            // Si l'utilisateur termine la sélection multiple par rectangle de sélection
            if (event.isPrimaryButtonDown()) {
                // Code pour sélectionner tous les objets dans le rectangle de sélection
                System.out.println("fin de la selection");
            }
        });

         */

        /*
        canvas.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                // Créer un menu contextuel avec l'option "Group"
                ContextMenu contextMenu = new ContextMenu();
                MenuItem groupMenuItem = new MenuItem("Group");
                MenuItem deGroupMenuItem = new MenuItem("DeGroup");
                groupMenuItem.setOnAction(e -> {
                    System.out.println("to be added");
                    //groupSelectedObjects(selectedShapes);
                    group.add();
                });
                contextMenu.getItems().addAll(groupMenuItem, deGroupMenuItem);

                // Afficher le menu contextuel à l'emplacement du clic
                contextMenu.show(canvas, event.getScreenX(), event.getScreenY());
            }
        });

         */


        /*
        canvas.setOnMouseClicked(event -> {
            Stage dialog = new Stage();
            // Créer les éléments graphiques dans la fenêtre de dialogue
            // pour permettre à l'utilisateur de modifier les propriétés du Shape.

            dialog.showAndWait();
        });

         */
        canvas.setOnMousePressed(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            if (selectedShape == null){
                for (ToolGroupComponent shape: this.toolbar.getShapes()) {
                    if (shape.getShape().IsArea(mouseX, mouseY)) {
                        fromToolBar = true;
                        selectedTool = shape;
                        selectedShape = shape.clone().getShape();
                        break;
                    }
                }
                for (ToolGroupComponent shape: this.whiteBoard.getShapes()) {
                    if (event.getButton() == MouseButton.SECONDARY && shape.getShape().IsArea(mouseX, mouseY)) {
                        // Créer un menu contextuel avec l'option "Group"
                        ContextMenu contextMenu = new ContextMenu();
                        MenuItem groupMenuItem = new MenuItem("Group");
                        MenuItem deGroupMenuItem = new MenuItem("DeGroup");
                        groupMenuItem.setOnAction(e -> {
                            System.out.println("to be added");
                            //groupSelectedObjects(selectedShapes);
                            group.add(shape);
                            System.out.println("groupe" + compterBis());
                        });
                        deGroupMenuItem.setOnAction(e -> {
                            System.out.println("to be deleted");
                            //groupSelectedObjects(selectedShapes);
                            group.remove(shape);
                            System.out.println("groupe" + compterBis());
                        });
                        contextMenu.getItems().addAll(groupMenuItem, deGroupMenuItem);

                        // Afficher le menu contextuel à l'emplacement du clic
                        contextMenu.show(canvas, event.getScreenX(), event.getScreenY());
                    } else {
                        if (shape.getShape().IsArea(mouseX, mouseY)) {
                            selectedShape = shape.getShape();
                            selectedTool = shape;
                            whiteBoard.remove(shape);
                            fromToolBar = false;
                            break;
                        }
                    }
                }
            }
        });
        canvas.setOnMouseDragged(dragEvent -> {
        });
        canvas.setOnMouseReleased(releaseEvent -> {
            if (selectedShape != null){
                double deltaX = releaseEvent.getX();
                double deltaY = releaseEvent.getY();
                if (deltaX >= 25 && deltaX < 25 + 50 && deltaY >= 500 && deltaY < 500+50){
                    if (fromToolBar){
                        Command command = new DeleteShapeCommand(selectedTool, toolbar);
                        executeCommand(command);
                    } else {
                        Command command = new DeleteShapeCommand(selectedTool, whiteBoard);
                        executeCommand(command);
                    }
                } else {
                    if (deltaX > 100 && fromToolBar) {
                        Command command = new DrawShapeCommand(selectedShape, releaseEvent.getX(), releaseEvent.getY(), whiteBoard);
                        executeCommand(command);
                    } else if (deltaX > 100 && !fromToolBar) {
                        Command command = new DragShapCommand(selectedTool, releaseEvent.getX(), releaseEvent.getY(), whiteBoard);
                        executeCommand(command);
                    } else {
                        Command command = new DrawShapeCommand(selectedShape, 50, releaseEvent.getY(), toolbar);
                        executeCommand(command);
                    }
                    fromToolBar = false;
                }
            }
        });
    }

    int compter(){
        int nb = 0;
        for (ToolGroupComponent tool : whiteBoard.getShapes()){
            nb++;
        }
        return nb;
    }

    int compterBis(){
        int nb = 0;
        for (ToolGroupComponent tool : group.getShapes()){
            nb++;
        }
        return nb;
    }

    void redraw(){
        canvas.getGraphicsContext2D().clearRect(0, 0, 800, 600);
        renderer.drawLine(100, 10, 100, 600);
        renderer.drawLine(10, 10, 10, 600);
        renderer.drawLine(10, 10, 100, 10);
        renderer.drawLine(10, 600, 100, 600);
        drawToolBar();
        whiteBoard.draw();
        image = new Image("/com/xshape/delete.png");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(image, 25, 500, 50, 50);
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
        canvas.getGraphicsContext2D().clearRect(0, 0, 800, 600);
        redraw();
    }

}
