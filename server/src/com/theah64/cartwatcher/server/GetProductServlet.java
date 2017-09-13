package com.theah64.cartwatcher.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

/**
 * Created by theapache64 on 13/9/17.
 */
public class GetProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final PrintWriter pw = resp.getWriter();
        final String productUrl = req.getParameter("product_url");

        try {

            if (productUrl != null) {

                //Simply validating the URL
                new URL(productUrl);


            } else {
                throw new CartWatcherException("product_url missing");
            }


        } catch (CartWatcherException e) {
            e.printStackTrace();

        }
    }

}
