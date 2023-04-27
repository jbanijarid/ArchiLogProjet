package com.xshape.modele;

public interface IRenderer {
    public void drawLine(double x1, double y1, double x2, double y2);
    public void setColor(int color);
    public void fillPolygon(double[] xPoints,double[] yPoints, int cote );
    public void fillRect(double x, double y, double width, double height);
}
