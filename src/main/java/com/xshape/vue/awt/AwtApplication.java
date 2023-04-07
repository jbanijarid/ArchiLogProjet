package com.xshape.vue.awt;

import com.xshape.modele.IRenderer;
import com.xshape.modele.Polygone;
import com.xshape.modele.Rectangle;
import com.xshape.modele.awt.AwtBuilder;
import com.xshape.modele.awt.AwtRenderer;
import com.xshape.modele.fx.FxBuilder;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AwtApplication extends Frame {
    public AwtApplication() {
        super("Titre de la fenêtre");
        setLayout(null);
        setSize(800, 600);
        AwtBuilder awtb = new AwtBuilder(this);
        awtb.build();



        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                dispose();
                System.exit(0);
            }
        });

        setVisible(true);



    }

    public static void main(String[] args) {
        new AwtApplication();
    }
}