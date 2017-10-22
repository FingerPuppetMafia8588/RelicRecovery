package org.firstinspires.ftc.teamcode.RelicOpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicAutonomousBase;
import org.firstinspires.ftc.teamcode.enums.RobotRunType;

/**
 * Created by FTC on 10/22/2017.
 */
@Autonomous
public class GyroTurn extends RelicAutonomousBase {
    @Override
    public void runOpMode() {
       initRobot(RobotRunType.AUTONOMOUS);

        waitForStart();

        while (imu.isCalibrating()) {

        }
        TurnDegrees(0.2, 90);
    }
}
