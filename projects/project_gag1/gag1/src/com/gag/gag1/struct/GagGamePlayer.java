package com.gag.gag1.struct;

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
	
	public GagGamePlayer()
	{
		CurMoveState = MoveState.MoveState_Stand;
		CurReverseX = false;
		CurReverseY = false;
		PassTime = 0f;
	}
	
}
