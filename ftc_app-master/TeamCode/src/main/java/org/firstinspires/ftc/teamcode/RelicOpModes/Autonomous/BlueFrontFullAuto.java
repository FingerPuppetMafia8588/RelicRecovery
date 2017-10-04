package org.firstinspires.ftc.teamcode.RelicOpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase.RelicAutonomousBase;
import org.firstinspires.ftc.teamcode.enums.RobotRunType;

/**
 * Created by FTC on 10/2/2017.
 */

@Autonomous (name = "Full Front Blue", group = "BlueAuto")
public class BlueFrontFullAuto extends RelicAutonomousBase {
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

        // all code after this will occur after the start button is pressed
        waitForStart();
        while (!gyro.isGyroCalibrated()) {
            //wait to continue code if gyro is not calibrated
        }

        //prepare vuforia
        relicTrackables.activate();
        RelicRecoveryVuMark VuMark = RelicRecoveryVuMark.from(relicTemplate);

        //wait for a vumark to be identified
        while(VuMark == RelicRecoveryVuMark.UNKNOWN) {
            VuMark = RelicRecoveryVuMark.from(relicTemplate);
        }
    }
}
