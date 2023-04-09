package com.xshape.modele;

public interface Command {
    void execute();
    void undo();
    void redo();
}
