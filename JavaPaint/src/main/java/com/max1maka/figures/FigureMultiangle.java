package com.max1maka.figures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.Figure;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.isNaN;

public class FigureMultiangle extends Figure {
    private List<Double> xs = new ArrayList<>();
    private List<Double> ys = new ArrayList<>();
    private int i;

    @Override
    public double[] draw(double[] x, double[] y, GraphicsContext gc) {
        setFigureType(3);
        isClassFilled = true;
        gc.setFill(Color.web(getColorS()));
        if (isNaN(x[0])){
            xs.clear();
            ys.clear();
            i = 1;
        }
        xs.add(x[1]);
        ys.add(y[1]);
        double[] coordX = makeAnArray(xs);
        double[] coordY = makeAnArray(ys);
        if (i < 3) {
            gc.fillOval(x[1], y[1], 1, 1);
        } else {
            gc.fillPolygon(coordX, coordY, coordY.length);
        }
        i++;
        setCoordinades(coordX, coordY);
        return new double[] {x[1], y[1]};
    }


    @Override
    public void preview(double[] x, double[] y,List<Double[]> lastCoords,  GraphicsContext gc) {
        gc.clearRect(0, 0, 800, 640);
        gc.setFill(Color.web(getColorS()));
        xs.clear();
        ys.clear();
        for (int j = 0; j < lastCoords.size(); j++) {
            xs.add(lastCoords.get(j)[0]);
            ys.add(lastCoords.get(j)[1]);
        }
        xs.add(x[1]);
        ys.add(y[1]);
        double[] coordX = makeAnArray(xs);
        double[] coordY = makeAnArray(ys);
        gc.fillPolygon(coordX, coordY, coordY.length);
    }

    @Override
    public void redraw(GraphicsContext gc) {
        gc.setFill(Color.web(getColorS()));
        gc.setLineWidth(getLineThickness());
        double[] x = new double[coordinades.get(0)[0].length];
        double[] y = new double[coordinades.get(0)[0].length];

        for (int m = 0; m < coordinades.get(0)[0].length; m++) {
            x[m] = coordinades.get(0)[0][m];
            y[m] = coordinades.get(0)[1][m];
            if (m > 1){
                gc.fillPolygon(x, y, (m + 1));
            }
        }

    }


    private double[] makeAnArray(List<Double> coords) {
        double[] arr = new double[coords.size()];
        for (int j = 0; j < coords.size(); j++) {
            arr[j] = coords.get(j);
        }
        return arr;
    }
}
