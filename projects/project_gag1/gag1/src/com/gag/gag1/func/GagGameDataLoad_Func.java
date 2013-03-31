package com.gag.gag1.func;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.badlogic.gdx.Gdx;
import com.gag.gag1.GagWorld;
import com.gag.gag1.struct.GagGameDoor;
import com.gag.gag1.struct.GagGameObject;
import com.gag.gag1.struct.GagGamePlatform;
import com.gag.gag1.struct.GagGameDoor.DoorType;

public class GagGameDataLoad_Func {
	public static void LoadSceneByXml(String file, GagWorld world) throws Exception
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(file);
		Element element = doc.getDocumentElement();
		
		NodeList objectNodes = element.getElementsByTagName("object");
		
		for( int i=0; i<objectNodes.getLength(); i++ )
		{
			Element element_object = (Element) objectNodes.item(i);
			GagGameObject gameObject = null;
			if( element_object.getAttribute("id").equals("Player") )
			{
				gameObject = world.m_Player;
			}else if( element_object.getAttribute("id").equals("Platform") )
			{
				gameObject = new GagGamePlatform();
				world.m_Objects.add(gameObject);
			}else if( element_object.getAttribute("id").equals("Door1") )
			{
				gameObject = new GagGameDoor();
				((GagGameDoor)gameObject).doorType = DoorType.DoorType_Enter;
				world.m_Objects.add(gameObject);
			}else if( element_object.getAttribute("id").equals("Door2") )
			{
				gameObject = new GagGameDoor();
				((GagGameDoor)gameObject).doorType = DoorType.DoorType_Exit;
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
					gameObject.postion.x = Float.parseFloat(node.getTextContent());
				}else if( node.getNodeName().equals("y") )
				{
					gameObject.postion.y = Float.parseFloat(node.getTextContent());
				}else if( node.getNodeName().equals("w") )
				{
					gameObject.bounds.width = Float.parseFloat(node.getTextContent());
				}else if( node.getNodeName().equals("h") )
				{
					gameObject.bounds.height = Float.parseFloat(node.getTextContent());
				}
			}
		}
		
	}
}
