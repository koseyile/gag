package com.gag.gag1.func;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.gag.gag1.struct.GagGamePlayer;

public class GagGameObject_Func {
	public enum InputState
	{
		InputState_None,
		InputState_Left,
		InputState_Right,
	};
	
	public static void UpdatePlayerPosByInputState(InputState inputState, GagGamePlayer player)
	{
		switch(inputState)
		{
			case InputState_None:
				{
					player.CurMoveState = GagGamePlayer.MoveState.MoveState_Stand;
				}
				break;
			case InputState_Left:
				{
					player.postion.x-=GagGamePlayer.MoveLeftDistance;
					player.CurMoveState = GagGamePlayer.MoveState.MoveState_Left;
					player.CurReverseX = true;
				}
				break;
			case InputState_Right:
				{
					player.postion.x+=GagGamePlayer.MoveLeftDistance;
					player.CurMoveState = GagGamePlayer.MoveState.MoveState_Right;
					player.CurReverseX = false;
				}
				break;
		}
	}
	
	public static void UpdatePlayerPosByTouch(boolean bTouched, float TouchX, float ScreenW, GagGamePlayer player)
	{
		if( bTouched )
		{
			if( TouchX>(ScreenW/2) )
			{
				UpdatePlayerPosByInputState(InputState.InputState_Right, player);
			}else{
				UpdatePlayerPosByInputState(InputState.InputState_Left, player);
			}
		}else{
			UpdatePlayerPosByInputState(InputState.InputState_None, player);
		}
	}
	
	public static void UpdatePlayerPosByAccelerometerY(float AY, GagGamePlayer player)
	{
		if( AY<-2f )
		{
			UpdatePlayerPosByInputState(InputState.InputState_Left, player);
		}
		
		if( AY>2f )
		{
			UpdatePlayerPosByInputState(InputState.InputState_Right, player);
		}
	}
	
	public static void UpdatePlayerPosByKeyboard(int key, GagGamePlayer player)
	{
		switch( key )
		{
			case Keys.RIGHT:
				{
					UpdatePlayerPosByInputState(InputState.InputState_Right, player);
				}
				break;
			case Keys.LEFT:
				{
					UpdatePlayerPosByInputState(InputState.InputState_Left, player);
				}
				break;
			default:
				{
					UpdatePlayerPosByInputState(InputState.InputState_None, player);
				}
				break;
		}
	}
	
	public static void UpdatePlayerAnimation(GagGamePlayer player, float delta)
	{
		player.PassTime += delta;
	}
	
	
}
