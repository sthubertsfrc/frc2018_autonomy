package org.usfirst.frc.team4637.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class HookArm extends Subsystem {

	
	private DoubleSolenoid hookSolenoid;
	
    public HookArm(int extendPort, int retractPort)
    {
    	hookSolenoid = new DoubleSolenoid(extendPort, retractPort);
    }
    
    public void extend()
    {
    	hookSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    
    public void retract()
    {
    	hookSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

