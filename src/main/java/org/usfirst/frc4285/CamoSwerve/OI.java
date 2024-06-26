// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4285.CamoSwerve;

import org.usfirst.frc4285.CamoSwerve.commands.*;
import edu.wpi.first.wpilibj.buttons.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;


public class OI {
    /*
     * This class contians all I/O related methods,
     * such as the initialization of Joysticks and
     * Controllers.
     */

    public Joystick leftJoy;
    public Joystick rightJoy;
    public XboxController controller;
    public Button btnThrow;
    public Button btnTurretTurnRight;
    public Button btnTurretTurnLeft;
    public Button btnTurretTracking;
    public Button btnBallIntakePickup;
    public Button btnBallIntakeUp;
    public Button btnBallIntakeDrop;
    public Button btnTurretFlapLower;
    public Button btnTurretFlapRaise;
    public Button btnHailMary;
    public Button btnLift;
    public Button btnLower;
    public Button btnLiftToColorWheel;
    public Button btnLiftToStartingPosition;
    public Button btnSpinColorWheel;
    public Button btnClimberRaise;
    public Button btnClimberLower;
    public Button btnBallIntakeRaise;
    public Button btnBallIntakeLower;
    public Timer time;
    public Button btnZeroDrive;

    public  OI() {
        //////////////////////////////////
        ///     Controller Mapping     ///
        //////////////////////////////////

        leftJoy = new Joystick(RobotMap.LEFT_JOYSTICK);
        rightJoy = new Joystick(RobotMap.RIGHT_JOYSTICK);
        controller = new XboxController(RobotMap.CONTROLLER);
    
        ///////////////////////////////
        ///      Button Mapping     ///
        ///////////////////////////////

        // Turret Shooting
        btnThrow = new JoystickButton(rightJoy, 1);
        btnThrow.whileHeld(new Throwing());

        // Turret Manual Right Turn
        btnTurretTurnRight = new JoystickButton(rightJoy, 6);
        btnTurretTurnRight.whileHeld(new TurretRight());

        // Turret Manual Left Turn
        btnTurretTurnLeft = new JoystickButton(rightJoy, 5);
        btnTurretTurnLeft.whileHeld(new TurretLeft());

        // Climber Manual Raise
        btnClimberRaise = new JoystickButton(rightJoy, 3);
        btnClimberRaise.whileHeld(new ClimberRaise());

        // Climber Manual Lower
        btnClimberLower = new JoystickButton(rightJoy, 4);
        btnClimberLower.whileHeld(new ClimberLower());

        // Ball Intake Manual Raise
        btnBallIntakeRaise = new JoystickButton(leftJoy, 5);
        btnBallIntakeRaise.whileHeld(new BallIntakeRaise());

        // Ball Intake Manual Lower
        btnBallIntakeLower = new JoystickButton(leftJoy, 6);
        btnBallIntakeLower.whileHeld(new BallIntakeLower());

        // Turret Auto-Tracking Initializer
        // btnTurretTracking = new JoystickButton(leftJoy, 4);
        // btnTurretTracking.whileHeld(new Follow());

        // Ball Intake Pickup
        btnBallIntakeUp = new JoystickButton(leftJoy, 1);
        btnBallIntakeUp.whileHeld(new BallIntake());

        // Zero Heading
        btnZeroDrive = new JoystickButton(leftJoy, 7);
        btnZeroDrive.whileHeld(new ZeroDrive());

    }

    public boolean getLeftJoyButton(int buttonNumber) {
        /*
         * Returns the activity status of the button number
         * associated with 'buttonNumber'.
         */

        return !leftJoy.getRawButton(buttonNumber);
    }

    public boolean getRightJoyButton(int buttonNumber) {
        /*
         * Returns the activity status of the button number
         * associated with 'buttonNumber'.
         */

        return !rightJoy.getRawButton(buttonNumber);
    }

    public double getRightTrigger() {
        /*
         * Returns the activity status of the right
         * trigger.
         */
        return controller.getRawAxis(3);
    }

}