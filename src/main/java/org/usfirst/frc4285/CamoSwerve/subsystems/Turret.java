/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4285.CamoSwerve.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.revrobotics.CANSparkMax.ControlType; //ControlType deprecated; use CANSparkMax.ControlType instead
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder; //CANEncoder deprecated; use RelativeEncoder instead.
import com.revrobotics.SparkMaxPIDController; //CANPIDController deprecated; use SparkMaxPIDController instead.

import org.usfirst.frc4285.CamoSwerve.Robot;
import org.usfirst.frc4285.CamoSwerve.RobotMap;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


public class Turret extends Subsystem {

    private CANSparkMax turretmotor;
    private SparkMaxPIDController turretPID;
    private RelativeEncoder turretencoder;
    private NetworkTable table;
    private double a1;
    private double a2;
    private double h1;
    private double h2;
    private double d;
    private double power;
    private double leadAngle;
    private double swerveAverageRPM;
    private double robotSpeed;
    private double pos;

    private RelativeEncoder driveLeftFrontEncoder;
    private RelativeEncoder driveLeftRearEncoder;
    private RelativeEncoder driveRightFrontEncoder;
    private RelativeEncoder driveRightRearEncoder;

    private CANSparkMax driveLeftFrontSpark;
    private CANSparkMax driveLeftRearSpark;
    private CANSparkMax driveRightFrontSpark;
    private CANSparkMax driveRightRearSpark;
    
    private double distanceCoefficiant;

    public Turret() {
        turretmotor = new CANSparkMax(RobotMap.TURRET_ID, MotorType.kBrushless);
        turretencoder = turretmotor.getEncoder();
        table = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public void following() {
        pos = turretencoder.getPosition();

        // Read values from limelight.
        NetworkTableEntry tx = table.getEntry("tx");
        double x = tx.getDouble(0.0);
        // System.out.println(x);

        final NetworkTableEntry ty = table.getEntry("ty");
        a1 = 21; // Angle of camera from the horizontal in degrees
        a2 = ty.getDouble(0.0); // Angel of tower to camera found with limelight

        d = 55 / Math.tan(Math.toRadians(a1+a2)); // Calculates the distance between camera and target

        driveLeftFrontEncoder = Robot.drive.driveLeftFrontSpark.getEncoder();
        driveLeftRearEncoder = Robot.drive.driveLeftRearSpark.getEncoder();
        driveRightFrontEncoder = Robot.drive.driveRightFrontSpark.getEncoder();
        driveRightRearEncoder = Robot.drive.driveRightRearSpark.getEncoder();

        swerveAverageRPM = (Math.abs(driveRightFrontEncoder.getVelocity()) + Math.abs(driveRightRearEncoder.getVelocity()) + Math.abs(driveLeftRearEncoder.getVelocity()) + Math.abs(driveLeftFrontEncoder.getVelocity())) / 4;

        robotSpeed = Math.abs((((swerveAverageRPM / 10)*12.5663)/60)/12);

        distanceCoefficiant = 1.7 + -.00762 * d + .0000173*(d*d); 
        leadAngle = .805 + 3.13 * robotSpeed + -.261*(robotSpeed*robotSpeed)-2;

        double turretOffset = (-.946 + .0843*d + -.000508 * (d*d) + .000000884 * (d*d*d)-1 + leadAngle) * distanceCoefficiant; //original golden
        double turretposition = turretencoder.getPosition();
        double target = turretposition + x * (53.76 / 90.0) + turretOffset;

        turretPID = turretmotor.getPIDController();
        turretPID.setP(0.2);
        turretPID.setI(0.00);
        turretPID.setD(0.28);
        turretPID.setIZone(0.0);
        turretPID.setFF(0.0);
        turretPID.setOutputRange(-0.2, 0.2);
        turretPID.setReference(target, ControlType.kPosition);
    }

    public void left() {
        /**
         * Turns the turret to the left.
         */

        pos = turretencoder.getPosition();
        // System.out.println("Left: " + pos);
        turretmotor.set(-0.25);
    }

    public void right() {
        /**
         * Turns the turret to the right.
         */

        pos = turretencoder.getPosition();
        turretmotor.set(0.25);
    }

    public void stop() {
      /**
       * Stop the motor entirely.
       */

      turretmotor.set(0.0);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    @Override
    public void initDefaultCommand() {
      // Set the default command for a subsystem here.
      // setDefaultCommand(new MySpecialCommand());
    }
}
