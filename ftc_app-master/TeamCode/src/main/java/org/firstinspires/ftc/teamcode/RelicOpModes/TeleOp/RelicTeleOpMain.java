package org.firstinspires.ftc.teamcode.RelicOpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicHardware;
import org.firstinspires.ftc.teamcode.enums.RobotRunType;

/**
 * Created by FTC on 10/2/2017.
 */

@TeleOp (name = "Comp TeleOp", group = "RelicRecovery")
public class RelicTeleOpMain extends RelicHardware {

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
        float RightPower, LeftPower;
        float strafe;

        while (opModeIsActive()){



            //creates tank drive control scheme
            float left = -gamepad1.left_stick_y;
            float right = -gamepad1.right_stick_y;


            //resets variables
            RightFrontPowerVar = 0;
            RightBackPowerVar = 0;
            LeftFrontPowerVar = 0;
            LeftBackPowerVar = 0;
            RightPower = 0;
            LeftPower = 0;


            RightPower += right;
            LeftPower += left;

            // uses the bumpers for strafing left or right with the robot
            if (gamepad1.right_bumper){
                strafe = 1;
            } else if (gamepad1.left_bumper) {
                strafe = -1;
            } else {
                strafe = 0;
            }

            //Makes sure the motor valuables do not exceed the usable range of -1 to 1
            //when using forward/backward motion with strafing
            RightFrontPowerVar += Range.clip(RightPower + strafe, -1, 1);
            RightBackPowerVar += Range.clip(RightPower - strafe, -1, 1);
            LeftFrontPowerVar += Range.clip(LeftPower - strafe, -1, 1);
            LeftBackPowerVar += Range.clip(LeftPower + strafe, -1, 1);

            SetDrivePower(RightFrontPowerVar, RightBackPowerVar, LeftFrontPowerVar, LeftBackPowerVar);

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

