package com.xshape.modele.fx;

import com.xshape.modele.*;
import com.xshape.modele.Goupage.ToolGroupComponent;
import com.xshape.modele.Goupage.ToolGroupComposite;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.Stack;

public class FxBuilder implements IBuilder, Event {
    private ToolBarMemento mytoolbar;
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
        //this.toolbar = new ToolGroupComposite();
        this.canvas = new Canvas(800, 600);
        this.renderer = new FxRenderer(canvas);
        this.mytoolbar = new ToolBarMemento(renderer);
        this.borderPane = borderPane;
        borderPane.setBottom(canvas);
        this.whiteBoard = new ToolGroupComposite();
        this.group = new ToolGroupComposite();
    }

    @Override
    public void toolBar() {
        renderer.drawLine(100, 10, 100, 600);
        renderer.drawLine(10, 10, 10, 600);
        renderer.drawLine(10, 10, 100, 10);
        renderer.drawLine(10, 600, 100, 600);

        try {
            mytoolbar.loadStateFromFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        mytoolbar.getFormes().draw();
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
                    saveManager.save(this.mytoolbar.getFormes(), this.whiteBoard, file.getAbsolutePath());
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
            mytoolbar.getFormes().clear();
            whiteBoard.clear();
            if (file != null){
                StrategyManager loadManager = new StrategyManager(new TextStrategy());
                try {
                    loadManager.load(this.mytoolbar.getFormes(), this.whiteBoard, renderer, file.getAbsolutePath());
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

    private void executeCommand(Command command) throws IOException {
        undoStack.push(command);
        command.execute();
        selectedShape = null;
        System.out.println(compter());
        canvas.setOnMouseDragged(null);
        redoStack.clear();
        mytoolbar.saveStateToFile();
        redraw();
    }

    @Override
    public void toolBarEvents() {
        canvas.setOnMousePressed(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            if (selectedShape == null){
                for (ToolGroupComponent shape: this.mytoolbar.getFormes().getShapes()) {
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
                        MenuItem edit = new MenuItem("Edit");
                        edit.setOnAction(e -> {
                            System.out.println("le client change de paramètre");
                            ContextMenu menuEdit = new ContextMenu();
                            MenuItem colorEditItem = new MenuItem("edit color");
                            MenuItem positionEditItem = new MenuItem("edit position");
                            menuEdit.getItems().addAll(colorEditItem, positionEditItem);
                            menuEdit.show(canvas, event.getScreenX(), event.getScreenY());

                            //couleur
                            ColorPicker colorPicker = new ColorPicker();

                            colorEditItem.setOnAction(eventColor -> {
                                // Créez une boîte de dialogue
                                Dialog<Color> dialog = new Dialog<>();
                                dialog.setTitle("Select a color");

                                // Ajoutez les boutons "OK" et "Cancel" à la boîte de dialogue
                                ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                                ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                                dialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);

                                // Ajoutez le ColorPicker à la boîte de dialogue
                                dialog.getDialogPane().setContent(colorPicker);

                                // Attachez un gestionnaire d'événements au bouton "OK" pour retourner la couleur sélectionnée
                                dialog.setResultConverter(dialogButton -> {
                                    if (dialogButton == okButtonType) {
                                        return colorPicker.getValue();
                                    }
                                    return null;
                                });

                                // Affichez la boîte de dialogue et attendez qu'elle se ferme
                                Optional<Color> result = dialog.showAndWait();

                                // Si l'utilisateur a sélectionné une couleur, utilisez-la pour mettre à jour l'apparence du shape
                                if (result.isPresent()) {
                                    Color selectedColor = colorPicker.getValue();
                                    System.out.println(selectedColor);
                                    String couleur = selectedColor.toString();
                                    String couleurFinal = couleur.substring(0, couleur.length() - 2);
                                    System.out.println("la couleur final" + couleurFinal);
                                    int i = Integer.parseInt(couleurFinal.substring(2), 16);
                                    System.out.println("notre i : "+ i);
                                    //ColorShapeCommand co = new ColorShapeCommand(shape.getShape(),i);
                                    if (group.contains(shape.getShape())){
                                        ChangeColorGroupCommand co = new ChangeColorGroupCommand(group, i);
                                        try {
                                            executeCommand(co);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    } else {
                                        ColorShapeCommand co = new ColorShapeCommand(shape.getShape(),i);
                                        try {
                                            executeCommand(co);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    }
                                }
                            });
                        });
                        groupMenuItem.setOnAction(e -> {
                            if (!group.contains(shape.getShape())){
                                group.add(shape);

                                System.out.println("Ce shape a été ajouté au groupe, nb de shapes actuel dans le groupe : " + compterBis());
                            } else {
                                System.out.println("le shape existe déja dans le groupe");
                            }
                            redraw();

                        });
                        deGroupMenuItem.setOnAction(e -> {
                            System.out.println("to be deleted");
                            //groupSelectedObjects(selectedShapes);
                            group.remove(shape);
                            System.out.println("Ce shape a été enlevé du groupe, nb de shapes actuel dans le groupe : " + compterBis());
                        });
                        contextMenu.getItems().addAll(groupMenuItem, deGroupMenuItem, edit);

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
                        Command command = new DeleteShapeCommand(selectedTool, mytoolbar.getFormes());
                        try {
                            executeCommand(command);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        Command command = new DeleteShapeCommand(selectedTool, whiteBoard);
                        try {
                            executeCommand(command);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    if (deltaX > 100 && fromToolBar) {
                        Command command = new DrawShapeCommand(selectedShape, releaseEvent.getX(), releaseEvent.getY(), whiteBoard);
                        try {
                            executeCommand(command);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (deltaX > 100 && !fromToolBar) {
                        Command command = new DragShapCommand(selectedTool, releaseEvent.getX(), releaseEvent.getY(), whiteBoard);
                        try {
                            executeCommand(command);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        Command command = new DrawShapeCommand(selectedShape, 50, releaseEvent.getY(), mytoolbar.getFormes());
                        try {
                            executeCommand(command);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
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
        for(ToolGroupComponent tool: this.mytoolbar.getFormes().getShapes()){
            tool.getShape().setPosition(50,  current_y);
            tool.getShape().draw();
            current_y += 75;
        }
    }

    @Override
    public void whiteBoardEvents() {
    }

}
