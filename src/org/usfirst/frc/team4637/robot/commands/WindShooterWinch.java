package org.usfirst.frc.team4637.robot.commands;

import org.usfirst.frc.team4637.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WindShooterWinch extends Command {

	double winchSpeed;
    public WindShooterWinch(double speed) {
        requires(Robot.m_shooter);
        winchSpeed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
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
    	Robot.m_shooter.setWinchSpeed(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
