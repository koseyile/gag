package com.gag.gag1.func;

import com.badlogic.gdx.Gdx;
import com.gag.gag1.GagGameConfig;
import com.gag.gag1.GagGameUI;
import com.gag.gag1.GagWorld;
import com.gag.gag1.struct.GagGameTreasure;

public class GagGameUI_Func {

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
			treasure.isShow = false;
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
	
}
