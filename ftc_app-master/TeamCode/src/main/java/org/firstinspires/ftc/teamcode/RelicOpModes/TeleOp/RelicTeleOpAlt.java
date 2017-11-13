package org.firstinspires.ftc.teamcode.RelicOpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicHardware;
import org.firstinspires.ftc.teamcode.enums.RobotRunType;

/**
 * Created by FTC on 10/2/2017.
 */

@TeleOp (name = "Comp TeleOp", group = "RelicRecovery")
public class RelicTeleOpAlt extends RelicHardware {

    @Override
    public void runOpMode() throws InterruptedException{

        initRobot(RobotRunType.TELEOP);

        GlyphArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //creates time elapsed variable for usage in telemetry
        ElapsedTime runtime = new ElapsedTime();

        //sets the drive speed shifter variable to off by default
        boolean speedShift = false;

        //all code after this line will not occur until the start button is pressed
        waitForStart();

        //start the timer for runtime for telemetry
        runtime.reset();

        // declares variables to be used
        float RightFrontPowerVar, RightBackPowerVar, LeftFrontPowerVar, LeftBackPowerVar;

        while (opModeIsActive()){

            /////////////////////////////////////////////////////////////////////////////
            /////////////////////////Drive///////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////

            //declares variables for the joystick variables to be used later
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

            //adds a speed shifting button to toggle a slow down of the drive
            if(gamepad1.a && !speedShift) {
                speedShift = true;
                sleep(300);
            } else if (gamepad1.a && speedShift) {
                speedShift = false;
                sleep(300);
            }

            //reduces speed to half if speed shift is on
            if (!speedShift) {
                SetDrivePower(RightFrontPowerVar,RightBackPowerVar,LeftFrontPowerVar,LeftBackPowerVar);
            } else {
                SetDrivePower(RightFrontPowerVar/2,RightBackPowerVar/2,LeftFrontPowerVar/2,LeftBackPowerVar/2);
            }

            //reduces speed to one-third power if x is being actively pressed down
            if (gamepad1.x) {
                SetDrivePower(RightFrontPowerVar/3, RightBackPowerVar/3, LeftFrontPowerVar/3, LeftBackPowerVar/3);
            }

            //////////////////////////////////////////////////////////////////////////////////
            //////////////////////hugger//////////////////////////////////////////////////////
            //////////////////////////////////////////////////////////////////////////////////

            //if right bumper is pressed then grab with hugger
            if(gamepad1.right_bumper) {
                HuggerRight.setPosition(0.67);
                HuggerLeft.setPosition(0.33);
                HuggerRightBottom.setPosition(0.67);
                HuggerLeftBottom.setPosition(0.33);
            }

            //if left bumper is pressed then release with hugger
            if (gamepad1.left_bumper) {
                HuggerRight.setPosition(0.85);
                HuggerRightBottom.setPosition(0.85);
                HuggerLeft.setPosition(0.15);
                HuggerLeftBottom.setPosition(0.15);
            }

            if (gamepad1.dpad_up) {
                ArmPos += 1;
                sleep(300);
            } else if (gamepad1.dpad_down) {
                ArmPos -= 1;
                sleep(300);
            }

            if (ArmPos > 4) {
                ArmPos = 4;
            } else if (ArmPos < 1) {
                ArmPos = 1;
            }

            if (ArmPos == 1) {
                GlyphArm.setTargetPosition(0);
            } else if (ArmPos == 2) {
                GlyphArm.setTargetPosition(500);
            } else if (ArmPos == 3) {
                GlyphArm.setTargetPosition(1900);
            } else {
                GlyphArm.setTargetPosition(3000);
            }

            if(Math.abs(GlyphArm.getCurrentPosition() - GlyphArm.getTargetPosition()) > 3 && GlyphArm.getCurrentPosition() < GlyphArm.getTargetPosition()) {
                GlyphArm.setPower(0.4);
            } else if (Math.abs(GlyphArm.getCurrentPosition() - GlyphArm.getTargetPosition()) > 3 && GlyphArm.getCurrentPosition() > GlyphArm.getTargetPosition()) {
                GlyphArm.setPower(-0.3);
            } else {
                GlyphArm.setPower(0);
            }

            //keep jewel arms in place
            JewelLeft.setPosition(0.6);
            JewelRight.setPosition(0.4);


            ///////////////////////////////////////////////////////////////////
            ////////////////////Telemetry//////////////////////////////////////
            ///////////////////////////////////////////////////////////////////

            //Show the elapsed game time
            telemetry.addData("Status", "Run Time: " + runtime.toString());

            //Shows the Motor Values for the DriveTrain
            telemetry.addData("Front Motors", "left (%.2f), right (%.2f)", LeftFrontPowerVar, RightFrontPowerVar);
            telemetry.addData("Back Motors", "left (%.2f), right (%.2f)", LeftBackPowerVar, RightBackPowerVar);

            //shows where the glyph arm is
            telemetry.addData("ArmPosValue", GlyphArm.getCurrentPosition());
            telemetry.addData("ArmPosition", ArmPos);

            //shows the current position of the hugger servos
            telemetry.addData("Right Hugger", HuggerRight.getPosition());
            telemetry.addData("Left Hugger", HuggerLeft.getPosition());

            telemetry.addData("heading", imu.getIntegratedZValue());

            telemetry.update();

            idle();
        }
        stop();
    }
}
