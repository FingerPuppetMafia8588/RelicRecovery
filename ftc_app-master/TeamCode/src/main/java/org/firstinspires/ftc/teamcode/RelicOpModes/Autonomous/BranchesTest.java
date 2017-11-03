package org.firstinspires.ftc.teamcode.RelicOpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicAutonomousBase;
import org.firstinspires.ftc.teamcode.enums.RobotRunType;

/**
 * Created by isaac.blandin on 11/1/17.
 */
@Autonomous(name = "branches")
public class BranchesTest extends RelicAutonomousBase {

public void runOpMode() throws InterruptedException {

    //initialize the robot hardware
    initRobot(RobotRunType.AUTONOMOUS);

    //intializes Vuforia
    int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
    VuforiaLocalizer.Parameters Vuforiaparameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

    //grabs vuforia license key
    Vuforiaparameters.vuforiaLicenseKey = "ARAFwpf/////AAAAGSy8w0755U20up5PN2O8O1UBOkQt7SoigmM2wM85m9H3Hm7nUAB5uSxG1BuS+hHJ4e9FkQ7WBf9DbjfEGKvBJyWxxOS3n8CXwmwolW4MU8zJ1sXm22xWOWVaThoTIPQE766o5Z7AoIYSTI+SnjQJms0rJROj/zLrj8awnHWtKPtjJxMWYXSOC7G00NNQ444hFW2AX1GVyBe2jijIlqirmJbrrRC07TgwPjkX1yFVvSA0A4ffJiGxktDVOuy0hosng6Ce50qxP8AjfSutnz4DENWBbU7AeZBHydm/b+JxQ9CUnmYSSt9q3czMBGOYOTIfYUt+d1ScLzQdTNFGpx2/2bMPLL5H+qC5PssXa+1t6j8a\n";

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

    while(!teampicked){
        if (gamepad2.x){
            telemetry.addData("Team", "Blue");
            telemetry.update();

            teampicked = true;
            isblue = true;
        } else if (gamepad2.b) {
            telemetry.addData("Team", "Red");
            telemetry.update();

            teampicked = true;
            isblue = false;
        }
    }

    //wait for user inout on which balance stone is being used
    // a for front, y for back
    telemetry.addLine("press A for front balance stone or Y for back stone");
    telemetry.update();

    while(!balancepicked){
        if (gamepad2.a) {
            telemetry.addData("Balance", "Front");
            telemetry.update();

            balancepicked = true;
            isfront = true;
        } else if (gamepad2.y) {
            telemetry.addData("Balance", "Back");
            telemetry.update();

            balancepicked = true;
            isfront = false;
        }
    }

    //all code after this runs after start is pressed on the robot controller
    waitForStart();
    while (imu.isCalibrating()){
        //wait for gyro to be fully calibrated
    }

    if(isblue && isfront) {
        //this code runs if the robot is on the front blue balance stone


    } else if (isblue && !isfront){
        //this code runs if the robot is on the back blue balance stone


    } else if (!isblue && isfront) {
        //this code runs if the robot is on the front red balance stone


    } else {
        //this code runs if the robot is on the back red balance stone


    }
}
}
