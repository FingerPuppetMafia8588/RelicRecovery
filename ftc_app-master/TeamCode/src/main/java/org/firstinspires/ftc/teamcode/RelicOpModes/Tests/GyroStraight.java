package org.firstinspires.ftc.teamcode.RelicOpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicAutonomousBase;
import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicHardware;
import org.firstinspires.ftc.teamcode.enums.RobotRunType;

/**
 * Created by isaac.blandin on 10/31/17.
 */

@Autonomous(name = "straightTest")
@Disabled
public class GyroStraight extends RelicAutonomousBase {
    public void runOpMode() throws InterruptedException {

        //initializes Robot hardware
        initRobot(RobotRunType.AUTONOMOUS);


        //code runs after start is pressed
        waitForStart();

        while(imu.isCalibrating()) {
            //wait to start if gyro is not fully calibrated
        }

        GyroStraight(0.4, 50);

    }

    public void GyroStraight (double power, double inches) {

        ResetEncoders();
        //find starting gyro position
        double target = imu.getIntegratedZValue();

        //create variables to be used later
        double drift;
        double right;
        double left;

        //convert encoder ticks to inches
        inches = inches*1120/12.566;

        while (Math.abs(RightFront.getCurrentPosition()) < inches && opModeIsActive()) {

            drift = (imu.getIntegratedZValue() - target)/100;

            right = power - drift;
            left = power + drift;

            //limits motor values if the variable exceeds ranges
            if (right > 1 | right < -1) {
                right = Range.clip(right, -1, 1);
            }
            if (left > 1 | left < -1) {
                left = Range.clip(left, -1, 1);
            }


            //assign motor powers from variable
            SetDrivePower(right, right, left, left);



        }

        //stop once the target distance is met
        StopDrive();

    }
}



