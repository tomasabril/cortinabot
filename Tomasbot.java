package cortinabot;
import robocode.*;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Tomasbot - a robot by (your name here)
 */
public class Tomasbot extends Robot
{
	/**
	 * run: Tomasbot's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		setColors(Color.green,Color.red,Color.green); // body,gun,radar

		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			ahead(120);
			turnRadarRight(360);
			back(120);

		//	turnLeft(90);
		//	ahead(100);
			
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {

		turnGunRight(normalize( getHeading() - getGunHeading() + e.getBearing() ));
		fire( Math.min(400 / e.getDistance(), 3) );
		//ahead(30);
	}

	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		//back(10);
		turnRight(normalize( e.getBearing() + 90 ));
		//turnLeft(90);
		//ahead(40);
	}

	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		back(20);
		turnRight(90);
		ahead(150);

	}
	// normalizes a bearing to between +180 and -180
	double normalize(double angle) {
		while (angle >  180) angle -= 360;
		while (angle < -180) angle += 360;
		return angle;
	}
}
