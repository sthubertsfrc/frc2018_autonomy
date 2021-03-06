package org.usfirst.frc.team4637.robot.commands;

import org.usfirst.frc.team4637.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimbWithHookAtSpeed extends Command {

	double climbSpeed;
    public ClimbWithHookAtSpeed(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.m_hookArm);
    	climbSpeed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.m_hookArm.winch.set(climbSpeed);
    }


    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.m_hookArm.winch.set(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
