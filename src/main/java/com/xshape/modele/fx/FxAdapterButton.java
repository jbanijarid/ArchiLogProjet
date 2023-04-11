package com.xshape.modele.fx;

import com.xshape.modele.AbstractButton;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class FxAdapterButton  extends Button implements AbstractButton {
    public AbstractButton setTitle(String text){   
        super.setText(text);
        return this;
    }

    public AbstractButton setSize(double width, double height){
        setPrefSize(width, height);
        return this;
    }

    @Override
    public AbstractButton setIconImage(String filepath,int height, int width) {
        ImageView imgUndo = new ImageView(filepath);
        imgUndo.setFitHeight(height);
        imgUndo.setFitWidth(width);
        setGraphic(imgUndo);
        return this;
    }
}
