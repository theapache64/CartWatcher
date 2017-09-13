package com.theah64.cartwatcher.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.theah64.bugmailer.core.BugMailer;
import com.theah64.bugmailer.core.BugMailerNode;
import com.theah64.bugmailer.core.NodeBuilder;
import com.theah64.bugmailer.models.Node;
import com.theah64.cartwatcher.R;
import com.theah64.retrokit.activities.BaseAppCompatActivity;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String lastClipboardData = getValidRecentProductURLFromClipboard();

                //Getting url
                new MaterialDialog.Builder(MainActivity.this)
                        .title(R.string.Add_product)
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .autoDismiss(false)
                        .input(getString(R.string.Product_URL), lastClipboardData, false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                            }
                        })
                        .build().show();

            }
        });

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(new Request.Builder().url("http://dl.flipkart.com/dl/haier-310-l-frost-free-double-door-refrigerator/p/itmevzx2jdjgjyyd?pid=RFREVZX2ZHARQPSH&cmpid=product.share.pp").build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        final String responseString = response.body().string();
                        Log.d("OK", responseString);

                    }
                });


    }

    public static final String FLIPKART_URL_REGEX = ".*(?:http|https):\\/\\/flipkart\\.com\\/(?:.+)\\/(?:.+).*";
    private static final String X = MainActivity.class.getSimpleName();

    public String getValidRecentProductURLFromClipboard() {

        String clipboardData = null;

        final ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipData.Item clipData = clipboardManager.getPrimaryClip().getItemAt(0);
        if (clipData != null) {
            clipboardData = clipData.getText().toString();
        }

        if (clipboardData != null) {
            System.out.println("Clipboard: " + clipboardData);
        }

        return null;
    }
}
