package org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.enums.BalanceStone;
import org.firstinspires.ftc.teamcode.enums.CryptoKey;
import org.firstinspires.ftc.teamcode.enums.DirectionTurn;
import org.firstinspires.ftc.teamcode.enums.JewelColor;
import org.firstinspires.ftc.teamcode.enums.TeamColor;

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
        inches = inches*1120/12.566;

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
        StopDrive();
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
        rotations = rotations*1120;

        //wait until the target is reached
        while (opModeIsActive() && getLeftAbsolute() <= rotations && getRightAbsolute() <= rotations) {

        }

        //Stop Motor
        StopDrive();
    }

    // turns the robot to a certain number of degrees off of current position
    public void TurnDegrees(double power, int target) {

        imu.resetZAxisIntegrator();
        Wait(3);
        zValue = imu.getIntegratedZValue();

        while (Math.abs( zValue - target) > 5 && opModeIsActive()) {  //Continue while the robot direction is further than three degrees from the target

            if (zValue > target) {  //if gyro is positive, we will turn right
                SetDrivePower(-power, -power, power, power);
            }
            if (zValue < target) {
                SetDrivePower(power, power, -power, -power);
            }

            zValue = imu.getIntegratedZValue();
        }
        while (Math.abs( zValue - target) > 1 && opModeIsActive()) {  //Continue while the robot direction is further than three degrees from the target

            if (zValue > target) {  //if gyro is positive, we will turn right
                SetDrivePower(-power/2, -power/2, power/2, power/2);
            }
            if (zValue < target) {
                SetDrivePower(power/2, power/2, -power/2, -power/2);
            }

            zValue = imu.getIntegratedZValue();
        }
        StopDrive();
    }

    //turn to a gyro heading based on initial robot position
    public void TurnHeading(double power, int target){
        zValue = imu.getIntegratedZValue();

        //move at full speed unitl within 5 degrees
        while(Math.abs(zValue - target) > 5 && opModeIsActive()) {

            if (zValue > target) {  //if gyro is positive, we will turn right
                SetDrivePower(-power, -power, power, power);
            }
            if (zValue < target) {
                SetDrivePower(power, power, -power, -power);
            }

            zValue = imu.getIntegratedZValue();

        }
        //slow down to half once within 5 degrees to create more precision
        while (Math.abs( zValue - target) > 1 && opModeIsActive()) {  //Continue while the robot direction is further than three degrees from the target

            if (zValue > target) {  //if gyro is positive, we will turn right
                SetDrivePower(-power/2, -power/2, power/2, power/2);
            }
            if (zValue < target) {
                SetDrivePower(power/2, power/2, -power/2, -power/2);
            }

            zValue = imu.getIntegratedZValue();
        }
        StopDrive();
    }

    protected void Wait(double seconds) {
        ElapsedTime t = new ElapsedTime(System.nanoTime());
        while (opModeIsActive() && t.time() <= seconds) {

        }
    }


    //////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////manipulators///////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////

    //move hugger arms to grabbing position
    protected void Grab() {
        HuggerLeft.setPosition(0.27);
        HuggerRight.setPosition(0.73);
    }

    //move hugger arms to open position
    protected void Release() {
        HuggerLeft.setPosition(0);
        HuggerRight.setPosition(1);
    }

    //autonomous function for jewels when on red team
    protected void keepRedJewel() {

    }

    //autonomous function for jewels when on blue team
    protected void keepBlueJewel() {

    }

    //////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////Getters//////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////

    // gets the color of the jewels from the right color sensor
    //public JewelColor getJewelColorRight() {
     //   if(RightColor.red() > RightColor.blue()){
       //     return JewelColor.RED;
       // } else if (RightColor.blue() > RightColor.red()) {
      //      return JewelColor.BLUE;
      //  }

      //  return null;
   // }




    // gets the color of the jewels from the left Color sensor
    //public JewelColor getJewelColorLeft () {
      //  if(LeftColor.red() > LeftColor.blue()) {
        //    return JewelColor.RED;
      //  } else if (LeftColor.blue() > LeftColor.red()){
      //      return JewelColor.BLUE;
        //}

       // return null;
   // }


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
    //public void printRightColorTelemetry() {
       // double r = RightColor.red();
       // double g = RightColor.green();
       // double b = RightColor.blue();
       // String rgb = "" + r + ", " + g + ", " + b;

       // telemetry.addData("rightRGB",rgb);
       // telemetry.update();
  //  }

    //gives telemetry data on the left color sensor
    //public void printLeftColorTelemetry() {
       // double r = LeftColor.red();
      //  double g = LeftColor.green();
      //  double b = LeftColor.blue();
      //  String rgb = "" + r + ", " + g + ", " + b;

      //  telemetry.addData("leftRGB", rgb);
      //  telemetry.update();
   // }

    // gives telemetry data on the current heading of the bot
    public void printGyroHeading () {

       telemetry.addData("heading", imu.getIntegratedZValue());
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////Init Setuo///////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////


    //user input on team color
    public TeamColor assignTeamColor() {

        boolean picked = false;

        //wait until imput is selected
        while(!picked) {
            if (gamepad2.a) {
                telemetry.addData("Team", "Blue");
                telemetry.update();

                picked = true;
                return TeamColor.BLUE;
            } else if (gamepad2.b) {
                telemetry.addData("Team", "Red");
                telemetry.update();

                picked = true;
                return TeamColor.RED;
            }
        }
        return null;
    }

    //user input on Balance Stone
    public BalanceStone assignBalance(){

        boolean picked = false;

        //wait until input is selected
        while(!picked){
            if (gamepad2.y){
                telemetry.addData("Balance", "Back");
                telemetry.update();

                picked = true;
                return BalanceStone.BACK;
            } else if (gamepad2.a) {
                telemetry.addData("Balance", "Front");
                telemetry.update();

                picked = true;
                return BalanceStone.FRONT;
            }
        }
        return null;
    }
}
