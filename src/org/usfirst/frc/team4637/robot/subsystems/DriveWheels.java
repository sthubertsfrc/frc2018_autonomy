package org.usfirst.frc.team4637.robot.subsystems;

import org.usfirst.frc.team4637.robot.RobotMap;
import org.usfirst.frc.team4637.robot.commands.DriveWithJoystick;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveWheels extends Subsystem {
	// NOTE hard-coded ports here for drive wheels
	Spark backLeft = new Spark(0);
	Spark frontLeft = new Spark(1);
	Spark backRight= new Spark(2);
	Spark frontRight = new Spark(3);

	SpeedControllerGroup leftDrive = new SpeedControllerGroup(frontLeft, backLeft);
	SpeedControllerGroup rightDrive = new SpeedControllerGroup (frontRight, backRight);
	DifferentialDrive myDrive = new DifferentialDrive (leftDrive, rightDrive);


	Encoder leftEncoder = new Encoder(RobotMap.encoderLeftPort1, RobotMap.encoderLeftPort2, false, Encoder.EncodingType.k4X);
	Encoder rightEncoder = new Encoder(RobotMap.encoderRightPort1, RobotMap.encoderRightPort2, false, Encoder.EncodingType.k4X);
	
	// Start of parameters needed by control loop
	// Change these to tune the controllers performance
	// Fixed parameters
	final int encoderPPR = 2048;
	final double horizontalWheelBase = 23.0; // in
	final double wheelDiameter = 6.0; // in 

	// Controls how sensitive it is
	final double Kp_pos = 0.2;
	final double Kp_angle = 3.0;
	
	// Controls how fast the robot will go 
	double maxLinearVel = 0.5;
	double maxTurnRate = 0.3;
	// End of parameters
	
	private double currentAngle = 0.0;
	private double currentPos = 0.0;

	private double lastLeftPos = 0.0;
	private double lastRightPos = 0.0;

	private double x = 0.0;
	private double y = 0.0;

	private double refTotalAngle = 0.0; // CCW positive
	private double refTotalDist = 0.0; // inches

	private double initialAngle = 0.0;
	private double initialPos = 0.0;

	private double posErr = 0.0;
	private double angleErr = 0.0;

	public static double saturateSymmetric(double val, double max)
	{
		return (val > max) ? max : ((val < -max) ? -max : val);
	}
	
	public DriveWheels()
	{
		super("DriveWheels");

		// Set up scale factors so encoders report in real units (in for distance, in / sec for rate)
		double distancePerPulse = wheelDiameter * Math.PI / (double)encoderPPR;
		leftEncoder.setDistancePerPulse(distancePerPulse);
		rightEncoder.setDistancePerPulse(distancePerPulse);
	}

	public void updateMeasurements()
	{
		double leftPos = -leftEncoder.getDistance();
		double rightPos = -rightEncoder.getDistance();

		double deltaLeft = leftPos - lastLeftPos;
		double deltaRight = rightPos - lastRightPos;

		double dPos = (deltaLeft + deltaRight) / 2.0;
		currentPos += dPos;

		// Moving the right wheel forward will turn the robot counter-clockwise
		double dAngle = (deltaRight - deltaLeft) / horizontalWheelBase;
		currentAngle += dAngle;

		lastLeftPos = leftPos;
		lastRightPos = rightPos;

		// Update incremental X / Y positions based on current angle / movement
		x += dPos * Math.cos(dAngle);
		y += dPos * Math.sin(dAngle);

		SmartDashboard.putNumber("X Position", x);
		SmartDashboard.putNumber("Y Position", y);
		SmartDashboard.putNumber("Total Displacement", currentPos);
		SmartDashboard.putNumber("Net Robot Angle", currentAngle);
		SmartDashboard.putNumber("Left Wheels", leftPos);
		SmartDashboard.putNumber("Right Wheels", rightPos);
	}
	
	public void resetCoordinates()
	{
		currentPos = 0.0;
		currentAngle = 0.0;
		x = 0.0;
		y = 0.0;
		leftEncoder.reset();
		rightEncoder.reset();
	}

	// This should be called periodically to do feedback control
	public void doFeedbackLoop()
	{
		updateMeasurements();
		posErr = refTotalDist - (currentPos - initialPos);
		// TODO handle divide by zero
		angleErr = refTotalAngle - (currentAngle - initialAngle);
		
		// Saturate speed inputs at the maximum rates set for the controller
		// This allows the gains to be higher without causing huge accelerations at the beginning 
		double ctrlSpeed = saturateSymmetric(posErr * Kp_pos, maxLinearVel);
		double ctrlTurn = saturateSymmetric(angleErr * Kp_angle, maxTurnRate);
		
		SmartDashboard.putNumber("CL Forward Speed", ctrlSpeed);
		SmartDashboard.putNumber("CL Turn Rate", ctrlTurn);
		moveOpenLoop(ctrlSpeed, ctrlTurn, false);
	}

	public boolean atTarget(double posTol, double angleTol_deg)
	{
		return Math.abs(posErr) < posTol && Math.abs(angleErr) < angleTol_deg  * Math.PI / 180.0; 
	}

	public void startClosedLoop(double totalDist, double totalAngle_deg)
	{
		initialPos = currentPos;
		initialAngle = currentAngle;
		refTotalAngle = totalAngle_deg * Math.PI / 180.0;
		refTotalDist = totalDist;
		SmartDashboard.putNumber("Ref total angle", refTotalAngle);
		SmartDashboard.putNumber("Ref total dist", refTotalDist);
	}

	public void moveOpenLoop(double driveSpeed, double turnRate, boolean squaredDrive) {

		SmartDashboard.putNumber("Drive Speed", driveSpeed);
		SmartDashboard.putNumber("Drive Turn Rate", turnRate);
		
		myDrive.arcadeDrive(driveSpeed, turnRate, squaredDrive);
	}

	public void stop() {
		moveOpenLoop(0.0, 0.0, false);
	}

	@Override
	protected void initDefaultCommand() {
		// Unless otherwise specified, give the operator control of the robot
		setDefaultCommand(new DriveWithJoystick());
	}
}

