package smart_meterInsert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DatabaseHelper {
	private LinkedHashMap<String, String> postParams = new LinkedHashMap<String, String>();
	private static DatabaseHelper single_instance = null;

	public static DatabaseHelper getInstance() {
		if (single_instance == null)
			single_instance = new DatabaseHelper();

		return single_instance;
	}

	public int httpPost() {

		int responseCode = 0;
		try {
			URL url = new URL(Prop.p.getProperty("API") + getQuery());
			System.out.println(url.toString());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);

			responseCode = con.getResponseCode();
			System.out.println("POST Response Code :: " + responseCode);
			return responseCode;
//			if (responseCode == HttpURLConnection.HTTP_OK) {
//				return true;
//			} else {
//				System.out.println("Writing");
//				
//				return false;
//			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			postParams.clear();
		}
		return responseCode;

	}

	public boolean prepareParams(String table, String[] paramsName, String[] value) {

		if (paramsName.length != value.length || (table.length() < 1))
			return false;
		postParams.put("table", table);
		for (int i = 0; i < paramsName.length; i++) {
			postParams.put(paramsName[i], value[i]);
		}

		return true;
	}

	public String getQuery() throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;

		Set set = postParams.entrySet();
		if (set.isEmpty() || set.size() < 1)
			return null;

		// Get an iterator
		Iterator i = set.iterator();

		// Display elements
		while (i.hasNext()) {
			Map.Entry pair = (Map.Entry) i.next();
			if (first)
				first = false;
			else
				result.append("&");
			result.append(URLEncoder.encode(pair.getKey().toString(), "utf-8"));
			if (pair.getValue().toString().length() >= 1)
				result.append("=");
			String encodedval = URLEncoder.encode(pair.getValue().toString(), "utf-8").replace("+", "%20");
			encodedval = encodedval.replace("%3A", ":");
			result.append(encodedval);
		}
//		String decoded = URLDecoder.decode(result.toString(), "UTF-8");

		return result.toString();
	}
}
