package org.usfirst.frc.team4637.robot.subsystems;

import org.usfirst.frc.team4637.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class HookArm extends Subsystem {

	private DoubleSolenoid hookSolenoid;
	
	// KLUDGE just expose the winch directly
	public Victor winch;
	
    public HookArm()
    {
    	hookSolenoid = new DoubleSolenoid(RobotMap.hookSolenoidFwdPort, RobotMap.hookSolenoidRevPort);
    	winch = new Victor(RobotMap.winchMotorPort);
    	retract();
    }
    
    public void extend()
    {
    	SmartDashboard.putString("Hook Status:", "Extend");
    	hookSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    
    public void retract()
    {
    	SmartDashboard.putString("Hook Status:", "Retract");
    	hookSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
     
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

