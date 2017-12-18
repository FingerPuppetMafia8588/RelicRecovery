package org.firstinspires.ftc.teamcode.RelicOpModes.Tests;

import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicAutonomousBase;
import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicHardware;
import org.firstinspires.ftc.teamcode.enums.RobotRunType;

/**
 * Created by isaac.blandin on 12/18/17.
 */

public class proportionalGyroTurn extends RelicAutonomousBase {

    public void runOpMode() {

        initRobot(RobotRunType.AUTONOMOUS);

        waitForStart();
    }

    public void turnToHeading (double power, double target) {
        double newPower;
        double startPoint = imu.getIntegratedZValue();
        zValue = imu.getIntegratedZValue();

        //move at full speed unitl within 5 degrees
        while(Math.abs(zValue - target) > 1 && opModeIsActive()) {



            if (zValue > target) {  //if gyro is positive, we will turn right
                SetDrivePower(-power, -power, power, power);
            }
            if (zValue < target) {
                SetDrivePower(power, power, -power, -power);

            }
            printGyroHeading();
            zValue = imu.getIntegratedZValue();

        }

        StopDrive();
    }
}

