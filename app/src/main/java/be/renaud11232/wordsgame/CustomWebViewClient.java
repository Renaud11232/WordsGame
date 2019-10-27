package be.renaud11232.wordsgame;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebViewClient extends WebViewClient {

    private boolean error;

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        error = false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if(error) {
            view.setVisibility(View.INVISIBLE);
            final WebView wv = view;
            final String u = url;
            new AlertDialog.Builder(view.getContext())
                    .setTitle(R.string.error)
                    .setMessage(R.string.error_loading)
                    .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            wv.loadUrl(u);
                        }
                    })
                    .setNegativeButton(R.string.quit, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setCancelable(false)
                    .show();
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        String host = Uri.parse(view.getContext().getString(R.string.url_homepage)).getHost();
        if(host != null && host.equals(Uri.parse(url).getHost())) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity(intent);
        return true;
    }

    @TargetApi(24)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return shouldOverrideUrlLoading(view, request.getUrl().toString());
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        String host = Uri.parse(failingUrl).getHost();
        if(host != null && host.equals(Uri.parse(view.getContext().getString(R.string.url_homepage)).getHost())) {
            error = true;
        }
    }

    @TargetApi(23)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        onReceivedError(view, error.getErrorCode(), error.getDescription().toString(), request.getUrl().toString());
    }
}
