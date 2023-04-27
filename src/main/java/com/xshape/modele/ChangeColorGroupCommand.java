package com.xshape.modele;

import com.xshape.modele.Goupage.Tool;
import com.xshape.modele.Goupage.ToolGroupComponent;

public class ChangeColorGroupCommand implements Command{


    private IShape copy;
    private double x;
    private double y;
    private ToolGroupComponent whiteBoard;
    private int color;
    private ToolGroupComponent selectedTool;

    public ChangeColorGroupCommand(ToolGroupComponent composite, int c){
        this.whiteBoard = composite;
        this.color = c;
    }

    @Override
    public void execute() {
        for (ToolGroupComponent tool : whiteBoard.getShapes()){
            tool.getShape().setColor(color);
        }
    }

    @Override
    public void undo() {
    }

    @Override
    public void redo() {
        execute();
    }

    public IShape getCopy() {
        return copy;
    }
}
