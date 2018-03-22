/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4637.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4637.robot.commands.ArmShooterSequence;
import org.usfirst.frc.team4637.robot.commands.ClimbWithHook;
import org.usfirst.frc.team4637.robot.commands.DriveFixedDistance;
import org.usfirst.frc.team4637.robot.commands.DriveToSameSideSwitchInner;
import org.usfirst.frc.team4637.robot.commands.DriveToSameSideSwitchOuter;

import org.usfirst.frc.team4637.robot.AutonomousStrategy;
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
	public static Shooter m_shooter = new Shooter();
	public static ArmController m_armController = new ArmController();
	public static Grabber m_grabber = new Grabber();
	public static HookArm m_hookArm = new HookArm();
	
	// Control framework for Joystick input
	public static OI m_oi;

	AutonomousStrategy m_autonomousStrategy;
	SendableChooser<AutonomousStrategy> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		
		// NOTE: the "AutonomousStrategy" holds two commands, one if the switch is on the left side, one if the switch is on the right side.
		
		m_chooser.addObject("Robot at LEFT: go to switch and score, or go straight",
				new AutonomousStrategy(new DriveToSameSideSwitchOuter(true), new DriveFixedDistance(268.0)));
		
		m_chooser.addObject("Robot at CENTER: go to correct switch and score", 
				new AutonomousStrategy(new DriveToSameSideSwitchInner(true), new DriveToSameSideSwitchOuter(true)));
		
		m_chooser.addObject("Robot at RIGHT: go straight, or go to switch and score",
				new AutonomousStrategy(new DriveFixedDistance(268.0), new DriveToSameSideSwitchOuter(false)));

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
		m_autonomousStrategy = m_chooser.getSelected();
		
		String gamedata = DriverStation.getInstance().getGameSpecificMessage();

		// schedule the autonomous command (example)
		if (m_autonomousStrategy != null && !gamedata.isEmpty()) {
			m_autonomousStrategy.start(gamedata);
		} else {
			SmartDashboard.putString("Auto Status", "Can't start autonomous mode, missing command / game data");
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
		if (m_autonomousStrategy != null) {
			m_autonomousStrategy.cancel();
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
		
		m_oi.climbWinchBtn.whileHeld(new ClimbWithHook());
		
		// Right joystick controls drive wheels by default in teleop mode (see default command in DriveWheels.java) 
		// Left joystick Y axis controls arm angle by default in teleop mode (see default command in ArmController.java)
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
