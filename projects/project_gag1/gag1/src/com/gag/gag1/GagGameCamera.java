package com.gag.gag1;

public class GagGameCamera {
	
	public enum CameraState
	{
		CameraState_None,
		CameraState_TouchStart,
		CameraState_TouchingAndMoving,
		CameraState_TouchEnd,
	};
	
	public CameraState curCameraState;
	
	public float x;
	public float y;
	public float w;
	public float h;
	
	public float touchStartX;
	public float touchStartY;
	public int touchID;
	public boolean isTouched;
	public GagGameCamera()
	{
		x = 0;
		y = 0;
		w = GagGameConfig.CameraWidth;
		h = GagGameConfig.CameraHeight;
		
		touchStartX = 0;
		touchStartY = 0;
		touchID = -1;
		isTouched = false;
		
		curCameraState = CameraState.CameraState_None;
	}
}
