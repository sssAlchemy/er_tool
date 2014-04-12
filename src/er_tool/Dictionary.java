package er_tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dictionary {

	HashMap<Integer, String> index;
	HashMap<String, String> word;
	int word_count = 0;
	String[] keyArray;
	
	Dictionary(String input_file) {
		index = new HashMap<Integer,String>();
		word = new HashMap<String,String>();
		try {
	        File csv = new File(input_file); 
	        BufferedReader br = new BufferedReader(new FileReader(csv));
	        // 最終行まで読み込む
	        String line = "";
	        String str = null;
	        while ((line = br.readLine()) != null) {
	            // 1行をデータの要素に分割
	        	String[] st = line.split(",", 0);
	        	index.put(word_count, st[0].replaceAll("\"", ""));
	        	if(st.length==1) {
	        		word.put(index.get(word_count), "");
	        	}else {
	        		str = st[1].replaceAll("\"", "");
	        		Pattern pat = Pattern.compile(".+[A-Z]");
	        		Matcher mat = pat.matcher(str);
	        		if(mat.find()){
	        			str = mat.replaceAll(str.substring(mat.start(),mat.end()-1)+"_"+str.substring(mat.end()-1,mat.end()));
	        		}
	        		word.put(index.get(word_count), str.toUpperCase());
	        	}
	        	word_count++;
	        }
	        br.close();
	    } catch (FileNotFoundException e) {
	        // Fileオブジェクト生成時の例外捕捉
	        e.printStackTrace();
	    } catch (IOException e) {
	        // BufferedReaderオブジェクトのクローズ時の例外捕捉
	        e.printStackTrace();
	    }
		
		//HashMap内の文字数の多い順にソート
		keyArray = new String[word_count];
		for (int j=0; j<keyArray.length; j++) {
			keyArray[j] = index.get(j);
		}
		quickSort(keyArray, 0, word_count-1);
		for (int j=0; j<keyArray.length; j++) {
			index.put(j, keyArray[keyArray.length-(j+1)]);
//			System.out.println("keyArray["+j+"] = "+keyArray[keyArray.length-(j+1)]);
		}
	}
	
	void quickSort(String[] array, int left, int right) {
		int curleft = left;
		int curright = right;
		int pivot = array[(curleft + curright) / 2].length();
		
		while (curleft <= curright) {
			while (array[curleft].length() < pivot) {
				curleft++;
			}
			while (array[curright].length() > pivot) {
				curright--;
			}
			if (curleft <= curright) {
				swap (array, curleft++, curright--);
			}
		}
		if (left < curright) {
			quickSort(array, left, curright);
		}
		if (curleft < right) {
			quickSort(array, curleft, right);
		}
	}
	
	void swap (String[] array, int idx1, int idx2) {
		String tmp = array[idx1];
		array[idx1] = array[idx2];
		array[idx2] = tmp;
	}
	
	//日本語を英語に変換するメソッド
	String jaToEn(String ja) {
		String en = null;
		String regex = null;
		for(int i=0; i<keyArray.length; i++){
			regex = index.get(i);
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(ja);
			ja = m.replaceAll(word.get(regex)+"_");
		}
		
		Pattern q = Pattern.compile("__");
		Matcher n = q.matcher(ja);
		ja = n.replaceAll("_");
		
		if(ja.endsWith("_")){
			ja = ja.substring(0, ja.length()-1);
		}
		en = ja;
		return en;
	}
	
	void ｒeadDictionary() {
		System.out.println(index.keySet());
		System.out.println(index.values());
		System.out.println(word.keySet());
		System.out.println(word.values());
		System.out.println("word_count = "+word_count);
	}
	
}
