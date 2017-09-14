package com.theah64.cartwatcher.server.servlets;

import com.theah64.cartwatcher.server.core.BaseECommerceService;
import com.theah64.cartwatcher.server.core.CartWatcherException;
import com.theah64.cartwatcher.server.core.Product;
import com.theah64.cartwatcher.server.webengine.RequestException;
import com.theah64.cartwatcher.server.webengine.servlets.AdvancedBaseServlet;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

/**
 * Created by theapache64 on 13/9/17.
 */
@WebServlet(urlPatterns = {AdvancedBaseServlet.VERSION_CODE + "/get_product"})
public class GetProductServlet extends AdvancedBaseServlet {

    private static final String KEY_PRODUCT_URL = "product_url";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected String[] getRequiredParameters() {
        return new String[]{KEY_PRODUCT_URL};
    }

    @Override
    protected void doAdvancedPost() throws JSONException, SQLException, RequestException, IOException, ServletException, RequestException {

        final String productUrl = getStringParameter(KEY_PRODUCT_URL);

        try {
            //Validating URL
            new URL(productUrl);

            final Response response = new OkHttpClient().newCall(new Request.Builder().url(productUrl).build()).execute();
            final String productResponse = response.body().string();

            try {
                final BaseECommerceService eCommerceService = BaseECommerceService.getECommerceService(productUrl, productResponse);
                final Product product = eCommerceService.getProduct();

                getWriter().write(new com.theah64.cartwatcher.server.webengine.Response("Product found", product.toJSONObject()).getResponse());

            } catch (CartWatcherException e) {
                e.printStackTrace();
                throw new RequestException(e.getMessage());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RequestException("Invalid product_url - " + e.getMessage());
        }


    }
}
