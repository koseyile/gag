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
				}else if( node.getNodeName().equals("TreasureType") )
				{
					if( node.getTextContent().equals("athwartWorld") )
					{
						((GagGameTreasure)gameObject).treasureType = TreasureType.TreasureType_AthwartWorld;
					}else if( node.getTextContent().equals("umbrella") )
					{
						((GagGameTreasure)gameObject).treasureType = TreasureType.TreasureType_Umbrella;
					}else if( node.getTextContent().equals("killme") )
					{
						((GagGameTreasure)gameObject).treasureType = TreasureType.TreasureType_KillMe;
					}else if( node.getTextContent().equals("speed") )
					{
						((GagGameTreasure)gameObject).treasureType = TreasureType.TreasureType_Speed;
					}else if( node.getTextContent().equals("newScene") )
					{
						((GagGameTreasure)gameObject).treasureType = TreasureType.TreasureType_NewScene;
					}else if( node.getTextContent().equals("saveScene") )
					{
						((GagGameTreasure)gameObject).treasureType = TreasureType.TreasureType_SaveScene;
					}else if( node.getTextContent().equals("saveSceneAs") )
					{
						((GagGameTreasure)gameObject).treasureType = TreasureType.TreasureType_SaveSceneAs;
					}else if( node.getTextContent().equals("openScene") )
					{
						((GagGameTreasure)gameObject).treasureType = TreasureType.TreasureType_OpenScene;
					}else if( node.getTextContent().equals("runScene") )
					{
						((GagGameTreasure)gameObject).treasureType = TreasureType.TreasureType_RunScene;
					}else if( node.getTextContent().equals("stopScene") )
					{
						((GagGameTreasure)gameObject).treasureType = TreasureType.TreasureType_StopScene;
					}else if( node.getTextContent().equals("moreTool") )
					{
						((GagGameTreasure)gameObject).treasureType = TreasureType.TreasureType_MoreTool;
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
		//构建XML
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		Element root = doc.createElement("scene");
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
			}
			
			if( id_object==null )
			{
				Gdx.app.error("SaveXmlError:", object.objectType.toString()+" can not save");
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
			h.setTextContent(""+object.bounds.width);
			element.appendChild(h);
			
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
		
		//生成XML
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
