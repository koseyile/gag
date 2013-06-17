package com.gag.gag1.func;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gag.gag1.GagGameConfig;
import com.gag.gag1.GagWorld;
import com.gag.gag1.struct.GagGameObject;
import com.gag.gag1.struct.GagGamePlayer;
import com.gag.gag1.struct.GagGamePlayer.MoveState;

public class GagGameObject_Func {
	public enum InputState
	{
		InputState_None,
		InputState_Left,
		InputState_Right,
	}

	public static void updatePlayerPosByInputState(InputState inputState, GagGamePlayer player)
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
					player.postion.x-=(GagGameConfig.PlayerMoveLeftDistance*player.SpeedScale);
					player.CurMoveState = GagGamePlayer.MoveState.MoveState_Left;
					player.CurReverseX = true;
				}
				break;
			case InputState_Right:
				{
					player.postion.x+=(GagGameConfig.PlayerMoveRightDistance*player.SpeedScale);
					player.CurMoveState = GagGamePlayer.MoveState.MoveState_Right;
					player.CurReverseX = false;
				}
				break;
		}
	}
	
	public static void updatePlayerPosByTouch(boolean bTouched, float TouchX, float TouchY, float ScreenW, GagGamePlayer player)
	{
		if( bTouched && !isTouchOutSide(TouchX, TouchY) )
		{
			if( TouchX>(ScreenW/2) )
			{
				updatePlayerPosByInputState(InputState.InputState_Right, player);
			}else{
				updatePlayerPosByInputState(InputState.InputState_Left, player);
			}
		}else{
			updatePlayerPosByInputState(InputState.InputState_None, player);
		}
	}
	
	public static void updatePlayerPosByTouchTreasure(boolean bTouched, float TouchX, float TouchY, GagWorld world)
	{
		TouchY = Gdx.graphics.getHeight() - TouchY;
		boolean bTouchGoLeft = pointInObject(TouchX, TouchY, world.m_GoLeft);
		boolean bTouchGoRight = pointInObject(TouchX, TouchY, world.m_GoRight);
		
		if( bTouched && ( bTouchGoLeft || bTouchGoRight ) )
		{
			if( bTouchGoRight )
			{
				updatePlayerPosByInputState(InputState.InputState_Right, world.m_Player);
			}

			if( bTouchGoLeft )
			{
				updatePlayerPosByInputState(InputState.InputState_Left, world.m_Player);
			}	
		}
	}
	
	public static boolean isTouchOutSide(float TouchX, float TouchY)
	{
		if( TouchY<GagGameConfig.TreasureHeight )
		{
			return true;
		}
		return false;
	}
	
	public static void updatePlayerPosByAccelerometerY(float AY, GagGamePlayer player)
	{
		if( AY<=-GagGameConfig.AccelerometerMaxY )
		{
			updatePlayerPosByInputState(InputState.InputState_Left, player);
		}
		
		if( AY>=GagGameConfig.AccelerometerMaxY )
		{
			updatePlayerPosByInputState(InputState.InputState_Right, player);
		}
	}
	
	public static void updatePlayerPosByKeyboard(int key, GagGamePlayer player)
	{
		switch( key )
		{
			case GagGameConfig.PlayerGoRightKey:
				{
					updatePlayerPosByInputState(InputState.InputState_Right, player);
				}
				break;
			case GagGameConfig.PlayerGoLeftKey:
				{
					updatePlayerPosByInputState(InputState.InputState_Left, player);
				}
				break;
			default:
				{
					updatePlayerPosByInputState(InputState.InputState_None, player);
				}
				break;
		}
	}
	
	public static void updatePlayerAnimation(GagGamePlayer player, float delta)
	{
		player.PassTime += delta;
	}
	
	public static void updateObjectByWorldG(GagGameObject object, float g)
	{
		object.downSpeed+=g;
		object.postion.y+=object.downSpeed;
	}
	
	public static void updateObjectDownSpeedByScreenBound(GagGameObject object, Rectangle bound)
	{
		float minY = bound.y+object.bounds.height/2;
		float maxY = bound.y+bound.height-object.bounds.height/2;

		object.downSpeed = object.postion.y<minY ? 0f : object.downSpeed;
		object.downSpeed = object.postion.y>maxY ? 0f : object.downSpeed;
	}
	
	public static boolean isInScreenBoundByY(GagGameObject object, Rectangle bound)
	{
		float minY = bound.y+object.bounds.height/2;
		float maxY = bound.y+bound.height-object.bounds.height/2;
		if( object.postion.y<minY || object.postion.y>maxY )
		{
			return false;
		}
		return true;
	}
	
	public static void updateObjectPosByScreenBound(GagGameObject object, Rectangle bound)
	{
		float minX = bound.x+object.bounds.width/2;
		float minY = bound.y+object.bounds.height/2;
		
		float maxX = bound.x+bound.width-object.bounds.width/2;
		float maxY = bound.y+bound.height-object.bounds.height/2;

		object.postion.x = object.postion.x<minX ? minX : object.postion.x;
		object.postion.y = object.postion.y<minY ? minY : object.postion.y;
		
		object.postion.x = object.postion.x>maxX ? maxX : object.postion.x;
		object.postion.y = object.postion.y>maxY ? maxY : object.postion.y;
	}
	
	public static boolean vector2IsAvailability( Vector2 v )
	{
		if( Float.isNaN(v.x) || Float.isNaN(v.y) )
		{
			return false;
		}
		
		if( Float.isInfinite(v.x) || Float.isInfinite(v.y) )
		{
			return false;
		}
		
		return true;
	}
	
	public static boolean vector2IsInCenter( Vector2 v, Vector2 v1, Vector2 v2 )
	{
		float dst_12 = v1.dst(v2);
		float dst_1 = v1.dst(v);
		float dst_2 = v2.dst(v);
		
		if( dst_1>=dst_12 || dst_2>=dst_12 )
		{
			return false;
		}
		return true;
	}
	
	public static boolean getIntersectionByObject(Vector2 out, Vector2 start, Vector2 end, float w, float h, GagGameObject object)
	{
		//计算最终end
		Vector2 temp_end = new Vector2(end);
		if( end.y>start.y )
		{
			temp_end.y+=(h/2);
		}else if( end.y<start.y )
		{
			temp_end.y-=(h/2);
		}
		
		if( end.x>start.x )
		{
			temp_end.x+=(w/2);
		}else if( end.x<start.x )
		{
			temp_end.x-=(w/2);
		}
		
		Vector2 LT = new Vector2( object.postion.x-object.bounds.width/2, object.postion.y+object.bounds.height/2 );
		Vector2 LB = new Vector2( object.postion.x-object.bounds.width/2, object.postion.y-object.bounds.height/2 );
		Vector2 RT = new Vector2( object.postion.x+object.bounds.width/2, object.postion.y+object.bounds.height/2 );
		Vector2 RB = new Vector2( object.postion.x+object.bounds.width/2, object.postion.y-object.bounds.height/2 );
		
		Vector2 Nearest_intersect = new Vector2(end);
		
		//得到与object上边的交点
		Vector2 T_intersect = new Vector2();
		Intersector.intersectLines(start, temp_end, LT, RT, T_intersect);
		
		//得到与object下边的交点
		Vector2 B_intersect = new Vector2();
		Intersector.intersectLines(start, temp_end, LB, RB, B_intersect);
		
		//得到与object左边的交点
		Vector2 L_intersect = new Vector2();
		Intersector.intersectLines(start, temp_end, LT, LB, L_intersect);
		
		//得到与object右边的交点
		Vector2 R_intersect = new Vector2();
		Intersector.intersectLines(start, temp_end, RT, RB, R_intersect);
		
		boolean bResult = false;
		//得到最近交点
		if( vector2IsAvailability(T_intersect) && vector2IsInCenter(T_intersect, new Vector2(LT.x-w/2, LT.y), new Vector2(RT.x+w/2, RT.y)) && vector2IsInCenter(T_intersect, start, temp_end) )
		{
			T_intersect.y+=(h/2);
			if( T_intersect.dst2(start)<Nearest_intersect.dst2(start) )
			{
				Nearest_intersect.set(T_intersect);
				bResult = true;
			}
		}
		
		if( vector2IsAvailability(B_intersect) && vector2IsInCenter(B_intersect, new Vector2(LB.x-w/2, LB.y), new Vector2(RB.x+w/2, RB.y)) && vector2IsInCenter(B_intersect, start, temp_end) )	
		{
			B_intersect.y-=(h/2);
			if( B_intersect.dst2(start)<Nearest_intersect.dst2(start) )
			{
				Nearest_intersect.set(B_intersect);
				bResult = true;
			}
		}
		
		if( vector2IsAvailability(L_intersect) && vector2IsInCenter(L_intersect, new Vector2(LT.x, LT.y+h/2), new Vector2(LB.x, LB.y-h/2)) && vector2IsInCenter(L_intersect, start, temp_end) )
		{
			L_intersect.x-=(w/2);
			if( L_intersect.dst2(start)<Nearest_intersect.dst2(start) )
			{
				Nearest_intersect.set(L_intersect);
				bResult = true;
			}
		}
		

		if( vector2IsAvailability(R_intersect) && vector2IsInCenter(R_intersect, new Vector2(RT.x, RT.y+h/2), new Vector2(RB.x, RB.y-h/2)) && vector2IsInCenter(R_intersect, start, temp_end) )
		{
			R_intersect.x+=(w/2);
			if( R_intersect.dst2(start)<Nearest_intersect.dst2(start) )
			{
				Nearest_intersect.set(R_intersect);
				bResult = true;
			}
		}
		
		out.set(Nearest_intersect);
		return bResult;
	}
	
	public static boolean pointInObject(float pointX, float pointY, GagGameObject object)
	{
		float minX = object.postion.x-object.bounds.width/2;
		float maxX = object.postion.x+object.bounds.width/2;
		
		float minY = object.postion.y-object.bounds.height/2;
		float maxY = object.postion.y+object.bounds.height/2;
		
		if( pointX>minX && pointX<maxX &&
			 pointY>minY && pointY<maxY 	
		  )
		{
			return true;
		}
		
		return false;
	}
	
}
