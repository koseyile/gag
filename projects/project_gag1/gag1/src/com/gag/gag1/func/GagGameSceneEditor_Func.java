package com.gag.gag1.func;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.gag.gag1.GagGameConfig;
import com.gag.gag1.GagWorld;
import com.gag.gag1.GagWorld.SceneID;
import com.gag.gag1.struct.GagGameEditor;
import com.gag.gag1.struct.GagGameTreasure;

public class GagGameSceneEditor_Func {
	
	public static void newScene(GagWorld world)
	{
		if( JOptionPane.showConfirmDialog(null, "场景还未保存，你确实要新建吗？", "提示", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION )
		{
			world.m_Editor.curFileName = null;
			GagGameWorld_Func.loadScene(SceneID.SceneID_1, world);
		}
	}
	
	public static void saveScene(GagWorld world)
	{
		String filePath = world.m_Editor.curFileName;
		
		if( filePath==null )
		{
			filePath = getFilePathBySaveDialog();
		}
		
		if( filePath==null )
		{
			return;
		}
		
		if( filePath.endsWith(".xml")==false )
		{
			filePath+=".xml";
		}
		
		//判断此文件是否存在
		FileHandle fileCheckHandle = Gdx.files.internal(filePath);
		boolean bExists = fileCheckHandle.exists();
		if( bExists && world.m_Editor.curFileName==null )
		{
			if( JOptionPane.showConfirmDialog(null, "文件已存在，你确实要替换吗？", "提示", JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION )
			{
				return;
			}
		}
		
		try {
			GagGameDataLoad_Func.SaveSceneToXml(filePath, world);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		world.m_Editor.curFileName = filePath;
		Gdx.app.log("MSG:", "save success: "+world.m_Editor.curFileName);
	}
	
	public static void saveSceneAs(GagWorld world)
	{
		String filePath = getFilePathBySaveDialog();
		
		if( filePath==null )
		{
			return;
		}
		
		if( filePath.endsWith(".xml")==false )
		{
			filePath+=".xml";
		}
		
		//判断此文件是否存在
		FileHandle fileCheckHandle = Gdx.files.internal(filePath);
		boolean bExists = fileCheckHandle.exists();
		if( bExists )
		{
			if( JOptionPane.showConfirmDialog(null, "文件已存在，你确实要替换吗？", "提示", JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION )
			{
				return;
			}
		}
		
		try {
			GagGameDataLoad_Func.SaveSceneToXml(filePath, world);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		world.m_Editor.curFileName = filePath;
		Gdx.app.log("MSG:", "save success: "+world.m_Editor.curFileName);
	}
	
	public static void openScene(GagWorld world)
	{
		if( world.m_Editor.curFileName==null )
		{
			if( JOptionPane.showConfirmDialog(null, "场景还未保存，你需要保存吗？", "提示", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION )
			{
				saveScene(world);
			}
		}
		
		String filePath = getFilePathByOpenDialog();
		
		if( filePath==null )
		{
			return;
		}
		
		if( filePath.endsWith(".xml")==false )
		{
			filePath+=".xml";
		}
		
		//判断此文件是否存在
		FileHandle fileCheckHandle = Gdx.files.internal(filePath);
		boolean bExists = fileCheckHandle.exists();
		if( bExists==false )
		{
			JOptionPane.showMessageDialog(null, "文件不存在！", "提示", JOptionPane.YES_OPTION);
			return;
		}
	
		world.m_Editor.curFileName = filePath;
		loadScene(world);
	}

	public static String getFilePathBySaveDialog()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("data/"));

		chooser.setFileFilter( new FileFilter(){ 
			public boolean accept(File f)
			{
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml");
			}
			public String getDescription()
			{
				return "配置文件(*.xml)";
			}
		} );

		int r = chooser.showSaveDialog(null);
		if (r != 0)
			return null;
		File file = chooser.getSelectedFile();
		return file.getPath();
	}
	
	public static String getFilePathByOpenDialog()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("data/"));

		chooser.setFileFilter( new FileFilter(){ 
			public boolean accept(File f)
			{
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml");
			}
			public String getDescription()
			{
				return "配置文件(*.xml)";
			}
		} );

		int r = chooser.showOpenDialog(null);
		if (r != 0)
			return null;
		File file = chooser.getSelectedFile();
		return file.getPath();
	}
	
	public static void runScene(GagWorld world, GagGameEditor editor)
	{
		editor.isRun = true;
		GagGameTreasure_Func.setAllTreasureEnable(world, false);
		GagGameTreasure_Func.setTreasureEnableByType(world, GagGameTreasure.TreasureType.TreasureType_StopScene, true);
	}
	
	public static void stopScene(GagWorld world, GagGameEditor editor)
	{
		if( editor.isRun )
		{
			loadScene(world);
			GagGameTreasure_Func.setAllTreasureEnable(world, true);
		}
		editor.isRun = false;
	}

	public static void loadScene(GagWorld world)
	{
		GagGameWorld_Func.loadScene(SceneID.SceneID_1, world);
		
		if( world.m_Editor.curFileName!=null )
		{
			try {
				GagGameDataLoad_Func.LoadSceneFromXml(world.m_Editor.curFileName, world);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void moreTool(GagWorld world)
	{
		GagGameWorld_Func.setWorldBound(world, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()-(GagGameConfig.GameUIHeight)*3);
	}
}
