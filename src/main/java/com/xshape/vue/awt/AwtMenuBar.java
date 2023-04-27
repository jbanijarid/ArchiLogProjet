package com.xshape.vue.awt;

import com.xshape.modele.Command;
import com.xshape.modele.IButtonFactory;
import com.xshape.modele.StrategyManager;
import com.xshape.modele.TextStrategy;
import com.xshape.modele.awt.AwtAdapterButton;
import com.xshape.modele.awt.AwtButtonFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


class AwtMenuBar extends JToolBar {

    private IButtonFactory factoryButton = new AwtButtonFactory();
    private int iconWidth = 24; // largeur des icônes des boutons
    private int iconHeight = 24; // hauteur des icônes des boutons

    public AwtMenuBar(AwtApplication app, int x, int y, int width, int height, AwtBuilder awtBuilder) {

        setBackground(Color.gray);
        setBounds(x,y,width,height);
        AwtAdapterButton undoButton = (AwtAdapterButton)factoryButton.createButton("","src/main/resources/com/xshape/undo.png",iconHeight,iconWidth);
        AwtAdapterButton redoButton = (AwtAdapterButton)factoryButton.createButton("","src/main/resources/com/xshape/redo.png",iconHeight,iconWidth);
        AwtAdapterButton saveButton = (AwtAdapterButton)factoryButton.createButton("","src/main/resources/com/xshape/save.png",iconHeight,iconWidth);
        AwtAdapterButton loadButton = (AwtAdapterButton)factoryButton.createButton("","src/main/resources/com/xshape/load.png",iconHeight,iconWidth);

        add(undoButton);
        add(redoButton);
        add(saveButton);
        add(loadButton);

        addSeparator();

        app.add(this, BorderLayout.NORTH);

        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!awtBuilder.undoStackAwt.empty()) {
                    Command command = awtBuilder.undoStackAwt.pop();
                    command.undo();
                    awtBuilder.redoStackAwt.push(command);
                    awtBuilder.toolBar.repaint();
                    awtBuilder.whiteBoard.repaint();
                }
            }
        });

        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!awtBuilder.redoStackAwt.empty()) {
                    Command command = awtBuilder.redoStackAwt.pop();
                    command.redo();
                    awtBuilder.undoStackAwt.push(command);
                    awtBuilder.toolBar.repaint();
                    awtBuilder.whiteBoard.repaint();
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog dialog = new FileDialog(new Frame(), "Sauvegarder le fichier", FileDialog.SAVE);
                dialog.setVisible(true);
                String fileName = dialog.getFile();
                if (fileName != null) {
                    String filePath = dialog.getDirectory() + fileName;
                    StrategyManager saveManager = new StrategyManager(new TextStrategy());
                    try {
                        saveManager.save(awtBuilder.toolbarContent.getFormes(), awtBuilder.whiteboardContent, filePath);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog dialog = new FileDialog(new Frame(), "Charger un fichier", FileDialog.LOAD);
                dialog.setVisible(true);
                String fileName = dialog.getFile();
                if (fileName != null) {
                    String filePath = dialog.getDirectory() + fileName;
                    awtBuilder.toolbarContent.getFormes().clear();
                    awtBuilder.whiteboardContent.clear();
                    StrategyManager loadManager = new StrategyManager(new TextStrategy());
                    try {
                        loadManager.load(awtBuilder.toolbarContent.getFormes(), awtBuilder.whiteboardContent, awtBuilder.whiteBoard.getRenderer(), filePath);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    awtBuilder.whiteBoard.repaint();
                    awtBuilder.toolBar.repaint();
                }
            }
        });

    }


}