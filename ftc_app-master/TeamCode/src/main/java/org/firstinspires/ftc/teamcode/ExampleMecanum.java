package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by isaac.blandin on 10/31/17.
 */

@TeleOp(name = "CrabExample")
public class ExampleMecanum extends LinearOpMode {


    //declare motors
    DcMotor RightF;
    DcMotor LeftF;
    DcMotor RightB;
    DcMotor LeftB;

    public void runOpMode() {

        //call for motors
        RightF = hardwareMap.dcMotor.get("rf");
        LeftF = hardwareMap.dcMotor.get("lf");
        RightB = hardwareMap.dcMotor.get("rb");
        LeftB = hardwareMap.dcMotor.get("lb");

        //reverse the right motors
        RightF.setDirection(DcMotor.Direction.REVERSE);
        RightB.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {

            //declare joystick variables
            double x1;
            double y1;
            double x2;

            //declare variables to store motor values
            double rightFront;
            double leftFront;
            double rightBack;
            double leftBack;

            //assign variables to the joystick values
            x1 = gamepad1.left_stick_x;
            y1 = -gamepad1.left_stick_y;
            x2 = gamepad1.right_stick_x;

            //Reset motor variables at beginning of the loop
            rightFront = 0;
            leftFront = 0;
            rightBack = 0;
            leftBack = 0;

            //add forward/backward movement
            rightFront += y1;
            leftFront += y1;
            rightBack += y1;
            leftBack += y1;

            //add strafing movement
            rightFront -= x1;
            leftFront += x1;
            rightBack += x1;
            leftBack -= x1;

            //add turning movement
            rightFront -= x2;
            leftFront += x2;
            rightBack -= x2;
            leftBack += x2;

            // Scale the movement variables so they no not exceed the range of -1 to 1
            double max = Math.max(Math.abs(leftFront), Math.max(Math.abs(leftBack),
                    Math.max(Math.abs(rightFront), Math.abs(rightBack))));
            if (max > 1) {
                leftFront = (float) Range.scale(leftFront, -max, max, -1, 1);
                leftBack = (float)Range.scale(leftBack, -max, max, -1, 1);
                rightFront = (float)Range.scale(rightFront, -max, max, -1, 1);
                rightBack = (float)Range.scale(rightBack, -max, max, -1, 1);

                RightF.setPower(rightFront);
                LeftF.setPower(leftFront);
                RightB.setPower(rightBack);
                LeftB.setPower(leftBack);


            }

        }

    }
}
