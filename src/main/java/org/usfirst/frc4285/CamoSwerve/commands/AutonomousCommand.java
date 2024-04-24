package org.usfirst.frc4285.CamoSwerve.commands;

import org.usfirst.frc4285.CamoSwerve.Robot;
// import org.usfirst.frc4285.CamoSwerve.commands.PathweaverSwerveDrive;

import edu.wpi.first.wpilibj.command.Command;

// import java.io.IOException;

public class AutonomousCommand extends Command {

    public AutonomousCommand() {
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (timeSinceInitialized() <= 3.5) {
            Robot.drive.swerveDrive(0, -0.16, 0);
            // Robot.ballpickup.lower_ballrun_intake();
            // Robot.ballpickup.ballrun_start();
        }

        if (timeSinceInitialized() > 5 && timeSinceInitialized() < 10) {
            Robot.drive.swerveDrive(0, 0.16, 0);
        }

        if (timeSinceInitialized() >= 9) {
            Robot.thrower.engage_thrower(1);
            Robot.drive.swerveDrive(0, 0, 0);
        }

        // Start Top Feeder
        // (Loads Shooter to Throw)
        if (timeSinceInitialized() >= 10.5) {
            Robot.thrower.loadshooter();
        }

        if (timeSinceInitialized() >= 13) {
            Robot.drive.swerveDrive(0, 0, 0);
            Robot.ballpickup.stop_ballrun();
            Robot.turret.stop();
            Robot.thrower.stop();
            // Robot.ballpickup.stop_ballrun_intake();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.drive.swerveDrive(0, 0, 0);
        Robot.ballpickup.stop_ballrun();
        Robot.turret.stop();
        Robot.thrower.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.drive.swerveDrive(0, 0, 0);
        Robot.ballpickup.stop_ballrun();
        Robot.turret.stop();
        Robot.thrower.stop();
    }
}
