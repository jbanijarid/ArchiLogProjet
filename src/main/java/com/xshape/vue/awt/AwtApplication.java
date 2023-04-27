package com.xshape.vue.awt;

import com.xshape.modele.Goupage.ToolGroupComponent;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AwtApplication extends Frame {
    public AwtApplication() {
        super("Mini Editeur de Formes");
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