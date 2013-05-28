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
		if( JOptionPane.showConfirmDialog(null, "������δ���棬��ȷʵҪ�½���", "��ʾ", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION )
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
		
		//�жϴ��ļ��Ƿ����
		FileHandle fileCheckHandle = Gdx.files.internal(filePath);
		boolean bExists = fileCheckHandle.exists();
		if( bExists && world.m_Editor.curFileName==null )
		{
			if( JOptionPane.showConfirmDialog(null, "�ļ��Ѵ��ڣ���ȷʵҪ�滻��", "��ʾ", JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION )
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
		
		//�жϴ��ļ��Ƿ����
		FileHandle fileCheckHandle = Gdx.files.internal(filePath);
		boolean bExists = fileCheckHandle.exists();
		if( bExists )
		{
			if( JOptionPane.showConfirmDialog(null, "�ļ��Ѵ��ڣ���ȷʵҪ�滻��", "��ʾ", JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION )
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
			if( JOptionPane.showConfirmDialog(null, "������δ���棬����Ҫ������", "��ʾ", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION )
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
		
		//�жϴ��ļ��Ƿ����
		FileHandle fileCheckHandle = Gdx.files.internal(filePath);
		boolean bExists = fileCheckHandle.exists();
		if( bExists==false )
		{
			JOptionPane.showMessageDialog(null, "�ļ������ڣ�", "��ʾ", JOptionPane.YES_OPTION);
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
				return "�����ļ�(*.xml)";
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
				return "�����ļ�(*.xml)";
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
