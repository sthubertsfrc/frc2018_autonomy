/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4637.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	// Define buttons / joysticks and map them to various functions
	// This approach makes it easy to see what button is assigned to what job
	Joystick leftStick = new Joystick(0);
	Joystick rightStick = new Joystick(1);
	Button autoRaiseBtn = new JoystickButton(leftStick, 4);
	
	Button intakeBtn = new JoystickButton(leftStick, 2);
	Button ejectBtn = new JoystickButton(leftStick, 3);
	
	Button tiltToShootBtn = new JoystickButton(rightStick, 8);
	Button tiltToGrabBtn = new JoystickButton(rightStick, 9);
	
	// TODO pick hook buttons
	Button armShooterBtn = new JoystickButton(leftStick, 1);
	Button shootBtn = new JoystickButton(rightStick, 1);
	
	Button extendHookBtn = new JoystickButton(rightStick, 2);
	Button retractHookBtn = new JoystickButton(rightStick, 3);
	
	public double getForwardSpeedCmd()
	{
		return -rightStick.getY() * 0.9;
	}
	
	public double getTurnRateCmd()
	{
		return rightStick.getX() * 0.8;
	}
	
	public double getArmSpeedCmd()
	{
		return leftStick.getY();
	}
	
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
