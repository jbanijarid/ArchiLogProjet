package com.xshape.modele;

public class Polygone extends SimpleShape {
    double centerX;
    double centerY;
    double radius;
    double nbSides;
    private int color= 0;

    public Polygone(double centerX, double centerY, double radius, double nbSides, IRenderer renderer) {
        super(renderer);
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.nbSides = nbSides;
    }

    @Override
    public void draw() {
        renderer.setColor(this.color);
        double angle = 2 * Math.PI / nbSides; // Angle entre deux sommets consécutifs du polygone
        double[] xPoints = new double[(int) nbSides];
        double[] yPoints = new double[(int) nbSides];
        xPoints[0] = centerX + radius * Math.cos(0);
        yPoints[0] = centerY + radius * Math.sin(0);
        for (int i = 1; i < nbSides; i++) {
            double nextX = centerX + radius * Math.cos(i * angle);
            double nextY = centerY + radius * Math.sin(i * angle);
            xPoints[i] = nextX;
            yPoints[i] = nextY;
        }
        renderer.fillPolygon(xPoints, yPoints, (int) nbSides);

    }
    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void setPosition(double x, double y){
        this.centerX = x;
        this.centerY = y;
    }

    @Override
    public void setHeight(double height) {

    }

    @Override
    public void setWidth(double width) {

    }

    @Override
    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public double getPositionX() {
        return this.centerX;
    }

    @Override
    public double getPositionY() {
        return this.centerY;
    }

    @Override
    public double getWidth() {
        double angle = Math.PI / nbSides; // angle entre le centre, un sommet et le sommet adjacent
        double sideLength = 2 * radius * Math.sin(angle); // longueur d'un côté
        return sideLength;
    }

    @Override
    public double getHeight() {
        double angle = Math.PI / nbSides; // angle entre le centre, un sommet et le sommet adjacent
        double apothem = radius * Math.cos(angle); // apothème du polygone (distance entre le centre et le milieu d'un côté)
        return 2 * apothem;
    }

    @Override
    public double getRadius() {
        return this.radius;
    }

    @Override
    public double getNbSides() {
        return this.nbSides;
    }

    @Override
    public void setNbSides(double nbSides) {
        this.nbSides = nbSides;
    }

    @Override
    public boolean IsArea(double mouseX, double mouseY) {
        double angle = 2 * Math.PI / nbSides;
        double apothem = radius * Math.cos(angle / 2);
        double centerX = this.centerX;
        double centerY = this.centerY;
        double x = centerX + radius;
        double y = centerY;
        double minX = x, maxX = x, minY = y, maxY = y;
        for (int i = 1; i <= nbSides; i++) {
            double nextX = centerX + radius * Math.cos(i * angle);
            double nextY = centerY + radius * Math.sin(i * angle);
            if (nextX < minX) {
                minX = nextX;
            }
            if (nextX > maxX) {
                maxX = nextX;
            }
            if (nextY < minY) {
                minY = nextY;
            }
            if (nextY > maxY) {
                maxY = nextY;
            }
            double dist = distanceToLineSegment(mouseX, mouseY, x, y, nextX, nextY);
            if (dist <= 2) {
                return true;
            }
            x = nextX;
            y = nextY;
        }
        if (mouseX >= minX && mouseX <= maxX && mouseY >= minY && mouseY <= maxY) {
            return true;
        }
        return false;
    }

    @Override
    public void setRenderer(IRenderer r) {
        renderer = r;
    }

    @Override
    public IRenderer getIRenderer() {
        return renderer;
    }

    @Override
    public void translate(double dx, double dy) {
        centerX += dx;
        centerY += dy;
    }
    private double distanceToLineSegment(double x, double y, double x1, double y1, double x2, double y2) {
        double A = x - x1;
        double B = y - y1;
        double C = x2 - x1;
        double D = y2 - y1;

        double dot = A * C + B * D;
        double len_sq = C * C + D * D;
        double param = dot / len_sq;

        double xx, yy;

        if (param < 0) {
            xx = x1;
            yy = y1;
        } else if (param > 1) {
            xx = x2;
            yy = y2;
        } else {
            xx = x1 + param * C;
            yy = y1 + param * D;
        }
        double dx = x - xx;
        double dy = y - yy;
        return Math.sqrt(dx * dx + dy * dy);
    }

}
