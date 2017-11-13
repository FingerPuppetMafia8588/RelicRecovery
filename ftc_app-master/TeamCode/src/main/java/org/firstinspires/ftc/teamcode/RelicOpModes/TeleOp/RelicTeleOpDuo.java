package org.firstinspires.ftc.teamcode.RelicOpModes.TeleOp;

import com.qualcomm.hardware.ams.AMSColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicHardware;
import org.firstinspires.ftc.teamcode.enums.RobotRunType;

/**
 * Created by isaac.blandin on 11/13/17.
 */

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

            // Scale the movement variables so they no not exceed the range of -1 to 1
            double max = Math.max(Math.abs(LeftFrontPowerVar), Math.max(Math.abs(LeftBackPowerVar),
                    Math.max(Math.abs(RightFrontPowerVar), Math.abs(RightBackPowerVar))));
            if (max > 1) {
                LeftFrontPowerVar = (float) Range.scale(LeftFrontPowerVar, -max, max, -1, 1);
                LeftBackPowerVar = (float)Range.scale(LeftBackPowerVar, -max, max, -1, 1);
                RightFrontPowerVar = (float)Range.scale(RightFrontPowerVar, -max, max, -1, 1);
                RightBackPowerVar = (float)Range.scale(RightBackPowerVar, -max, max, -1, 1);
            }

            //adds a speed shifting button to toggle a slow down of the drive
            if(gamepad1.a) {
                if (!speedShift) {
                    speedShift = true;
                    sleep(30);
                } else {
                    speedShift = false;
                    sleep(30);
                }
            }


            //reduces speed to half if speed shift is on
            if (!speedShift) {
                SetDrivePower(RightFrontPowerVar,RightBackPowerVar,LeftFrontPowerVar,LeftBackPowerVar);
            } else {
                SetDrivePower(RightFrontPowerVar/2,RightBackPowerVar/2,LeftFrontPowerVar/2,LeftBackPowerVar/2);
            }

            //reduces speed to one-third power if x is being actively pressed down
            if (gamepad1.x) {
                SetDrivePower(RightFrontPowerVar/3, RightBackPowerVar/3, LeftFrontPowerVar/3, LeftBackPowerVar/3);
            }


        }

    }

    public void controller2 () {
        
    }

}
