package com.xshape.modele;

import com.xshape.modele.Goupage.Tool;
import com.xshape.modele.Goupage.ToolGroupComponent;
import com.xshape.modele.Goupage.ToolGroupComposite;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TextStrategy implements IStrategy {
    @Override
    public void save(ToolGroupComponent toolbar, ToolGroupComponent whiteboard, String filePath) throws java.io.IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(toolbar.getShapes().size() + "\n");
        for (ToolGroupComponent tool : toolbar.getShapes()){
            writer.write(tool.getShape().getPositionX() + "\n");
            writer.write(tool.getShape().getPositionY() + "\n");
            writer.write(tool.getShape().getHeight() + "\n");
            writer.write(tool.getShape().getWidth() + "\n");
            writer.write(tool.getShape().getNbSides() + "\n");
            writer.write(tool.getShape().getColor() + "\n");
        }
        writer.write(whiteboard.getShapes().size() + "\n");
        for (ToolGroupComponent tool : whiteboard.getShapes()) {
            writer.write(tool.getShape().getPositionX() + "\n");
            writer.write(tool.getShape().getPositionY() + "\n");
            writer.write(tool.getShape().getHeight() + "\n");
            writer.write(tool.getShape().getWidth() + "\n");
            writer.write(tool.getShape().getNbSides() + "\n");
            writer.write(tool.getShape().getColor() + "\n");
        }
        writer.close();
    }

    @Override
    public void load(ToolGroupComponent toolbar, ToolGroupComponent whiteboard, IRenderer renderer, String filePath) throws java.io.IOException{
        Scanner scanner = new Scanner(new File(filePath));
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
                IShape shape = new Rectangle(x, y, w, h, renderer);
                shape.setColor(c);
                ToolGroupComponent tool = new Tool(shape);
                toolbar.add(tool);
            } else {
                IShape shape = new Polygone(x, y, w, nb, renderer);
                shape.setColor(c);
                ToolGroupComponent tool = new Tool(shape);
                toolbar.add(tool);
            }
        }
        String s2 = scanner.nextLine();
        int nbWhiteBoard = Integer.parseInt(s2);
        System.out.println(nbWhiteBoard);
        for (int i = 0; i < nbWhiteBoard; i++){
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
                IShape shape = new Rectangle(x, y, w, h, renderer);
                shape.setColor(c);
                ToolGroupComponent tool = new Tool(shape);
                whiteboard.add(tool);
            } else {
                IShape shape = new Polygone(x, y, w, nb, renderer);
                shape.setColor(c);
                ToolGroupComponent tool = new Tool(shape);
                whiteboard.add(tool);
            }
        }
    }
}