import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.RootUser;

import static org.junit.Assert.assertEquals;

public class UpdateUserPositiveTests {
    StellarBurgersRestClient client = new StellarBurgersRestClient();
    RootUser rootUser;
    UserData data = new UserData(Constants.EMAIL, Constants.PASSWORD, Constants.NAME);

    @Before
    public void setUp() {
        UserData loginData = new UserData(Constants.EMAIL, Constants.PASSWORD);
        rootUser = client.createUser(data).extract().as(RootUser.class);
        rootUser = client.loginUser(loginData).extract().as(RootUser.class);
    }

    @After
    public void cleanUp() {
        client.deleteUser(rootUser);
    }

    @Test
    public void updateEmailTest() {
        RootUser testRootUser;
        String expectedResult = Constants.NEW_EMAIL;
        ValidatableResponse response = client.updateUser("{\"email\":"+"\""+expectedResult+"\"}", rootUser).statusCode(200);
        testRootUser = response.extract().as(RootUser.class);
        assertEquals(expectedResult, testRootUser.user.getEmail());
    }
    @Test
    public void updatePasswordTest() {
        RootUser testRootUser;
        String expectedResult = Constants.NEW_PASSWORD;
        ValidatableResponse response = client.updateUser("{\"password\":"+"\""+expectedResult+"\"}", rootUser).statusCode(200);
        testRootUser = response.extract().as(RootUser.class);
        UserData loginData = new UserData(Constants.EMAIL,Constants.NEW_PASSWORD);
                client.loginUser(loginData).statusCode(200);
    }
    @Test
    public void updateNameTest() {
        RootUser testRootUser;
        String expectedResult = Constants.NEW_NAME;
        ValidatableResponse response = client.updateUser("{\"name\":"+"\""+expectedResult+"\"}", rootUser).statusCode(200);
        testRootUser = response.extract().as(RootUser.class);
        assertEquals(expectedResult, testRootUser.user.getName());
    }
}
