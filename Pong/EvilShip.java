import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class EvilShip extends Circle {

	private double move = .05;
	private double heading = Math.random()*2*Math.PI;
	private boolean alive = true;
	private int errorREsize = 0;
	public EvilShip() {
		setRadius(15);
		setFill(Color.RED);

	}

	public void move() {
		try {
			if (getScene().getHeight()< getLayoutY() + (Math.sin(heading))*move+ getRadius() 
			|| getLayoutY() + (Math.sin(heading))*move - getRadius()  < 0 
			|| getScene().getWidth()< getLayoutX() + (Math.cos(heading))*move + getRadius()  
			|| getLayoutX() + (Math.cos(heading))*move - getRadius()  < 0) {
				heading = Math.random()*2*Math.PI;
				/*	System.out.println(  getScene().getHeight() + "  <  " + getLayoutY() + getRadius() );
				System.out.println(  getLayoutY() + getRadius() + "  <  " + 0 );
				System.out.println(  getScene().getWidth() + "  <  " + getLayoutX() + getRadius() );
				System.out.println(  getLayoutX() + getRadius() + "  <  " + 0);
				System.out.println(  "DONE");*/
				if(errorREsize++ % 100 == 0) {
					if (getScene().getHeight()< getLayoutY() + (Math.sin(heading))*move+ getRadius()) {
						setLayoutY(getScene().getHeight() - getRadius());
					}
					if (getScene().getWidth()< getLayoutX() + (Math.cos(heading))*move + getRadius()) {
						setLayoutX(getScene().getWidth() - getRadius());
					}
				}
			}else {
				setLayoutY(getLayoutY() + (Math.sin(heading))*move);
				setLayoutX(getLayoutX() + (Math.cos(heading))*move );
			}
		}catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void setinitLocation() {	
		Random rn = new Random();
		double x;
		double y ;
		do {
			x = rn.nextInt((int) getScene().getWidth());
		}while ( getRadius() > x || x > getScene().getWidth() - getRadius() );
		do {
			y =rn.nextInt((int) getScene().getHeight()) ;
		}while ( getRadius() > y || y > getScene().getHeight() -getRadius() );

		setLayoutX(x);
		setLayoutY(y);
		//System.out.println( x + ":" +y);
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
