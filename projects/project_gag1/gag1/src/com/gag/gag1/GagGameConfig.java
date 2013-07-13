package com.gag.gag1;

import com.badlogic.gdx.Input.Keys;

public class GagGameConfig {
	//need change when screen size have changed
	///////////////////////////////////////////////////////////
	public static int GameBottomUIHeight = 64; 
	public static int GameTopUIHeight = 16; 
	
	public static float UI_treasures_x = 128f;
	public static float UI_treasures_y = 32f;
	public static float UI_treasures_h = 64f;
	public static float UI_treasure_w = 64f;
	public static float UI_treasures_spacing = 6f;
	public static float UI_goLeft_x = 32f;
	public static float UI_goLeft_y = 32f;
	public static float UI_Property_w = 360f;
	public static float UI_Property_h = 120f;
	public static float UI_Property_FontH = 1.2f;
	
	public static float CameraWidth = 800f;
	public static float CameraHeight = 480f;
//	public static float CameraWidth = 400f;
//	public static float CameraHeight = 240f;
	

	public static float World_g = -1f;
	public static float UmbrellaScale_g = 0.1f;
	
	public static float PlayerMoveLeftDistance = 2f;
	public static float PlayerMoveRightDistance = 2f;
	
	public static float DownSpeedDead = 18f;
	public static float DisByDoorToPlayer = 8f;
	public static float DisByTreasureToPlayer = 8f;
	public static float CollisionAppendW = 0f;
	public static float CollisionAppendH = -18f;
	///////////////////////////////////////////////////////////
	public static float World_Graphic_Defult_W = 800.0f;
	public static float World_Graphic_Defult_H = 480.0f;
	
	public static float World_Defult_W = 800.0f;
	public static float World_Defult_H = 400.0f;
	
	public static float PlayerStartX = 16f;
	public static float PlayerStartY = 16f;
	public static float AccelerometerMaxX = 5f;
	public static float AccelerometerMaxY = 2f;
	public static final int PlayerGoRightKey = Keys.RIGHT;
	public static final int PlayerGoLeftKey = Keys.LEFT;
	public static final int PlayerGoUpKey = Keys.UP;
	public static final int PlayerGoDownKey = Keys.DOWN;
	public static float PlatformWidth = 64f;
	public static float PlatformHeight = 16f;
	public static float BoxWidth = 64f;
	public static float BoxHeight = 16f;
	public static float TreasureWidth = 32f;
	public static float TreasureHeight = 32f;
	public static float DoorWidth = 64f;
	public static float DoorHeight = 64f;
	public static String SceneFileName[] = 
	{
		"data/scene_editor.xml",
		
//		"data/scene1.xml",
//		"data/scene2.xml",
//		"data/scene3.xml",
//		"data/scene4.xml",
//		"data/scene5.xml",
	};
	public static float FadeInTime = 1f;
	public static float FadeOutTime = 1f;
	public static float DownSpeedDeadByHitPlayer = 9f;
	public static float UmbrellaUseTime = 5f;
	public static float SpeedAddScale = 4.0f;
	public static float SpeedAddUseTime = 5f;
	public static String Id_Player = "Player";
	public static String Id_Platform = "Platform";
	public static String Id_Door1 = "Door1";
	public static String Id_Door2 = "Door2";
	public static String Id_Treasure = "Treasure";
	public static String Id_Box = "Box";
	
	public static String TreasureName[] = 
	{
		"none",
		"athwartWorld",
		"umbrella",
		"killme",
		"speed",
		
		"notSaveStart",
		
		"goRight",
		"goLeft",
		"pageNext",
		"pagePre",
		
		//scene_editor
		"newScene",
		"saveScene",
		"saveSceneAs",
		"openScene",
		"runScene",
		"stopScene",
	};
	
	public static String TreasureTopString[] = 
	{
		"none",
		"athwartWorld",
		"umbrella",
		"killme",
		"speed",
		
		"notSaveStart",
		
		"goRight",
		"goLeft",
		"pageNext",
		"pagePre",
		
		//scene_editor
		"create a new scene",
		"save scene",
		"save scene as another file",
		"open a scene from file",
		"run scene like game",
		"stop scene",
	};
	public static float topStringH = 24f;
	public static boolean ShowFps = true;
	public static float touchedOnceTime = 0.5f;
	public static float cameraMoveScale = 2f;
	
	public static float touchStep = 2.0f;
	public static int propertyNum = 4;
	public static final int propertyNextIndexKey = Keys.DOWN;
	public static final int propertyPreIndexKey = Keys.UP;
	public static final int propertyAddKey = Keys.F2;
	public static final int propertySubKey = Keys.F1;
	public static final int propertyPresskeyFrames = 10;
	public static final float propertyValueChange = 0.1f;
	public static final float propertyValueChangeMin = 1.0f;
	public static final float propertyValueChangeMax = 50.0f;
}
