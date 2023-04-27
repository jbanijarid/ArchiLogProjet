package com.xshape.vue.awt;


import com.xshape.modele.*;
import com.xshape.modele.Goupage.ToolGroupComponent;
import com.xshape.modele.awt.AwtRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;


public class AwtConcreteWhiteBoard extends AwtAbstractWhiteBoard {


    private IRenderer renderer;
    private IShape selectedShape;
    private ToolGroupComponent selectedTool;
    AwtBuilder builder;


    public IRenderer getRenderer() {
        return renderer;
    }



    public AwtConcreteWhiteBoard(AwtApplication app, int x, int y, int width, int height, AwtBuilder builder) {
        super(app, x, y, width, height);
        app.add(this);
        this.builder=builder;
        addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    System.out.println("ohhhhhhhhhhhhhhhhhhhhhhhh");
                    for (ToolGroupComponent c : builder.whiteboardContent.getShapes()) {
                        if (c.getShape().IsArea(e.getX(), e.getY())) {
                            // Créer un menu contextuel avec l'option "Group"
                            JPopupMenu popupMenu = new JPopupMenu();
                            JMenuItem groupMenuItem = new JMenuItem("Group");
                            JMenuItem deGroupMenuItem = new JMenuItem("DeGroup");
                            JMenuItem edit = new JMenuItem("Edit");
                            edit.addActionListener(a -> {
                                JPopupMenu menuEdit = new JPopupMenu();
                                JMenuItem colorEditItem = new JMenuItem("edit color");
                                JMenuItem positionEditItem = new JMenuItem("edit position");
                                menuEdit.add(colorEditItem);
                                menuEdit.add(positionEditItem);

                                // couleur
                                Color color = new Color(c.getShape().getColor());
                                Color selectedColor = JColorChooser.showDialog(AwtConcreteWhiteBoard.this, "Choose a color", color);
                                if (selectedColor != null) {
                                    System.out.println(selectedColor);
                                    String couleur = Integer.toHexString(selectedColor.getRGB()).substring(2);
                                    System.out.println("la couleur final" + couleur);
                                    Command co = new ColorShapeCommand(c.getShape(), Integer.parseInt(couleur, 16));
                                    try {
                                        builder.executeCommand(co);
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                            popupMenu.add(groupMenuItem);
                            popupMenu.add(deGroupMenuItem);
                            popupMenu.add(edit);
                            popupMenu.show(AwtConcreteWhiteBoard.this, e.getX(), e.getY());
                            break;
                        }
                    }
                }
            }

            public void mousePressed(MouseEvent e) {
                if(selectedShape==null){
                    for (ToolGroupComponent c : builder.whiteboardContent.getShapes()) {
                        if (c.getShape().IsArea(e.getX(),e.getY())) {
                            selectedTool = c;
                            selectedShape = c.getShape();
                            break;
                        }

                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (selectedShape!=null){
                    selectedShape.setPosition(e.getX(), e.getY());
                    Command command = new DragShapCommand(selectedTool, e.getX(), e.getY(), builder.whiteboardContent);
                    try {
                        builder.executeCommand(command);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    selectedShape = null;

                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (selectedShape != null) {
                    selectedShape.setPosition(e.getX(), e.getY());
                    repaint();
                }
            }
        });
    }




    @Override
    public void paint(Graphics g) {
        IRenderer r = new AwtRenderer(g);
        this.renderer = r;
        super.paint(g);
        for (ToolGroupComponent c : this.builder.whiteboardContent.getShapes()) {
            c.getShape().setRenderer(r);
            c.getShape().draw();
        }
    }

}
