package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by FTC on 10/2/2017.
 */

@TeleOp (name = "Treads", group = "Example")
public class TreadsExample extends OpMode {

    //declares the two drive motors
    DcMotor Left;
    DcMotor Right;

    //this code runs once after init is pressed
    @Override
    public void init () {

        //grabs motor names from the configuration file
        Left = hardwareMap.dcMotor.get("l");
        Right = hardwareMap.dcMotor.get("r");
    }

    //this code runs continually after start is pressed
    @Override
    public void loop () {

        //sets up a basic tank drive control scheme

        //left joystick corresponds with left motors
        Left.setPower(-gamepad1.left_stick_y);
        //right joystick corresponds with right motors
        Right.setPower(-gamepad1.right_stick_y);
    }
}
