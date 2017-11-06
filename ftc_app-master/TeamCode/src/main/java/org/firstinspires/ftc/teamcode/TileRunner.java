package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by FTC on 10/2/2017.
 */

@TeleOp (name = "tilerunner", group = "Example")
@Disabled
public class TileRunner extends OpMode {

    //declares motors
    DcMotor frontright;
    DcMotor frontleft;
    DcMotor backright;
    DcMotor backleft;

    //this code will run once after init is pressed
    @Override
    public void init () {

        //grab motor names from the configuration file
        frontright = hardwareMap.dcMotor.get("rf");
        frontleft = hardwareMap.dcMotor.get("lf");
        backright = hardwareMap.dcMotor.get("rb");
        backleft = hardwareMap.dcMotor.get("lb");

        //reverse the right motors due to using AndyMark motors on direct drive
        frontright.setDirection(DcMotor.Direction.REVERSE);
        backright.setDirection(DcMotor.Direction.REVERSE);

        //set the motors to run closed loop PID
        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //this code will run continually after start is pressed
    @Override
    public void loop () {

        //sets drives the bot with a basic tank drive control scheme

        //left joystick corresponds to left motors
        //right joystick corresponds to right motors
        frontright.setPower(-gamepad1.right_stick_y);
        backright.setPower(-gamepad1.right_stick_y);
        frontleft.setPower(-gamepad1.left_stick_y);
        backleft.setPower(-gamepad1.left_stick_y);

        telemetry.addData("right joystick", gamepad1.right_stick_y);
        telemetry.addData("left joystick", gamepad1.left_stick_y);

        telemetry.addData("right front", frontright.getPower());
        telemetry.addData("right back", backright.getPower());
        telemetry.addData("left front", frontright.getPower());
        telemetry.addData("left back", backleft.getPower());

        telemetry.update();
    }
}
