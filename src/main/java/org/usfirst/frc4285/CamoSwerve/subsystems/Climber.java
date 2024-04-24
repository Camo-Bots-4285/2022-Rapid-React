package org.usfirst.frc4285.CamoSwerve.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc4285.CamoSwerve.RobotMap;


public class Climber extends Subsystem {
    /*
     * Throwing Subsystem
     *
     * This class implements a thrower system to track the target
     * using a limelight visual sensor and automatically adjust power
     * curves to guarantee accuracy to the target.
     */

    private CANSparkMax climb_motor_left;
    private CANSparkMax climb_motor_right;
    private RelativeEncoder climb_motor_left_encoder;
    private RelativeEncoder climb_motor_right_encoder;
    // private SparkMaxPIDController climb_motor_left_pid;
    // private SparkMaxPIDController climb_motor_right_pid;

    public Climber() {
        /*
         * Construct motors for the climber.
         */
        climb_motor_left = new CANSparkMax(RobotMap.CLIMB_LEFT_ID, MotorType.kBrushless);
        climb_motor_left_encoder = climb_motor_left.getEncoder();

        climb_motor_right = new CANSparkMax(RobotMap.CLIMB_RIGHT_ID, MotorType.kBrushless);
        climb_motor_right_encoder = climb_motor_right.getEncoder();
    }

    public void engage_climber(boolean direction) {
        /*
         * Engage throwing motor and determine power based on visual
         * sensor information.
         */

        double power = 0.95;
        double pos = climb_motor_left_encoder.getPosition();

        System.out.println("climb motor pos: " + pos);

        if (direction) {
            climb_motor_left.set(power);
            climb_motor_right.set(-power);
        }
        else {
            climb_motor_left.set(-power);
            climb_motor_right.set(power);
        }
    }

    public void stop() {
        /*
         * Turn off all motors.
         */

        climb_motor_left.set(0.0);
        climb_motor_right.set(0.0);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
}