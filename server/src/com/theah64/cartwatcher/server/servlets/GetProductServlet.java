package com.theah64.cartwatcher.server.servlets;

import com.theah64.cartwatcher.server.webengine.RequestException;
import com.theah64.cartwatcher.server.webengine.servlets.AdvancedBaseServlet;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by theapache64 on 13/9/17.
 */
@WebServlet(urlPatterns = {AdvancedBaseServlet.VERSION_CODE + "/get_product"})
public class GetProductServlet extends AdvancedBaseServlet {

    private static final String KEY_PRODUCT_URL = "product_url";

    @Override
    protected String[] getRequiredParameters() {
        return new String[]{KEY_PRODUCT_URL};
    }

    @Override
    protected void doAdvancedPost() throws JSONException, SQLException, RequestException, IOException, ServletException, RequestException {

    }
}
