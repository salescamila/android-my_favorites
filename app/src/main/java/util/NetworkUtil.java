package util;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtil {

    private static final String TAG = "NetworkUtil";

    private static final String LINK_CLINICAS = "https://pouppe.websiteseguro.com/sistema/v2/api/credenciadas";
    private static final String LINK_PHOTO = "https://pouppe.websiteseguro.com/evosaude/sistema/fotos/";

    public static URL buildUrlPhoto(String id, String photo) {
        Uri buildUri = Uri.parse(LINK_PHOTO + id + '/' + photo).buildUpon().build();
        URL url =  null;
        try{
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildUrlClinicas() {
        Uri buildUri = Uri.parse(LINK_CLINICAS).buildUpon().build();
        URL url =  null;
        try{
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        Log.d(TAG, "metodo getResponseFromHttpUrl");

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
