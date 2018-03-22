/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4637.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	// TODO map robot winch and add joystick button 
	// TODO add maximum velocity cap to drive control
	// TODO read game data
	// TODO Make chooser handle getting game data only at start of auto
	
	
	// Digital In
	public static int encoderLeftPort1 = 0;
	public static int encoderLeftPort2 = 1;
	public static int encoderRightPort1 = 2;
	public static int encoderRightPort2 = 3;

	public static int shooterLimitSwitchPort = 4;
	
	public static int armLowerLimitSwitchPort = 8; // Not used
	public static int armUpperLimitSwitchPort = 9;
	
	// Digital Out
	public static int grabberSolenoidFwdPort = 0;
	public static int grabberSolenoidRevPort = 1;
	
	public static int shooterSolenoidFwPort = 2;
	public static int shooterSolenoidRevPort = 3;
	
	public static int hookSolenoidFwdPort = 6;
	public static int hookSolenoidRevPort = 7;
	
	// PWM out
	public static int driveMotorRearLeftPort = 0;
	public static int driveMotorFrontLeftPort = 1;
	public static int driveMotorRearRightPort = 2;
	public static int driveMotorFrontRightPort = 3;
	
	public static int armMotorPort = 4;
	
	public static int winchMotorPort = 5;
	
	public static int grabberRightMotorPort = 6;
	public static int grabberLeftMotorPort = 7;
	
	public static int shooterMotor1Port = 8;
	public static int shooterMotor2Port = 9;
	

}
