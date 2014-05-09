package nik.parsers;
import java.util.ArrayList;

import nik.Data.Users;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class DOMPars {
	private ArrayList<Users> users = new ArrayList<Users>();
	
	@Override
	public String toString() {
		return "DOMPars [users=" + users + "]";
	}

	private static String getTagValue(String sTag, Element eElement){
	    NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    return nValue.getNodeValue();   
	}
	
	public void pars(Document doc){
	    NodeList nList = doc.getElementsByTagName("person");
		for (int i = 0; i < nList.getLength(); i++) {
			Users user = new Users();
			Node nNode = nList.item(i);      
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				user.setUserName(getTagValue("firstName",eElement));
			    user.setEmail(getTagValue("email",eElement));
			    user.setPassword(getTagValue("password",eElement));
			}
			users.add(user);
		}
	}
	public ArrayList<Users> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<Users> users) {
		this.users = users;
	}
}