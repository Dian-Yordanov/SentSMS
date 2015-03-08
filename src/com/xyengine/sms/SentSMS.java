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
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

/**
 * Created by XelnectMobileUser on 3/8/2015.
 */
public class SentSMS implements Runnable{
    public static final String ACCOUNT_SID = "AC0e3f839440fb5b82381ce06cd2dccb91";
    public static final String AUTH_TOKEN = "3bd35460384cf304b1855f08b329d244";
    public static void main(String[] args) {

        //System.out.println(takeHtml("http://reddit.com/"));
        //compressHtml(takeHtml("http://reddit.com/"));
        String html = takeHtml("http://reddit.com/");
        html = html.substring(1,html.length()-1);
        System.out.println(html);
        String compressedHtml = compressHtml(html);
        System.out.println(compressedHtml);
        try {
            System.out.println(decompress(compressedHtml));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //takeHtml("http://reddit.com/");
        try {
            sentSmS();
        } catch (TwilioRestException e) {
            e.printStackTrace();
        }
    }
    public static String decompress(String stringToBeDecompiled) throws UnsupportedEncodingException {
        //String encodedString = stringToBeDecompiled;
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedByteArray = decoder.decode(stringToBeDecompiled);
//Verify the decoded string
        return new String(decodedByteArray, "UTF-16");
    }
    public static String compressHtml(String websiteHtml){
        //String compressedHtml ="";
        Base64.Encoder encoder = Base64.getEncoder();
        //String normalString = "username:password";
        return encoder.encodeToString(websiteHtml.getBytes(StandardCharsets.UTF_16));
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
                //System.out.println(line);
               // compressHtml(line);
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
                // nothing to see here
            }
        }
       /* try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //new Thread(new SentSMS()).start();
        //System.out.print(compressHtml(returnedHtml));
        return  returnedHtml.toString();
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

    public void run() {
        System.out.println("Hello from a thread!");
    }
}