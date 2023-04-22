package com.xshape.vue.awt;

import com.xshape.modele.Command;
import com.xshape.modele.IButtonFactory;
import com.xshape.modele.awt.AwtAdapterButton;
import com.xshape.modele.awt.AwtButtonFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
                    System.out.println("ahhhhhhhhhhhhhhhhhh");
                    awtBuilder.redoStackAwt.push(command);
                    AwtConcreteWhiteBoard w= (AwtConcreteWhiteBoard) awtBuilder.getWhiteBoard();
                    w.update(awtBuilder.undoStackAwt, awtBuilder.redoStackAwt);
                }
            }
        });

        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!awtBuilder.redoStackAwt.empty()) {
                    Command command = awtBuilder.redoStackAwt.pop();
                    command.execute();
                    awtBuilder.undoStackAwt.push(command);
                    AwtConcreteWhiteBoard w= (AwtConcreteWhiteBoard) awtBuilder.getWhiteBoard();
                    w.update(awtBuilder.undoStackAwt, awtBuilder.redoStackAwt);
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }


}