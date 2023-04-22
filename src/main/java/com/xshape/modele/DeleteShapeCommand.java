package com.xshape.modele;


import com.xshape.modele.Goupage.ToolGroupComponent;


public class DeleteShapeCommand implements Command{
    private ToolGroupComponent copy;
    private ToolGroupComponent whiteBoard;
    public DeleteShapeCommand(ToolGroupComponent tool, ToolGroupComponent composite){
        this.copy = tool;
        this.whiteBoard = composite;
    }

    @Override
    public void execute() {
        this.whiteBoard.remove(copy);

    }

    @Override
    public void undo() {
        whiteBoard.add(copy);
    }

    @Override
    public void redo() {
        execute();
    }
}
