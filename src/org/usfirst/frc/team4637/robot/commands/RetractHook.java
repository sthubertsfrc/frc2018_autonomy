package org.usfirst.frc.team4637.robot.commands;

import org.usfirst.frc.team4637.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class RetractHook extends InstantCommand {

    public RetractHook() {
    	requires(Robot.m_hookArm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.m_hookArm.retract();
    }
}
