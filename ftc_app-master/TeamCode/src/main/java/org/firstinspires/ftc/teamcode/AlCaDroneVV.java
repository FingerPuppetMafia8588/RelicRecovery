package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by FTC on 10/2/2017.
 */

@TeleOp (name = "AlCaDrone", group = "Example")
public class AlCaDroneVV  extends OpMode {

    DcMotor Right;
    DcMotor Left;
    DcMotor flipper;
    DcMotor collector;
    DcMotor conveyor;

    @Override
    public void init() {
        Right = hardwareMap.dcMotor.get("rightMotor");
        Left = hardwareMap.dcMotor.get("leftMotor");
        flipper = hardwareMap.dcMotor.get("flipper");
        collector = hardwareMap.dcMotor.get("collector");
        conveyor = hardwareMap.dcMotor.get("convey");

        Right.setDirection(DcMotor.Direction.FORWARD);
        Left.setDirection(DcMotor.Direction.REVERSE);
        flipper.setDirection(DcMotor.Direction.REVERSE);
        collector.setDirection(DcMotor.Direction.FORWARD);
        conveyor.setDirection(DcMotor.Direction.FORWARD);
    }

    @Override
    public void loop() {
        Left.setPower(-gamepad1.left_stick_y);
        Right.setPower(-gamepad1.right_stick_y);

        if (gamepad2.dpad_up) {
            collector.setPower(1);
        } else if (gamepad2.dpad_down) {
            collector.setPower(-1);
        } else {
            collector.setPower(0);
        }

        if(gamepad1.y){flipper.setPower(0.5);}
        else {flipper.setPower(0);}

        if(gamepad2.right_bumper){
            conveyor.setPower(1);
        } else if(gamepad2.left_bumper) {
            conveyor.setPower(-1);
        }else {conveyor.setPower(0);
        }
    }
}
