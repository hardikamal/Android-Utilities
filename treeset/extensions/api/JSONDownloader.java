package treeset.extensions.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


/**
 * Easy url->JSON downloader.
 *
 * Created by daemontus on 11/04/14.
 */
public class JSONDownloader {

    public static JSONObject get(URL url) throws IOException, JSONException {

        // Read all the text returned by the server
        StringBuilder contents = new StringBuilder();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            while ((str = in.readLine()) != null) {
                contents.append(str);
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    //no problem
                }
            }
        }

        return new JSONObject(contents.toString());
    }

}
