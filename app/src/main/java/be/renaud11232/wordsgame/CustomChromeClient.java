package be.renaud11232.wordsgame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class CustomChromeClient extends WebChromeClient {

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        final JsResult res = result;
        new AlertDialog.Builder(view.getContext())
                .setMessage(message)
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        res.confirm();
                    }
                })
                .setCancelable(false)
                .show();
        return true;
    }
}
