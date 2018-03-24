package org.usfirst.frc.team4637.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveToSameSideSwitchOuter extends CommandGroup {
	
	/** From left side to left switch*/
    public DriveToSameSideSwitchOuter(boolean shootFromLeft) {
    	
    	// Allow the caller to invoke the mirror image of this motion depending on which side of the field and which starting slot we're in
    	double sgn = shootFromLeft ? 1.0 : -1.0;
    	// Center of robot starts out at 20 inches out from start
    	// Goal position is rotated toward switch, at the end of the switch
    	//addSequential(new RaiseArmToLimit());
    	
    	addSequential(new DriveFixedDistance(60.0));
    	//addSequential(new DriveRotate(-90.0 * sgn));
    	//addSequential(new TiltGrabber(true));
    	//addSequential(new DriveFixedDistance(36.0));
       	//addSequential(new DriveRotate(-45.0 * sgn));
       	
       	//addSequential(new DriveFixedDistance(20.0));
       	
       	//addSequential(new DriveRotate(-90.0 * sgn));
       	// Tilt shooter down?
       	//addSequential(new TiltGrabber(true));
       	//addSequential(new DriveFixedDistance(18.0));
       	//addSequential(new LowerArmToEjectPosFromRaised());
       	
       	//addSequential(new EjectBox(2.0));
    }
}
