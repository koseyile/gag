package com.gag.gag1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GagGameAssets {
	public static Texture treasureTex;
	public static Texture athwartWorldTex;
	
	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}
	
	public static void load()
	{
		treasureTex =  loadTexture("data/treasure.png");
		athwartWorldTex = loadTexture("data/athwart_w.png");
	}
}
