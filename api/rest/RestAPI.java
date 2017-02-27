package rest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.sun.jersey.core.util.Base64;

import data.Picture;
import data.ProductStatus;
import database.DBClass;

//The first 3 bits of a input define the task
//000-> all Products in database
//001-> all Products in database of categorie Fleisch und Fisch
//002-> all Products in database fo categorie Milchprodukte und Eier
//003-> all Products in database of categorie Obst und Gemüse
//004-> all Products in database of categorie Sonstiges
//005-> all stored Products in database
//006-> all stored Products in database of categorie Fleisch und Fisch
//007-> all stored Products in database fo categorie Milchprodukte und Eier
//008-> all stored Products in database of categorie Obst und Gemüse
//009-> all stored Products in database of categorie Sonstiges
//010-> set store mode
//011-> set delete mode
//012 register token
//013 get all categories
//014 update Categorieifconfig

//015 update shelflife
//016 searchOrCreateProduct and store it after
//017 get photos of fridge and freezer 
//018 delete stored product by id
//19 update expDate of already stored Product
//20 search stored Products by Name
//21 search all Shoppinglists
//22 del product from shoppinglist
//23 get prductnames for autocomplete
//24 add product to shoppinglist
//25 add Product to database by Name
//26 dele Shoppinglist by id
//27 update name if Shoppinglist
//28 create new shoppingList
//29 get list of deleted items for shoppinglist suggestions


@Path("/hello")
public class RestAPI {
	
	
	private DBClass dbC = DBClass.getInstance( );
	private JsonAPI jAPI = JsonAPI.getInstance( );
	String token = "";
	
	@POST
	@Path("/sendPics")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTrackInJSON(String input) throws org.json.simple.parser.ParseException, FileNotFoundException, SQLException {
		System.out.println("woho "+input);
		JSONArray jArray= parseStringToJsonArray(input);
		JSONObject jObject = (JSONObject)jArray.get(0);
		System.out.println(jObject.toString());
		System.out.println(jObject.get("name").toString());
		
		Picture pic = new Picture(jObject.get("name").toString(),jObject.get("imageB64").toString());
		 dbC.storePicture(pic);
		return Response.status(201).entity("hallo").build();

	}
	


