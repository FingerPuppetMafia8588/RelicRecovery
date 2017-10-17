package org.firstinspires.ftc.teamcode.RelicRecoveryRobotBase;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.enums.RobotRunType;

/**
 * Created by FTC on 10/2/2017.
 */

public abstract class RelicHardware extends RelicRobot {
    //declares motors for the mecanum drive train
    protected DcMotor RightFront;
    protected DcMotor RightBack;
    protected DcMotor LeftFront;
    protected DcMotor LeftBack;

    //declares motors for utility arms
    protected DcMotor GlyphArm;
    protected DcMotor RelicArmElv;
    protected DcMotor RelicArmExt;

    //declares servos for jewel arms
    protected Servo JewelRight;
    protected Servo JewelLeft;

    //declares servos for hugger
    protected Servo HuggerRight;
    public Servo HuggerLeft;

    //declares variables for gyro
    protected BNO055IMU gyro;
    protected Orientation angles;

    //declares the two rev color/distance sensors
    protected ColorSensor RightColor;
    protected DistanceSensor RightDistance;
    protected ColorSensor LeftColor;
    protected DistanceSensor LeftDistance;

    //start Vuforia
    protected OpenGLMatrix lastLocation = null;
    protected VuforiaLocalizer vuforia;


    @Override
    public void initRobot (RobotRunType robotRunType) {

        //calls for motor names from RobotController for drivetrain
        RightFront = hardwareMap.dcMotor.get("rf");
        RightBack = hardwareMap.dcMotor.get("rb");
        LeftFront = hardwareMap.dcMotor.get("lf");
        LeftBack = hardwareMap.dcMotor.get("lb");

        //Reverses the right motors to match direction
        RightFront.setDirection(DcMotor.Direction.REVERSE);
        RightBack.setDirection(DcMotor.Direction.REVERSE);

        //calls for motor names from Robot Controller for utility arms
        GlyphArm = hardwareMap.dcMotor.get("glyph");
        RelicArmElv = hardwareMap.dcMotor.get("elevation");
        RelicArmExt = hardwareMap.dcMotor.get("extension");

        //calls for servo names from Robot Controller
        JewelRight = hardwareMap.servo.get("jewelR");
        JewelLeft = hardwareMap.servo.get("jewelL");

        HuggerRight = hardwareMap.servo.get("huggerR");
        HuggerLeft = hardwareMap.servo.get("huggerL");

        // calls for names from Robot Controller for color/distance sensors.
        RightColor = hardwareMap.get(ColorSensor.class, "ColorR");
        LeftColor = hardwareMap.get(ColorSensor.class, "ColorL");

        RightDistance = hardwareMap.get(DistanceSensor.class, "DistanceR");
        LeftDistance = hardwareMap.get(DistanceSensor.class, "DistanceL");







        // sets up parameters for integrated imu
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "gyro";

        //calls for the imu name from Robot Controller
        gyro = hardwareMap.get(BNO055IMU.class, "gyro");

        if(robotRunType == RobotRunType.AUTONOMOUS){
            gyro.initialize(parameters);
        }

        //creates variable to be used later
        angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    }

    // method to simplify setting drive power for the robot drive motors
    public void SetDrivePower (double RightFrontPower, double RightBackPower, double LeftFrontPower, double LeftBackPower) {
        RightFront.setPower(RightFrontPower);
        RightBack.setPower(RightBackPower);
        LeftFront.setPower(LeftFrontPower);
        LeftBack.setPower(LeftBackPower);
    }

    //method to easily stop the robot
    public void Stop() {
        SetDrivePower(0, 0, 0, 0);
    }

}
