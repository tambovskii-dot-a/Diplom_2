import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import pojo.RootUser;

import static org.junit.Assert.assertEquals;

public class CreateUniqueUserTest {
    StellarBurgersRestClient client = new StellarBurgersRestClient();
    RootUser rootUser;

    @Test
    public void createUniqueUserTest() {
        UserData data = new UserData(Constants.EMAIL,Constants.PASSWORD,Constants.NAME);
        ValidatableResponse response = client.createUser(data);
        response.statusCode(200);
        rootUser = response.extract().as(RootUser.class);
        assertEquals(data.getEmail(), rootUser.user.getEmail());
    }

    @After
    public void deleteUser() {
        client.deleteUser(rootUser);
    }
}
