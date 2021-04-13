package org.example2;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.Figure;

import java.util.List;

import static java.lang.Double.NaN;

public class FigureTrapezoid extends Figure {
    @Override
    public void preview(double[] x, double[] y, List<Double[]> lastCoords, GraphicsContext gc) {
        gc.clearRect(0, 0, 800, 640);
        gc.setStroke(Color.web(getColorS()));
        gc.setLineWidth(getLineThickness());

        double[] xs = new double[] {x[0], x[0] + (x[1] - x[0]) / 3, x[0] + (x[1] - x[0]) / 3 * 2, x[1]};
        double[] ys = new double[] {y[1], y[0], y[0], y[1]};
        gc.strokePolygon(xs, ys, 4);
    }


    @Override
    public double[] draw(double[] x, double[] y, GraphicsContext gc) {
        setFigureType(8);
        isClassFilled = true;
        gc.setStroke(Color.web(getColorS()));
        gc.setLineWidth(getLineThickness());

        double[] xs = new double[] {x[0], x[0] + (x[1] - x[0]) / 3, x[0] + (x[1] - x[0]) / 3 * 2, x[1]};
        double[] ys = new double[] {y[1], y[0], y[0], y[1]};
        gc.strokePolygon(xs, ys, 4);

        setCoordinades(xs, ys);
        isClassFilled = true;
        return new double[] {NaN, NaN};
    }

    @Override
    public void redraw(GraphicsContext gc) {
        gc.setStroke(Color.web(getColorS()));
        gc.setLineWidth(getLineThickness());

        double[] x = new double[coordinades.get(0)[0].length];
        double[] y = new double[coordinades.get(0)[0].length];
        for (int i = 0; i < coordinades.get(0)[0].length; i++) {
            x[i] = coordinades.get(0)[0][i];
            y[i] = coordinades.get(0)[1][i];
        }
        gc.strokePolygon(x, y, 4);
    }
}

