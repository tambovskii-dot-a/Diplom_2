import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.RootOrder;
import pojo.RootUser;

import java.lang.reflect.Type;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;

public class GetOrdersTests {
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
    public void GetOrders() {
        ValidatableResponse response
         = client.getOrders(rootUser)
                .assertThat()
                .body("success",is(true))
                .statusCode(200);
         RootOrder order = response.extract().as(RootOrder.class);
         assertNotNull(order.getOrders());

    }

    @Test
    public void getAllOrdersWithoutToken() {
        ValidatableResponse response = client.getOrdersWithOutToken().assertThat().body("success",is(false)).statusCode(401);
    }


}
