package org.usfirst.frc.team4637.robot.commands;

import org.usfirst.frc.team4637.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveRotate extends Command {

	double cmdAngle;
    public DriveRotate(double angle_deg) {
		requires(Robot.m_driveWheels);
		setTimeout(20.0); // For safety
		cmdAngle = angle_deg;
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.m_driveWheels.startClosedLoop(0.0, cmdAngle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.m_driveWheels.doFeedbackLoop();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut() && Robot.m_driveWheels.atTarget(1.0, 5.0);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.m_driveWheels.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
