package com.xshape.modele.fx;

import com.xshape.modele.AbstractButton;
import com.xshape.modele.IButton;

public class FxFactoryButton implements IButton {
    @Override
    public AbstractButton createButton(String title, String filepath, int height, int width) {
        return new FxAdapterButton().setTitle(title).setIconImage(filepath,height,width);
    }
}
