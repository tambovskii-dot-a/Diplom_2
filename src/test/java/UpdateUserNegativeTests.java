import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.RootUser;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class UpdateUserNegativeTests {
    StellarBurgersRestClient client = new StellarBurgersRestClient();
    RootUser rootUser;
    UserData data = new UserData(Constants.EMAIL, Constants.PASSWORD, Constants.NAME);

    @Before
    public void setUp() {
        rootUser = client.createUser(data).extract().as(RootUser.class);
    }

    @After
    public void cleanUp() {
        client.deleteUser(rootUser);
    }

    @Test
    public void updateEmailTest() {
        String expectedResult = Constants.NEW_EMAIL;
        client.updateUserWithoutToken
                ("{\"email\":" + "\"" + expectedResult + "\"}", rootUser)
                .assertThat()
                .body("message", equalTo("You should be authorised"))
                .statusCode(401);
    }

    @Test
    public void updatePasswordTest() {
        String expectedResult = Constants.NEW_PASSWORD;
        client.updateUserWithoutToken
                ("{\"password\":" + "\"" + expectedResult + "\"}", rootUser)
                .assertThat()
                .body("message", equalTo("You should be authorised"))
                .statusCode(401);

    }

    @Test
    public void updateNameTest() {
        String expectedResult = Constants.NEW_NAME;
        client.updateUserWithoutToken
                ("{\"name\":" + "\"" + expectedResult + "\"}", rootUser)
                .assertThat()
                .body("message", equalTo("You should be authorised"))
                .statusCode(401);

    }
}
