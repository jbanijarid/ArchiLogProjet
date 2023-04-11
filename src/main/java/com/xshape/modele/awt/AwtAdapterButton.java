package com.xshape.modele.awt;

import com.xshape.modele.AbstractButton;

import javax.swing.*;
import java.awt.*;

public class AwtAdapterButton extends JButton implements AbstractButton {
    @Override
    public AbstractButton setTitle(String text) {
        super.setLabel(text);
        return this;
    }

    @Override
    public AbstractButton setSize(double width, double height) {
        setPreferredSize(new Dimension((int)width, (int)height));
        return this;
    }

    @Override
    public AbstractButton setIconImage(String filepath, int height, int width) {
        ImageIcon undoIcon = new ImageIcon(filepath);
        undoIcon.setImage(undoIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        setIcon(undoIcon);
        return this;
    }
}
