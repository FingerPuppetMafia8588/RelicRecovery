package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by FTC on 10/2/2017.
 */

@TeleOp (name = "tilerunner", group = "Example")
public class TileRunner extends OpMode {
    DcMotor frontright;
    DcMotor frontleft;
    DcMotor backright;
    DcMotor backleft;

    @Override
    public void init () {
        frontright = hardwareMap.dcMotor.get("rf");
        frontleft = hardwareMap.dcMotor.get("lf");
        backright = hardwareMap.dcMotor.get("rb");
        backleft = hardwareMap.dcMotor.get("lb");

        frontright.setDirection(DcMotor.Direction.REVERSE);
        backright.setDirection(DcMotor.Direction.REVERSE);

        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop () {
        frontright.setPower(-gamepad1.right_stick_y);
        backright.setPower(-gamepad1.right_stick_y);

        frontleft.setPower(-gamepad1.left_stick_y);
        backleft.setPower(-gamepad1.left_stick_y);
    }
}
