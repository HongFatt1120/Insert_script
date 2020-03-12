/*
 * DatabaseHelper class is used to handle inserting data   
 * By doing a REST API call to server.  
 * 
 * @version 1.0 10 March 2020  
 * 
 * @author Siew HongFatt 
 * */
package smart_meterInsert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DatabaseHelper {
	/**
	 * DatabaseHelper class is developed using a singleton pattern it required to
	 * set the parameters before it can do an API request to server
	 */

	/* DatabaseHelper object for singleton pattern */
	private static DatabaseHelper single_instance = null;

	/* a listed list hashmap to format query string values */
	private LinkedHashMap<String, String> postParams = new LinkedHashMap<String, String>();

	/**
	 * Retrieve the current databaseHelper instance, 
	 * if not created. Create and return instance
	 * 
	 * @param null
	 * @return DatabaseHelper instance
	 */
	public static DatabaseHelper getInstance() {
		if (single_instance == null)
			single_instance = new DatabaseHelper();

		return single_instance;
	}

	/**
	 * Do a POST request to server and get the http response to determine result of
	 * the POST request
	 * 
	 * @param null
	 * @return responseCode return the HTTP response
	 * @exception IOException I/O operations got interrupt.
	 */
	public int httpPost() {
		int responseCode = 0;
		try {
			URL url = new URL(PropertiesReader.prop.getProperty("API")
			        + getQuery());
//			System.out.println(url.toString());
			HttpURLConnection con = (HttpURLConnection) url
			        .openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);

			responseCode = con.getResponseCode();
//			System.out.println("POST Response Code :: " + responseCode);
			return responseCode;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			postParams.clear();
		}
		return responseCode;

	}

	/**
	 * Store the values into paramsName
	 * 
	 * @param table      database table name
	 * @param paramsName database columns name
	 * @param value      data to be inserted
	 * @return boolean return true when the parameter is set correctly.
	 */
	public boolean prepareParams(String table, String[] paramsName,
	        String[] value) {

		if ((paramsName.length != value.length)
		        || (table.length() < 1))
			return false;
		postParams.put("table", table);
		for (int i = 0; i < paramsName.length; i++) {
			postParams.put(paramsName[i], value[i]);
		}

		return true;
	}

	/**
	 * format and encode postParams value into a query strings
	 * 
	 * @return result
	 * @throws UnsupportedEncodingException
	 */
	public String getQuery() throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		Set<?> set = postParams.entrySet();
		if (set.isEmpty() || set.size() < 1)
			return null;
		// Get an iterator
		Iterator<?> i = set.iterator();

		// Display elements
		while (i.hasNext()) {
			Map.Entry pair = (Map.Entry) i.next();
			if (first) {
				first = false;
			} else {
				result.append("&");
			}
			result.append(URLEncoder.encode(pair.getKey().toString(),
			        "utf-8"));

			if (pair.getValue().toString().length() >= 1) {
				result.append("=");
			}
			String encodedval = URLEncoder
			        .encode(pair.getValue().toString(), "utf-8")
			        .replace("+", "%20");
			encodedval = encodedval.replace("%3A", ":");
			result.append(encodedval);
		}
		return result.toString();
	}
}
