package kum.App;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mysql.jdbc.Connection;

public class Case2 implements Case{

	private final  Scanner sc;
	
	private  final String USER;
	private  final String PASSWORD;
	
	public static final String CREATE_TABLE_USER= "CREATE TABLE user("
			+ "id INT PRIMARY KEY AUTO_INCREMENT, user_id varchar(25), name VARCHAR (255), age varchar(25))";
	

	public Case2(Scanner sc, String uSER, String pASSWORD) {
		this.sc = sc;
		USER = uSER;
		PASSWORD = pASSWORD;
	}


	static private String getAttrValue(Node node,String attrName)
	{
	    if ( ! node.hasAttributes() ) return "";
	    NamedNodeMap nmap = node.getAttributes();
	    if ( nmap == null ) return "";
	    Node n = nmap.getNamedItem(attrName);
	    if ( n == null ) return "";
	    return n.getNodeValue();
	}
	
	
	static private String getTextContent(Node parentNode,String childName)
	{
	    NodeList nlist = parentNode.getChildNodes();
	    for (int i = 0 ; i < nlist.getLength() ; i++) {
	    Node n = nlist.item(i);
	    String name = n.getNodeName();
	    if ( name != null && name.equals(childName) )
	        return n.getTextContent();
	    }
	    return "";
	}

	@Override
	public boolean run() {
		System.out.println("Enter database name: ");
		String CONNECTION_URL="jdbc:mysql://localhost:3306/"+sc.next()+"?useSSL=false";
		try(Connection connection = (Connection) DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD)){
    		Statement statement = connection.createStatement();
    		statement.execute(CREATE_TABLE_USER);
			statement.close();
			File file = new File("user.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlDoc = builder.parse(file);
			
			XPath  xpath = XPathFactory.newInstance().newXPath();
			Object res = xpath.evaluate("/catalog/user", xmlDoc, XPathConstants.NODESET);

			PreparedStatement stmt = connection
				    .prepareStatement("INSERT INTO user(user_id, name, age) VALUES(?, ?, ?)");

			
			Node parentNode = xmlDoc.getFirstChild();
			NodeList nlist = parentNode.getChildNodes();
			for (int i = 0 ; i < nlist.getLength() ; i++) {
			    Node node = nlist.item(i);
			    List<String> columns = Arrays.asList(getAttrValue(node, "id"),
			        getTextContent(node, "name"),
			        getTextContent(node, "age"));
			    for (int n = 0 ; n < columns.size() ; n++) {
			    stmt.setString(n+1, columns.get(n));
			    }
			    stmt.execute();
			}
    		
		}
		catch(SQLException e){
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
