package com.gag.gag1.unittest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gag.gag1.GagGameConfig;
import com.gag.gag1.GagWorld;
import com.gag.gag1.func.GagGameObject_Func;
import com.gag.gag1.func.GagGameObject_Func.InputState;
import com.gag.gag1.func.GagGameWorld_Func;
import com.gag.gag1.struct.GagGameObject;
import com.gag.gag1.struct.GagGamePlayer;

public class GagGameObject_FuncTest {

	@Test
	public void test_UpdatePlayerPosByInputState()
	{
		GagGamePlayer player = new GagGamePlayer();
		player.postion.x = 0f;
		GagGameObject_Func.updatePlayerPosByInputState(InputState.InputState_None, player);
		if( player.postion.x!=0f )
		{
			fail("test failed by UpdatePlayerPosByInput_test");
		}
		
		if( player.CurMoveState!=GagGamePlayer.MoveState.MoveState_Stand )
		{
			fail("test failed by UpdatePlayerPosByInput_test");
		}
		
		player.postion.x = 0f;
		GagGameObject_Func.updatePlayerPosByInputState(InputState.InputState_Left, player);
		if( player.postion.x!=-GagGameConfig.PlayerMoveLeftDistance )
		{
			fail("test failed by UpdatePlayerPosByInput_test");
		}
		
		if( player.CurMoveState!=GagGamePlayer.MoveState.MoveState_Left )
		{
			fail("test failed by UpdatePlayerPosByInput_test");
		}
		
		if( player.CurReverseX!=true )
		{
			fail("test failed by UpdatePlayerPosByInput_test");
		}
		
		player.postion.x = 0f;
		GagGameObject_Func.updatePlayerPosByInputState(InputState.InputState_Right, player);
		if( player.postion.x!=GagGameConfig.PlayerMoveRightDistance )
		{
			fail("test failed by UpdatePlayerPosByInput_test");
		}
		
		if( player.CurMoveState!=GagGamePlayer.MoveState.MoveState_Right )
		{
			fail("test failed by UpdatePlayerPosByInput_test");
		}
		
		if( player.CurReverseX!=false )
		{
			fail("test failed by UpdatePlayerPosByInput_test");
		}
	}
	
	@Test
	public void test_UpdatePlayerPosByTouch()
	{
		GagGamePlayer player = new GagGamePlayer();
		player.postion.x = 0f;
		
		GagGameObject_Func.updatePlayerPosByTouch(false, 0f, 100f, 640f, player);
		if( player.postion.x!=0f )
		{
			fail("test failed by test_UpdatePlayerPosByInput");
		}
		
		player.postion.x = 0f;
		GagGameObject_Func.updatePlayerPosByTouch(true, 0f, 100f, 640f, player);
		if( player.postion.x!=-GagGameConfig.PlayerMoveLeftDistance )
		{
			fail("test failed by test_UpdatePlayerPosByInput");
		}
		
		player.postion.x = 0f;
		GagGameObject_Func.updatePlayerPosByTouch(true, 640f, 100f, 640f, player);
		if( player.postion.x!=GagGameConfig.PlayerMoveRightDistance )
		{
			fail("test failed by test_UpdatePlayerPosByInput");
		}
		
		player.postion.x = 10f;
		GagGameObject_Func.updatePlayerPosByTouch(true, 640f, 100f, 640f, player);
		if( player.postion.x!=(10f+GagGameConfig.PlayerMoveRightDistance) )
		{
			fail("test failed by test_UpdatePlayerPosByInput");
		}
	}
	
