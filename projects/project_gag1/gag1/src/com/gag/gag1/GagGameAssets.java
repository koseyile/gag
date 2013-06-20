package com.gag.gag1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GagGameAssets {
	public static Texture treasureTex;
	public static Texture athwartWorldTex;
	public static Texture umbrellaTex;
	public static Texture killMeTex;
	public static Texture speedTex;
	public static Texture boxTex;
	public static Texture goRightTex;
	public static Texture goLeftTex;
	public static Texture nextPageTex;
	public static Texture prePageTex;
	
	//scene editor
	public static Texture newSceneTex;
	public static Texture saveSceneTex;
	public static Texture saveSceneAsTex;
	public static Texture openSceneTex;
	public static Texture runSceneTex;
	public static Texture stopSceneTex;
	public static Texture disableTex;
	public static Texture moreToolTex;
	
	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}
	
	public static void load()
	{
		treasureTex =  loadTexture("data/treasure.png");
		athwartWorldTex = loadTexture("data/athwart_w.png");
		umbrellaTex = loadTexture("data/umbrella.png");
		killMeTex = loadTexture("data/killme.png");
		speedTex = loadTexture("data/speed.png");
		boxTex = loadTexture("data/box.png");
		goRightTex = loadTexture("data/goRight.png");
		goLeftTex = loadTexture("data/goLeft.png");
		nextPageTex = loadTexture("data/nextPage.png");
		prePageTex = loadTexture("data/prePage.png");
		
		//scene editor
		newSceneTex = loadTexture("data/newScene.png");
		saveSceneTex = loadTexture("data/saveScene.png");
		saveSceneAsTex = loadTexture("data/saveSceneAs.png");
		openSceneTex = loadTexture("data/openScene.png");
		runSceneTex = loadTexture("data/runScene.png");
		stopSceneTex = loadTexture("data/stopScene.png");
		disableTex = loadTexture("data/disable.png");
		moreToolTex = loadTexture("data/moreTool.png");
	}
}
