package org.usfirst.frc.team4637.robot.commands;

import org.usfirst.frc.team4637.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RaiseArmToLimit extends Command {

    public RaiseArmToLimit() {
    	requires(Robot.m_armController);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.m_armController.updateMotorSpeed(1.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.m_armController.inLimits() != 0;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.m_armController.updateMotorSpeed(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
