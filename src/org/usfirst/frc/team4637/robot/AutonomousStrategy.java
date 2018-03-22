package org.usfirst.frc.team4637.robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutonomousStrategy {
	private Command leftSwitchCommand;
	private Command rightSwitchCommand;
	private Command activeCommand = null;
	
	public AutonomousStrategy(Command ifLeftSwitch, Command ifRightSwitch)
	{
		leftSwitchCommand = ifLeftSwitch;
		rightSwitchCommand = ifRightSwitch;
	}
	
	public void start(String gameInfo)
	{
		if (gameInfo.startsWith("L")) {
			activeCommand = leftSwitchCommand;
		} else if (gameInfo.startsWith("R")) {
			activeCommand = rightSwitchCommand;
		} 
		
		if (activeCommand != null) {
			activeCommand.start();
		}
	}

	public void cancel()
	{
		activeCommand.cancel();
	}
	
}
