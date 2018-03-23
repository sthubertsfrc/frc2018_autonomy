package org.usfirst.frc.team4637.robot.commands;

import org.usfirst.frc.team4637.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class TakeBox extends Command {

	double timeout = 0.0;
    public TakeBox() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.m_grabber);
    }
    public TakeBox(double timeout_in) {
    	this();
    	timeout = timeout_in;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.m_grabber.spinMotorsAtSpeed(1.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return timeout > 0.0 && isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.m_grabber.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
