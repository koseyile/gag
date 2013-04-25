package com.gag.gag1.struct;

import com.gag.gag1.struct.GagGameObject.ObjectType;

public class GagGamePlayer extends GagGameObject {

	public enum MoveState
	{
		MoveState_Stand,
		MoveState_Left,
		MoveState_Right,
	};
	
	public MoveState CurMoveState;
	public boolean CurReverseX;
	public boolean CurReverseY;
	public float PassTime;
	public float SpeedScale;
	
	public GagGamePlayer()
	{
		CurMoveState = MoveState.MoveState_Stand;
		CurReverseX = false;
		CurReverseY = false;
		PassTime = 0f;
		SpeedScale = 1.0f;
		
		objectType = ObjectType.ObjectType_Player;
		beDown = true;
	}
	
}
