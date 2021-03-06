package com.gag.gag1.func;

import com.badlogic.gdx.Gdx;
import com.gag.gag1.GagGameConfig;
import com.gag.gag1.GagGameTreasureUI;
import com.gag.gag1.GagWorld;
import com.gag.gag1.struct.GagGameTreasure;

public class GagGameTreasureUI_Func {

	public static void initUI(GagWorld world)
	{
		world.m_UI.treasuresBound.x = GagGameConfig.UI_treasures_x;
		world.m_UI.treasuresBound.y = GagGameConfig.UI_treasures_y;
		world.m_UI.treasuresBound.width = Gdx.graphics.getWidth()-2*GagGameConfig.UI_treasures_x;
		world.m_UI.treasuresBound.height = GagGameConfig.UI_treasures_h;
		
		world.m_UI.curPageIndex = 0;
		world.m_UI.treasureNumOfOnePage = (int)((world.m_UI.treasuresBound.width-GagGameConfig.UI_treasures_spacing)/(GagGameConfig.UI_treasure_w+GagGameConfig.UI_treasures_spacing));
	}
	
	public static void updateAllTreasureByUI(GagWorld world)
	{
		for(int i=0; i<world.m_Treasures.size(); ++i)
		{
			GagGameTreasure treasure = world.m_Treasures.get(i);
			treasure.bounds.width = GagGameConfig.UI_treasure_w;
			treasure.bounds.height = GagGameConfig.UI_treasures_h;
			treasure.isShow = false;
		}
		
		world.m_UI.pageNum = world.m_Treasures.size()/world.m_UI.treasureNumOfOnePage+1;
		if( world.m_Treasures.size()%world.m_UI.treasureNumOfOnePage==0 && world.m_Treasures.size()>0 )
		{
			world.m_UI.pageNum = world.m_Treasures.size()/world.m_UI.treasureNumOfOnePage;
		}
		
		int pageIndex = world.m_UI.curPageIndex;
		int numOfOnePage = world.m_UI.treasureNumOfOnePage;
		int startIndex = pageIndex*numOfOnePage;
		
		float w = GagGameConfig.UI_treasures_spacing;
		for(int i=0; i<numOfOnePage; ++i)
		{
			int index = startIndex+i;
			if( index>=world.m_Treasures.size() )
			{
				break;
			}
			GagGameTreasure treasure = world.m_Treasures.get(index);
			w+=(treasure.bounds.width+GagGameConfig.UI_treasures_spacing);
			treasure.isShow = true;
		}
		
		float start_x = (Gdx.graphics.getWidth()-w)/2+GagGameConfig.UI_treasures_spacing+GagGameConfig.UI_treasure_w/2;
		for(int i=0; i<numOfOnePage; ++i)
		{
			int index = startIndex+i;
			if( index>=world.m_Treasures.size() )
			{
				break;
			}
			GagGameTreasure treasure = world.m_Treasures.get(index);
			treasure.postion.x = start_x+i*(GagGameConfig.UI_treasures_spacing+GagGameConfig.UI_treasure_w);
			treasure.postion.y = GagGameConfig.UI_treasures_y;
		}
	}
	
	public static void nextPageByTreasures(GagWorld world)
	{
		world.m_UI.curPageIndex++;
		if( world.m_UI.curPageIndex>=world.m_UI.pageNum )
		{
			world.m_UI.curPageIndex = 0;
		}
		updateAllTreasureByUI(world);
	}
	
	public static void prePageByTreasures(GagWorld world)
	{
		world.m_UI.curPageIndex--;
		if( world.m_UI.curPageIndex<0 )
		{
			world.m_UI.curPageIndex = world.m_UI.pageNum-1;
		}
		updateAllTreasureByUI(world);
	}
	
	public static void updateByTouchTreasurePage(boolean bTouched, float TouchX, float TouchY, GagWorld world)
	{
		TouchY = Gdx.graphics.getHeight() - TouchY;
		boolean bTouchNextPage = GagGameObject_Func.pointInObject(TouchX, TouchY, world.m_NextPage);
		boolean bTouchPrePage = GagGameObject_Func.pointInObject(TouchX, TouchY, world.m_PrePage);
		
		if( bTouched && ( bTouchNextPage || bTouchPrePage ) )
		{
			if( bTouchNextPage )
			{
				GagGameTreasureUI_Func.nextPageByTreasures(world);
			}

			if( bTouchPrePage )
			{
				GagGameTreasureUI_Func.prePageByTreasures(world);
			}	
		}
	}
}
