package org.firstinspires.ftc.teamcode.RelicOpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicHardware;
import org.firstinspires.ftc.teamcode.enums.RobotRunType;

/**
 * Created by FTC on 10/2/2017.
 */

@TeleOp (name = "Comp Cartesian", group = "RelicRecovery")
public class RelicTeleOpAlt extends RelicHardware {

    @Override
    public void runOpMode() throws InterruptedException{

        initRobot(RobotRunType.TELEOP);

        //creates time elapsed variable for usage in telemetry
        ElapsedTime runtime = new ElapsedTime();

        //all code after this line will not occur until the start button is pressed
        waitForStart();

        //start the timer for runtime for telemetry
        runtime.reset();

        // declares variables to be used
        float RightFrontPowerVar, RightBackPowerVar, LeftFrontPowerVar, LeftBackPowerVar;

        while (opModeIsActive()){

            //adds variables for the joystick variables to be used later
            double x1;
            double y1;
            double x2;

            //creates a squared input for the joysticks to allow more controlled movement in low speed
            if(gamepad1.left_stick_x > 0) {
                x1 = Math.pow(gamepad1.left_stick_x, 2);
            } else if (gamepad1.left_stick_x < 0) {
                x1 = -Math.pow(gamepad1.left_stick_x, 2);
            } else {
                x1 = 0;
            }

            if (gamepad1.left_stick_y > 0) {
                y1 = -Math.pow(gamepad1.left_stick_y, 2);
            } else if (gamepad1.left_stick_y < 0) {
                y1 = Math.pow(gamepad1.left_stick_y, 2);
            } else {
                y1 = 0;
            }

            if (gamepad1.right_stick_x > 0) {
                x2 = Math.pow(gamepad1.right_stick_x, 2);
            } else if (gamepad1.right_stick_x < 0) {
                x2 = -Math.pow(gamepad1.right_stick_x, 2);
            } else {
                x2 = 0;
            }
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

            // Scale the movement variables so they no not exceed the range of -1 to 1
            double max = Math.max(Math.abs(LeftFrontPowerVar), Math.max(Math.abs(LeftBackPowerVar),
                    Math.max(Math.abs(RightFrontPowerVar), Math.abs(RightBackPowerVar))));
            if (max > 1) {
                LeftFrontPowerVar = (float)Range.scale(LeftFrontPowerVar, -max, max, -1, 1);
                LeftBackPowerVar = (float)Range.scale(LeftBackPowerVar, -max, max, -1, 1);
                RightFrontPowerVar = (float)Range.scale(RightFrontPowerVar, -max, max, -1, 1);
                RightBackPowerVar = (float)Range.scale(RightBackPowerVar, -max, max, -1, 1);
            }

            SetDrivePower(RightFrontPowerVar,RightBackPowerVar,LeftFrontPowerVar,LeftBackPowerVar);

            //if right bumper is pressed then grab with hugger
            if(gamepad1.right_bumper) {
                HuggerRight.setPosition(0.70);
                HuggerLeft.setPosition(0.3);
            }

            //if left bumper is pressed then release with hugger
            if (gamepad1.left_bumper) {
                HuggerRight.setPosition(0.85);
                HuggerLeft.setPosition(0.15);
            }

            if (gamepad1.dpad_up) {
                ArmPos += 1;
            } else if (gamepad1.dpad_down) {
                ArmPos -= 1;
            }

            if (ArmPos > 4) {
                ArmPos = 4;
            } else if (ArmPos < 1) {
                ArmPos = 1;
            }

            if (ArmPos == 1) {
                GlyphArm.setTargetPosition(0);
            } else if (ArmPos == 2) {
                //GlyphArm.setTargetPosition();
            } else if (ArmPos == 3) {
                //GlyphArm.setTargetPosition();
            } else {
                //GlyphArm.setTargetPosition();
            }
            GlyphArm.setPower(0.7);


            //Update Telemetry

            //Show the elapsed game time
            telemetry.addData("Status", "Run Time: " + runtime.toString());

            //Show the Motor Values
            telemetry.addData("Front Motors", "left (%.2f), right (%.2f)", LeftFrontPowerVar, RightFrontPowerVar);
            telemetry.addData("Back Motors", "left (%.2f), right (%.2f)", LeftBackPowerVar, RightBackPowerVar);

            telemetry.update();
        }
    }
}
