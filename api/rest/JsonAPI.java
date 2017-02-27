package rest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import data.Product;
import database.DBClass;

/**
 * Converts Java Objects to JSON
 */
public class JsonAPI {
	
	ObjectMapper mapper = new ObjectMapper();

	private static JsonAPI instance;

	private DBClass dbC = DBClass.getInstance( );
	
	/**
	 * Constructor
	 */
	private JsonAPI() {
	}

	/**
	 * Singleton pattern, returns a Object of JsonAPI if it already exists or creats a new Object if it does not exist yet
	 * @return
	 */
	public static JsonAPI getInstance() {
		if (JsonAPI.instance == null) {
			JsonAPI.instance = new JsonAPI();
		}
		return JsonAPI.instance;
	}
	
	/**
	 * converts the return value of  getProductsByCategorie to Json
	 * @param categorieID
	 * @return
	 * @throws SQLException
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public String searchAllProductsByCategorie(int categorieID) throws SQLException, JsonGenerationException, JsonMappingException, IOException{
		
		return objectToJson(dbC.getProductsByCategorie(0,categorieID));
	}
	/**
	 * converts the return value of  getProductsByCategorie to Json
	 * @param categorieID
	 * @return
	 * @throws SQLException
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public String searchStoredProductsByCategorie(int categorieID) throws SQLException, JsonGenerationException, JsonMappingException, IOException{
		
		List<Product> prodList = new ArrayList<Product>();
		
		prodList = dbC.getProductsByCategorie(1,categorieID);
		return objectToJson(dbC.getProductsByCategorie(1,categorieID));
	}
	/**
	 * converts the return value of  getallCategories to Json
	 * @return
	 * @throws SQLException
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public String searchAllCategories() throws SQLException, JsonGenerationException, JsonMappingException, IOException{
		
		return objectToJson(dbC.getallCategories());
	}
	/**
	 * converts the return value of  searchOrCreateProduct to Json
	 * @param productName
	 * @return
	 * @throws SQLException
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public String searchOrCreateProduct(String productName) throws SQLException, JsonGenerationException, JsonMappingException, IOException{
		
		return objectToJson(dbC.searchOrCreateProduct(productName));
	}
	
	/**
	 * converts the returns a given object to Json
	 * @param obj
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public String objectToJson(Object obj) throws JsonGenerationException, JsonMappingException, IOException{
		
		return mapper.writeValueAsString(obj);
	}
	/**
	 * converts the return value of  getPircturesFromDB to Json
	 * @return
	 * @throws SQLException
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 * @throws JSONException 
	 */
	public String getAllPhotos(String jr) throws SQLException, JsonGenerationException, JsonMappingException, IOException, JSONException{
		
		return objectToJson(dbC.getPircturesFromDB(jr));
	}
	/**
	 * converts the return value of  getStoredProductsByName to Json
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public String searchStoredProductsByName(String name) throws SQLException, JsonGenerationException, JsonMappingException, IOException{
		
		return objectToJson(dbC.getStoredProductsByName(name));
	}
	/**
	 * converts the return value of  getAllShoppingLists to Json
	 * @return
	 * @throws SQLException
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public String searchAllShoppingLists() throws SQLException, JsonGenerationException, JsonMappingException, IOException{
		return objectToJson(dbC.getAllShoppingLists());
	}
	/**
	 * converts the return value of  getProductsByCategorie to Json
	 * @return
	 * @throws SQLException
	 * @throws JSONException 
	 */
	public String searchProductsForAutocomplete() throws SQLException, JSONException{
		 List<Product> pList =  dbC.getProductsByCategorie(0,0);
		 JSONArray jArray = new JSONArray();
		 
		 for(Product p : pList){
			 JSONObject  jObject = new JSONObject ();
			 jObject.put("id", p.getProductID());
			 jObject.put("name",p.getProductname());
			 jArray.put(jObject);				 			 
		 }
		
		return jArray.toString();
	}
	/**
	 * converts the return value of  searchProductByName to Json
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public String searchProductByName(String name) throws SQLException, JsonGenerationException, JsonMappingException, IOException{
		return objectToJson(dbC.searchProductByName(name));
	}
	/**
	 * converts the return value of  createNewShoppingList to Json
	 * @return
	 * @throws SQLException
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public String getNewShoppingList() throws SQLException, JsonGenerationException, JsonMappingException, IOException{
		return objectToJson(dbC.createNewShoppingList());
	}
	/**
	 * converts the return value of  getDeletedProducts to Json
	 * @return
	 * @throws SQLException
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public String getDeletedProducts() throws SQLException, JsonGenerationException, JsonMappingException, IOException{
		return objectToJson(dbC.getDeletedProducts());
	}

	
	
	
	

}
