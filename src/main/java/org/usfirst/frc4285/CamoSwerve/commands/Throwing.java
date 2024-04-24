package org.usfirst.frc4285.CamoSwerve.commands;

import org.usfirst.frc4285.CamoSwerve.Robot;

import edu.wpi.first.wpilibj.command.Command;


public class Throwing extends Command {

    public Throwing() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.thrower);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (timeSinceInitialized() >= 0) {
            // Low Goal is engage_thrower(1)
            if (!Robot.oi.getRightJoyButton(2)) {
                Robot.thrower.engage_thrower(1);
            }
            // High Goal is engage_thrower(2)
            else {
                Robot.thrower.engage_thrower(2);
            }
        }

        if (timeSinceInitialized() >= 1.65) {
            Robot.thrower.loadshooter();
        }

        //Robot.thrower.engage_thrower();
        //Robot.thrower.loadshooter();
    }
   

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.thrower.stop();
    }
}
