package org.firstinspires.ftc.teamcode.RelicOpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicAutonomousBase;
import org.firstinspires.ftc.teamcode.enums.RobotRunType;

/**
 * Created by FTC on 11/10/2017.
 */
@Autonomous(name = "jewelAuto", group = "RelicRecovery")
public class jewelAuto extends RelicAutonomousBase {

    public void runOpMode() throws InterruptedException {

        //initialize the robot hardware
        initRobot(RobotRunType.AUTONOMOUS);

        ElapsedTime runtime = new ElapsedTime();

        GlyphArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //intializes Vuforia
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters Vuforiaparameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        //grabs vuforia license key
        Vuforiaparameters.vuforiaLicenseKey = "6ARAFwpf/////AAAAGSy8w0755U20up5PN2O8O1UBOkQt7SoigmM2wM85m9H3Hm7nUAB5uSxG1BuS+hHJ4e9FkQ7WBf9DbjfEGKvBJyWxxOS3n8CXwmwolW4MU8zJ1sXm22xWOWVaThoTIPQE766o5Z7AoIYSTI+SnjQJms0rJROj/zLrj8awnHWtKPtjJxMWYXSOC7G00NNQ444hFW2AX1GVyBe2jijIlqirmJbrrRC07TgwPjkX1yFVvSA0A4ffJiGxktDVOuy0hosng6Ce50qxP8AjfSutnz4DENWBbU7AeZBHydm/b+JxQ9CUnmYSSt9q3czMBGOYOTIfYUt+d1ScLzQdTNFGpx2/2bMPLL5H+qC5PssXa+1tj8a\n";

        //sets vuforia camera as back camera
        Vuforiaparameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(Vuforiaparameters);

        //grabs relic recovery vumark library
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);

        boolean teampicked = false;
        boolean balancepicked = false;

        boolean isblue = false;
        boolean isfront = false;

        //wait for user inout on team
        // x for blue, b for red
        telemetry.addLine("Press X for blue team or press B for red team");
        telemetry.update();

        while(!teampicked && !isStopRequested()){
            if (gamepad1.x){
                telemetry.addData("Team", "Blue");

                teampicked = true;
                isblue = true;
            } else if (gamepad1.b) {
                telemetry.addData("Team", "Red");

                teampicked = true;
                isblue = false;
            }
        }

        //wait for user inout on which balance stone is being used
        // a for front, y for back
        telemetry.addLine("press A for front balance stone or Y for back stone");
        telemetry.update();

        while(!balancepicked && !isStopRequested()){
            if (gamepad1.a) {
                telemetry.addData("Balance", "Front");
                telemetry.update();

                balancepicked = true;
                isfront = true;
            } else if (gamepad1.y) {
                telemetry.addData("Balance", "Back");
                telemetry.update();

                balancepicked = true;
                isfront = false;
            }

        }

        //if a long time passes between init and match start, press a to reset the gyro value to 0 to negate imu drift.
        while (!opModeIsActive() && !isStopRequested()) {
            if(gamepad1.a) {
                imu.resetZAxisIntegrator();
            }
        }
        //all code after this runs after start is pressed on the robot controller
        waitForStart();
        Grab();
        Wait(1);
        //raiseArm();
        GlyphArm.setPower(0.3);
        Wait(0.6);
        GlyphArm.setPower(0);

        runtime.reset();

        //prepare vuforia
        relicTrackables.activate();
        RelicRecoveryVuMark VuMark = RelicRecoveryVuMark.from(relicTemplate);

        int key = 0;

        //wait for a vumark to be identified
        while(VuMark == RelicRecoveryVuMark.UNKNOWN && opModeIsActive() && runtime.seconds() < 2) {
            VuMark = RelicRecoveryVuMark.from(relicTemplate);
        }

        //store the results in the key enum
        while( key == 0 && opModeIsActive() && runtime.seconds() < 3) {
            if (VuMark == RelicRecoveryVuMark.RIGHT) {
                key = 1;
            } else if (VuMark == RelicRecoveryVuMark.CENTER) {
                key = 2;
            } else if (VuMark == RelicRecoveryVuMark.LEFT) {
                key = 3;
            } else {
                key = 0;
            }
        }

        while (imu.isCalibrating()){
            //wait for gyro to be fully calibrated
        }


        if (isblue) {
          keepBlueJewel();
        } else {
          keepRedJewel();
        }

        //drive off of balance stone
        //Drive(0.4, 20);
        //reverse to line up with balance stone's edge
        quickReverse(0.25, 0.35);

        if(isblue && isfront) {
            //this code runs if the robot is on the front blue balance stone

            // make decision on how far to go forward based on vumark
            if (key == 1) {
                Drive(0.3,22);
            } else if (key == 2 || key == 0) {
                Drive(0.3, 15);

            } else if (key == 3){
                Drive(0.3, 8);
            }

            //turn 90 degrees to the left
            TurnHeading(0.4, 90);
            //drive forward into glyph storage
            Drive(0.4, 8);
            //release the glyph
            Release();
            // pull out to stop contact with glyph
            quickReverse(0.4, 0.5);
            Drive(0.4, 12);
            quickReverse(0.4, 0.3);


        } else if (isblue && !isfront){
            //this code runs if the robot is on the back blue balance stone

            Drive(0.4, 5);
            TurnHeading(0.2, -90);
            quickReverse(0.4, 1.0);
            //make decision on how far to go based on vumark
            if (key == 1) {
                Drive(0.3,32.5);
            } else if (key == 2 || key == 0) {
                Drive(0.3, 25);

            } else if (key == 3){
                Drive(0.3, 17.5);
            }
            TurnHeading(0.3, 0);
            Drive(0.4, 8);
            //release glyph
            Release();
            //pull out to stop contact with glyph
            quickReverse(0.4, 0.5);
            Drive(0.4, 12);
            quickReverse(0.4, 0.3);

        } else if (!isblue && isfront) {
            //this code runs if the robot is on the front red balance stone

            //make decision on how far to go based on vumark
            if (key == 1) {
                Drive(0.3,8.5);
            } else if (key == 2 || key == 0) {
                Drive(0.3, 15);

            } else if (key == 3){
                Drive(0.3, 21.6);
            }

            //turn 90 degrees to the right
            TurnHeading(0.4, -90);
            //drive into glyph storage
            Drive(0.4, 8);
            //release glyph
            Release();
            //pull out to stop contact with glyph
            quickReverse(0.4, 0.5);
            Drive(0.4, 12);
            quickReverse(0.4, 0.3);

        } else {
            //this code runs if the robot is on the back red balance stone

            Drive(0.4, 5);
            TurnHeading(0.2, 90);
            quickReverse(0.4, 1.0);
            //make decision on how far to go based on vumark
            if (key == 1) {
                Drive(0.3,17.5);
            } else if (key == 2 || key == 0) {
                Drive(0.3, 25);

            } else if (key == 3){
                Drive(0.3, 34);
            }
            TurnHeading(0.3, 0);
            Drive(0.4, 8);
            //release glyph
            Release();
            //pull out to stop contact with glyph
            quickReverse(0.4, 0.5);
            Drive(0.4, 12);
            quickReverse(0.4, 0.3);
        }

        //lower arm to home position for start of teleop
        //lowerArm();
        GlyphArm.setPower(-0.6);
        Wait(0.27);
        GlyphArm.setPower(0);
        stop();
    }
}
