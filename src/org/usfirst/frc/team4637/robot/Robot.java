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
import org.usfirst.frc.team4637.robot.subsystems.ArmController;
import org.usfirst.frc.team4637.robot.subsystems.DriveWheels;
import org.usfirst.frc.team4637.robot.subsystems.Grabber;
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
		// chooser.addObject("My Auto", new MyAutoCommand());
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
		
		
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		handleArmControl(m_oi.leftStick.getY(), m_oi.autoRaiseBtn.get());

		// Push grabber Piston Out
		if (m_oi.tiltToShootBtn.get()){
			m_grabber.tiltToShootPos();
		}

		// Pull grabber piston in
		if (m_oi.tiltToGrabBtn.get()){
			m_grabber.tiltToGrabPos();
		}

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
	
	private void handleArmControl(double armLiftVel, boolean auto_lift_active) {
		// Handle arm positioning (throttle arm motor based on left joystick Y axis)

		// Check if fast-positioning button is depressed, and override joystick input with maximum speed
		if (auto_lift_active == true){
			armLiftVel = 1.0;
		}

		SmartDashboard.putNumber("Arm Speed", armLiftVel);
		m_armController.updateMotorSpeed(armLiftVel);
	}
	
}
