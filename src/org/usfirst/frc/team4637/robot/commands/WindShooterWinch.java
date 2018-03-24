package org.usfirst.frc.team4637.robot.commands;

import org.usfirst.frc.team4637.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class WindShooterWinch extends Command {

	double winchSpeed = 0.5;
	
    public WindShooterWinch() {
        requires(Robot.m_shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putString("Shooter", "Winding shooter");
    	Robot.m_shooter.setWinchSpeed(winchSpeed); // Don't release the clutch while the motor is spinning (reduce friction on the clutch)
    	setTimeout(10.0); // Don't wind forever
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.m_shooter.atLimitSwitch() || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	SmartDashboard.putString("Shooter", "Arming complete");
    	Robot.m_shooter.setWinchSpeed(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
