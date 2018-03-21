package org.usfirst.frc.team4637.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ArmShooterSequence extends CommandGroup {

    public ArmShooterSequence() {
    	addSequential(new PrepareShooterWinch());
    	addSequential(new WindShooterWinch()); // Control windup speed here
    }
}
