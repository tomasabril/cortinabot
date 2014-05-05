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

		// if the gun is cool and we're pointed at the target, shoot!
		if ( getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 10 ) {
			setFire(firePower);
		}
			
		turnRadarRightRadians(Utils.normalRelativeAngle( getHeadingRadians() + e.getBearingRadians() - getRadarHeadingRadians() ));
		execute();

	}

	public void onHitByBullet(HitByBulletEvent e) {
		setTurnRightRadians(Utils.normalRelativeAngle( e.getBearingRadians() + Math.PI/2 ));
	}

	public void onHitWall(HitWallEvent e) {
		back(20);
		turnRight(90);
		ahead(150);
	}
	
	public void WinEvent() {
		while(true) {
			setTurnGunRight(180);
			setTurnLeft(180);
			execute();
		}
	}
}
