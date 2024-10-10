import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import pojo.Datum;
import pojo.Ingredients;
import pojo.RootUser;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class StellarBurgersRestClient {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";

    @Step("Register user POST /api/auth/register")
    public ValidatableResponse createUser(UserData data) {
        return given()
                .baseUri(BASE_URL)
                .log().all()
                .header("Content-Type", "application/json")
                .body(data)
                .post("/api/auth/register")
                .then()
                .log().all();
    }

    @Step("delete user DELETE /api/auth/user")
    public void deleteUser(RootUser rootUser) {
        given()
                .baseUri(BASE_URL)
                .log().all()
                .header("authorization", rootUser.getAccessToken())
                .log().all()
                .delete("/api/auth/user");
    }

    @Step("Login user POST /api/auth/login")
    public ValidatableResponse loginUser(UserData data) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body(data)
                .post("/api/auth/login")
                .then()
                .log().all();
    }

    @Step("Update user data PATCH /api/auth/user")
    public ValidatableResponse updateUser(String data, RootUser rootUser) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .header("authorization", rootUser.getAccessToken())
                .body(data)
                .patch("/api/auth/user")
                .then()
                .log().all();
    }

    @Step("Update user data without access token PATCH /api/auth/user")
    public ValidatableResponse updateUserWithoutToken(String data, RootUser rootUser) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body(data)
                .patch("/api/auth/user")
                .then()
                .log().all();
    }

    @Step("Create order POST /api/orders")
    public ValidatableResponse createOrder(ArrayList<String> ids, RootUser rootUser) {
        OrderData orderData = new OrderData(ids);
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .header("authorization", rootUser.getAccessToken())
                .body(orderData)
                .post("/api/orders")
                .then();
    }

    @Step("Create order without access token POST /api/orders")
    public ValidatableResponse createOrderWithOutToken(ArrayList<String> ids, RootUser rootUser) {
        OrderData orderData = new OrderData(ids);
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body(orderData)
                .post("/api/orders")
                .then();
    }

    @Step("Get ingredients for order GET /api/ingredients")
    public ArrayList<String> getIngredients() {
        Response response =
                given()
                        .log().all()
                        .baseUri(BASE_URL)
                        .header("Content-Type", "application/json")
                        .get("/api/ingredients");
        String jsonResponse = response.getBody().asString();
        Gson gson = new Gson();
        Ingredients ingredients = gson.fromJson(jsonResponse, Ingredients.class);
        ArrayList<String> ids = new ArrayList<>();
        for (Datum datum : ingredients.data) {
            ids.add(datum._id);
        }
        return ids;
    }
    @Step("Get order without access token GET /api/orders")
    public ValidatableResponse getOrdersWithOutToken() {
        return
                given()
                        .baseUri(BASE_URL)
                        .header("Content-Type", "application/json")
                        .get("/api/orders")
                        .then();
    }
    @Step("Get order GET /api/orders")
    public ValidatableResponse getOrders(RootUser rootUser) {
        return
                given()
                        .baseUri(BASE_URL)
                        .header("Content-Type", "application/json")
                        .header("authorization", rootUser.getAccessToken())
                        .get("/api/orders")
                        .then();
    }
}
