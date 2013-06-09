package com.gag.gag1.func;

import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.badlogic.gdx.Gdx;
import com.gag.gag1.GagGameConfig;
import com.gag.gag1.GagWorld;
import com.gag.gag1.struct.GagGameBox;
import com.gag.gag1.struct.GagGameDoor;
import com.gag.gag1.struct.GagGameObject;
import com.gag.gag1.struct.GagGameObject.ObjectType;
import com.gag.gag1.struct.GagGamePlatform;
import com.gag.gag1.struct.GagGameDoor.DoorType;
import com.gag.gag1.struct.GagGameTreasure;
import com.gag.gag1.struct.GagGameTreasure.TreasureType;

public class GagGameDataLoad_Func {
	public static void LoadSceneFromXml(String file, GagWorld world) throws Exception
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(Gdx.files.internal(file).read());
		Element element = doc.getDocumentElement();
	
		float scene_w = world.worldBound.width;
		float scene_h = world.worldBound.height;
		
		if( !element.getAttribute("w").isEmpty() && !element.getAttribute("w").isEmpty() )
		{
			scene_w = Float.parseFloat(element.getAttribute("w"));
			scene_h = Float.parseFloat(element.getAttribute("h"));
		}
		
		float scene_x_scale = scene_w/world.worldBound.width;
		float scene_y_scale = scene_h/world.worldBound.height;
		
		NodeList objectNodes = element.getElementsByTagName("object");
		
