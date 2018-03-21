package org.usfirst.frc.team4637.robot.commands;

import org.usfirst.frc.team4637.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


/**
 *
 */
public class TiltGrabber extends Command {
	public enum GrabberAngle
	{
		ALIGN_WITH_GROUND,
		ALIGN_TO_SHOOTER
	}
	
	GrabberAngle goalAngle;
	
    public TiltGrabber(GrabberAngle goal_angle) {
    	requires(Robot.m_grabber);
    	goalAngle = goal_angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	switch (goalAngle) {
    	case ALIGN_WITH_GROUND:
    		Robot.m_grabber.tiltToGrabPos();
    		break;
    	case ALIGN_TO_SHOOTER:
    		Robot.m_grabber.tiltToShootPos();
    		break;
    	default:
    		break;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	// After sending the solenoid command, we're done
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
