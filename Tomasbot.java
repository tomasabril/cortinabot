package cortinabot;
import robocode.*;
import robocode.util.Utils;
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

		setColors(Color.blue,Color.white,Color.green); // body,gun,radar

		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);

		// Robot main loop
		while(true) {

			setAhead(120);
			setTurnRadarRight(360);
			scan();
			//execute();
			back(120);
			
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		// calculate firepower based on distance
		double firePower = Math.min(500 / e.getDistance(), 3);
		double bulletSpeed = 20 - firePower * 3;

		double eAbsoluteBearing = getHeadingRadians() + e.getBearingRadians();
		double eLateralVelocity = e.getVelocity() * Math.sin(e.getHeadingRadians() - eAbsoluteBearing);
		double offset = Math.asin(eLateralVelocity/bulletSpeed);
		setTurnGunRightRadians(Utils.normalRelativeAngle( eAbsoluteBearing - getGunHeadingRadians() + offset ));

		//mira simples
		//turnGunRight(normalize( getHeading() - getGunHeading() + e.getBearing() ));

		// if the gun is cool and we're pointed at the target, shoot!
		if ( getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 10 ) {
			setFire(firePower);
		}
		execute();
			
		turnRadarRightRadians(Utils.normalRelativeAngle( getHeadingRadians() + e.getBearingRadians() - getRadarHeadingRadians() ));

	}

	public void onHitByBullet(HitByBulletEvent e) {
		setTurnRight(normalize( e.getBearing() + 90 ));
	}

	public void onHitWall(HitWallEvent e) {
		back(20);
		turnRight(90);
		ahead(150);
	}
	
	// normalizes a bearing to between +180 and -180
	double normalize(double angle) {
		while (angle >  180){ angle -= 360;}
		while (angle < -180){ angle += 360;}
		return angle;
	}
	
	public void WinEvent() {
		while(true) {
			turnGunRight(180);
			turnLeft(180);
		}
	}
}
