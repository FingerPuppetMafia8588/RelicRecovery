import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by Geoff on 10/17/2017.
 */
@Disabled
@TeleOp (name = "Squared ")
public class SquaredJoyStick extends OpMode {

    DcMotor RightMotor;
    DcMotor LeftMotor;

    double right;
    double left;

    @Override public void init () {
        RightMotor = hardwareMap.dcMotor.get("right");
        LeftMotor = hardwareMap.dcMotor.get("left");

        RightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override public void loop () {

        if (gamepad1.right_stick_y > 1 ) {
             right = -Math.pow(gamepad1.right_stick_y, 2);
         } else if (gamepad1.right_stick_y < 1) {
             right = Math.pow(gamepad1.right_stick_y, 2);
         } else {
             right = 0;
         }
    }
 }

