import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.RootUser;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class UserLoginTest {
    StellarBurgersRestClient client = new StellarBurgersRestClient();
    RootUser rootUser;

    @Before
    public void setUp() {
        UserData data = new UserData(Constants.EMAIL, Constants.PASSWORD, Constants.NAME);
        rootUser = client.createUser(data).extract().as(RootUser.class);
    }

    @After
    public void cleanUp() {
        client.deleteUser(rootUser);
    }

    @Test
    public void successLoginTest() {
        UserData data = new UserData(Constants.EMAIL, Constants.PASSWORD);
        RootUser testRootUser;
        ValidatableResponse response = client.loginUser(data).statusCode(200);
        testRootUser = response.extract().as(RootUser.class);
        assertEquals(rootUser.user.getEmail(), testRootUser.user.getEmail());
        assertEquals(rootUser.user.getName(), testRootUser.user.getName());

    }
@Test
    public void loginWithEmptyEmail(){
    UserData data = new UserData("", Constants.PASSWORD);
    client.loginUser(data)
            .assertThat()
            .body("message",equalTo("email or password are incorrect")).statusCode(401);
}
    @Test
    public void loginWithEmptyPassword(){
        UserData data = new UserData(Constants.EMAIL, "");
        client.loginUser(data)
                .assertThat()
                .body("message",equalTo("email or password are incorrect")).statusCode(401);
    }
}
