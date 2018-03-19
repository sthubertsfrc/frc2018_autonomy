package org.usfirst.frc.team4637.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import java.util.concurrent.locks.ReentrantLock;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

/**
 *
 */
public class ShooterArm extends Subsystem {

	// Two motors used to drive the winch 
	private PWMTalonSRX shooterTalon1;
	private PWMTalonSRX shooterTalon2;

	// Controls both winch motors in unison (more convenient interface)
	private SpeedControllerGroup winch;

	// Winch limit switch (trips when shooter is fully drawn
	// TODO change to analog input
	private DigitalInput limitSwitch;

	// Clutch solenoid to engage / disengage the puller motor from the shooter
	private DoubleSolenoid triggerClutch;

	ReentrantLock mutex;

	public ShooterArm(int talonPort1, int talonPort2, int switchPort, int clutchForwardPort, int clutchReversePort) {
		limitSwitch = new DigitalInput(switchPort); 
		shooterTalon1 = new PWMTalonSRX(talonPort1);
		shooterTalon2 = new PWMTalonSRX(talonPort2);
		winch = new SpeedControllerGroup(shooterTalon1, shooterTalon2);
		triggerClutch = new DoubleSolenoid(clutchForwardPort, clutchReversePort);
	}

	public void armShooterClutch() {
		triggerClutch.set(DoubleSolenoid.Value.kForward);
	}

	public void releaseShooterClutch() throws InterruptedException {
		triggerClutch.set(DoubleSolenoid.Value.kReverse);
	}

	public void setWinchSpeed(double speed) throws InterruptedException {
		winch.set(speed);
	}

	public boolean limitSwitchTest(){
		boolean isSwitchPushed = !limitSwitch.get(); // 5V connected to NC so it's true by default, false when switch is closed
		return isSwitchPushed;
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
