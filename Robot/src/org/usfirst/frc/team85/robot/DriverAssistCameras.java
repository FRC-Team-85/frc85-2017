package org.usfirst.frc.team85.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import org.opencv.core.Mat;

public class DriverAssistCameras {
	private UsbCamera _forwardCamera;
	private UsbCamera _reverseCamera;
	private UsbCamera _currentCamera;
	
	public DriverAssistCameras()
	{   
        new Thread(new Runnable() {
			@Override
			public void run() {
				_forwardCamera = CameraServer.getInstance().startAutomaticCapture(Addresses.FORWARD_CAMERA);
				//_forwardCamera.setResolution(640, 480);
					
				_reverseCamera = CameraServer.getInstance().startAutomaticCapture(Addresses.REVERSE_CAMERA);
				//_reverseCamera.setResolution(640, 480);
					
				_currentCamera = _forwardCamera;      
					
				Mat frame = new Mat();
				CvSource outputStream = CameraServer.getInstance().putVideo("Drive", 320, 240);
				
				while(!Thread.interrupted()) {
					try {
						CameraServer.getInstance().getVideo(_currentCamera).grabFrame(frame);
						outputStream.putFrame(frame);
					}
					catch(Exception ex) {
						System.out.println(ex.toString());
					}
				}
			}
		}).start();
	}
	
	public void setForward()
	{
		_currentCamera = _forwardCamera;
	}
	
	public void setReverse()
	{
		_currentCamera = _reverseCamera;
	}
}
