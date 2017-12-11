package org.firstinspires.ftc.teamcode.RelicOpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicAutonomousBase;
import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicHardware;
import org.firstinspires.ftc.teamcode.enums.RobotRunType;

/**
 * Created by isaac.blandin on 12/2/17.
 */

@Disabled
@TeleOp(name = "Field Centric")
public class FieldCentric extends RelicHardware {

    public void runOpMode() {
       //initialize hardware
        initRobot(RobotRunType.TELEOP);

        //calibrate gryo
        imu.calibrate();

        double frontRight;
        double frontLeft;
        double rearRight;
        double rearLeft;

        waitForStart();

        while (imu.isCalibrating()) {
            //wait to start if gyro is not aligned
        }
        //main loop
        while (opModeIsActive()) {

            if (gamepad1.a) {
                imu.resetZAxisIntegrator();
            }

            double forward;
            double right;
            double clockwise;
            double temp;
            double theta;
            double max;

            forward = -gamepad1.left_stick_y;
            right = gamepad1.left_stick_x;
            clockwise = gamepad1.right_stick_x;

            theta = imu.getIntegratedZValue()/180*Math.PI;

            //reset variables
            frontRight = 0;
            frontLeft = 0;
            rearRight = 0;
            rearLeft = 0;

            //reduce turning sensitivity
            clockwise = clockwise*0.7;

            //field centric conversion
            temp = forward*Math.cos(theta) - right*Math.sin(theta);
            right = forward*Math.sin(theta) + right*Math.cos(theta);
            forward = temp;

            //handle forward/back movement
            frontLeft += forward;
            frontRight += forward;
            rearLeft += forward;
            rearRight += forward;

            //handle strafing movement
            frontLeft += right;
            frontRight -= right;
            rearLeft -= right;
            rearRight += right;

            //handle turning movement
            frontLeft += clockwise;
            frontRight -= clockwise;
            rearLeft += clockwise;
            rearRight -= clockwise;

            //limit the motor values to the acceptable range by motor
            max = Math.abs(frontLeft);
            if (Math.abs(frontRight)>max){
                max = Math.abs(frontRight);
            }
            if (Math.abs(rearLeft)>max){
                max = Math.abs(rearLeft);
            }
            if (Math.abs(rearRight)>max){
                max = Math.abs(rearRight);
            }
            if (max > 1) {
                frontLeft/=max;
                frontRight/=max;
                rearLeft/=max;
                rearRight/=max;
            }

            SetDrivePower(frontRight, rearRight, frontLeft, rearLeft);

            //give telemetry feedback for the drive motors
            telemetry.addData("Front Right", RightFront.getPower());
            telemetry.addData("Front Left", LeftFront.getPower());
            telemetry.addData("Rear Right", RightBack.getPower());
            telemetry.addData("Rear Left", LeftBack.getPower());

            telemetry.update();
        }

        stop();
    }
}
