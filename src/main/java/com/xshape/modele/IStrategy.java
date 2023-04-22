package com.xshape.modele;

import com.xshape.modele.Goupage.ToolGroupComponent;

import java.io.IOException;

public interface IStrategy {
    void save(ToolGroupComponent toolbar, ToolGroupComponent whiteboard, String filePath) throws java.io.IOException;
    void load(ToolGroupComponent toolbar, ToolGroupComponent whiteboard, IRenderer renderer, String filePath) throws java.io.IOException;
}
