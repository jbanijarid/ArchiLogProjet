package com.xshape.modele;

import com.xshape.modele.Goupage.Tool;
import com.xshape.modele.Goupage.ToolGroupComponent;
import com.xshape.modele.Goupage.ToolGroupComposite;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ToolBarMemento{
    private ToolGroupComponent formes;
    private final String fileName = "saveToolBar.txt";
    private IRenderer _renderer;
    private IFactory factory = new Factory();

    public ToolBarMemento(IRenderer renderer) {
        //this.formes = new ArrayList<>();
        //Ajouter les shapes par defaut.
        formes = new ToolGroupComposite();
        this._renderer = renderer;
    }

    public ToolGroupComponent getFormes() {
        return formes;
    }


    public void saveStateToFile() throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writer.write(formes.getShapes().size() + "\n");
        for (ToolGroupComponent tool : formes.getShapes()){
            writer.write(tool.getShape().getPositionX() + "\n");
            writer.write(tool.getShape().getPositionY() + "\n");
            writer.write(tool.getShape().getHeight() + "\n");
            writer.write(tool.getShape().getWidth() + "\n");
            writer.write(tool.getShape().getNbSides() + "\n");
            writer.write(tool.getShape().getColor() + "\n");
        }
        writer.close();
    }

    public void loadStateFromFile() throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(fileName));
        if (!scanner.hasNext() || scanner == null){
            IShape rect = factory.createRectangle(25, 40, 50, 40, _renderer);
            IShape poly = factory.createPolygone(50, 120, 30, 6, _renderer);
            ToolGroupComponent rectTool = new Tool(rect);
            ToolGroupComponent polyTool = new Tool(poly);
            this.formes.add(rectTool);
            this.formes.add(polyTool);
        } else {
            String s = scanner.nextLine();
            int nbToolBar = Integer.parseInt(s);
            System.out.println(nbToolBar);
            for (int i = 0; i < nbToolBar; i++){
                String positionX = scanner.nextLine();
                String positionY = scanner.nextLine();
                String height = scanner.nextLine();
                String width = scanner.nextLine();
                String nbSides = scanner.nextLine();
                String color = scanner.nextLine();
                double x = Double.parseDouble(positionX);
                double y = Double.parseDouble(positionY);
                double w = Double.parseDouble(width);
                double h = Double.parseDouble(height);
                double nb = Double.parseDouble(nbSides);
                int c = Integer.parseInt(color);
                if (nb == 4){
                    IShape shape = new Rectangle(x, y, w, h, _renderer);
                    shape.setColor(c);
                    ToolGroupComponent tool = new Tool(shape);
                    formes.add(tool);
                } else {
                    IShape shape = new Polygone(x, y, w, nb, _renderer);
                    shape.setColor(c);
                    ToolGroupComponent tool = new Tool(shape);
                    formes.add(tool);
                }
            }
        }
    }


    /*
    public void ajouterForme(ToolGroupComponent forme) {
        this.formes.add(forme);
    }

    public void supprimerForme(ToolGroupComponent forme) {
        this.formes.remove(forme);
    }



    public void setFormes(ToolGroupComponent formes) {
        this.formes = formes;
    }
    */


    /*
    public ToolbarMemento createMemento() {
        return new ToolbarMemento(this.formes);
    }

    public void restoreMemento(ToolbarMemento memento) {
        this.formes = memento.getFormes();
    }

     */
}
