package com.xyengine.sms;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.*;
import java.util.zip.Deflater;

/**
 * Created by XelnectMobileUser on 3/8/2015.
 */
public class SentSMS{
    public static final String ACCOUNT_SID = "AC0e3f839440fb5b82381ce06cd2dccb91";
    public static final String AUTH_TOKEN = "3bd35460384cf304b1855f08b329d244";
    public static void main(String[] args) {

        String html = takeHtml("http://reddit.com/");
        html = html.substring(1,html.length()-1);
        System.out.println(html);

        String compressOutput = LZString.compress(html);
        System.out.println("Compressed: " + compressOutput);
        String decompressed = LZString.decompress(compressOutput);
        System.out.println("Decompressed: " + decompressed);

        try {
            sentSmS(html.substring(0,240));
        } catch (TwilioRestException e) {
            e.printStackTrace();
        }
    }

    public static String takeHtml(String websiteURL){
        ArrayList<String> returnedHtml = new ArrayList<String>();
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;

        try {
            url = new URL(websiteURL);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                returnedHtml.add(line);
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException ioe) {
            }
        }
        return  returnedHtml.toString();
    }
    public static void sentSmS(String body) throws TwilioRestException {
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        // Build the parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("From","+441297533022"));
        params.add(new BasicNameValuePair("To", "+4407479265143"));
        params.add(new BasicNameValuePair("Body", body));

        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        Message message = messageFactory.create(params);
        System.out.println(message.getSid());
    }
    
}