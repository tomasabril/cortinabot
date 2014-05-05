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
	
	double direction = 1;
	int i=0;

	public void run() {
		// Initialization of the robot should be put here

		setColors(Color.green,Color.red,Color.white); // body,gun,radar

		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);

		// Robot main loop
		while(true) {

			setTurnRadarRightRadians(2*Math.PI);
			execute();
		    scan();
			
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		setAhead(100*direction);	
		i++;
		if( i>20 ) {
			direction*=-1;
			i=0;
		}
		// calculate firepower based on distance
		double fp = Math.min(500 / e.getDistance(), 3);
		double firePower = Math.min( fp , getEnergy()/10 );	
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
		direction*=-1;
		setAhead(160*direction);
		setTurnRightRadians(Math.PI/4);
	}
	public void onHitRobot(HitRobotEvent e) {
		direction*=-1;
	}
	
	public void WinEvent() {
		while(true) {
			setColors(Color.red,Color.red,Color.red); 
			setTurnGunRight(180);
			setTurnLeft(180);
			execute();
			setColors(Color.blue,Color.blue,Color.blue); 
		}
	}
}
