
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@RunWith(Parameterized.class)
public class WrongDataCreateUserParamTest {
    private final String email;
    private final String password;
    private final String name;

    public WrongDataCreateUserParamTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {Constants.EMAIL, Constants.PASSWORD, ""},
                {Constants.EMAIL, "", Constants.NAME},
                {"", Constants.PASSWORD, Constants.NAME}
        };
    }

    @Test
    public void wrongDataCreateUserParamTest() {
        UserData data = new UserData(email, password, name);
        StellarBurgersRestClient client = new StellarBurgersRestClient();
        client.createUser(data)
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .statusCode(403);
    }
}
