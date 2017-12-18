package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by isaac.blandin on 12/13/17.
 */

//this opmode is used for driving a 2 motor drive robot with a single joystick
// rather than the typical 2 joystick tank drive
@Disabled
@TeleOp(name = "Joe Single Joystick")
public class JoeClass extends LinearOpMode {

    //declare motors
    DcMotor left;
    DcMotor right;

    public void runOpMode() {

        //assign motor names
        left = hardwareMap.dcMotor.get("l");
        right = hardwareMap.dcMotor.get("r");

        right.setDirection(DcMotorSimple.Direction.REVERSE);

        //declare variables
        double throttle;
        double steering;
        double rightPower;
        double leftPower;

        waitForStart();
        while (opModeIsActive()){

            //assign joysticks to variables
            throttle = -gamepad1.left_stick_y;
            steering = gamepad1.left_stick_x;

            //single joystick equation
            rightPower = throttle - steering;
            leftPower = throttle + steering;

            //add power to motors
            right.setPower(rightPower);
            left.setPower(leftPower);
        }
        stop();
    }

}
