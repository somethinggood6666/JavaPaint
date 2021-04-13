package com.max1maka;

import com.max1maka.actions.Actionable;
import com.max1maka.figures.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.Figure;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Double.NaN;
import static java.lang.Double.isNaN;

public class PrimaryController implements Actionable {

    @FXML
    private Pane pane;

    @FXML
    private Canvas canvasDraw;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem menuClose;

    @FXML
    private MenuItem menuSave;

    @FXML
    private MenuItem menuOpen;

    @FXML
    private HBox hBox;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TextField brushSize;

    @FXML
    private GridPane gridInstruments;

    @FXML
    private ImageView imgCircle;

    @FXML
    private ImageView imgSquare;

    @FXML
    private ImageView imgLine;

    @FXML
    private ImageView imgTriangle;

    @FXML
    private ImageView imgMultiline;

    @FXML
    private ImageView imgMultiangle;

    @FXML
    private ImageView imgPolygon;

    @FXML
    private ImageView imgAdd;

    @FXML
    private Canvas canvasPreview;

    @FXML
    private ImageView imgTrapezoid;

    private List<Figure> deletedFigures = new ArrayList<>();
    private List<Figure> figures = new ArrayList<>();
    private Figure currentFigure;
    private List<Double[]> lastCoords = new ArrayList<>();
    private double[] coords = {NaN, NaN};

    private Map<Integer, Class> mapFigureTypes = new HashMap<>();

    @FXML
    public void initialize() {
        GraphicsContext graphicsContextDraw = canvasDraw.getGraphicsContext2D();
        GraphicsContext graphicsContextPreview = canvasPreview.getGraphicsContext2D();

        canvasPreview.setVisible(false);
        canvasDraw.setFocusTraversable(true);

        mapFigureTypes = initFigureTypes();

        imgCircle.setOnMouseClicked(event -> {
            figures.add(new FigureCircle());
            currentFigure = new FigureCircle();
            lastCoords.clear();
        });

        imgLine.setOnMouseClicked(event -> {
            figures.add(new FigureLine());
            currentFigure = new FigureLine();
            lastCoords.clear();
        });

        imgSquare.setOnMouseClicked(event -> {
            figures.add(new FigureSquare());
            currentFigure = new FigureSquare();
            lastCoords.clear();
        });

        imgMultiline.setOnMouseClicked(event -> {
            figures.add(new FigureMultiline());
            currentFigure = new FigureMultiline();
            lastCoords.clear();
        });

        imgTriangle.setOnMouseClicked(event -> {
            figures.add(new FigureTriangle());
            currentFigure = new FigureTriangle();
            lastCoords.clear();
        });

        imgPolygon.setOnMouseClicked(event -> {
            figures.add(new FigurePolygon());
            currentFigure = new FigurePolygon();
            lastCoords.clear();
        });

        imgMultiangle.setOnMouseClicked(event -> {
            figures.add(new FigureMultiangle());
            currentFigure = new FigureMultiangle();
            lastCoords.clear();
        });

        imgTrapezoid.setOnMouseClicked(event -> {
            if (isClass("org.example2.FigureTrapezoid")) {
                try {
                    figures.add((Class.forName("org.example2.FigureTrapezoid").asSubclass(Figure.class).getConstructor().newInstance()));
                    currentFigure = (Class.forName("org.example2.FigureTrapezoid").asSubclass(Figure.class).getConstructor().newInstance());
                } catch (ClassNotFoundException | NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                lastCoords.clear();
            }
        });

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //когда мышь начинают перетаскивать с зажатой ЛКМ
        canvasDraw.setOnDragDetected(mouseEvent -> {
            if (isNaN(coords[0])) {
                coords[0] = mouseEvent.getX();
                coords[1] = mouseEvent.getY();
            }
        });

        //когда ведут мышью по экрану с зажатой ЛКМ
        canvasDraw.setOnMouseDragged(event -> {
            if (currentFigure != null) {
                currentFigure.setBorderColor(colorPicker.getValue());
                currentFigure.setLineThickness(Integer.parseInt(brushSize.getText()));
                canvasPreview.setVisible(true);
                currentFigure.preview(new double[]{coords[0], event.getX()},
                        new double[]{coords[1], event.getY()}, lastCoords,
                        graphicsContextPreview);
            }
        });

        //когда отпущена ЛКМ
        canvasDraw.setOnMouseReleased(dragEvent -> {
            if (currentFigure != null) {
                if (figures.size() > 0 && figures.get(figures.size() - 1).isClassFilled()) {
                    makeLastFigureCopy();
                }
                lastCoords.add(new Double[]{dragEvent.getX(), dragEvent.getY()});
                figures.get(figures.size() - 1).setBorderColor(colorPicker.getValue());
                figures.get(figures.size() - 1).setLineThickness(Integer.parseInt(brushSize.getText()));
                canvasPreview.setVisible(false);
                coords = figures.get(figures.size() - 1).draw(new double[]{coords[0], dragEvent.getX()},
                        new double[]{coords[1], dragEvent.getY()},
                        graphicsContextDraw);
                canvasDraw.requestFocus();
                if (deletedFigures.size() != 0) {
                    deletedFigures.clear();
                }
            }
        });

        //когда нажата клавиша:
        canvasDraw.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                coords[0] = NaN;
                coords[1] = NaN;
                lastCoords.clear();
                makeLastFigureCopy();
            }
            if (keyEvent.getCode() == KeyCode.HOME) {
                undo(figures, deletedFigures, graphicsContextDraw);
            }
            if (keyEvent.getCode() == KeyCode.PAGE_UP) {
                redo(figures, deletedFigures, graphicsContextDraw);
            }
        });

        menuSave.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save file as");
            //file - Наш файл для сохранения
            File file = fileChooser.showSaveDialog(Stage.getWindows().get(0));

            if (file != null) {
                saveFile(figures, file);
            }
        });

        menuOpen.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open file");
            //file - Наш файл для сохранения
            File file = fileChooser.showOpenDialog(Stage.getWindows().get(0));

            if (file != null) {
                try {
                    figures = openFile(file, mapFigureTypes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            graphicsContextDraw.clearRect(0, 0, 800, 640);
            for (int i = 0; i < figures.size(); i++) {
                figures.get(i).redraw(graphicsContextDraw);
            }
        });
    }

    public void makeLastFigureCopy() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream ous = null;
        try {
            ous = new ObjectOutputStream(baos);
            ous.writeObject(figures.get(figures.size() - 1));
            ous.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //сохранили состояние объекта в поток и закрыли его

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //создаем копию последней фигуры и инициализируем состоянием предыдущей
        Figure newFigure = null;
        try {
            newFigure = (Figure) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ;
        figures.add(newFigure);
        figures.get(figures.size() - 1).setIfsClassNew();
    }

    //каждой фигуре ставим в соответствие номер для возможной сериализации
    private Map<Integer, Class> initFigureTypes() {
        Map<Integer, Class> map = new HashMap<>();
        map.put(1, FigureCircle.class);
        map.put(2, FigureLine.class);
        map.put(3, FigureMultiangle.class);
        map.put(4, FigureMultiline.class);
        map.put(5, FigurePolygon.class);
        map.put(6, FigureSquare.class);
        map.put(7, FigureTriangle.class);

        if (isClass("org.example2.FigureTrapezoid")) {
            try {
                map.put(8, Class.forName("org.example2.FigureTrapezoid"));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    private boolean isClass(String classname) {
        try {
            Class.forName(classname.trim());
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}
