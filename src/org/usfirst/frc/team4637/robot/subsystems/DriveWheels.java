package org.usfirst.frc.team4637.robot.subsystems;

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
	
	// TODO verify directions
	Encoder leftEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	Encoder rightEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
	final int encoderPPR = 2048;

	// TODO verify these values
	final double horizontalWheelBase = 24.0; // in
	final double wheelDiameter = 8.0; // in 
	
	double forwardSpeed_ips = 0.0;
	double turningSpeedCCW_rad_per_sec = 0.0;
	
	double currentAngle = 0.0;
	double currentPos = 0.0;
	
	double lastLeftPos = 0.0;
	double lastRightPos = 0.0;
	
	double x = 0.0;
	double y = 0.0;
	
	double refTotalAngle = 0.0; // CCW positive
	double refTotalDist = 0.0; // inches
	
	double initialAngle = 0.0;
	double initialPos = 0.0;
	
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
		double leftVel_ips = leftEncoder.getRate();
		double rightVel_ips = rightEncoder.getRate();
		
		forwardSpeed_ips = (leftVel_ips + rightVel_ips) / 2.0;
		turningSpeedCCW_rad_per_sec = (rightVel_ips - leftVel_ips) / horizontalWheelBase;
		
		double leftPos = leftEncoder.getDistance();
		double rightPos = rightEncoder.getDistance();
		
		double deltaLeft = leftPos - lastLeftPos;
		double deltaRight = rightPos - lastRightPos;
		
		double dPos = (deltaLeft + deltaRight) / 2.0;
		currentPos += dPos;
		
		double dAngle = (deltaLeft = deltaRight) / horizontalWheelBase;
		currentAngle += dAngle;
		
		lastLeftPos = leftPos;
		lastRightPos = rightPos;
		
		// Update incremental X / Y positions based on current angle / movement
		x += dPos * Math.cos(dAngle);
		y += dPos * Math.cos(dAngle);
	}
	
	public void resetLocalCsys()
	{
		currentPos = 0.0;
		currentAngle = 0.0;
	}
	
	public void resetGlobalCsys()
	{
		x = 0.0;
		y = 0.0;
	}

	// This should be called periodically to do feedback control
	public void doFeedbackLoop()
	{
		updateMeasurements();
		double posErr = refTotalDist - currentPos;
		// TODO handle divide by zero
		double angleErr = refTotalAngle - currentAngle;
		moveOpenLoop(posErr * 0.04, angleErr * 0.02, false);
	}
	
	public void startClosedLoop(double totalDist, double totalAngle)
	{
		initialPos = currentPos;
		initialAngle = currentAngle;
		refTotalAngle = totalAngle;
		refTotalDist = totalDist;
	}
	
	
	public void moveOpenLoop(double turnRate, double driveSpeed, boolean squaredDrive) {
		
		SmartDashboard.putNumber("Drive Angle", turnRate);
		SmartDashboard.putNumber("Drive Speed", driveSpeed);
		
		myDrive.arcadeDrive(driveSpeed, turnRate, squaredDrive);
	}
	
	public void stop() {
		moveOpenLoop(0.0, 0.0, true);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}

