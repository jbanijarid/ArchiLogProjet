package com.xshape.modele.awt;

import com.xshape.modele.AbstractButton;
import com.xshape.modele.IButtonFactory;

public class AwtButtonFactory implements IButtonFactory {
    @Override
    public AbstractButton createButton(String title, String filepath, int height, int width) {
        return new AwtAdapterButton().setTitle(title).setIconImage(filepath,height,width);
    }
}
