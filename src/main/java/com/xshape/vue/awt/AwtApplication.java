package com.xshape.vue.awt;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AwtApplication extends Frame {
    public AwtApplication() {
        super("Titre de la fenêtre");
        setSize(300, 200);

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