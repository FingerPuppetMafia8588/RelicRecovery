package org.firstinspires.ftc.teamcode.RelicOpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicAutonomousBase;
import org.firstinspires.ftc.teamcode.enums.RobotRunType;

/**
 * Created by isaac.blandin on 11/17/17.
 */

@Autonomous(name = "JewelTest", group = "tests")
public class JewelTest extends RelicAutonomousBase {

    public void runOpMode() {

        //initiallize the hardware
        initRobot(RobotRunType.AUTONOMOUS);

        //this code runs after start is pressed
        waitForStart();
        while (imu.isCalibrating()) {
            //wait to start if gyro is not calibrated
        }

        keepBlueJewel();
    }
}
