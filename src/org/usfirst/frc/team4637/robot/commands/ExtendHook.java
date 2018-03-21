package org.usfirst.frc.team4637.robot.commands;

import org.usfirst.frc.team4637.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ExtendHook extends InstantCommand {
    public ExtendHook() {
    	requires(Robot.m_hookArm);
    }

    protected void initialize() {
    	Robot.m_hookArm.extend();
    }
}
