package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name = "DrivetrainTest", group = "7518")
public class DrivetrainTest extends LinearOpMode {

    private DcMotor leftFront, rightFront, leftRear, rightRear, rightLift, leftLift, rightIntake, leftIntake;
    private Servo rightFlip, leftFlip, colorSensorServo;
    private ColorSensor colorSensor;
    VuforiaLocalizer vuforia;

    ElapsedTime timer = new ElapsedTime();
    char vuforiaPosition = ' ';


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        //Declare hardwareMap here, example below
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        rightLift = hardwareMap.dcMotor.get("rightLift");
        leftLift = hardwareMap.dcMotor.get("leftLift");
        rightIntake = hardwareMap.dcMotor.get("rightIntake");
        leftIntake = hardwareMap.dcMotor.get("leftIntake");

        rightFlip = hardwareMap.servo.get("rightFlip");
        leftFlip = hardwareMap.servo.get("leftFlip");
        colorSensorServo = hardwareMap.servo.get("colorSensorServo");
        colorSensor = hardwareMap.colorSensor.get("colorSensor");

        initVuforia();
        waitForStart(); //Wait for the user to press the play button


        driveForward(0.5, 24);
        driveForward(-0.5, 24);
        rotateLeft(0.5);
        rotateRight(0.5);

    }//end runOpMode

    public void driveForward(double power, int inches) {
        double rpm = Math.abs(160*power);
        int ticks = 1120;
        double circ = 4*Math.PI;
        double time = (rpm*ticks)/60;
        double move = (ticks/circ)*inches;
        double setTimer = move/time;



        //set drivetrain to run using encoder
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        timer.reset();
        while(timer.seconds()<setTimer){
            //set the power of the drivetrain
            leftFront.setPower(-power);
            rightFront.setPower(-power);
            leftRear.setPower(-power);
            rightRear.setPower(-power);
        }//end while

        //reset drivetrain power
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);

        //reset the motor encoders and put motors in stop mode
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(250); //wait a quarter of a second
    }//end driveForward

    public void rotateRight(double power) {
        int inches = 20;
        double rpm = Math.abs(160*power);
        int ticks = 1120;
        double circ = 4*Math.PI;
        double time = (rpm*ticks)/60;
        double move = (ticks/circ)*inches;
        double setTimer = move/time;



        //set drivetrain to run using encoder
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        timer.reset();
        while(timer.seconds()<setTimer){
            //set the power of the drivetrain
            leftFront.setPower(power);
            leftRear.setPower(power);
        }//end while

        //reset drivetrain power
        leftFront.setPower(0);
        leftRear.setPower(0);

        //reset the motor encoders and put motors in stop mode
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(250); //wait a quarter of a second
    }//end rotateRight

    public void rotateLeft(double power) {
        int inches = 20 ;
        double rpm = Math.abs(160*power);
        int ticks = 1120;
        double circ = 4*Math.PI;
        double time = (rpm*ticks)/60;
        double move = (ticks/circ)*inches;
        double setTimer = move/time;



        //set drivetrain to run using encoder
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        timer.reset();
        while(timer.seconds()<setTimer){
            //set the power of the drivetrain
            rightFront.setPower(power);
            rightRear.setPower(power);
        }//end while

        //reset drivetrain power
        rightFront.setPower(0);
        rightRear.setPower(0);

        //reset the motor encoders and put motors in stop mode
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(250); //wait a quarter of a second
    }//end rotateLeft

    public void initVuforia (){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);


        parameters.vuforiaLicenseKey = "ATsODcD/////AAAAAVw2lR...d45oGpdljdOh5LuFB9nDNfckoxb8COxKSFX";


        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);


        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        telemetry.addData(">", "Press Play to start");
        telemetry.update();

        relicTrackables.activate();

        while (opModeIsActive()) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                /* Found an instance of the template. In the actual game, you will probably
                 * loop until this condition occurs, then move on to act accordingly depending
                 * on which VuMark was visible. */
                telemetry.addData("VuMark", "%s visible", vuMark);
                if (vuMark == RelicRecoveryVuMark.RIGHT)
                    vuforiaPosition='r';
                else if (vuMark == RelicRecoveryVuMark.CENTER)
                    vuforiaPosition='c';
            }//end if
            else {
                telemetry.addData("VuMark", "not visible");
            }//end else

            telemetry.update();
        }//end while
    }//end initVuforia


}//end class