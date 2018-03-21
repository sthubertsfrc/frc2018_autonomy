package org.usfirst.frc.team4637.robot.commands;

import org.usfirst.frc.team4637.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;


/**
 *
 */
public class TiltGrabber extends InstantCommand {
	
	boolean toShootPos;
	
    public TiltGrabber(boolean to_shoot_pos) {
    	requires(Robot.m_grabber);
    	toShootPos = to_shoot_pos;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (toShootPos) {
    		Robot.m_grabber.tiltToShootPos();
    	} else {
       		Robot.m_grabber.tiltToGrabPos();
    	}
    }
}
