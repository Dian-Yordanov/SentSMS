package com.xyengine.sms;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XelnectMobileUser on 3/8/2015.
 */
public class SentSMS {
    public static final String ACCOUNT_SID = "AC0e3f839440fb5b82381ce06cd2dccb91";
    public static final String AUTH_TOKEN = "3bd35460384cf304b1855f08b329d244";
    public static void main(String[] args) {
        System.out.println(takeHtml("http://reddit.com/"));
       //takeHtml("http://reddit.com/");
        try {
            sentSmS();
        } catch (TwilioRestException e) {
            e.printStackTrace();
        }
    }
    public static String takeHtml(String websiteURL){
        String returnedHtml ="";
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;

        try {
            url = new URL(websiteURL);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }
        return  returnedHtml;
    }
    public static void sentSmS() throws TwilioRestException {
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        // Build the parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("From","+441297533022"));
        params.add(new BasicNameValuePair("To", "+4407479265143"));
        params.add(new BasicNameValuePair("Body", "test"));

        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        Message message = messageFactory.create(params);
        System.out.println(message.getSid());
    }
}

