package com.weyu.json.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * A wrapper class for JSONObject to code it more quickly
 * @author weyu
 *
 */
public class JSONObjectHelper {
	final Object object;
	
	/**
	 * Construct Function.
	 * @param object
	 */
	public JSONObjectHelper(Object object){
		this.object = object;
	}
	
	/**
	 * Get wrapper if want a JSONObject
	 * @param key
	 * @return
	 */
	JSONObjectHelper		get(String key){
		if(!(object instanceof JSONObject)) return null;
		if(((JSONObject)object).containsKey(key))
			return new JSONObjectHelper(((JSONObject)object).get(key));
		return null;
	}
	/**
	 * Get wrapper if want a JSONArray
	 * @param key
	 * @return
	 */
	List<JSONObjectHelper> 	getList(String key){
		if(!(object instanceof JSONObject)) return null;
		if(((JSONObject)object).containsKey(key) && ((JSONObject)object).get(key) instanceof JSONArray){
			List<JSONObjectHelper> result = new ArrayList<JSONObjectHelper>();
			JSONArray array = (JSONArray)((JSONObject)object).get(key);
			for(int i =0 ; i<array.size();i++){
				result.add(new JSONObjectHelper(array.get(i)));
			}
			
			return result;
		}
		return null;
	}	
	
	/**
	 * Get value
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	<T> T	getValue(String key){
		if(((JSONObject)object).containsKey(key))
			return (T)((JSONObject)object).get(key);
		return null;		
	}
	
	/**
	 * Get List value
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	<T> List<T> getValueList(String key){
		if(((JSONObject)object).containsKey(key))
			return (List<T>)((JSONObject)object).get(key);
		return null;			
	}
	
	/**
	 * Get current object value
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	<T> T value(){
		return (T)object;
	}
	
	static void test() throws IOException{
		String s="{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}";
		JSONObject array = JSONObject.parse(s);
		
		JSONObjectHelper helper = new JSONObjectHelper(array);
		Long value = helper.get("1").get("2").get("3").getList("4").get(1).getValue("6");
		assert(value.toString().equals("7"));
		//assertEquals(value.toString(),"7");
		
		value = helper.get("1").get("2").get("3").getList("4").get(0).value();
		assert(value.toString().equals("5"));
		//assertEquals(value.toString(),"5");
	}
}
