package er_tool;

import java.io.*;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;




public class Main {

	public static void main(String[] args) {
		//作成するディレクトリを指定
	      File file_d = new File("c:\\test0227");
	      //ディレクトリを作成します
	      if (file_d.mkdir()) {
	         System.out.println("ディレクトリを作成しました。");
	      } else {
	         System.out.println("ディレクトリを作成に失敗しました。");
	      }
	      File newfile = new File("c:\\test0227\\newfile.edm");
	      try{
	    	  if (newfile.createNewFile()){
	    	      System.out.println("ファイルの作成に成功しました");
	    	  }else{
	    	      System.out.println("ファイルの作成に失敗しました");
	    	  }
	      }catch(IOException e){
	          System.out.println(e);
	      }
	      try{
	    	  FileReader f = new FileReader("c:\\20140227\\input.edm");
	    	  FileWriter fw = new FileWriter(newfile);
	    	  BufferedReader b = new BufferedReader(f);
	    	  String s;
	    	  while((s = b.readLine())!=null){
	    		  fw.write(s+"\r\n");            	
	    	  }
	    	  fw.close();
	      }catch(Exception e){
	    	  System.out.println("失敗しました");
	      }
//----------------------------------------------------------------------------------------------	      
	      
//	      try {
//	          File csv = new File("c:\\20140227\\dictionary.csv"); // CSVデータファイル
//
//	          BufferedReader br = new BufferedReader(new FileReader(csv));
//
//	          // 最終行まで読み込む
//	          String line = "";
//	          while ((line = br.readLine()) != null) {
//
//	            // 1行をデータの要素に分割
//	            StringTokenizer st = new StringTokenizer(line, ",");
//	            while (st.hasMoreTokens()) {
//	              // 1行の各要素をタブ区切りで表示
//	              System.out.print(st.nextToken());
//	            }
//	            System.out.println();
//	          }
//	          br.close();
//
//	        } catch (FileNotFoundException e) {
//	          // Fileオブジェクト生成時の例外捕捉
//	          e.printStackTrace();
//	        } catch (IOException e) {
//	          // BufferedReaderオブジェクトのクローズ時の例外捕捉
//	          e.printStackTrace();
//	        }
	      
//--------------------------------------------------------------------------------------------------	  
	      
	      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    		DocumentBuilder builder;
	    		FileInputStream in;
	    		Document root;
	    		Node connection = null;
				try {
					builder = factory.newDocumentBuilder();
					in = new FileInputStream("c:\\test0227\\newfile.edm");
					root = builder.parse(in);
					connection = root.getFirstChild();
				} catch (ParserConfigurationException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
	    		
	      NodeList params = connection.getChildNodes();
	      
	      String driver = null;
	      String url = null;
	      String user = null;
	      String password = null;
	      Properties props = new Properties();
	      
	      for (int i = 0; i < params.getLength(); i++) {
	    	  Node node = params.item(i);
	    	  if (node.getNodeType() == Node.ELEMENT_NODE) {
	    		  String tag = node.getNodeName();
	    		  if (tag.equals("ENTITY")) {
	    			  NamedNodeMap attrs = node.getAttributes();
	    			  String name = attrs.getNamedItem("P-NAME").getNodeValue();
	    			  System.out.println(name+"\n");
	    			  for (int j = 0; j < node.getChildNodes().getLength(); j++) {
	    				  Node node_2 = node.getChildNodes().item(j);
	    				  if (node_2.getNodeType() == Node.ELEMENT_NODE) {
	    					  String tag_2 = node_2.getNodeName();
	    					  NamedNodeMap attrs_2 = node_2.getAttributes();
	    					  if (tag_2.equals("ATTR") || tag_2.equals("INDEX") || tag_2.equals("RELATION")) {
	    						  String name_2 = attrs_2.getNamedItem("P-NAME").getNodeValue();
	    						    try {
	    						          File csv = new File("c:\\20140227\\dictionary.csv"); // CSVデータファイル

	    						          BufferedReader br = new BufferedReader(new FileReader(csv));

	    						          // 最終行まで読み込む
	    						          String line = "";
	    						          while ((line = br.readLine()) != null) {

	    						            // 1行をデータの要素に分割
	    						            StringTokenizer st = new StringTokenizer(line, ",");
	    						            
	    						            while (st.hasMoreTokens()) {
	    						            
	    						              if(name_2.equals(st.nextToken().replaceAll("\"",""))) {
	    						            	  name_2 = st.nextToken().replaceAll("\"","");
	    						              }
	    						            }
//	    						            System.out.println(st.nextToken());
	    						          }
	    						          br.close();

	    						        } catch (FileNotFoundException e) {
	    						          // Fileオブジェクト生成時の例外捕捉
	    						          e.printStackTrace();
	    						        } catch (IOException e) {
	    						          // BufferedReaderオブジェクトのクローズ時の例外捕捉
	    						          e.printStackTrace();
	    						        }
	    					  
	    						  System.out.println("\t"+name_2+"\n");
	    					  }
	    				  }
	    			  }
	    		  }
	    	  }
	      }
	      
	      
	}

}
