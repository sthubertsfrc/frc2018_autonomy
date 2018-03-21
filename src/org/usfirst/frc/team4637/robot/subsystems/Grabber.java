package org.usfirst.frc.team4637.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWMVictorSPX;

/**
 *
 */
public class Grabber extends Subsystem {
	
	// Solenoid to drive the grabber 
	DoubleSolenoid grabberSolenoid;
	Compressor compressor;
	
	PWMVictorSPX leftVictor;
	PWMVictorSPX rightVictor;
	double intakeSpeed;
	double outtakeSpeed;
	
	public Grabber (int grabberForwardPort, int grabberReversePort, int leftPort, int rightPort){
		grabberSolenoid = new DoubleSolenoid(grabberForwardPort, grabberReversePort);
		leftVictor = new PWMVictorSPX (leftPort);
		rightVictor = new PWMVictorSPX (rightPort);
		intakeSpeed = 1.0;
		outtakeSpeed = 1.0;
		compressor = new Compressor();
	}

	/**
	 * @param speed positive = grab, negative = eject
	 */
	public void spinMotorsAtSpeed(double speed){
		leftVictor.set(speed);
		rightVictor.set(-speed);
	}
	public void stop(){
		spinMotorsAtSpeed(0.0);
	}

	public void tiltToShootPos(){
		grabberSolenoid.set(DoubleSolenoid.Value.kForward);

	}
	public void tiltToGrabPos(){
		grabberSolenoid.set(DoubleSolenoid.Value.kReverse);

	}
	@Override
	protected void initDefaultCommand() {
	}
}
