package function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.MStocks;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListStocksFunctionService extends ListStocksFunction{

    private static String API_URL = "https://api.twelvedata.com";

    private static String API_KEY = "a79f1565eamshc1b195fb4b1c7cfp1d869cjsnbb8f34b31317";

    public ListStocksFunctionService() {
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context)
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        try {
            final String stockList = getStocksList();
            String output = String.format("{ \"message\": \"stock list generated successfully\", \"list\": \"%s\" }", stockList);

            return response
                    .withStatusCode(200)
                    .withBody(output);
        } catch (UnirestException e) {
            return response
                    .withBody("{\"message\":\"fail to get stock list\"}")
                    .withStatusCode(500);
        }
    }

    private String getStocksList() throws UnirestException {
        HttpResponse response = Unirest.get(API_URL+"/stocks").asJson();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JSONObject my_obj = new JSONObject(response.getBody());
        JSONArray object = (JSONArray) my_obj.get("array");
        JSONArray data = (JSONArray) ((JSONObject) object.get(0)).get("data");

        List<MStocks> stocksList = new ArrayList<MStocks>();
        for (int i=0;i<=data.length()-1;i++){
            stocksList.add(gson.fromJson(data.get(i).toString(), MStocks.class));
        }

        List<MStocks> newStockList = MStocks.filterByCurrency(stocksList, "BRL");
        String mStockJson = gson.toJson(newStockList);

        return mStockJson;
    }
}
