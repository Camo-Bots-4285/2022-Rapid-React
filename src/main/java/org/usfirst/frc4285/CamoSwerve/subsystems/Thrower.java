package org.usfirst.frc4285.CamoSwerve.subsystems;


import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc4285.CamoSwerve.Robot;
import org.usfirst.frc4285.CamoSwerve.RobotMap;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import java.lang.Math;


public class Thrower extends Subsystem {
    /*
     * Throwing Subsystem
     *
     * This class implements a thrower system to track the target
     * using a limelight visual sensor and automatically adjust power
     * curves to guarantee accuracy to the target.
     */

    private CANSparkMax throwermotor;
    private CANSparkMax feedmotor;
    private CANSparkMax feedhighmotor;
    private RelativeEncoder throwermotorEncoder;
    private RelativeEncoder feedmotorEncoder;
    private RelativeEncoder feedhighmotorEncoder;
    private SparkMaxPIDController throwerPID;
    private NetworkTable table;
    private double a1;
    private double a2;
    private double h1;
    private double h2;
    private double d;
    private NetworkTableEntry ty;
    private double power;
    private RelativeEncoder shooterEncoder;

    public Thrower() {
        /*
         * Construct motors.
         */
        feedmotor = new CANSparkMax(RobotMap.FEED_MOTOR_ID, MotorType.kBrushless);
        feedhighmotor = new CANSparkMax(RobotMap.FEED_MOTOR_HIGH_ID, MotorType.kBrushless);
        // feedmotorEncoder = feedmotor.getEncoder();

        this.table = NetworkTableInstance.getDefault().getTable("limelight");
        this.ty = table.getEntry("ty");
        // NetworkTableEntry ledMode = table.getEntry("ledMode");
        // ledMode.forceSetNumber(1);

        throwermotor = new CANSparkMax(RobotMap.THROWER_MOTOR_ID, MotorType.kBrushless);
        // this.throwermotorEncoder = throwermotor.getEncoder();

        // this.stackmotor = new CANSparkMax(RobotMap.STACK_MOTOR_ID, MotorType.kBrushless);    
        // this.stackmotorEncoder = stackmotor.getEncoder();
    }

    public void engage_thrower(int throw_type) {
        /*
         * Engage throwing motor and determine power based on visual
         * sensor information.
         */
        // throwermotor.set(-0.55);
        // Throw 2 is high goal; 1 is low.
        if (throw_type == 2) {
            Robot.turret.following();
            a1 = 21; // Angle of camera from the horizontal in degrees
            a2 = ty.getDouble(0.0); // Angle of tower to camera found with limelight

            d = 100 * (1 + Math.tan(Math.toRadians(a1+a2))); // Calculates the distance between camera and target
            // System.out.println(d);

            // Set motor power for shooting the turret.
            // Over time, the turret occasionally gets less accurate
            // use the power nerf to adjust for that.

            // Equation Obtained via Point Approximation
            // https://www.dcode.fr/function-equation-finder
            // (Use Paraboilic/Hyperbolic Expression)
            // Inch | RPM
            // 230	-4080
            // 210	-3900
            // 186	-3700
            // 175	-3500
            // 165	-3375
            // 150	-3300
            // 133	-3275
            // 115	-3185
            // 100	-3150
            // 87	-3135
            // ----------
            // D Value | RPM
            // 152	    -4080
            // 155.63	-3900
            // 158.72	-3700
            // 160.92	-3500
            // 162.9	-3375
            // 165.9	-3300
            // 174.5	-3275
            // 183.68	-3185
            // 187.78	-3150
            // 200.19	-3135
            // Robot.oi.getLeftJoyButton(3)
            power = -0.750302*(d*d) + 280.586*d - 29313.2;
        }
        else if (throw_type == 1) {
            power = -1600.0;
        }

        throwerPID = throwermotor.getPIDController();
        throwerPID.setP(0.001);
        throwerPID.setI(0.0);
        throwerPID.setD(25.0);
        throwerPID.setIZone(0.0);
        throwerPID.setFF(0.0);
        throwerPID.setOutputRange(-1.0, 0.0);
        throwerPID.setReference(power * 1.02, ControlType.kVelocity);
    }

    public void loadshooter() {
        /*
         * Turn on motor to load shooter.
         */
        feedmotor.set(-0.95);
        feedhighmotor.set(-0.95);
    }

    public void ballrun_start() {
        /*
         * Experimental code, runs the lower feed motors
         */
        feedmotor.set(-0.8);
    }

    public void stop_ballrun() {
        /*
         * Experimental code, runs the lower feed motors
         */
        feedmotor.set(0.0);
    }

    public void loadstack() {
        /*
         * Turn on motor to shooter stack.
         */
        // stackmotor.set(1.0);
    }

    public void stop() {
        /*
         * Turn off all motors.
         */

        throwermotor.set(0.0);
        feedmotor.set(0.0);
        feedhighmotor.set(0.0);
        Robot.turret.stop();
        // this.stackmotor.set(0);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
}