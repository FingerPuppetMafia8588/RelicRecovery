package org.firstinspires.ftc.teamcode.RelicOpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicAutonomousBase;
import org.firstinspires.ftc.teamcode.enums.RobotRunType;

/**
 * Created by FTC on 10/22/2017.
 */
@Autonomous
@Disabled
public class GyroTurn extends RelicAutonomousBase {
    @Override
    public void runOpMode() {
       initRobot(RobotRunType.AUTONOMOUS);

        waitForStart();

        while (imu.isCalibrating()) {

        }
        Drive(.5, 10);
        TurnDegrees(0.3, 90);
        Drive(.5, 10);
        TurnDegrees(0.3, 90);
        Drive(.5, 10);
        TurnDegrees(0.3, 90);
        Drive(.5, 10);
        TurnDegrees(0.3, 90);
    }
}
