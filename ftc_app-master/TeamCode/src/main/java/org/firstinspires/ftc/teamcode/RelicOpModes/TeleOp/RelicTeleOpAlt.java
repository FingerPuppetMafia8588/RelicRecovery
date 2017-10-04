package org.firstinspires.ftc.teamcode.RelicOpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicHardware;
import org.firstinspires.ftc.teamcode.enums.RobotRunType;

/**
 * Created by FTC on 10/2/2017.
 */

@TeleOp (name = "Alternate TeleOp", group = "RelicRecovery")
public class RelicTeleOpAlt extends RelicHardware {

    @Override
    public void runOpMode() throws InterruptedException{

        initRobot(RobotRunType.TELEOP);

        //creates time elapsed variable for usage in telemetry
        ElapsedTime runtime = new ElapsedTime();

        //all code after this line will not occur until the start button is pressed
        waitForStart();

        //start the timer for runtime for telemetry
        runtime.reset();

        // declares variables to be used
        float RightFrontPowerVar, RightBackPowerVar, LeftFrontPowerVar, LeftBackPowerVar;

        while (opModeIsActive()){

            //adds variables for the joystick variables to be used later
            float x1 = gamepad1.right_stick_x, y1 = -gamepad1.right_stick_y;
            float x2 = gamepad1.left_stick_x;

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
                LeftFrontPowerVar = (float)Range.scale(LeftFrontPowerVar, -max, max, -1, 1);
                LeftBackPowerVar = (float)Range.scale(LeftBackPowerVar, -max, max, -1, 1);
                RightFrontPowerVar = (float)Range.scale(RightFrontPowerVar, -max, max, -1, 1);
                RightBackPowerVar = (float)Range.scale(RightBackPowerVar, -max, max, -1, 1);
            }

            SetDrivePower(RightFrontPowerVar,RightBackPowerVar,LeftFrontPowerVar,LeftBackPowerVar);

            //Update Telemetry

            //Show the elapsed game time
            telemetry.addData("Status", "Run Time: " + runtime.toString());

            //Show the Motor Values
            telemetry.addData("Front Motors", "left (%.2f), right (%.2f)", LeftFrontPowerVar, RightFrontPowerVar);
            telemetry.addData("Back Motors", "left (%.2f), right (%.2f)", LeftBackPowerVar, RightBackPowerVar);

            telemetry.update();
        }
    }
}
