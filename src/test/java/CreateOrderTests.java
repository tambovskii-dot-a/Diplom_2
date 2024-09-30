import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.RootUser;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;

public class CreateOrderTests {
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
    public void createOrderWithIngredients() {
        ArrayList<String> ids = client.getIngredients();
        client.createOrder(ids, rootUser).statusCode(200).assertThat().body("success", is(true));
    }

    @Test
    public void createOrderWithOutToken() {
        ArrayList<String> ids = client.getIngredients();
        client.createOrderWithOutToken(ids, rootUser).statusCode(200).assertThat().body("success", is(true));
    }
    @Test
    public void createOrderWithOutIngredients(){
        ArrayList<String> ids = new ArrayList<>();
        client.createOrder(ids, rootUser).statusCode(400).assertThat().body("success", is(false));
    }
    @Test
    public void createOrderWithNotAHashIngredient(){
        ArrayList<String> ids = client.getIngredients();
        ids.add("is not a hash");
        client.createOrder(ids, rootUser).statusCode(500);
    }

}
