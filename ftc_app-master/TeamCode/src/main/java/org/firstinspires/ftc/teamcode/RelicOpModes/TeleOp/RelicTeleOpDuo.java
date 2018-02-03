package org.firstinspires.ftc.teamcode.RelicOpModes.TeleOp;

import com.qualcomm.hardware.ams.AMSColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicHardware;
import org.firstinspires.ftc.teamcode.enums.RobotRunType;

/**
 * Created by isaac.blandin on 11/13/17.
 */

@TeleOp(name = "Comp Duo", group = "RelicRecovery")
public class RelicTeleOpDuo extends RelicHardware {

    public void runOpMode() {
        initRobot(RobotRunType.TELEOP);
        GlyphArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //creates time elapsed variable for usage in telemetry
        ElapsedTime runtime = new ElapsedTime();

        //sets the drive speed shifter variable to off by default
        boolean speedShift = false;

        //all code after this line will not occur until the start button is pressed
        waitForStart();

        //start the timer for runtime for telemetry
        runtime.reset();

        // declares variables to be used
        float RightFrontPowerVar, RightBackPowerVar, LeftFrontPowerVar, LeftBackPowerVar;

        while(opModeIsActive()) {

            //declares variables for the joystick variables to be used later
            double x1;
            double y1;
            double x2;

            double max;

            x1 = gamepad1.left_stick_x;
            y1 = -gamepad1.left_stick_y;
            x2 = gamepad1.right_stick_x;

            // Reset variables
            LeftFrontPowerVar = 0;
            LeftBackPowerVar = 0;
            RightFrontPowerVar = 0;
            RightBackPowerVar = 0;

            // Handle regular movement
            LeftFrontPowerVar += y1;
            LeftBackPowerVar += y1;
            RightFrontPowerVar += y1;
            RightBackPowerVar += y1;

            // Handle strafing movement
            LeftFrontPowerVar += x1;
            LeftBackPowerVar -= x1;
            RightFrontPowerVar -= x1;
            RightBackPowerVar += x1;

            // Handle turning movement
            LeftFrontPowerVar += x2;
            LeftBackPowerVar += x2;
            RightFrontPowerVar -= x2;
            RightBackPowerVar -= x2;

            //limit the motor values to the acceptable range by motor
            max = Math.abs(LeftFrontPowerVar);
            if (Math.abs(RightFrontPowerVar)>max){
                max = Math.abs(RightFrontPowerVar);
            }
            if (Math.abs(LeftBackPowerVar)>max){
                max = Math.abs(LeftBackPowerVar);
            }
            if (Math.abs(RightBackPowerVar)>max){
                max = Math.abs(RightBackPowerVar);
            }
            if (max > 1) {
                LeftFrontPowerVar/=max;
                RightFrontPowerVar/=max;
                LeftBackPowerVar/=max;
                RightBackPowerVar/=max;
            }

            //reduces speed to half if speed shift is on
            if (!speedShift) {
                SetDrivePower(RightFrontPowerVar,RightBackPowerVar,LeftFrontPowerVar,LeftBackPowerVar);
            } else {
                SetDrivePower(RightFrontPowerVar/2,RightBackPowerVar/2,LeftFrontPowerVar/2,LeftBackPowerVar/2);
            }

            //keep jewel arms in place
            //JewelLeft.setPosition(0.4);
            //JewelRight.setPosition(0.60);

            //check for controller two input
            controller2();

            ///////////////////////////////////////////////////////////////////
            ////////////////////Telemetry//////////////////////////////////////
            ///////////////////////////////////////////////////////////////////

            //Show the elapsed game time
            telemetry.addData("Status", "Run Time: " + runtime.toString());

            //Shows the Motor Values for the DriveTrain
            telemetry.addData("Front Motors", "left (%.2f), right (%.2f)", LeftFrontPowerVar, RightFrontPowerVar);
            telemetry.addData("Back Motors", "left (%.2f), right (%.2f)", LeftBackPowerVar, RightBackPowerVar);

            //shows where the glyph arm is
            telemetry.addData("ArmPosValue", GlyphArm.getCurrentPosition());
            telemetry.addData("ArmPosition", ArmPos);

            //shows the current position of the hugger servos
            telemetry.addData("Right Hugger", HuggerRight.getPosition());
            telemetry.addData("Left Hugger", HuggerLeft.getPosition());

            telemetry.addData("heading", imu.getIntegratedZValue());

            telemetry.update();

        }
        stop();

    }

    public void controller2 () {

        double glyph;

        //prevent the hugger from running itself off of its guides.
        if (GlyphArm.getCurrentPosition() > 3050) {
            glyph = -0.1;
        } else if (touch.getState() == false){
            glyph = Math.pow(gamepad2.left_stick_y, 2);
        }else {
            glyph = -gamepad2.left_stick_y;
        }

        //use the left joystick to raise and lower the arm
        GlyphArm.setPower(glyph);

        //if a is pressed, grab with the hugger
        if(gamepad2.a) {
            HuggerLeft.setPosition(0.33);
            HuggerLeftBottom.setPosition(0.33);
            HuggerRight.setPosition(0.67);
            HuggerRightBottom.setPosition(0.67);
        }
        //if b is pressed, partially open
        if (gamepad2.b) {
            HuggerLeft.setPosition(0.15);
            HuggerLeftBottom.setPosition(0.15);
            HuggerRight.setPosition(0.85);
            HuggerRightBottom.setPosition(0.85);
        }


            RelicArmExt.setPower(gamepad2.right_stick_y);
        

    }

}
