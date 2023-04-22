package com.xshape.modele.fx;

import com.xshape.modele.AbstractButton;
import com.xshape.modele.IButtonFactory;

public class FxButtonFactory implements IButtonFactory {
    @Override
    public AbstractButton createButton(String title, String filepath, int height, int width) {
        return new FxAdapterButton().setTitle(title).setIconImage(filepath,height,width);
    }
}
