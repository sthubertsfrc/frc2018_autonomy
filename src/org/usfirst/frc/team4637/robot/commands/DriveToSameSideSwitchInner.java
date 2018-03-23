package org.usfirst.frc.team4637.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToSameSideSwitchInner extends CommandGroup {

    public DriveToSameSideSwitchInner(boolean turnRight) {
        	
        	// Allow the caller to invoke the mirror image of this motion depending on which side of the field and which starting slot we're in
        	double sgn = turnRight ? 1.0 : -1.0;
        	// Center of robot starts out at 20 inches out from start
        	// Goal position is rotated toward switch, at the side of switch closest to the robot's starting position
        	//addSequential(new RaiseArmToLimit());
        	addSequential(new DriveFixedDistance(48.0));
        	addSequential(new DriveRotate(-45.0 * sgn));
        	addSequential(new DriveFixedDistance(38.0));
           	addSequential(new DriveRotate(45.0 * sgn));
           	addSequential(new DriveFixedDistance(35.0));
           	//addSequential(new LowerArmToEjectPosFromRaised());
           	// Tilt shooter down?
           	addSequential(new EjectBox(2.0));
        }
}
