package game.view;

import game.controller.Controller;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.io.IOException;


public class CellView extends StackPane
{
    private  Controller controller;
    private final Label label;
    private Label timeSecLabel;
    private Rectangle rect1;
    private Rectangle rect;


    public CellView(Controller controller, int i, int j)
    {

        this.controller = controller;
        this.label = new Label();
        this.label.setText(controller.onClickedCellBombsAround(i,j));
        this.relocate(i*50, j*50);

        this.setWidth(50);
        this.setHeight(50);



        Rectangle rect1 = new Rectangle();
        rect1.setWidth(50);
        rect1.setHeight(50);
        rect1.setStrokeWidth(4);
        rect1.setStroke(Color.DIMGRAY);
        rect1.setFill(Color.WHITE);


        //timeView.showTimeLabel(controller.getGameTickingSek());
        //                                                                                                                                                                              timeView.showTimeLabel(controller.getGameTickingSek());
        this.getChildren().addAll(rect1,label);
        if (controller.BombChecking(i,j))
        {
            Circle circle = new Circle(10);
            circle.setFill(Color.RED);
            circle.relocate(15,15);
            this.getChildren().add(circle);
        }


        rect = new Rectangle();
        rect.setWidth(50);
        rect.setHeight(50);
        rect.setStrokeWidth(4);
        rect.setStroke(Color.DIMGRAY);
        rect.setFill(Color.BLACK);
        this.getChildren().add(rect);

        Polygon flag = new Polygon();
        flag.setFill(Color.CYAN);
        flag.getPoints().addAll(0.0, 0.0,
                20.0, 10.0,
                10.0, 20.0);


        setOnMouseClicked(mouseEvent -> {
            System.out.println("clicked on cell");
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && controller.BombChecking(i,j))
            {
                var a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Game over");
                a.setContentText("Game is over!");
                a.showAndWait();
                controller.gameOnClickedTime(i,j);
                controller.setIsOpen(i,j);
                controller.removeAllRects();
            }

            if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && !controller.isFlagged(i,j))
            {

                controller.gameOnClickedTime(i,j);
                controller.onClickedCellBombsAround(i,j);
                controller.setIsOpen(i,j);
                controller.removeRects();
            }

            if(mouseEvent.getButton().equals(MouseButton.SECONDARY) && !controller.isFlagged(i,j) && !controller.isOpen(i,j) )
            {
                controller.setFlag(i,j);
                this.getChildren().add(flag);
            }
            else if (mouseEvent.getButton().equals(MouseButton.SECONDARY) && controller.isFlagged(i,j))
            {
                controller.removeFlag(i,j);
                this.getChildren().remove(flag);
            }

        });

    }

    public void removeRect(){
        this.getChildren().remove(getRect());
    }

    public Rectangle getRect() {
        return rect;
    }
}
