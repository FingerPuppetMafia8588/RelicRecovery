package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by FTC on 10/2/2017.
 */

@TeleOp (name = "Treads", group = "Example")
public class TreadsExample extends OpMode {

    DcMotor Left;
    DcMotor Right;

    @Override
    public void init () {
        Left = hardwareMap.dcMotor.get("l");
        Right = hardwareMap.dcMotor.get("r");
    }

    @Override
    public void loop () {
        Left.setPower(-gamepad1.left_stick_y);
        Right.setPower(-gamepad1.right_stick_y);
    }
}
