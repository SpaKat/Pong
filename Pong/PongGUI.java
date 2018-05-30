import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PongGUI extends Application {
	final BooleanProperty left = new SimpleBooleanProperty(false);
	final BooleanProperty right = new SimpleBooleanProperty(false);
	final BooleanProperty up = new SimpleBooleanProperty(false);
	final BooleanProperty down = new SimpleBooleanProperty(false);
	final BooleanProperty pageUp = new SimpleBooleanProperty(false);
	final BooleanProperty pageDown = new SimpleBooleanProperty(false);
	ArrayList<Pane> panes = new ArrayList<>();
	int level = 0;
	@Override
	public void start(Stage stage) {
		StackPane  root = new StackPane();


		Circle circle = new Circle(15, Color.HOTPINK);
		circle.setLayoutX(circle.getRadius());
		circle.setLayoutY(circle.getRadius());

		for (int i = 0; i < 50; i++) {
			Pane pane = new Pane();
			pane.setVisible(false);
			pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
			panes.add(pane);
		}
		panes.forEach(e -> root.getChildren().add(e));
		panes.get(level).getChildren().add(circle);
		panes.get(level).setVisible(true);
		Scene scene = new Scene(root,500,500);

		timelineKeysPress(circle);
		onPressed(scene);
		onReleased(scene);

		scene.setFill(Color.BLACK);
		stage.setScene(scene);
		stage.show();
	}
	private void timelineKeysPress(Circle circle) {
		EnemyShips ships = new EnemyShips();
		Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1), e ->{
			if (up.getValue()) {
				circle.setLayoutY(circle.getLayoutY() - .1);
			}
			if (down.getValue()) {
				circle.setLayoutY(circle.getLayoutY() + .1);
			}
			if (left.getValue()) {
				circle.setLayoutX(circle.getLayoutX() - .1);
			}
			if (right.getValue()) {
				circle.setLayoutX(circle.getLayoutX() + .1);
			}
			if (circle.getLayoutY() > circle.getScene().getHeight()-circle.getRadius()) {
				circle.setLayoutY(circle.getScene().getHeight()-circle.getRadius());
			}
			if (circle.getLayoutX() > circle.getScene().getWidth()-circle.getRadius()) {
				circle.setLayoutX(circle.getScene().getWidth()-circle.getRadius());
			}
			if (circle.getLayoutY() - .1 < circle.getRadius()) {
				circle.setLayoutY(circle.getRadius());
			}
			if (circle.getLayoutX() - .1 < circle.getRadius() ) {
				circle.setLayoutX(circle.getRadius());
			}

			ships.kill(circle,panes.get(level));
		}));
		timeline.play();
		Timeline levelS = new Timeline();
		levelS.setCycleCount(Timeline.INDEFINITE);
		levelS.getKeyFrames().add(new KeyFrame(Duration.millis(50), e -> {
			if (pageUp.getValue()) {
				if(level + 1 <50 ) {
					panes.get(level).getChildren().remove(circle);
					panes.get(level).setVisible(false);
					ships.remove(panes.get(level));
					level += 1;
					panes.get(level).getChildren().add(circle);
					panes.get(level).setVisible(true);
					ships.load(panes.get(level));
					circle.setRadius(circle.getRadius() + 1);
				}
			}
			if (pageDown.getValue()) {
				if(level - 1 >=0 ) {
					panes.get(level).getChildren().remove(circle);
					panes.get(level).setVisible(false);
					ships.remove(panes.get(level));
					level -= 1;
					panes.get(level).getChildren().add(circle);
					panes.get(level).setVisible(true);
					ships.load(panes.get(level));
					circle.setRadius(circle.getRadius() - 1);
				}
			}
		}));
		levelS.play();
		Timeline enemys = new Timeline();
		enemys.setCycleCount(Timeline.INDEFINITE);

		enemys.getKeyFrames().add(new KeyFrame(Duration.millis(1), enemy -> {
			ships.move();
		}));
		enemys.play();
	}
	private void onReleased(Scene scene) {
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				switch (key.getCode()) {
				case UP:
					up.set(false);
					break;
				case DOWN:
					down.set(false);
					break;
				case LEFT:
					left.set(false);
					break;
				case RIGHT:
					right.set(false);
					break;
				case W:
					pageUp.set(false);
					break;
				case S:
					pageDown.set(false);
					break;
				default:
					break;
				}
			}
		});
	}
	private void onPressed(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				switch (key.getCode()) {
				case UP:
					up.set(true);
					break;
				case DOWN:
					down.set(true);
					break;
				case LEFT:
					left.set(true);
					break;
				case RIGHT:
					right.set(true);
					break;
				case W:
					pageUp.set(true);
					break;
				case S:
					pageDown.set(true);
					break;
				default:
					break;
				}
			}
		});
	}
	public static void main(String[] args) {
		launch(args);
	}

}
