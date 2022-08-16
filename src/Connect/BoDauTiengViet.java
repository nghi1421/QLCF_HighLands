package Connect;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class BoDauTiengViet {
	public String boDau(String s) {
		  
		  String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		  Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		  return pattern.matcher(temp).replaceAll("");
		 }
}
