package org.firstinspires.ftc.teamcode.RelicOpModes.Tests;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicHardware;
import org.firstinspires.ftc.teamcode.enums.RobotRunType;

/**
 * Created by isaac.blandin on 10/31/17.
 */

public class GyroStraight extends RelicHardware {
    public void runOpMode() {

        //initializes Robot hardware
        initRobot(RobotRunType.AUTONOMOUS);


        //code runs after start is pressed
        waitForStart();

        while(imu.isCalibrating()) {
            //wait to start if gyro is not fully calibrated
        }

    }

    public void GyroStraight (double power, double distance) {

        //find starting gyro position
        double target = imu.getIntegratedZValue();
        double drift;
        double right;
        double left;

        distance = distance*1120/12.566;

        while (Math.abs(RightFront.getCurrentPosition()) < distance) {
            drift = (imu.getIntegratedZValue() - target)/100;

            right = power + drift;
            left = power - drift;

            if (right > power | right < -power) {
                right = Range.clip(right, -power, power);
            }
            if (left > power | left < -power) {
                left = Range.clip(left, -power, power);
            }

            RightFront.setPower(right);
            RightBack.setPower(right);
            LeftFront.setPower(left);
            LeftBack.setPower(left);

        }

        Stop();

    }
}



