package org.usfirst.frc.team1601.robot;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;

public class Vision extends Thread {

	boolean allowCam1 = false;

	UsbCamera camera1, camera2;

	CvSource outputStream;
	CvSink cvSink1, cvSink2;
	Mat image;
	Joystick joystick;

	public Vision(Joystick joystick) {
		camera1 = CameraServer.getInstance().startAutomaticCapture(0);
		camera2 = CameraServer.getInstance().startAutomaticCapture(1);
		outputStream = CameraServer.getInstance().putVideo("Switcher", 320, 240);
		cvSink1 = CameraServer.getInstance().getVideo(camera1);
		cvSink2 = CameraServer.getInstance().getVideo(camera2);
		image = new Mat();
		camera1.setResolution(320, 240);
		camera1.setFPS(20);

		camera2.setResolution(320, 240);
		camera2.setFPS(20);

		this.joystick = joystick;
	}

	public void run() {
		if (joystick.getRawButton(1)) {
			cvSink2.setEnabled(false);
			cvSink1.setEnabled(true);
			cvSink1.grabFrame(image);
		} else {
			cvSink1.setEnabled(false);
			cvSink2.setEnabled(true);
			cvSink2.grabFrame(image);
		}

		outputStream.putFrame(image);
	}

}
