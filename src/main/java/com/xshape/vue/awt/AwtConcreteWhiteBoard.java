package com.xshape.vue.awt;


import com.xshape.modele.*;
import com.xshape.modele.Goupage.ToolGroupComponent;
import com.xshape.modele.Goupage.ToolGroupComposite;
import com.xshape.modele.Rectangle;
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

    private ToolGroupComponent group;
    AwtBuilder builder;


    public IRenderer getRenderer() {
        return renderer;
    }



    public AwtConcreteWhiteBoard(AwtApplication app, int x, int y, int width, int height, AwtBuilder builder) {
        super(app, x, y, width, height);
        app.add(this);
        this.builder=builder;
        this.group = new ToolGroupComposite();
        addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    System.out.println("ohhhhhhhhhhhhhhhhhhhhhhhh");
                    for (ToolGroupComponent c : builder.whiteboardContent.getShapes()) {
                        if (c.getShape().IsArea(e.getX(), e.getY())) {
                            // Créer un menu contextuel avec l'option "Group"
                            PopupMenu popupMenu = new PopupMenu();
                            MenuItem groupMenuItem = new MenuItem("Group");
                            MenuItem deGroupMenuItem = new MenuItem("DeGroup");
                            MenuItem editMenuItem = new MenuItem("Edit");
                            popupMenu.add(groupMenuItem);
                            popupMenu.add(deGroupMenuItem);
                            popupMenu.add(editMenuItem);
                            editMenuItem.addActionListener(ev -> {
                                Menu editMenu = new Menu("Edit");
                                MenuItem colorEditItem = new MenuItem("Edit Color");
                                MenuItem positionEditItem = new MenuItem("Edit Radius");
                                MenuItem slideEditItem = new MenuItem("Edit Slides");
                                MenuItem heightEditItem = new MenuItem("Edit Height");
                                MenuItem widthEditItem = new MenuItem("Edit Width");
                                editMenu.add(colorEditItem);
                                editMenu.add(positionEditItem);
                                editMenu.add(slideEditItem);
                                editMenu.add(heightEditItem);
                                editMenu.add(widthEditItem);
                                PopupMenu popupMenu1 = new PopupMenu();
                                popupMenu1.add(editMenu);
                                add(popupMenu1);
                                popupMenu1.show(e.getComponent(), e.getX(), e.getY());
                                if (c.getShape() instanceof Polygone) {
                                    positionEditItem.addActionListener(eventPosition -> {
                                        aux("Radius", c);
                                    });
                                    slideEditItem.addActionListener(eventSlide -> {
                                        aux("Slide", c);
                                    });
                                } else if (c.getShape() instanceof Rectangle) {
                                    heightEditItem.addActionListener(eventHeight -> {
                                        aux("Height", c);
                                    });
                                    widthEditItem.addActionListener(eventWidth -> {
                                        aux("Width", c);
                                    });
                                }
                                colorEditItem.addActionListener(eventColor -> {
                                    Color color = JColorChooser.showDialog(null, "Select a color", null);
                                    if (color != null) {
                                        int rgb = color.getRGB();
                                        ColorShapeCommand co = new ColorShapeCommand(c.getShape(), rgb);
                                        try {
                                            builder.executeCommand(co);
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                });
                            });
                            /*add(popupMenu);
                            popupMenu.show(e.getComponent(), e.getX(), e.getY());*/

                            groupMenuItem.addActionListener(a->{
                                if (!group.contains(c.getShape())){
                                    group.add(c);
                                } else {
                                    System.out.println("le shape existe déja dans le groupe");
                                }
                                repaint();
                            });
                            deGroupMenuItem.addActionListener(a->{
                                System.out.println("to be deleted");
                                //groupSelectedObjects(selectedShapes);
                                group.remove(c);
                                repaint();
                            });

                            popupMenu.add(groupMenuItem);
                            popupMenu.add(deGroupMenuItem);
                            popupMenu.add(editMenuItem);
                            add(popupMenu);
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



    private void aux(String txt, ToolGroupComponent shape) {
        String value = JOptionPane.showInputDialog("Set " + txt);
        if (value != null && isNumeric(value)) {
            switch (txt) {
                case "Slide":
                    SlidePolygoneCommand t = new SlidePolygoneCommand(shape.getShape(), Double.parseDouble(value));
                    try {
                        builder.executeCommand(t);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "Radius":
                    RadiusPolygoneCommand r = new RadiusPolygoneCommand(shape.getShape(), Double.parseDouble(value));
                    try {
                        builder.executeCommand(r);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "Height":
                    HeightRectangleCommand h = new HeightRectangleCommand(shape.getShape(), Double.parseDouble(value));
                    try {
                        builder.executeCommand(h);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "Width":
                    WidthRectangleCommand w = new WidthRectangleCommand(shape.getShape(), Double.parseDouble(value));
                    try {
                        builder.executeCommand(w);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
            }
        }
    }

    private boolean isNumeric(String txt){
        if (txt == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(txt);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
