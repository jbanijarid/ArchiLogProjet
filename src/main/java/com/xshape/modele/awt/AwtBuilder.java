package com.xshape.modele.awt;

import com.xshape.modele.IBuilder;
import com.xshape.vue.awt.AwtApplication;
import javafx.scene.layout.BorderPane;

import java.awt.*;

public class AwtBuilder implements IBuilder {

    AwtApplication app;

    public AwtBuilder(AwtApplication awtApplication){
        this.app = awtApplication;
    }


    @Override
    public void toolBar() {

    }

    @Override
    public void menuBar() {

        // Créer un panneau pour les boutons
        Panel buttonPanel = new Panel();
        Button okButton = new Button("Undo");
        buttonPanel.add(okButton);
        Button cancelButton = new Button("Redo");
        buttonPanel.add(cancelButton);
        // Ajouter le panneau de boutons au bas de la fenêtre
        this.app.add(buttonPanel, BorderLayout.NORTH);
        this.app.setLayout(new FlowLayout(FlowLayout.LEFT));

    }

    @Override
    public void whiteBoard() {
        Panel awtb = new AwtWhiteBord();
        this.app.add(awtb);
    }

    public void build(){
        toolBar();
        menuBar();
        whiteBoard();
    }
}
