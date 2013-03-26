package com.gag.gag1.func;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogicgames.superjumper.Animation;
import com.badlogicgames.superjumper.Assets;

public class GagGameRender {

	public static SpriteBatch batcher;
	public static OrthographicCamera guiCam;
	public static boolean IsInitialized = false;
	
	public static void Init()
	{
		batcher = new SpriteBatch();
		guiCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		guiCam.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		
		IsInitialized = true;
	}
	
	public static void Draw()
	{
		if( IsInitialized==false )
		{
			Init();
		}
		
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		
		batcher.begin();
		batcher.enableBlending();
		GagGameWorldRender.Draw();
		batcher.end();
	}
	
	public static void DrawTexByCenter(TextureRegion tex, float x, float y, float w, float h, boolean reverse_x, boolean reverse_y)
	{
		if( reverse_x )
		{
			x+=(w/2);
		}else{
			x-=(w/2);
		}
		
		if( reverse_y )
		{
			y+=(h/2);
		}else{
			y-=(h/2);
		}
		
		int flag_x = reverse_x ? -1 : 1;
		int flag_y = reverse_y ? -1 : 1;
		
		batcher.draw(tex,  x, y, w*flag_x, h*flag_y);
	}
	
	public static void DrawTexByCenter(Texture tex, float x, float y, float w, float h, boolean reverse_x, boolean reverse_y)
	{
		if( reverse_x )
		{
			x+=(w/2);
		}else{
			x-=(w/2);
		}
		
		if( reverse_y )
		{
			y+=(h/2);
		}else{
			y-=(h/2);
		}
		
		int flag_x = reverse_x ? -1 : 1;
		int flag_y = reverse_y ? -1 : 1;
		
		batcher.draw(tex,  x, y, w*flag_x, h*flag_y);
	}
	
}