	@Test
	public void test_UpdatePlayerPosByKeyboard()
	{
		GagGamePlayer player = new GagGamePlayer();
		player.postion.x = 0f;
		
		GagGameObject_Func.updatePlayerPosByKeyboard(Keys.SPACE, player);
		if( player.postion.x!=0f )
		{
			fail("test failed by test_UpdatePlayerPosByKeyboard");
		}
		
		if( player.CurMoveState!=GagGamePlayer.MoveState.MoveState_Stand )
		{
			fail("test failed by test_UpdatePlayerPosByKeyboard");
		}
		
		player.postion.x = 0f;
		GagGameObject_Func.updatePlayerPosByKeyboard(Keys.LEFT, player);
		if( player.postion.x!=-GagGameConfig.PlayerMoveLeftDistance )
		{
			fail("test failed by test_UpdatePlayerPosByKeyboard");
		}
		
		if( player.CurMoveState!=GagGamePlayer.MoveState.MoveState_Left )
		{
			fail("test failed by test_UpdatePlayerPosByKeyboard");
		}
		
		player.postion.x = 0f;
		GagGameObject_Func.updatePlayerPosByKeyboard(Keys.RIGHT, player);
		if( player.postion.x!=GagGameConfig.PlayerMoveRightDistance )
		{
			fail("test failed by test_UpdatePlayerPosByKeyboard");
		}
		
		if( player.CurMoveState!=GagGamePlayer.MoveState.MoveState_Right )
		{
			fail("test failed by test_UpdatePlayerPosByKeyboard");
		}
		
		player.postion.x = 10f;
		GagGameObject_Func.updatePlayerPosByKeyboard(Keys.RIGHT, player);
		if( player.postion.x!=(10f+GagGameConfig.PlayerMoveRightDistance) )
		{
			fail("test failed by test_UpdatePlayerPosByKeyboard");
		}
		
		if( player.CurMoveState!=GagGamePlayer.MoveState.MoveState_Right )
		{
			fail("test failed by test_UpdatePlayerPosByKeyboard");
		}
		
		player.postion.x = 0f;
		GagGameObject_Func.updatePlayerPosByKeyboard(Keys.SPACE, player);
		if( player.postion.x!=0f )
		{
			fail("test failed by test_UpdatePlayerPosByKeyboard");
		}
		
		if( player.CurMoveState!=GagGamePlayer.MoveState.MoveState_Stand )
		{
			fail("test failed by test_UpdatePlayerPosByKeyboard");
		}
		
	}

	@Test
	public void test_UpdatePlayerAnimation()
	{
		GagGamePlayer player = new GagGamePlayer();
		player.PassTime = 0f;
		GagGameObject_Func.updatePlayerAnimation(player, 10f);
		
		if( player.PassTime!=10f )
		{
			fail("test failed by test_UpdatePlayerAnimation");
		}
		
		GagGameObject_Func.updatePlayerAnimation(player, 5f);
		if( player.PassTime!=15f )
		{
			fail("test failed by test_UpdatePlayerAnimation");
		}
	}
	
	@Test
	public void test_UpdatePlayerPosByAccelerometerY()
	{
		GagGamePlayer player = new GagGamePlayer();
		
		player.postion.x = 10f;
		GagGameObject_Func.updatePlayerPosByAccelerometerY(GagGameConfig.AccelerometerMaxY, player);
		if( player.postion.x!=(10f+GagGameConfig.PlayerMoveRightDistance) )
		{
			fail("test failed by test_UpdatePlayerPosByAccelerometerY");
		}
		
		player.postion.x = 10f;
		GagGameObject_Func.updatePlayerPosByAccelerometerY(-GagGameConfig.AccelerometerMaxY, player);
		if( player.postion.x!=(10f-GagGameConfig.PlayerMoveLeftDistance) )
		{
			fail("test failed by test_UpdatePlayerPosByAccelerometerY");
		}
		
	}
	
	@Test
	public void test_UpdateObjectByWorldG()
	{
		GagGameObject object = new GagGameObject();

		object.postion.y = 12f;
		object.downSpeed = 10f;
		GagGameObject_Func.updateObjectByWorldG(object, 1f);
		if( object.downSpeed!=11f )
		{
			fail("test failed by test_UpdateObjectByWorldG");
		}
		
		if( object.postion.y!=23f )
		{
			fail("test failed by test_UpdateObjectByWorldG");
		}
		
	}
	
	@Test
	public void test_UpdateObjectDownSpeedByScreenBound()
	{
		GagGameObject object = new GagGameObject();
		Rectangle bound = new Rectangle(10f, 10f, 100f, 200f);
		
		object.postion.x = 1000f;
		object.postion.y = 1000f;
		object.downSpeed = 10f;
		
		GagGameObject_Func.updateObjectDownSpeedByScreenBound(object, bound);
		if( object.downSpeed!=0f )
		{
			fail("test failed by test_UpdateObjectDownSpeedByScreenBound");
		}
		
	}
	
