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

	public void outtake(){
		leftVictor.set(outtakeSpeed);
		rightVictor.set(-outtakeSpeed);
	}
	public void intake() {
		leftVictor.set(-intakeSpeed);
		rightVictor.set(intakeSpeed);
	}
	public void spin() {
		leftVictor.set(-intakeSpeed * 0.5);
		rightVictor.set(intakeSpeed);
	}
	public void stop(){
		leftVictor.set(0);
		rightVictor.set(0);
	}

	public void pushOut1(){
		grabberSolenoid.set(DoubleSolenoid.Value.kForward);

	}
	public void pullIn1(){
		grabberSolenoid.set(DoubleSolenoid.Value.kReverse);

	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
