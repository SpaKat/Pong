import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class EnemyShips {

	ArrayList<EvilShip> evilShips = new ArrayList<EvilShip>();

	public EnemyShips() {

	}

	public void move() {
		evilShips.forEach(e -> e.move());
	}

	public void load(Pane pane) {
		for (int i = 0; i < 100; i++) {
			EvilShip x = new EvilShip();
			x.setVisible(false);
			evilShips.add(x);
			pane.getChildren().add(x);
			x.setinitLocation();
			x.setVisible(true);
		}
	}

	public void remove(Pane pane) {
		evilShips.forEach(e ->{	
			pane.getChildren().remove(e);	
		});
		evilShips.clear();
	}

	public void kill(Circle circle, Pane pane) {
		evilShips.forEach(ship ->{
			if ( Math.abs(circle.getLayoutX() -ship.getLayoutX()) <(circle.getRadius() + ship.getRadius()-5) 
					&&Math.abs(circle.getLayoutY() -ship.getLayoutY()) <(circle.getRadius() + ship.getRadius()-5 ) ){
				pane.getChildren().remove(ship);
				ship.setAlive(false);
				//System.out.println((circle.getLayoutX() -ship.getLayoutX()) + " : " + (circle.getLayoutY() -ship.getLayoutY()) );
			}
		});
		for(int i = 0; i<evilShips.size(); i ++) {
			if (!evilShips.get(i).isAlive()) {
				evilShips.remove(evilShips.get(i));
				if (evilShips.size()<= 0) {
					Stage wonScreen = new Stage();
					wonScreen.setScene(new Scene(new VBox(new Text("YOU WON \nmaybe i dont know \nwhy are you still reading this\n...\n...\n...\n..\n..\n.\nSTOP!"))));
					wonScreen.show();
				}
			}
		}

	}

	
}

