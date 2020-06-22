package apiTest.testEndPoints;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.request;
import static io.restassured.RestAssured.delete;
import static helper.Endpoints.*;

public class TestGetCountryName {


	@Test
	public void testAcountryInDetail() {
		
		//This method is to validate the endpoint country where response contains all expected key and values 
		Response resp = get(getAllCountries);
		String respString = resp.asString();
		System.out.print(respString);

		//Validate few country names India, Ethiopia, South Africa 
		Assert.assertTrue(respString.contains("Ethiopia"));
		Assert.assertTrue(respString.contains("India"));
		Assert.assertTrue(respString.contains("South Africa"));

		JSONArray countries = new JSONArray(respString); // Why I am not changing into json object

		int actualTotalCountries = countries.length();
		System.out.println("\ncountreis count ======================\n");
		System.out.println(actualTotalCountries);

		//Validate total countries
		int expectedTotalCountires = 250;
		Assert.assertTrue(actualTotalCountries == expectedTotalCountires);

		// get the first country 
		JSONObject afgJson = countries.getJSONObject(0);

		//Validate Afghanistan JSON object is correct or not 
		System.out.println(afgJson);
		String actualCity = afgJson.getString("capital");

		String expectedCity = "Kabul";
		Assert.assertTrue(actualCity.equals(expectedCity));

		//Validate population 
		int expectedPopulation = 100000;
		int actualPopulation = afgJson.getInt("population");
		Assert.assertTrue(expectedPopulation > actualPopulation);

	}

	// Test is written in Data driven Approach 
	@Test(dataProvider ="countryNameData")
	public void testValidateEachCoutryCapitalCity(String countryName, String city) {
		//Validate each country and city is matching  
		String endPointGetByName = getBycountryName + countryName.toLowerCase();


		Response resp = get(endPointGetByName);

		String respString = resp.asString();
		System.out.print(respString);

		//Get JSON Object
		JSONArray countries = new JSONArray(respString); 
		JSONObject country = countries.getJSONObject(0);

		//Validate respective country JSON object is correct or not 
		System.out.println(country);
		String actualCity = country.getString("capital");
		String expectedCity =city;
		Assert.assertEquals(actualCity,expectedCity);

	}


	@DataProvider
	public Object[][] countryNameData() {
		return new Object[][] {
			{ "Afghanistan", "Kabul" },
			{ "Ethiopia", "Addis Ababa" },
			{ "United States Of America", "Washington, D.C." },
			{ "United States", "Washington, D.C." },
			{ "India", "New Delhi" },
			{ "Belgium", "Brussels" },
			{ "Netherlands", "Amsterdam" },
			{ "Spain", "Madrid" },
			{ "Denmark", "Copenhagen" },
			{ "Finland", "Helsinki" },
		};
	}


	// Test case is written in Data driven Approach 
	@Test(dataProvider ="countryCodeData")
	public void testValidateEachCoutryByCoutnryCode(String countryCode, String city) {
		//validate each country and city is matching using country code 
		String endPointGetByName = baseUri + "/alpha/" + countryCode.toLowerCase();


		Response resp = get(endPointGetByName);

		String respString = resp.asString();
		System.out.print(respString);

		//Get JSON object
		JSONObject country = new JSONObject(respString);

		// validate respective country JSON object is correct or not 
		System.out.println(country);
		String actualCity = country.getString("capital");
		String expectedCity =city;
		Assert.assertTrue(actualCity.equals(expectedCity));

	}

	@DataProvider
	public Object[][] countryCodeData() {
		return new Object[][] {
			{ "af", "Kabul" },
			{ "et", "Addis Ababa" },
			{ "us", "Washington, D.C." },
			{ "in", "New Delhi" }
		};
	}
}
