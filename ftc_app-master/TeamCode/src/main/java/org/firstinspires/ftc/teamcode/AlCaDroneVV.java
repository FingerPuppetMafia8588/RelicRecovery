package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by FTC on 10/2/2017.
 */

@TeleOp (name = "AlCaDrone", group = "Example")
public class AlCaDroneVV  extends OpMode {

    //declares the drive motors
    DcMotor Right;
    DcMotor Left;

    //declares the utility motors
    DcMotor flipper;
    DcMotor collector;
    DcMotor conveyor;

    //this code runs once after init is pressed
    @Override
    public void init() {

        //grabs the motor names from the configuration file.
        Right = hardwareMap.dcMotor.get("rightMotor");
        Left = hardwareMap.dcMotor.get("leftMotor");
        flipper = hardwareMap.dcMotor.get("flipper");
        collector = hardwareMap.dcMotor.get("collector");
        conveyor = hardwareMap.dcMotor.get("convey");

        //reverses the left motor due to having andymark motors with a gear reduction
        Right.setDirection(DcMotor.Direction.FORWARD);
        Left.setDirection(DcMotor.Direction.REVERSE);
        flipper.setDirection(DcMotor.Direction.REVERSE);
        collector.setDirection(DcMotor.Direction.FORWARD);
        conveyor.setDirection(DcMotor.Direction.FORWARD);
    }

    //this code runs continually after start is pressed
    @Override
    public void loop() {

        //sets up a basic tank drive control scheme
        Left.setPower(-gamepad1.left_stick_y);
        Right.setPower(-gamepad1.right_stick_y);

        //creates a control for the collector with the dpad on gamepad 2

        //up on dpad brings balls in, down spits out
        if (gamepad1.right_bumper) {
            collector.setPower(1);
        } else if (gamepad1.dpad_down) {
            collector.setPower(-1);
        } else {
            collector.setPower(0);
        }

        //activates the flipper when pressing y on gamepad 1
        if(gamepad1.y){flipper.setPower(1);}
        else {flipper.setPower(0);}

        //makes conveyor correspond to bumpers.
        if(gamepad1.left_bumper){
            conveyor.setPower(-1);
        } else if(gamepad1.b) {
            conveyor.setPower(1);
        }else {conveyor.setPower(0);
        }
    }
}