	@POST
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String input) throws SQLException, ParseException, JSONException, JsonGenerationException, JsonMappingException, IOException, org.json.simple.parser.ParseException {

		 System.out.println(input);

		 byte[] decoded= DatatypeConverter.parseBase64Binary(input);
		 String theInput = new String(decoded);
		//return Response.status(200).entity(output).build();
		
		
		 System.out.println("income: "+theInput);
		 String taskCode = "";
		 if(theInput.length()>=3){
			 taskCode  = theInput.substring(0,3);
		 }
		 String information = "";
		 if(theInput.length()>3){
			
			 information = theInput.substring(3,theInput.length());
		 }
		 System.out.println("code: "+taskCode+" info:"+information);

		 
		 
		 
		 if(taskCode.equals("000")){
			 return Response.status(200).entity(taskCode+jAPI.searchAllProductsByCategorie(0)).build();

		 }
		 if(taskCode.equals("001")){
			
			 return Response.status(200).entity(taskCode+jAPI.searchAllProductsByCategorie(1)).build();
			 
		 }
		 if(taskCode.equals("002")){
			 try {
				return Response.status(200).entity(taskCode+jAPI.searchAllProductsByCategorie(2)).build();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 if(taskCode.equals("003")){  
			 try {
				return Response.status(200).entity(taskCode+jAPI.searchAllProductsByCategorie(3)).build();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 if(taskCode.equals("004")){
			 try {
				return Response.status(200).entity(taskCode+jAPI.searchAllProductsByCategorie(4)).build();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 if(taskCode.equals("005")){
			 	JSONObject jobject = parseStringToJson(information);
			    int count =  Integer.parseInt(jobject.get("count").toString());
			    int lastID =  Integer.parseInt(jobject.get("lastID").toString());
			    ProductStatus pSTat = dbC.getCountAndLastIDStoredProducts();
			    //if(pSTat.getLastID()!=lastID || count != pSTat.getCount())
			    {
					 try {
							return Response.status(200).entity(taskCode+jAPI.searchStoredProductsByCategorie(0)).build();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    }
		 }
		 if(taskCode.equals("006")){
			 try {
				return Response.status(200).entity(taskCode+jAPI.searchStoredProductsByCategorie(1)).build();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 if(taskCode.equals("007")){
			 try {
				return Response.status(200).entity(taskCode+jAPI.searchStoredProductsByCategorie(2)).build();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 if(taskCode.equals("008")){ 
			 try {
				return Response.status(200).entity(taskCode+jAPI.searchStoredProductsByCategorie(3)).build();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 if(taskCode.equals("009")){
			 try {
				return Response.status(200).entity(taskCode+jAPI.searchStoredProductsByCategorie(4)).build();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 if(taskCode.equals("013")){
			 try {
				return Response.status(200).entity(taskCode+jAPI.searchAllCategories()).build();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 if(taskCode.equals("014")){
			 	JSONObject jobject = parseStringToJson(information);
			    int productID =  Integer.parseInt(jobject.get("productID").toString());
			    int categorieID =  Integer.parseInt(jobject.get("categorieID").toString());	
			    dbC.updateCategorieOfProductByID(productID,categorieID);
			 return Response.status(200).entity(taskCode+"updated").build();
		 }
		 if(taskCode.equals("015")){
			 	JSONObject jobject = parseStringToJson(information);
			    int productID =  Integer.parseInt(jobject.get("productID").toString());
			    int shelflife =  Integer.parseInt(jobject.get("shelflife").toString());	
			    dbC.updateShelfLifeByID(productID,shelflife);
			 return Response.status(200).entity(taskCode+"updated").build();
		 }
		 if(taskCode.equals("016")){
			 	JSONObject jobject = parseStringToJson(information);
			 	String productName =  jobject.get("productName").toString();
			 	return Response.status(200).entity(taskCode+jAPI.searchOrCreateProduct(productName)).build();
		 }
		 if(taskCode.equals("017")){
			/* JSONObject o1 =jarray.getJSONObject(0);
			 String date1 = 
			 JSONObject o2 =jarray.getJSONObject(1);*/
			// System.out.println("abgeschickt: "+taskCode+jAPI.getAllPhotos());
			 return Response.status(200).entity(taskCode+jAPI.getAllPhotos(information)).build();
		 }
		 if(taskCode.equals("018")){
			 	JSONObject jobject = parseStringToJson(information);
			    int id =  Integer.parseInt(jobject.get("storedID").toString());
			   dbC.deleteStoredProductByStoreID(id);
			 return Response.status(200).entity(taskCode+"deleted").build();
		 }
		 if(taskCode.equals("019")){
			 	JSONObject jobject = parseStringToJson(information);
			    int storedId =  Integer.parseInt(jobject.get("storedID").toString());
			    String newExpDateStr = jobject.get("newExpDate").toString();
			    System.out.println("new date: "+newExpDateStr);
			    //Sat Jan 28 19:50:15 GMT+00:00 2017
			    //Fri Sep 16 19:24:43 GMT+00:00 2016
			    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
			   
			    Date newExpDate = sdf.parse(newExpDateStr);
			   dbC.updateExpireDateOfdStoredProductByStoreID(storedId,newExpDate);
			 return Response.status(200).entity(taskCode+"deleted").build();
		 }
		 if(taskCode.equals("020")){
			 	JSONObject jobject = parseStringToJson(information);
			 	String productName =  jobject.get("productName").toString();
			 	return Response.status(200).entity(taskCode+jAPI.searchStoredProductsByName(productName)).build();
		 }
		 if(taskCode.equals("021")){
			 System.out.println(taskCode+jAPI.searchAllShoppingLists());
			 return Response.status(200).entity(taskCode+jAPI.searchAllShoppingLists()).build();
		 }
		 if(taskCode.equals("022")){
			 	JSONObject jobject = parseStringToJson(information);
			    int productid =  Integer.parseInt(jobject.get("productid").toString());
			    int shoppinglistid =  Integer.parseInt(jobject.get("shoppinglistid").toString());
			    dbC.deleteProductFromShoppingList(productid, shoppinglistid);
			 return Response.status(200).entity(taskCode+"deleted").build();
		 }
		 
		 if(taskCode.equals("023")){
			return Response.status(200).entity(taskCode+jAPI.searchProductsForAutocomplete()).build();
		 }
		 if(taskCode.equals("024")){
			 	JSONObject jobject = parseStringToJson(information);
			    int productid =  Integer.parseInt(jobject.get("productid").toString());
			    int shoppinglistid =  Integer.parseInt(jobject.get("shoppinglistid").toString());
			    dbC.addProducttoShoppingList(productid, shoppinglistid);
			    return Response.status(200).entity(taskCode+"added").build();
		 }
		 if(taskCode.equals("025")){
			 	JSONObject jobject = parseStringToJson(information);
			    String productname =  jobject.get("name").toString();
			    dbC.insertNewProduct(0,productname);
			    return Response.status(200).entity(taskCode+jAPI.searchProductByName(productname)).build();
		 }
		 if(taskCode.equals("026")){
			 	JSONObject jobject = parseStringToJson(information);
			    int shoppinglistid =  Integer.parseInt(jobject.get("shoppinglistid").toString());
			    dbC.deleteShoppingListById(shoppinglistid);
			    return Response.status(200).entity(taskCode+"added").build();
		 }
		 if(taskCode.equals("027")){
			 	JSONObject jobject = parseStringToJson(information);
			    int shoppinglistid =  Integer.parseInt(jobject.get("shoppinglistid").toString());
			    String newshoppinglistname =  jobject.get("name").toString();
			    dbC.updateShoppingListNameById(shoppinglistid,newshoppinglistname);
			    return Response.status(200).entity(taskCode+"updated").build();
		 }
		 if(taskCode.equals("028")){
			 return Response.status(200).entity(taskCode+jAPI.getNewShoppingList()).build();
		 }
		 if(taskCode.equals("029")){
			 System.out.println("new shoppinglist response: "+taskCode+jAPI.getDeletedProducts());
			 return Response.status(200).entity(taskCode+jAPI.getDeletedProducts()).build();
		 }
		 if(taskCode.equals("030")){
			 	JSONObject jobject = parseStringToJson(information);
			    String name =  jobject.get("productname").toString();
			   dbC.deleteStoredProductByName(name);
			 return Response.status(200).entity(taskCode+"deleted").build();
		 }
		 if(taskCode.equals("031")){
			 	JSONArray jarray = parseStringToJsonArray(information);
			 	JSONObject jObject = null;
			 	for(int i = 0; i < jarray.size() ;i++){
			 		jObject = (JSONObject) jarray.get(i);
			 		dbC.inserPic();
			 	}
			    String name =  jObject.get("productname").toString();
			   System.out.println("name: "+name	);
			 return Response.status(200).entity(taskCode+"deleted").build();
		 }
		 return Response.status(200).entity(taskCode+"fail").build();
	}
	public JSONArray parseStringToJsonArray(String jsonstr) throws org.json.simple.parser.ParseException{
	 	JSONParser parser = new JSONParser();
	 	JSONArray jArray = (JSONArray) parser.parse(jsonstr);
	 	return jArray;
	}	
	public JSONObject parseStringToJson(String jsonstr) throws org.json.simple.parser.ParseException{
	 	JSONParser parser = new JSONParser();
	 	System.out.println("hadjwhdjajkwhdwajkhdwajk!!!!"+jsonstr);
	 	JSONObject jobject = (JSONObject) parser.parse(jsonstr);
	 	return jobject;
	}
}
