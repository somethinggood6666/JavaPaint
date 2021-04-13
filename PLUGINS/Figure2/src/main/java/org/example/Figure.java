package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Figure implements Serializable {
    private int figureType;
    private String colorS;
    private int lineThickness;
    protected boolean isClassFilled = false;
    protected List<double[][]> coordinades = new ArrayList();

    public Figure() {
    }

    public int getFigureType() {
        return this.figureType;
    }

    public void setFigureType(int figureType) {
        this.figureType = figureType;
    }

    public void setLineThickness(int lineThickness) {
        this.lineThickness = lineThickness;
    }

    public abstract double[] draw(double[] var1, double[] var2, GraphicsContext var3);

    public abstract void redraw(GraphicsContext var1);

    public abstract void preview(double[] var1, double[] var2, List<Double[]> var3, GraphicsContext var4);

    public boolean isClassFilled() {
        return this.isClassFilled;
    }

    public void setIfsClassNew() {
        this.isClassFilled = false;
    }

    protected void setCoordinades(double[] x, double[] y) {
        double[][] coord = new double[][]{x, y};
        this.coordinades.clear();
        this.coordinades.add(coord);
    }

    public void setBorderColor(Color borderColor) {
        this.colorS = toRGBCode(borderColor);
    }

    public String getColorS() {
        return this.colorS;
    }

    protected double getLineThickness() {
        return (double)this.lineThickness;
    }

    protected static String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X", (int)(color.getRed() * 255.0D), (int)(color.getGreen() * 255.0D), (int)(color.getBlue() * 255.0D));
    }
}
