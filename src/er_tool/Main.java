package er_tool;

import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {
	private static final String in_file = "c:\\20140227\\input.edm";
	private static final String out_file = "c:\\20140227\\demo_b.edm";
	private static final String csv_file = "c:\\20140227\\dictionary_1.csv";
	private static final String xmlRootName = "ERD";
	private static final String[] ele = {"ENTITY","ATTR","INDEX","RELATION"};
	private static Main test = new Main();
	private static Dictionary dictionary = new Dictionary(csv_file);

	public static void main(String[] args) {
		dictionary.ｒeadDictionary();
		if(test.fncXMLFileCheckCreate()==false) return;
		test.fncXMLChgPnameAttr();
	}

	private boolean fncXMLFileCheckCreate() {
		File wrkFile = new File(out_file);
		if(wrkFile.isFile() == false) {
			try {
				DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docbuilder = dbfactory.newDocumentBuilder();
				Document doc = docbuilder.newDocument();
				Element root = doc.createElement(xmlRootName);
				doc.appendChild(root);
				// XML文書出力
				test.fncXMLPutDoc(doc);
				System.out.println("success create xml file.");
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
				System.out.println("[error]failure create xml file.");
				return false;
			}
		}
		return true;
	}

	private Document fncXMLGetDoc(){
		Document doc = null;
		try {
			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder docbuilder = dbfactory.newDocumentBuilder();
			doc = docbuilder.parse(new File(in_file));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	private boolean fncXMLPutDoc(Document doc){
		try {
			TransformerFactory tfactory = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = tfactory.newTransformer();
			File outfile = new File(out_file);
			transformer.transform(new DOMSource(doc), new StreamResult(outfile));
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			return false;
		} catch (TransformerException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private void fncXMLChgPnameAttr(){
		String agValue = null;
		boolean wrkChk = false;
		Document doc = test.fncXMLGetDoc();
		// ルート要素を取得
		Element root = doc.getDocumentElement();
	    // 要素のリストを取得
		for(int h=0; h < ele.length; h++){
		    NodeList list = root.getElementsByTagName(ele[h]);
		    for (int i=0; i < list.getLength() ; i++) {
		    	Node element = list.item(i);
		    	if(element.getNodeType()==Node.ELEMENT_NODE){
		    		Element element_a = (Element)element;
		    		
		    		String tag = element_a.getNodeName();
		    		if(tag.equals(ele[h])){
			    		String name = element_a.getAttribute("P-NAME");
			    		try {
			    	        File csv = new File(csv_file); 
			    	        BufferedReader br = new BufferedReader(new FileReader(csv));
			    	        // 最終行まで読み込む
			    	        String line = "";
			    	        while ((line = br.readLine()) != null) {
			    	            // 1行をデータの要素に分割
			    	        	String[] st = line.split(",", 0);
			    	        	String str0 = st[0].replaceAll("\"", "");
			    	            	if(name.equals(str0)) {
			    	            		wrkChk = true;
			    	            			agValue = st[1].replaceAll("\"", "").toUpperCase();
			    	            			element_a.setAttribute("P-NAME", agValue);
			    	            			System.out.println("P-NAME:"+agValue);
			    	            		break;
			    	            	}
			    	        }
			    	        br.close();
			    	    } catch (FileNotFoundException e) {
			    	        // Fileオブジェクト生成時の例外捕捉
			    	        e.printStackTrace();
			    	    } catch (IOException e) {
			    	        // BufferedReaderオブジェクトのクローズ時の例外捕捉
			    	        e.printStackTrace();
			    	    }
			    	}
		    	}
		    }
		    if(wrkChk == true){
			    // XML 文書出力
		    	test.fncXMLPutDoc(doc);
		    	System.out.println("[INFO]変更しました。");
		    }else{
		    	System.out.println("[INFO]変更する指定要素が見つかりません。");
		    }
		}
	}
	
}
