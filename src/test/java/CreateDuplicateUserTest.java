import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.RootUser;

import static org.hamcrest.Matchers.equalTo;

public class CreateDuplicateUserTest {
    StellarBurgersRestClient client = new StellarBurgersRestClient();
    RootUser rootUser;
    UserData data = new UserData(Constants.EMAIL, Constants.PASSWORD, Constants.NAME);

    @Before
    public void setUp() {
        rootUser = client.createUser(data).extract().as(RootUser.class);
    }

    @Test
    public void createDuplicateUserTest() {
        client.createUser(data)
                .assertThat()
                .body("message",equalTo("User already exists"))
                .statusCode(403);
    }

    @After
    public void cleanUp() {
        client.deleteUser(rootUser);
    }
}
