package org.usfirst.frc.team4637.robot.commands;

import org.usfirst.frc.team4637.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


/**
 *
 */
public class TiltGrabber extends Command {
	
	boolean toShootPos;
	
    public TiltGrabber(boolean to_shoot_pos) {
    	requires(Robot.m_grabber);
    	toShootPos = to_shoot_pos;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (toShootPos) {
    		Robot.m_grabber.tiltToShootPos();
    	} else {
       		Robot.m_grabber.tiltToGrabPos();
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
