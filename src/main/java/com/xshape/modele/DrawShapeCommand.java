package com.xshape.modele;

import com.xshape.modele.Goupage.Tool;
import com.xshape.modele.Goupage.ToolGroupComponent;

public class DrawShapeCommand implements Command{


    private IShape copy;
    private double x;
    private double y;
    private ToolGroupComponent _whiteBoard;
    private ToolGroupComponent selectedTool;

    public DrawShapeCommand(IShape shape, double newPosX, double newPosY, ToolGroupComponent composite){
        this.copy = shape;
        this._whiteBoard = composite;
        this.x = newPosX;
        this.y = newPosY;
    }

    @Override
    public void execute() {
        copy.setPosition(x, y);
        selectedTool = new Tool(copy);
        _whiteBoard.add(selectedTool);
        copy.draw();
    }

    @Override
    public void undo() {
        _whiteBoard.remove(selectedTool);
    }

    @Override
    public void redo() {

    }

    public IShape getCopy() {
        return copy;
    }
}
