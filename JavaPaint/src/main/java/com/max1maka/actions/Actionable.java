package com.max1maka.actions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.canvas.GraphicsContext;
import org.example.Figure;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Actionable {
    //сериализация фигур в файл в формате Json
    default void saveFile(List<Figure> figures, File file){
        Gson gson = new Gson();
        String json;
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Figure fig: figures){
            json = gson.toJson(fig);
            try {
                writer.write(fig.getFigureType() + "\n");
                writer.write(json + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //десериaлизация фигуры
    default List<Figure> openFile(File file, Map<Integer, Class> mapTypes) throws IOException {
        List<Figure> figures = new ArrayList<>();
        int figureType = 0;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        Figure figure = null;
        int k = 1;
        String line = reader.readLine();
        while (line != null){
            if (k % 2 != 0){
                figureType = Character.getNumericValue(line.charAt(0));
            } else {
                figure = (Figure) gson.fromJson(line, mapTypes.get(figureType));
                figures.add(figure);
            }
            line = reader.readLine();
            k++;
        }

        return figures;
    }

    //отмена последнего действия
     default void undo(List<Figure> figures, List<Figure> deletedFigures, GraphicsContext graphicsContextDraw){
         if (figures.size() > 0) {
             graphicsContextDraw.clearRect(0, 0, 800, 640);
             int temp = (figures.get(figures.size() - 1).isClassFilled()) ? 1 : 2;
             for (int i = 0; i < figures.size() - temp; i++) {
                 figures.get(i).redraw(graphicsContextDraw);
             }
             deletedFigures.add(figures.get(figures.size() - temp));
             if (temp == 2){
                 figures.remove(figures.get(figures.size() - 2));
             }
             figures.remove(figures.get(figures.size() - 1));
         }
    }

    //повтор последнего действия
    default void redo(List<Figure> figures, List<Figure> deletedFigures, GraphicsContext graphicsContextDraw){
        if (deletedFigures.size() > 0) {
            deletedFigures.get(deletedFigures.size() - 1).redraw(graphicsContextDraw);
            figures.add(deletedFigures.get(deletedFigures.size() - 1));
            deletedFigures.remove(deletedFigures.size() - 1);
        }
    }

}
