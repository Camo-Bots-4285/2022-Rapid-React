package org.usfirst.frc4285.CamoSwerve.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc4285.CamoSwerve.Robot;
import org.usfirst.frc4285.CamoSwerve.RobotMap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Ballpickup extends Subsystem {
   /*
    * Ball Pickup Subsystem
    *
    * This system is responsible for picking up power cells from the
    * ground in the game field.
    */
    
    private CANSparkMax ballpickupmotor;
    private CANSparkMax ballpickupliftmotor;
    private RelativeEncoder liftencoder;
    private SparkMaxPIDController liftPID;
    private boolean legacy = true; // pids or no pids; that is the question.

    public Ballpickup() {
        ballpickupmotor = new CANSparkMax(RobotMap.BALL_PICKUP_MOTOR_ID, MotorType.kBrushless);
        ballpickupliftmotor = new CANSparkMax(RobotMap.BALL_PICKUP_LIFT_MOTOR_ID, MotorType.kBrushless);
        liftencoder = ballpickupliftmotor.getEncoder();
    }

    public void ballrun_start () {
        /* 
        * Engage the feeder motor to begin picking up a ball.
        */
        ballpickupmotor.set(0.85);
        Robot.thrower.ballrun_start();
    }

    public void stop_ballrun() {
        /*
        * Stops the intake motors.
        */
        ballpickupmotor.set(0.0);
        Robot.thrower.stop_ballrun();
    }

    public void lower_ballrun_intake () {
        /* 
        * Engage the feeder motor to begin picking up a ball.
        */
        // System.out.println("Lower: " + liftencoder.getPosition());

        // if (legacy) {
        ballpickupliftmotor.set(-0.55);
        // }
        // else {
        //     liftPID = ballpickupliftmotor.getPIDController();
        //     liftPID.setP(0.1);
        //     liftPID.setI(0.0);
        //     liftPID.setD(0.0);
        //     liftPID.setIZone(0.0);
        //     liftPID.setFF(0.0);
        //     liftPID.setOutputRange(-0.65, 0.65);
        //     liftPID.setReference(55, ControlType.kPosition);
        // }
    }

    public void raise_ballrun_intake () {
        /* 
        * Engage the feeder motor to begin picking up a ball.
        */
        System.out.println("Raise: " + liftencoder.getPosition());
        ballpickupliftmotor.set(0.55);
        // if (legacy) {
            
        // }
        // else {
        //     liftPID = ballpickupliftmotor.getPIDController();
        //     liftPID.setP(0.1);
        //     liftPID.setI(0.0);
        //     liftPID.setD(0.0);
        //     liftPID.setIZone(0.0);
        //     liftPID.setFF(0.0);
        //     liftPID.setOutputRange(-0.65, 0.65);
        //     liftPID.setReference(275, ControlType.kPosition);
        // }
    }

    public void stop_ballrun_intake () {
        /* 
        * Engage the feeder motor to begin picking up a ball.
        */
        ballpickupliftmotor.set(0.0);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
}
