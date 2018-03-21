package org.usfirst.frc.team4637.robot.commands;

import org.usfirst.frc.team4637.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PrepareShooterWinch extends Command {

	boolean atLimit;
    public PrepareShooterWinch() {
        requires(Robot.m_shooter);
        atLimit = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.m_shooter.setWinchSpeed(0.2);
    	setTimeout(0.5); // Wait just long enough for the motor to spin ~ 1/2 revolution
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	atLimit = Robot.m_shooter.atLimitSwitch(); 
        return  atLimit || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	if (!atLimit) {
    		Robot.m_shooter.engageShooterClutch();
    	}
    	Robot.m_shooter.setWinchSpeed(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.m_shooter.setWinchSpeed(0.0);
    }
}
