package com.xshape.modele;

import com.xshape.modele.Goupage.Tool;
import com.xshape.modele.Goupage.ToolGroupComponent;

public class DrawShapeCommand implements Command{


    private IShape copy;
    private double x;
    private double y;

    private int color;
    private ToolGroupComponent whiteBoard;
    private ToolGroupComponent selectedTool;

    public DrawShapeCommand(IShape shape, double newPosX, double newPosY, ToolGroupComponent composite){
        this.color = shape.getColor();
        this.copy = shape;
        this.whiteBoard = composite;
        this.x = newPosX;
        this.y = newPosY;
    }

    @Override
    public void execute() {
        copy.setPosition(x, y);
        copy.setColor(color);
        selectedTool = new Tool(copy);
        whiteBoard.add(selectedTool);
        copy.draw();
    }

    @Override
    public void undo() {
        whiteBoard.remove(selectedTool);
    }

    @Override
    public void redo() {
        execute();
    }

    public IShape getCopy() {
        return copy;
    }
}
