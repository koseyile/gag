package com.gag.gag1.struct;

public class GagGamePlayer extends GagGameObject {

	public static final float MoveLeftDistance = 2f;
	public static final float MoveRightDistance = 2f;
	
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
