package org.firstinspires.ftc.teamcode.RelicOpModes.positioning;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicHardware;
import org.firstinspires.ftc.teamcode.enums.RobotRunType;

/**
 * Created by isaac.blandin on 11/18/17.
 */

@Autonomous(name = "ArmPositioning", group = "tests")
public class armsDown extends RelicHardware {

    public void runOpMode() {

        initRobot(RobotRunType.AUTONOMOUS);

        double right = 1;
        double left = 0;

        waitForStart();

        JewelRight.setPosition(1);
        JewelLeft.setPosition(0);

        while (opModeIsActive()) {
            if (gamepad1.a) {
                right -= 0.1;
                left += 0.1;
            } else if (gamepad1.b) {
                right += 0.1;
                left -= 0.1;
            }

            JewelRight.setPosition(right);
            JewelLeft.setPosition(left);
        }
    }
}
