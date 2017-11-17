package org.firstinspires.ftc.teamcode.RelicOpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicHardware;
import org.firstinspires.ftc.teamcode.enums.RobotRunType;

/**
 * Created by isaac.blandin on 11/17/17.
 */

@TeleOp(name = "Mecanum Test", group = "tests")
public class mecanumTest extends RelicHardware {

    public void runOpMode() {
        initRobot(RobotRunType.TELEOP);

        waitForStart();
        // declares variables to be used
        float RightFrontPowerVar, RightBackPowerVar, LeftFrontPowerVar, LeftBackPowerVar;

        while(opModeIsActive()) {
            //declares variables for the joystick variables to be used later
            double x1;
            double y1;
            double x2;

            x1 = gamepad1.left_stick_x;
            y1 = -gamepad1.left_stick_y;
            x2 = gamepad1.right_stick_x;

            // Reset variables
            LeftFrontPowerVar = 0;
            LeftBackPowerVar = 0;
            RightFrontPowerVar = 0;
            RightBackPowerVar = 0;

            // Handle regular movement
            LeftFrontPowerVar += y1;
            LeftBackPowerVar += y1;
            RightFrontPowerVar += y1;
            RightBackPowerVar += y1;

            // Handle strafing movement
            LeftFrontPowerVar += x1;
            LeftBackPowerVar -= x1;
            RightFrontPowerVar -= x1;
            RightBackPowerVar += x1;

            // Handle turning movement
            LeftFrontPowerVar += x2;
            LeftBackPowerVar += x2;
            RightFrontPowerVar -= x2;
            RightBackPowerVar -= x2;

            //limit the motor values to the acceptable range by motor
            if (LeftFrontPowerVar > 1|| LeftFrontPowerVar < -1 || RightFrontPowerVar > 1 || RightFrontPowerVar < -1 || LeftBackPowerVar > 1 || LeftBackPowerVar < -1 || RightBackPowerVar > 1 || RightBackPowerVar < -1) {
                LeftFrontPowerVar = Range.clip(LeftFrontPowerVar, -1, 1);
                LeftBackPowerVar = Range.clip(LeftBackPowerVar, -1, 1);
                RightFrontPowerVar = Range.clip(RightFrontPowerVar, -1, 1);
                RightBackPowerVar = Range.clip(RightBackPowerVar, -1, 1);
            }

            //set variables to the motor powers
            SetDrivePower(RightFrontPowerVar,RightBackPowerVar,LeftFrontPowerVar,LeftBackPowerVar);

        }
    }
}