	@Test
	public void test_UpdateObjectByPosScreenBound()
	{
		GagGameObject object = new GagGameObject();
		Rectangle bound = new Rectangle(10f, 10f, 100f, 200f);
		
		object.postion.x = 1000f;
		object.postion.y = 1000f;
		
		GagGameObject_Func.updateObjectPosByScreenBound(object, bound);
		
		if( object.postion.x!=(110f-object.bounds.width/2) )
		{
			fail("test failed by test_UpdateObjectByScreenBound");
		}
		
		if( object.postion.y!=(210f-object.bounds.height/2) )
		{
			fail("test failed by test_UpdateObjectByScreenBound");
		}
		
		object.postion.x = 50f;
		object.postion.y = 30f;
		
		GagGameObject_Func.updateObjectPosByScreenBound(object, bound);
		
		if( object.postion.x!=50f )
		{
			fail("test failed by test_UpdateObjectByScreenBound");
		}
		
		if( object.postion.y!=30f )
		{
			fail("test failed by test_UpdateObjectByScreenBound");
		}
		
	}
	
	@Test
	public void test_GetIntersectionByObject()
	{
		GagGameObject object = new GagGameObject();

		object.postion.x = 10f;
		object.postion.y = 5f;
		object.bounds.x = 0f;
		object.bounds.y = 0f;
		object.bounds.width = 6f;
		object.bounds.height = 4f;
		
		Vector2 start = new Vector2(10f, 20f);
		Vector2 end = new Vector2(10f, 1f);
		float w = 2;
		float h = 2;
		
		Vector2 out = new Vector2(0f, 0f);
		GagGameObject_Func.getIntersectionByObject(out, start, end, w, h, object);
		
		if( out.x!=10 )
		{
			fail("test failed by test_GetIntersectionByObject");
		}
		
		if( out.y!=8 )
		{
			fail("test failed by test_GetIntersectionByObject");
		}
		
		start.x = -10f;
		start.y = 6f;
		end.x = 20f;
		end.y = 6f;
		GagGameObject_Func.getIntersectionByObject(out, start, end, w, h, object);
		if( out.x!=6f )
		{
			fail("test failed by test_GetIntersectionByObject");
		}
		
		if( out.y!=6f )
		{
			fail("test failed by test_GetIntersectionByObject");
		}
		
		end.x = -10f;
		end.y = 6f;
		start.x = 20f;
		start.y = 6f;
		GagGameObject_Func.getIntersectionByObject(out, start, end, w, h, object);
		if( out.x!=14f )
		{
			fail("test failed by test_GetIntersectionByObject");
		}
		
		if( out.y!=6f )
		{
			fail("test failed by test_GetIntersectionByObject");
		}

		start.x = 100f;
		start.y = 20;
		end.x = 100f;
		end.y = 1f;
		GagGameObject_Func.getIntersectionByObject(out, start, end, w, h, object);
		if( out.x!=100f )
		{
			fail("test failed by test_GetIntersectionByObject");
		}
		
		if( out.y!=1f )
		{
			fail("test failed by test_GetIntersectionByObject");
		}
		
		start.x = -100f;
		start.y = 20;
		end.x = -100f;
		end.y = 1f;
		GagGameObject_Func.getIntersectionByObject(out, start, end, w, h, object);
		if( out.x!=-100f )
		{
			fail("test failed by test_GetIntersectionByObject");
		}
		
		if( out.y!=1f )
		{
			fail("test failed by test_GetIntersectionByObject");
		}
		
	}
	
	@Test
	public void test_UpdateWorldGByTouch()
	{
		GagWorld world = new GagWorld();
		GagGameWorld_Func.updateWorldGByTouch(true, 0, 800, world);
		if( world.m_g<0f )
		{
			fail("test failed by test_UpdateWorldGByTouch");
		}
		
		GagGameWorld_Func.updateWorldGByTouch(true, 500, 800, world);
		if( world.m_g>0f )
		{
			fail("test failed by test_UpdateWorldGByTouch");
		}
	}
	
	@Test
	public void test_PointInObject()
	{
		GagGameObject object = new GagGameObject();
		object.postion.x = 10f;
		object.postion.y = 20f;
		object.bounds.x = 0f;
		object.bounds.y = 0f;
		object.bounds.width = 100f;
		object.bounds.height = 50f;
		
		if( GagGameObject_Func.pointInObject(-10, 20, object)==false )
		{
			fail("test failed by test_pointInObject");
		}
		
		if( GagGameObject_Func.pointInObject(80, 20, object)==true )
		{
			fail("test failed by test_pointInObject");
		}
		
	}

}
