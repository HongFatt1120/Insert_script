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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;

public class DatabaseHelper {
	/**
	 * DatabaseHelper class is developed using a singleton pattern it required to
	 * set the parameters before it can do an API request to server
	 */

	/* DatabaseHelper object for singleton pattern */
	private static DatabaseHelper single_instance = null;

	/* a listed list hashmap to format query string values */
//	private LinkedHashMap<String, String> postParams = new LinkedHashMap<String, String>();
	JSONObject params = new JSONObject();

	/**
	 * Retrieve the current databaseHelper instance, if not created. Create and
	 * return instance
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
	public int httpPost(String table) {
		int responseCode = 0;
		try {
			URL url = new URL(
			        PropertiesReader.prop.getProperty("API") + table);

			HttpURLConnection con = (HttpURLConnection) url
			        .openConnection();
			
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			OutputStream os = con.getOutputStream();
            os.write(params.toString().getBytes("UTF-8"));
            os.close();
            System.out.println(params);
			responseCode = con.getResponseCode();
			System.out.print(responseCode);
			return responseCode;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			params.clear();
		}
		return responseCode;

	}

	/**
	 * Store the values into paramsName
	 * 
	 * @param paramsName database columns name
	 * @param value      data to be inserted
	 * @return boolean return true when the parameter is set correctly.
	 */
	public boolean prepareParams( String[] paramsName,
	        String[] value) {

		if ((paramsName.length != value.length))
		{
			return false;
		}
		for (int i = 0; i < paramsName.length; i++) {
			params.put(paramsName[i], value[i]);
		}
	
		return true;
	}

	
}
