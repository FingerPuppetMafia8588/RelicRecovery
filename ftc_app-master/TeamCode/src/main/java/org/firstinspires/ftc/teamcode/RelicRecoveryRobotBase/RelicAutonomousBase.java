package org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.enums.DirectionTurn;
import org.firstinspires.ftc.teamcode.enums.JewelColor;

import java.util.Locale;

/**
 * Created by FTC on 10/2/2017.
 */

public abstract class RelicAutonomousBase extends RelicHardware {
    //////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////Movement/////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////


    //Applies a power value to all of the motors
    public void Drive (double power) {
        SetDrivePower(power, power, power, power);
    }

    //drives to a certain distance in inches at a set power
    public void Drive (double power, double inches){

        //set encoder values back to 0
        ResetEncoders();

        //turn on motors to power
        Drive(power);

        //convert to encoder ticks
        inches = inches*28/12.566;

        boolean hasCorrected = false;

        //wait until target is reached & make vary speeds for more accuracy
        while(opModeIsActive() && getLeftAbsolute() <= inches && getRightAbsolute() <= inches) {
            if (!hasCorrected && getLeftAbsolute() >= (inches / 2)) { // Slowing down once we read 1/2 to our target
                double newPower;
                if (power > 0) {
                    newPower = power - (power / 2);
                } else {
                    newPower = power + (power / 2);
                }

                Drive(newPower);

                hasCorrected = true;
            }
        }

        //stop the motors
        Stop();
    }

    //applies power to wheels for correct strafing
    public void Strafe(double power) {
        SetDrivePower(-power, power, power, -power);
    }

    //strafes for a set power and distance in rotations
    public void Strafe (double power, double rotations) {

        //set encoder values to 0
        ResetEncoders();

        //turn motors on in strafe directions
        Strafe(power);

        //convert to encoder ticks per rotation
        rotations = rotations*28;

        //wait until the target is reached
        while (opModeIsActive() && getLeftAbsolute() <= rotations && getRightAbsolute() <= rotations) {

        }

        //Stop Motor
        Stop();
    }

    // turns the robot to a certain gyro heading with a set power.
    public void TurnToHeading(double power, double target, boolean centerTurn) {

        LeftFront.setPower(power);
        LeftBack.setPower(power);
        if (centerTurn) {
            RightFront.setPower(-power);
            RightBack.setPower(-power);
        }

        DirectionTurn direction;
        if (power < 0) {
            direction = DirectionTurn.LEFT;
        } else {
            direction = DirectionTurn.RIGHT;
        }

        if (direction == DirectionTurn.LEFT) {
            while (opModeIsActive() && angles.thirdAngle < target) {
                printGyroHeading();
            }
            Stop();
        } else {
            while (opModeIsActive() && angles.thirdAngle > target) {
                printGyroHeading();
            }
            Stop();
        }

    }

    protected void Wait(double seconds) {
        ElapsedTime t = new ElapsedTime(System.nanoTime());
        while (opModeIsActive() && t.time() <= seconds) {

        }
    }



    //////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////Getters//////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////

    // gets the color of the jewels from the right color sensor
    public JewelColor getJewelColorRight() {
        if(RightColor.red() > RightColor.blue()){
            return JewelColor.RED;
        } else if (RightColor.blue() > RightColor.red()) {
            return JewelColor.BLUE;
        }

        return null;
    }

    // gets the color of the jewels from the left Color sensor
    public JewelColor getJewelColorLeft () {
        if(LeftColor.red() > LeftColor.blue()) {
            return JewelColor.RED;
        } else if (LeftColor.blue() > LeftColor.red()){
            return JewelColor.BLUE;
        }

        return null;
    }

    //gets the absolute value of the right encoder value
    public int getRightAbsolute() {
        return Math.abs(RightFront.getCurrentPosition());
    }

    //gets absolute value of the left encoder value
    public int getLeftAbsolute() {
        return Math.abs(LeftFront.getCurrentPosition());
    }


    //////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////Encoders//////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////

    //sets all of the encoder values back to 0
    public void ResetEncoders(){

        RightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        while(opModeIsActive() && RightFront.getCurrentPosition() != 0 && RightBack.getCurrentPosition() != 0
                && LeftFront.getCurrentPosition() != 0 && LeftBack.getCurrentPosition() != 0 ) {

        }

        RightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while(opModeIsActive() && RightFront.getMode() != DcMotor.RunMode.RUN_USING_ENCODER && RightBack.getMode() != DcMotor.RunMode.RUN_USING_ENCODER
                && LeftFront.getMode() != DcMotor.RunMode.RUN_USING_ENCODER && LeftBack.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {


        }

    }


    ////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////Telemetry////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////

    //gives telemetry data on the right color sensor
    public void printRightColorTelemetry() {
        double r = RightColor.red();
        double g = RightColor.green();
        double b = RightColor.blue();
        String rgb = "" + r + ", " + g + ", " + b;

        telemetry.addData("RGB",rgb);
        telemetry.update();
    }

    //gives telemetry data on the left color sensor
    public void printLeftColorTelemetry() {
        double r = LeftColor.red();
        double g = LeftColor.green();
        double b = LeftColor.blue();
        String rgb = "" + r + ", " + g + ", " + b;

        telemetry.addData("RGB", rgb);
        telemetry.update();
    }

    // gives telemetry data on the current heading of the bot
    public void printGyroHeading () {
        telemetry.addData("heading", angles.thirdAngle);
        telemetry.update();
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////Formatting///////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
}
