package org.usfirst.frc.team4637.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToSwitchSideFromCenter extends CommandGroup {

    public DriveToSwitchSideFromCenter(boolean toLeftSwitch) {
    	// Allow the caller to invoke the mirror image of this motion depending on which side of the field and which starting slot we're in
    	double sgn = toLeftSwitch ? 1.0 : -1.0;
    	// Center of robot starts out at 20 inches out from start
    	// Goal position is rotated toward switch, at the side of switch closest to the robot's starting position
    	//addSequential(new RaiseArmToLimit());
    	addSequential(new DriveFixedDistance(24.0));
    	addSequential(new DriveRotate(60.0 * sgn));
    	addSequential(new DriveFixedDistance(56.0));
       	addSequential(new DriveRotate(-60.0 * sgn));
       	addSequential(new DriveFixedDistance(62.0));
       	//addSequential(new LowerArmToEjectPosFromRaised());
       	// Tilt shooter down?
       	addSequential(new EjectBox());
    }
}
