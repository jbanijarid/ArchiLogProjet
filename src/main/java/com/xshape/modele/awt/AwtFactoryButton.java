package com.xshape.modele.awt;

import com.xshape.modele.AbstractButton;
import com.xshape.modele.IButton;

public class AwtFactoryButton implements IButton {
    @Override
    public AbstractButton createButton(String title, String filepath, int height, int width) {
        return new AwtAdapterButton().setTitle(title).setIconImage(filepath,height,width);
    }
}
