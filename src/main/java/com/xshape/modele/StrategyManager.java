package com.xshape.modele;

import com.xshape.modele.Goupage.ToolGroupComponent;

public class StrategyManager {
    private IStrategy _strategy;

    public StrategyManager(IStrategy strategy) {
        this._strategy = strategy;
    }

    public void save(ToolGroupComponent toolbar, ToolGroupComponent whiteboard, String filePath) throws java.io.IOException  {
        _strategy.save(toolbar, whiteboard, filePath);
    }

    public void load(ToolGroupComponent toolbar, ToolGroupComponent whiteboard, IRenderer renderer, String filePath) throws java.io.IOException  {
        _strategy.load(toolbar, whiteboard, renderer, filePath);
    }
}