		for( int i=0; i<objectNodes.getLength(); i++ )
		{
			Element element_object = (Element) objectNodes.item(i);
			GagGameObject gameObject = null;
			if( element_object.getAttribute("id").equals(GagGameConfig.Id_Player) )
			{
				gameObject = world.m_Player;
			}else if( element_object.getAttribute("id").equals(GagGameConfig.Id_Platform) )
			{
				gameObject = new GagGamePlatform();
				world.m_Objects.add(gameObject);
			}else if( element_object.getAttribute("id").equals(GagGameConfig.Id_Door1) )
			{
				gameObject = new GagGameDoor();
				((GagGameDoor)gameObject).doorType = DoorType.DoorType_Enter;
				world.m_Objects.add(gameObject);
			}else if( element_object.getAttribute("id").equals(GagGameConfig.Id_Door2) )
			{
				gameObject = new GagGameDoor();
				((GagGameDoor)gameObject).doorType = DoorType.DoorType_Exit;
				world.m_Objects.add(gameObject);
			}else if( element_object.getAttribute("id").equals(GagGameConfig.Id_Treasure) )
			{
				gameObject = new GagGameTreasure();
				world.m_Objects.add(gameObject);
			}else if( element_object.getAttribute("id").equals(GagGameConfig.Id_Box) )
			{
				gameObject = new GagGameBox();
				world.m_Objects.add(gameObject);
			}else{
				Gdx.app.error("LoadXmlError:", element_object.getAttribute("id")+" is not a object");
				continue;
			}
			
			NodeList element_object_nodes = element_object.getChildNodes();
			for(int j=0; j<element_object_nodes.getLength(); ++j)
			{
				Node node = element_object_nodes.item(j);
				if( node.getNodeName().equals("x") )
				{
					gameObject.postion.x = Float.parseFloat(node.getTextContent())*scene_x_scale;
				}else if( node.getNodeName().equals("y") )
				{
					gameObject.postion.y = Float.parseFloat(node.getTextContent())*scene_y_scale;
				}else if( node.getNodeName().equals("w") )
				{
					gameObject.bounds.width = Float.parseFloat(node.getTextContent());
				}else if( node.getNodeName().equals("h") )
				{
					gameObject.bounds.height = Float.parseFloat(node.getTextContent());
				}else if( node.getNodeName().equals("TreasureType") )
				{
					
					for(int k=0; k<GagGameConfig.TreasureName.length; ++k)
					{
						if( node.getTextContent().equals(GagGameConfig.TreasureName[k]) )
						{
							((GagGameTreasure)gameObject).treasureType = TreasureType.values()[k];
						}
					}
					
				}else if( node.getNodeName().equals("downScaleG") )
				{
					gameObject.canBeDown = true;
					gameObject.downScaleByWorldG = Float.parseFloat(node.getTextContent());
				}
			}
		}
		
	}
	
	public static void SaveSceneToXml(String file, GagWorld world) throws Exception
	{
		//����XML
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		Element root = doc.createElement("scene");
		root.setAttribute("w", ""+world.worldBound.width);
		root.setAttribute("h", ""+world.worldBound.height);
		doc.appendChild(root);
		
		int len = world.m_Objects.size();
		for (int i = 0; i < len; i++)
		{
			GagGameObject object = world.m_Objects.get(i);
			String id_object = null;
			switch( object.objectType )
			{
				case ObjectType_Player:
					{
						id_object = GagGameConfig.Id_Player;
					}
					break;
				case ObjectType_Plaform:
					{
						id_object = GagGameConfig.Id_Platform;
					}
					break;
				case ObjectType_Door:
					{
						GagGameDoor door = (GagGameDoor)object;
						if(door.doorType==DoorType.DoorType_Enter)
						{
							id_object = GagGameConfig.Id_Door1;
						}else if(door.doorType==DoorType.DoorType_Exit){
							id_object = GagGameConfig.Id_Door2;
						}
					}
					break;
				case ObjectType_Box:
					{
						id_object = GagGameConfig.Id_Box;
					}
					break;
				case ObjectType_Treasure:
					{
						GagGameTreasure treasure = (GagGameTreasure)object;
						if( treasure.treasureType.ordinal()<TreasureType.TreasureType_EditorStart.ordinal() )
						{
							id_object = GagGameConfig.Id_Treasure;
						}
					}
					break;
			}
			
			if( id_object==null )
			{
				Gdx.app.error("SaveXmlError:", object.objectType.toString()+" not saved");
				if( object.objectType==ObjectType.ObjectType_Treasure )
				{
					Gdx.app.error("SaveXmlError:", ((GagGameTreasure)object).treasureType.toString()+" not saved");
				}
				continue;
			}

			Element element = doc.createElement("object");
			element.setAttribute("id", id_object);
			
			Element x = doc.createElement("x");
			x.setTextContent(""+object.postion.x);
			element.appendChild(x);
			
			Element y = doc.createElement("y");
			y.setTextContent(""+object.postion.y);
			element.appendChild(y);
			
			Element w = doc.createElement("w");
			w.setTextContent(""+object.bounds.width);
			element.appendChild(w);
			
			Element h = doc.createElement("h");
			h.setTextContent(""+object.bounds.height);
			element.appendChild(h);
			
			if( object.canBeDown )
			{
				Element downScaleG = doc.createElement("downScaleG");
				downScaleG.setTextContent(""+object.downScaleByWorldG);
				element.appendChild(downScaleG);
			}
			
			if( object.objectType==ObjectType.ObjectType_Treasure )
			{
				Element TreasureType = doc.createElement("TreasureType");
				TreasureType.setTextContent(""+GagGameConfig.TreasureName[ ((GagGameTreasure)object).treasureType.ordinal() ]);
				element.appendChild(TreasureType);
			}
			
			root.appendChild(element);
		}
		
//	 	<object id="Player">
//			<x>16</x>
//			<y>20</y>
//			<w>32</w>
//			<h>32</h>
//		</object>
		
//		Element element = doc.createElement("object");
//		element.setAttribute("id", "Player");
//		Element x = doc.createElement("x");
//		x.setTextContent("16");
//		element.appendChild(x);
//		
//		root.appendChild(element);
		
		//����XML
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		DOMSource source = new DOMSource(doc);
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		PrintWriter pw = new PrintWriter(new FileOutputStream(file));
		StreamResult result = new StreamResult(pw);
		transformer.transform(source, result);
		
	}
}
