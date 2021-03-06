package org.usfirst.frc.team4637.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import java.util.concurrent.locks.ReentrantLock;

import org.usfirst.frc.team4637.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWMTalonSRX;

/**
 *
 */
public class Shooter extends Subsystem {

	// Two motors used to drive the winch 
	private PWMTalonSRX shooterTalon1;
	private PWMTalonSRX shooterTalon2;

	// Winch limit switch (trips when shooter is fully drawn
	// TODO change to analog input
	private DigitalInput limitSwitch;

	// Clutch solenoid to engage / disengage the puller motor from the shooter
	private DoubleSolenoid triggerClutch;

	ReentrantLock mutex;

	public Shooter() {
		limitSwitch = new DigitalInput(RobotMap.shooterLimitSwitchPort); 
		shooterTalon1 = new PWMTalonSRX(RobotMap.shooterMotor1Port);
		shooterTalon2 = new PWMTalonSRX(RobotMap.shooterMotor2Port);
		triggerClutch = new DoubleSolenoid(RobotMap.shooterSolenoidFwPort, RobotMap.shooterSolenoidRevPort);
	}

	public void engageShooterClutch() {
		triggerClutch.set(DoubleSolenoid.Value.kForward);
	}

	public void releaseShooterClutch() {
		triggerClutch.set(DoubleSolenoid.Value.kReverse);
	}

	public void setWinchSpeed(double speed) {
		// Motors spin in opposite direction from before
		shooterTalon1.set(-speed);
		shooterTalon2.set(-speed);
	}

	// TODO fix for analog input
	public boolean atLimitSwitch(){
		boolean isSwitchPushed = limitSwitch.get(); // 5V connected to NC so it's true by default, false when switch is closed
		return isSwitchPushed;
	}

	@Override
	protected void initDefaultCommand() {
		// By default do nothing
	}
}
