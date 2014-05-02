package cortinabot;
import robocode.*;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Tomasbot - a robot
 */
public class Tomasbot extends AdvancedRobot
{
	/**
	 * run: Tomasbot's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		setColors(Color.red,Color.white,Color.green); // body,gun,radar

		// Robot main loop
		while(true) {

			ahead(120);
			turnRadarRight(360);
			back(120);
			
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {

		// calculate firepower based on distance
		double firePower = Math.min(500 / e.getDistance(), 3);
		// calculate speed of bullet
		double bulletSpeed = 20 - firePower * 3;

		double absoluteBearing = getHeading() + e.getBearing();
		double eLateralVelocity = e.getVelocity() * Math.sin(e.getHeading() - absoluteBearing);
		double offset = Math.asin(eLateralVelocity/bulletSpeed);
		turnGunRight(normalize( absoluteBearing - getGunHeading() + offset ));

		//mira simples
		//turnGunRight(normalize( getHeading() - getGunHeading() + e.getBearing() ));

		// if the gun is cool and we're pointed at the target, shoot!
		if ( getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 10 ) {
			fire(firePower);
		}

	}

	public void onHitByBullet(HitByBulletEvent e) {
		turnRight(normalize( e.getBearing() + 90 ));
	}

	public void onHitWall(HitWallEvent e) {
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
