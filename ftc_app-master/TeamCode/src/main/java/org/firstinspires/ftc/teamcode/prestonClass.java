package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

/**
 * Created by FTC on 12/18/2017.
 */
@TeleOp(name = "preston", group = "Example")
public class prestonClass extends OpMode
//@Disabled
{
    DigitalChannel digitalTouch;  // Hardware Device Object
    //declares motors
    DcMotor frontright;
    DcMotor frontleft;
    DcMotor backright;
    DcMotor backleft;
    double throttle;

    //this code will run once after init is pressed
    @Override
    public void init () {

        //grab motor names from the configuration file
        frontright = hardwareMap.dcMotor.get("rf");
        frontleft = hardwareMap.dcMotor.get("lf");
        backright = hardwareMap.dcMotor.get("rb");
        backleft = hardwareMap.dcMotor.get("lb");

        //reverse the right motors due to using AndyMark motors on direct drive
        frontleft.setDirection(DcMotor.Direction.REVERSE);
        backleft.setDirection(DcMotor.Direction.REVERSE);

        //set the motors to run closed loop PID
        //frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        digitalTouch = hardwareMap.get(DigitalChannel.class, "sensor_digital");

        // set the digital channel to input.
        digitalTouch.setMode(DigitalChannel.Mode.INPUT);

    }

    //this code will run continually after start is pressed
    @Override
    public void loop () {

        if(digitalTouch.getState() == true){

            throttle = 0;
        } else {

            throttle = 1;
        }
         double steering = gamepad1.left_stick_x;


        //sets drives the bot with a basic tank drive control scheme

        //left joystick corresponds to left motors
        //right joystick corresponds to right motors
        frontright.setPower(throttle - steering);
        backright.setPower(throttle - steering);
        frontleft.setPower(throttle + steering);
        backleft.setPower(throttle + steering);

        telemetry.addData("right joystick", gamepad1.right_stick_y);
        telemetry.addData("left joystick", gamepad1.left_stick_y);

        telemetry.addData("right front", frontright.getPower());
        telemetry.addData("right back", backright.getPower());
        telemetry.addData("left front", frontright.getPower());
        telemetry.addData("left back", backleft.getPower());

        telemetry.update();
    }

}

        /**
         * The REV Robotics Touch Sensor
         * is treated as a digital channel.  It is HIGH if the button is unpressed.
         * It pulls LOW if the button is pressed.
         *
         * Also, when you connect a REV Robotics Touch Sensor to the digital I/O port on the
         * Expansion Hub using a 4-wire JST cable, the second pin gets connected to the Touch Sensor.
         * The lower (first) pin stays unconnected.*
         */




