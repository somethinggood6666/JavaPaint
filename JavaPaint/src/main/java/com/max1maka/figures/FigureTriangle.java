package com.max1maka.figures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.Figure;

import java.util.List;

import static java.lang.Double.NaN;

public class FigureTriangle extends Figure {

    @Override
    public void preview(double[] x, double[] y, List<Double[]> lastCoords, GraphicsContext gc) {
        gc.clearRect(0, 0, 800, 640);
        gc.setStroke(Color.web(getColorS()));
        gc.setLineWidth(getLineThickness());

        double[] xs = new double[] {x[0], (x[1] - x[0]) / 2 + x[0], x[1]};
        double[] ys = new double[] {y[1], y[0], y[1]};
        gc.strokePolygon(xs, ys, 3);
    }

    @Override
    public double[] draw(double[] x, double[] y, GraphicsContext gc) {
        setFigureType(7);
        isClassFilled = true;
        gc.setStroke(Color.web(getColorS()));
        gc.setLineWidth(getLineThickness());
        double[] xs = new double[] {x[0], (x[1] - x[0]) / 2 + x[0], x[1]};
        double[] ys = new double[] {y[1], y[0], y[1]};
        gc.strokePolygon(xs, ys, 3);

        setCoordinades(xs, ys);
        isClassFilled = true;
        return new double[] {NaN, NaN};
    }

    @Override
    public void redraw(GraphicsContext gc) {
        gc.setStroke(Color.web(getColorS()));
        gc.setLineWidth(getLineThickness());
        double[] x = new double[3];
        double[] y = new double[3];
        for (int i = 0; i < 3; i++) {
            x[i] = coordinades.get(0)[0][i];
            y[i] = coordinades.get(0)[1][i];
        }
        gc.strokePolygon(x, y, 3);
    }

}
