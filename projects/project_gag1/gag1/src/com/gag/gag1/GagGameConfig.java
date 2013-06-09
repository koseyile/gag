package com.gag.gag1;

import com.badlogic.gdx.Input.Keys;

public class GagGameConfig {
	public static float World_g = -1f;
	public static float PlayerStartX = 16f;
	public static float PlayerStartY = 16f;
	public static float AccelerometerMaxX = 5f;
	public static float AccelerometerMaxY = 2f;
	public static final int PlayerGoRightKey = Keys.RIGHT;
	public static final int PlayerGoLeftKey = Keys.LEFT;
	public static final int PlayerGoUpKey = Keys.UP;
	public static final int PlayerGoDownKey = Keys.DOWN;
	public static float PlayerMoveLeftDistance = 2f;
	public static float PlayerMoveRightDistance = 2f;
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
	public static float DisByDoorToPlayer = 5f;
	public static float DisByTreasureToPlayer = 5f;
	public static float FadeInTime = 1f;
	public static float FadeOutTime = 1f;
	public static int GameUIHeight = 32; 
	public static float DownSpeedDead = 18f;
	public static float DownSpeedDeadByHitPlayer = 9f;
	public static float UmbrellaScale_g = 0.5f;
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
		
		//scene_editor
		"editorStart",
		"newScene",
		"saveScene",
		"saveSceneAs",
		"openScene",
		"runScene",
		"stopScene",
		"moreTool",
	};
	
	public static String TreasureTopString[] = 
	{
		"none",
		"athwartWorld",
		"umbrella",
		"killme",
		"speed",
		
		//scene_editor
		"editorStart",
		"create a new scene",
		"save scene",
		"save scene as another file",
		"open a scene from file",
		"run scene like game",
		"stop scene",
		"more tool",
	};
	
	public static boolean ShowFps = true;
	
}
