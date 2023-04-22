package com.xshape.modele;

import com.xshape.modele.Goupage.Tool;
import com.xshape.modele.Goupage.ToolGroupComponent;

public class DragShapCommand implements Command{

    private ToolGroupComponent copy;
    private double _newX;
    private double _newY;
    private double _oldX;
    private double _oldY;
    private ToolGroupComponent whiteBoard;
    private ToolGroupComponent selectedTool;

    public DragShapCommand(ToolGroupComponent shape, double newPosX, double newPosY, ToolGroupComponent composite){
        this.copy = shape;
        this.whiteBoard = composite;
        this._newX = newPosX;
        this._newY = newPosY;
        this._oldX = shape.getShape().getPositionX();
        this._oldY = shape.getShape().getPositionY();
    }

    @Override
    public void execute() {
        copy.getShape().setPosition(_newX, _newY);
        selectedTool = copy;
        whiteBoard.add(selectedTool);
    }

    @Override
    public void undo() {
        copy.getShape().setPosition(_oldX, _oldY);
    }

    @Override
    public void redo() {
        copy.getShape().setPosition(_newX, _newY);
    }
}
