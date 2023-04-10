package com.xshape.vue.awt;

import com.xshape.modele.Command;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class AwtMenuBar extends JToolBar {


    public AwtMenuBar(AwtApplication app, int x, int y, int width, int height, AwtBuilder awtBuilder) {

        setBackground(Color.gray);
        setBounds(x,y,width,height);

        ImageIcon undoIcon = new ImageIcon("src/main/resources/com/xshape/undo.png");
        ImageIcon redoIcon = new ImageIcon("src/main/resources/com/xshape/redo.png");
        ImageIcon saveIcon = new ImageIcon("./src/main/resources/com/xshape/save.png");
        ImageIcon loadIcon = new ImageIcon("src/main/resources/com/xshape/load.png");

        int iconWidth = 24; // largeur des icônes des boutons
        int iconHeight = 24; // hauteur des icônes des boutons
        undoIcon.setImage(undoIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH));
        redoIcon.setImage(redoIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH));
        saveIcon.setImage(saveIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH));
        loadIcon.setImage(loadIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH));

        JButton undoButton = new JButton(undoIcon);
        JButton redoButton = new JButton(redoIcon);
        JButton saveButton = new JButton(saveIcon);
        JButton loadButton = new JButton(loadIcon);

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
                    w.repaint();
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
                    w.repaint();
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