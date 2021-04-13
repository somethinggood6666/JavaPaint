package com.max1maka.figures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.Figure;

import java.util.List;

import static java.lang.Double.NaN;

public class FigureSquare extends Figure {


    @Override
    public void preview(double[] x, double[] y, List<Double[]> lastCoords, GraphicsContext gc) {
        gc.clearRect(0, 0, 800, 640);
        gc.setStroke(Color.web(getColorS()));
        gc.setLineWidth(getLineThickness());
        double tempX = (x[1] - x[0] > 0) ? x[1] - x[0] : x[0] - x[1];
        double tempY = (y[1] - y[0] > 0) ? y[1] - y[0] : y[0] - y[1];
        double startX = Math.min(x[0], x[1]);
        double startY = Math.min(y[0], y[1]);
        gc.strokeRect(startX, startY, tempX, tempY);

    }

    @Override
    public double[] draw(double[] x, double[] y, GraphicsContext gc) {
        setFigureType(6);
        isClassFilled = true;
        gc.setStroke(Color.web(getColorS()));
        gc.setLineWidth(getLineThickness());
        double tempX = (x[1] - x[0] > 0) ? x[1] - x[0] : x[0] - x[1];
        double tempY = (y[1] - y[0] > 0) ? y[1] - y[0] : y[0] - y[1];
        double startX = Math.min(x[0], x[1]);
        double startY = Math.min(y[0], y[1]);
        gc.strokeRect(startX, startY, tempX, tempY);

        setCoordinades(new double[] {x[0], x[1] - x[0]}, new double[] {y[0], y[1] - y[0]});
        isClassFilled = true;
        return new double[] {NaN, NaN};
    }

    @Override
    public void redraw(GraphicsContext gc) {
        gc.setStroke(Color.web(getColorS()));
        gc.setLineWidth(getLineThickness());
        gc.strokeRect(coordinades.get(0)[0][0], coordinades.get(0)[1][0],
                coordinades.get(0)[0][1], coordinades.get(0)[1][1]);

    }

}
