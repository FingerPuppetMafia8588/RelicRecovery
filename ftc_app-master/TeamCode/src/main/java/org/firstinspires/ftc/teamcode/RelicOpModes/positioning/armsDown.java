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

        //initialize hardware
        initRobot(RobotRunType.AUTONOMOUS);

        //set variables to full down
        double right = 1;
        double left = 0;

        waitForStart();

        //put arms to full down position
        JewelRight.setPosition(1);
        JewelLeft.setPosition(0);

        while (opModeIsActive()) {

            //raise arms if a is pressed
            if (gamepad1.a) {
                right -= 0.1;
                left += 0.1;
                sleep(100);
            } //lower arm if b is pressed
            else if (gamepad1.b) {
                right += 0.1;
                left -= 0.1;
                sleep(100);
            }

            //assign variables to servo position
            JewelRight.setPosition(right);
            JewelLeft.setPosition(left);

            //give feedback to the Driver Station
            telemetry.addData("Right", JewelRight.getPosition());
            telemetry.addData("Left", JewelLeft.getPosition());
            telemetry.update();
        }
    }
}
