package files;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;

public class GraphQLScript {
    public static void main (String[] args)
    {
        //QUERY

        //the variable on the row below is to showcase how a variable can be extracted outside the body
        int characterID =7686;
        String response = given().log().all().header("Content-Type", "application/json").
                body("{\"query\":\"query($characterId: Int!, $episodeId: Int!)\\n{\\n  character(characterId: $characterId)\\n  {\\n    name\\n    gender\\n    status\\n    type\\n    id\\n  }\\n  episode(episodeId: $episodeId)\\n  {\\n    name\\n    air_date\\n    episode\\n  }\\n  location( locationId: 8928)\\n  {\\n    id\\n    name\\n  }\\n}\",\"variables\":{\"characterId\":"+characterID+",\"episodeId\":6096}}").
                when().post("https://rahulshettyacademy.com/gq/graphql").
                then().extract().response().asString();
        System.out.println(response);
        JsonPath js = new JsonPath(response);
        String characterName = js.getString("data.character.name");
        Assert.assertEquals(characterName, "IME");

        //MUTATIONS

        String newCharacter= "IME";
        String mutationResponse = given().log().all().header("Content-Type", "application/json").
                body("{\"query\":\"mutation($locationName: String!, $characterName: String!, $episodeName: String!)\\n{\\n  createLocation(location:{name: $locationName, type: \\\"city\\\", dimension:\\\"Dimens\\\"})\\n  {\\n    id\\n  }\\n  \\n  createCharacter(character:{name: $characterName, type: \\\"4ovek\\\", status:\\\"stat\\\", species: \\\"human\\\",\\n  gender: \\\"MAle\\\", image: \\\"png\\\", originId: 555, locationId: 555})\\n    \\n  {\\n    id\\n  }\\n  createEpisode(episode: {name: $episodeName, air_date: \\\"0503\\\", episode:\\\"D21\\\"})\\n  {\\n    id\\n  }\\n  \\n  \\n  \\ndeleteLocations(locationIds: [1])\\n  {\\n    locationsDeleted\\n  }\\n  \\n}\",\"variables\":{\"locationName\":\"GRAD\",\"characterName\":\""+newCharacter+"\",\"episodeName\":\"EP_IME\"}}").
                when().post("https://rahulshettyacademy.com/gq/graphql").
                then().extract().response().asString();
        System.out.println(mutationResponse);
        JsonPath js1 = new JsonPath(mutationResponse);
    }
}
