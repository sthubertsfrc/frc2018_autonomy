package org.usfirst.frc.team4637.robot.commands;

import org.usfirst.frc.team4637.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


/**
 *
 */
public class LowerArmToEjectPosFromRaised extends Command {

    public LowerArmToEjectPosFromRaised() {
        	requires(Robot.m_armController);
        }

        protected void initialize() {
        	// Adjust this time / speed as needed to get the arm in the right final position
        	Robot.m_armController.updateMotorSpeed(-0.6);
        	setTimeout(2.0);
        }

        // Called repeatedly when this Command is scheduled to run
        protected void execute() {
        }

        // Make this return true when this Command no longer needs to run execute()
        protected boolean isFinished() {
            return isTimedOut();
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
