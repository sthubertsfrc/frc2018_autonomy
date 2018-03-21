package org.usfirst.frc.team4637.robot.commands;

import org.usfirst.frc.team4637.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class Shoot extends InstantCommand {

    public Shoot() {
    	requires(Robot.m_shooter);
    }

    protected void initialize() {
    	Robot.m_shooter.releaseShooterClutch();
    }
}
