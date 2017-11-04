package org.firstinspires.ftc.teamcode.RelicOpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.RelicOpModes.Tests.GyroTurn;
import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicAutonomousBase;
import org.firstinspires.ftc.teamcode.enums.CryptoKey;
import org.firstinspires.ftc.teamcode.enums.RobotRunType;

/**
 * Created by FTC on 10/26/2017.
 */

@Autonomous (name = "Comp Autonomous")
public class TestAuto extends RelicAutonomousBase {

    @Override
    public void runOpMode () throws InterruptedException {
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

        
        waitForStart();


        Grab();
        Wait(1);

        GlyphArm.setPower(0.2);
        Wait(1);
        GlyphArm.setPower(0);

        while (imu.isCalibrating()) {}
        //prepare vuforia
        relicTrackables.activate();
        RelicRecoveryVuMark VuMark = RelicRecoveryVuMark.from(relicTemplate);

        int key = 0;

        //wait for a vumark to be identified
        while(VuMark == RelicRecoveryVuMark.UNKNOWN) {
            VuMark = RelicRecoveryVuMark.from(relicTemplate);
        }

        //store the results in the key enum
        while( key == 0) {
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

        if (key == 1) {
            Drive(0.3,23);
            Strafe(1, 3);
        } else if (key == 2) {
            Drive(0.3, 23);
            Strafe(1, 2);
        } else if (key == 3){
            Drive(0.3, 23);
            Strafe(1, 1);

        }


    }
}
