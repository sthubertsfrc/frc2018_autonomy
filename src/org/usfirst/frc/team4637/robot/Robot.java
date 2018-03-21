/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4637.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4637.robot.commands.ArmShooterSequence;
import org.usfirst.frc.team4637.robot.commands.DriveToLeftSwitchSequence;
import org.usfirst.frc.team4637.robot.commands.DriveToRightSwitchSequence;
import org.usfirst.frc.team4637.robot.commands.EjectBox;
import org.usfirst.frc.team4637.robot.commands.ExtendHook;
import org.usfirst.frc.team4637.robot.commands.RaiseArmToLimit;
import org.usfirst.frc.team4637.robot.commands.RetractHook;
import org.usfirst.frc.team4637.robot.commands.Shoot;
import org.usfirst.frc.team4637.robot.commands.TakeBox;
import org.usfirst.frc.team4637.robot.commands.TiltGrabber;
import org.usfirst.frc.team4637.robot.subsystems.ArmController;
import org.usfirst.frc.team4637.robot.subsystems.DriveWheels;
import org.usfirst.frc.team4637.robot.subsystems.Grabber;
import org.usfirst.frc.team4637.robot.subsystems.HookArm;
import org.usfirst.frc.team4637.robot.subsystems.Shooter;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	
	// Robot subsystems
	public static DriveWheels m_driveWheels = new DriveWheels();
	public static Shooter m_shooter = new Shooter(8, 9, 1, 2, 3);
	public static ArmController m_armController = new ArmController(4, 8, 9);
	public static Grabber m_grabber = new Grabber(0, 1, 7, 6);
	public static HookArm m_hookArm = new HookArm(6, 7);
	
	// Control framework for Joystick input
	public static OI m_oi;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		m_chooser.addObject("My Auto", new DriveToLeftSwitchSequence());
		m_chooser.addObject("My Auto", new DriveToRightSwitchSequence());
		// TODO decide if this is necessary
		SmartDashboard.putData("Auto mode", m_chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		
		m_oi.autoRaiseBtn.whenPressed(new RaiseArmToLimit());		
		m_oi.armShooterBtn.whenPressed(new ArmShooterSequence());
		m_oi.shootBtn.whenPressed(new Shoot());
		
		m_oi.intakeBtn.whileHeld(new TakeBox());
		m_oi.ejectBtn.whileHeld(new EjectBox());
		m_oi.tiltToGrabBtn.whenPressed(new TiltGrabber(true));
		m_oi.tiltToShootBtn.whenPressed(new TiltGrabber(false));
		
		m_oi.extendHookBtn.whenPressed(new ExtendHook());
		m_oi.retractHookBtn.whenPressed(new RetractHook());
		
		// Right joystick controls drive wheels by default in teleop mode (see default command in DriveWheels.java) 
		// Left joystick Y axis controls arm angle by default in teleop mode (see default command in ArmController.java)
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		// TODO move these to commands
		// Arm shooter
		// Extend the hook
		if (m_oi.armShooterBtn.get()) {
			m_shooter.engageShooterClutch();
		}

		// Shoot! (and reload)
		// Retract the hook
		if (m_oi.shootBtn.get()){
			m_shooter.releaseShooterClutch();
		}	

	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
