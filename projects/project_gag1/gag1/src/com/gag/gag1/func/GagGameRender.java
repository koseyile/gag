package com.gag.gag1.func;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogicgames.superjumper.Animation;
import com.badlogicgames.superjumper.Assets;
import com.gag.gag1.GagGameConfig;
import com.gag.gag1.GagGameScreen;
import com.gag.gag1.GagWorld;

public class GagGameRender {

	public static SpriteBatch batcher;
	public static OrthographicCamera guiCam;
	public static boolean IsInitialized = false;
	
	public enum FontLayout_X
	{
		FontLayout_X_Left,
		FontLayout_X_Right,
		FontLayout_X_Center,
	};
	
	public enum FontLayout_Y
	{
		FontLayout_Y_Up,
		FontLayout_Y_Bottom,
		FontLayout_Y_Center,
	};
	
	public static void Init()
	{
		batcher = new SpriteBatch();

		float Camera_W = GagGameConfig.CameraWidth;
		float Camera_H = GagGameConfig.CameraHeight;
		guiCam = new OrthographicCamera(Camera_W, Camera_H);
		guiCam.position.set(Camera_W / 2, Camera_H / 2, 0);

		IsInitialized = true;
	}
	
	public static void Draw()
	{
		if( IsInitialized==false )
		{
			Init();
		}
		
		GagWorld gagWorld = GagGameScreen.m_GagWorld;
		
		
		batcher.begin();
		batcher.enableBlending();
		
		{
			float viewW = Gdx.graphics.getWidth();
			float viewH = Gdx.graphics.getHeight();
//			float viewW = GagGameConfig.CameraWidth;
//			float viewH = GagGameConfig.CameraHeight;
			
			guiCam.setToOrtho( false, viewW, viewH );
			
			float x = GagGameScreen.m_GagWorld.m_Player.postion.x;
			float y = GagGameScreen.m_GagWorld.m_Player.postion.y;
			
			if( x<viewW/2 )
			{
				x = viewW/2;
			}
			
			if( x>(gagWorld.worldBound.width-viewW/2) )
			{
				x = gagWorld.worldBound.width-viewW/2;
			}
			
			if( y<viewH/2 )
			{
				float temp_h = GagGameConfig.GameBottomUIHeight*viewH/Gdx.graphics.getHeight();
				y = viewH/2-temp_h;
			}
			
			if( y>(gagWorld.worldBound.height-viewH/2) )
			{
				float temp_h = GagGameConfig.GameTopUIHeight*viewH/Gdx.graphics.getHeight();
				y = (gagWorld.worldBound.height-viewH/2)+temp_h;
			}
			
			//float x = viewW/2;
			//float y = viewH/2+GagGameConfig.GameBottomUIHeight-temp_h;
			//float y = viewH/2-temp_h;
			
			guiCam.position.set( x, y, 0f );
			guiCam.update();
			batcher.setProjectionMatrix(guiCam.combined);
			
			GagGameWorldRender.Draw();
		}

		{
			guiCam.setToOrtho( false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
			guiCam.position.set( Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0 );
			
			guiCam.update();
			batcher.setProjectionMatrix(guiCam.combined);	
			
			GagGameWorldRender.DrawUI();
		}

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
	
	public static void SetFontColor(float r, float g, float b, float a)
	{
		Assets.font.setColor(r, g, b, a);
	}
	
	public static void DrawString(float x, float y, String text, float fontXScale, float fontYScale)
	{
		Assets.font.setScale(fontXScale, fontYScale);
		Assets.font.draw(batcher, text, x, y);
		Assets.font.setScale(1.0f, 1.0f);
	}
	
	public static void DrawString(String text, float fontXScale, float fontYScale, FontLayout_X layoutX, FontLayout_Y layoutY)
	{
		Assets.font.setScale(fontXScale, fontXScale);
		float textWidth = Assets.font.getBounds(text).width;
		float textHeight = Assets.font.getBounds(text).height;
		float GraphicW = Gdx.graphics.getWidth();
		float GraphicH = Gdx.graphics.getHeight();
		float x = 0;
		float y = 0;
		
		switch(layoutX)
		{
			case FontLayout_X_Left:
				{
					x = 0;
				}
				break;
			case FontLayout_X_Right:
				{
					x = GraphicW-textWidth;
				}
				break;
			case FontLayout_X_Center:
				{
					x = (GraphicW-textWidth)/2;
				}
				break;
		}
		
		switch(layoutY)
		{
			case FontLayout_Y_Up:
				{
					y = GraphicH;
				}
				break;
			case FontLayout_Y_Bottom:
				{
					y = textHeight;
				}
				break;
			case FontLayout_Y_Center:
				{
					y = (GraphicH-textHeight)/2;
				}
				break;
		}
		
		Assets.font.draw(batcher, text, x, y);
		Assets.font.setScale(1.0f, 1.0f);
	}
	
}
