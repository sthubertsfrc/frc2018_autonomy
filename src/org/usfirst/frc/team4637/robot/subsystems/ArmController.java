package org.usfirst.frc.team4637.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4637.robot.RobotMap;
import org.usfirst.frc.team4637.robot.commands.ControlArmAngleWithJoystick;

import edu.wpi.first.wpilibj.DigitalInput;

public class ArmController extends Subsystem{
	Spark positionMotor;
	DigitalInput lowerLimit;
	DigitalInput upperLimit;
	
	// Gains for speed control
	double upSpeedGain;
	double downSpeedGain;
	double armUpDirection;

	public ArmController(){
		super("ArmController");// The constructor passes a name for the subsystem and the P, I and D constants that are used when computing the motor output
		
		// NOTE These gains MUST be between 0.0 and 1.0
		upSpeedGain = 0.8;
		downSpeedGain = 0.5;
		
		armUpDirection = -1.0;
		
		positionMotor = new Spark (RobotMap.armMotorPort);

		lowerLimit = new DigitalInput(RobotMap.armLowerLimitSwitchPort);
		// TODO replace with analog input
		upperLimit = new DigitalInput(RobotMap.armUpperLimitSwitchPort);
	}
	
	public int inLimits(){
		// NOTE: switches are normally closed (i.e. "on") when arm is within limits
		// This way, if anything fails (wire breaks, 
		//boolean inLowerLimit = lowerLimit.get();
		boolean inLowerLimit = true;
		boolean inUpperLimit = upperLimit.get();
		// SmartDashboard.putBoolean("Arm at Lower Limit", inLowerLimit);
		SmartDashboard.putBoolean("Arm at Upper Limit", inUpperLimit);
		
		// Need to know which limit we've hit so the speed controller can back off
		int arm_status = 0;
		if (!inUpperLimit) {
			arm_status = 1;
		} else if (!inLowerLimit) {
			arm_status = -1;
		} 
		SmartDashboard.putNumber("Arm Limit Status", arm_status);
		return arm_status;
	}
	
	public static double makePositive(double in)
	{
		return Math.max(in, 0.0);
	}
	
	public static double makeNegative(double in)
	{
		return Math.min(in, 0.0);
	}
	
	public static double applyBound(double in, double min_val, double max_val)
	{
		return Math.max(Math.min(in, max_val), min_val);
	}
	
	public void updateMotorSpeed(double speed)
	{
		double net_speed = speed;
		int limitState = inLimits();
		switch (limitState) {
			case -1: // At lower limit, speed has to be positive
				net_speed = makePositive(net_speed);
				break;
			case 1: // At upper limit, speed has to be negative
				net_speed = makeNegative(net_speed);
				break;
			case 0:
			default:
		}
		
		// Multiply by a different gain depending on if speed is positive or not
		net_speed *= ((net_speed >= 0.0) ? upSpeedGain : downSpeedGain);
		
		// Bound input speed based on controller limits and gain
		net_speed = applyBound(net_speed * armUpDirection, -1.0, 1.0);
		SmartDashboard.putNumber("Net Arm Speed", net_speed);

		positionMotor.set(net_speed);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ControlArmAngleWithJoystick());
	}
	
}