package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by isaac.blandin on 10/31/17.
 */

@TeleOp(name = "CrabExample")
@Disabled
public class ExampleMecanum extends LinearOpMode {


    //declare motors
    DcMotor RightF;
    DcMotor LeftF;
    DcMotor RightB;
    DcMotor LeftB;

    //declare joystick variables
    double x1;
    double y1;
    double x2;

    //declare variables to store motor values
    double rightFront;
    double leftFront;
    double rightBack;
    double leftBack;

    public void runOpMode() {

        //call for motors
        RightF = hardwareMap.dcMotor.get("front_right");
        LeftF = hardwareMap.dcMotor.get("front_left");
        RightB = hardwareMap.dcMotor.get("rear_right");
        LeftB = hardwareMap.dcMotor.get("rear_left");

        //reverse the right motors
        RightF.setDirection(DcMotor.Direction.REVERSE);
        RightB.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {


            //assign variables to the joystick values
            x1 = gamepad1.left_stick_x;
            y1 = -gamepad1.left_stick_y;
            x2 = gamepad1.right_stick_x;

            double max;


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

//limit the motor values to the acceptable range by motor
            max = Math.abs(leftFront);
            if (Math.abs(rightFront)>max){
                max = Math.abs(rightFront);
            }
            if (Math.abs(leftBack)>max){
                max = Math.abs(leftBack);
            }
            if (Math.abs(rightBack)>max){
                max = Math.abs(rightBack);
            }
            if (max > 1) {
                leftFront/=max;
                rightFront/=max;
                leftBack/=max;
                rightBack/=max;
            }

            RightF.setPower(rightFront);
            RightB.setPower(rightBack);
            LeftF.setPower(leftFront);
            LeftB.setPower(leftBack);

            }

            ComposeTelemetry();
        }



    public void ComposeTelemetry (){
        //print joystick values
        telemetry.addData("drive", y1);
        telemetry.addData("strafe", x1);
        telemetry.addData("turn", x2);

        //print motor values
        telemetry.addData("Right Front", RightF);
        telemetry.addData("Left Front", LeftF);
        telemetry.addData("Right Back", RightB);
        telemetry.addData("Left Back", LeftB);

        telemetry.update();
    }
}
