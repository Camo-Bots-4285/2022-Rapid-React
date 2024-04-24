package org.usfirst.frc4285.CamoSwerve.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import java.io.IOException;
import java.nio.file.Path;

import org.usfirst.frc4285.CamoSwerve.subsystems.Drive;
import org.usfirst.frc4285.CamoSwerve.Robot;
import org.usfirst.frc4285.CamoSwerve.subsystems.SwerveIOUtils;



/**
 * A command that, while running, will execute a PathWeaver trajectory
 * on a Swerve drive.
 *
 * @author Jordan Bancino
 */
public class PathweaverSwerveDrive extends Command {

    private final Trajectory trajectory;
    private final Drive swerve;
    private final double angularVelocity;

    private Trajectory.State currentState;
    private long startTime, currentTime;

    /**
     * Create a Pathweaver swerve drive.
     *
     * @param swerve The swerve drive object to drive.
     * @param pathweaverJson The pathweaver file to load, relative to the deploy directory.
     * @throws IOException If there is an IO error when reading the pathweaver json file.
     */
    public PathweaverSwerveDrive(Drive swerve, String pathweaverJson) throws IOException {
        if (pathweaverJson != null) {
            Path jsonPath = Filesystem.getDeployDirectory().toPath().resolve(pathweaverJson);
            trajectory = TrajectoryUtil.fromPathweaverJson(jsonPath);
            angularVelocity = SwerveIOUtils.getAngularVelocity(trajectory);
        } else {
            throw new IllegalArgumentException("Pathweaver JSON file name cannot be null.");
        }
        if (swerve != null) {
            requires(Robot.drive);
            this.swerve = swerve;
        } else {
            throw new IllegalArgumentException("Swerve drive cannot be null.");
        }
    }

    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
    }

    private double getTimeSeconds() {
        currentTime = System.currentTimeMillis();
        return (currentTime - startTime) / 1000;
    }

    @Override
    public void execute() {
        currentState = trajectory.sample(getTimeSeconds());
        ChassisSpeeds swerveVector = SwerveIOUtils.convertToChassisSpeeds(currentState, angularVelocity);
        Robot.drive.swerveDrive(swerveVector.vxMetersPerSecond, swerveVector.vyMetersPerSecond, swerveVector.omegaRadiansPerSecond);
        System.out.println("[SwerveIO Pathweaver] (" + getTimeSeconds() + " s) Running vector: " + swerveVector);
    }

    @Override
    public boolean isFinished() {
        return currentState == null || trajectory.getTotalTimeSeconds() <= getTimeSeconds();
    }
}