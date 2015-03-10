package com.xyengine.sms;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by XelnectMobileUser on 3/8/2015.
 */
public class SentSMS{
    public static final String ACCOUNT_SID = "AC0e3f839440fb5b82381ce06cd2dccb91";
    public static final String AUTH_TOKEN = "3bd35460384cf304b1855f08b329d244";
    public static String smsLenghtTest = "" +
            "_013456789112345678921234567893123456789412345678951234567896123456789712345678981234567899123456789" +
            "_023456789112345678921234567893123456789412345678951234567896123456789712345678981234567899123456789" +
            "_033456789112345678921234567893123456789412345678951234567896123456789712345678981234567899123456789" +
            "_043456789112345678921234567893123456789412345678951234567896123456789712345678981234567899123456789" +
            "_053456789112345678921234567893123456789412345678951234567896123456789712345678981234567899123456789" +
            "_063456789112345678921234567893123456789412345678951234567896123456789712345678981234567899123456789" +
            "_073456789112345678921234567893123456789412345678951234567896123456789712345678981234567899123456789" +
            "_083456789112345678921234567893123456789412345678951234567896123456789712345678981234567899123456789" +
            "_093456789112345678921234567893123456789412345678951234567896123456789712345678981234567899123456789" +
            "_103456789112345678921234567893123456789412345678951234567896123456789712345678981234567899123456789" +
            "_113456789112345678921234567893123456789412345678951234567896123456789712345678981234567899123456789" +
            "_123456789112345678921234567893123456789412345678951234567896123456789712345678981234567899123456789" +
            "_133456789112345678921234567893123456789412345678951234567896123456789712345678981234567899123456789" +
            "_143456789112345678921234567893123456789412345678951234567896123456789712345678981234567899123456789" +
            "_153456789112345678921234567893123456789412345678951234567896123456789712345678981234567899123456789" +
            "_163456789112345678921234567893123456789412345678951234567896e123456789712345678981234567899123456-3";
    public static void main(String[] args) throws IOException {

        String html = takeHtml("http://reddit.com/");
        html = html.substring(1,html.length()-1);
        //System.out.println(html);

        JSONObject json = JsonReader.readJsonFromUrl("https://www.reddit.com/r/all/.json");
        System.out.println(json.toString());
        //System.out.println(json.get("id"));

        String compressOutput = LZString.compress(html);
        System.out.println("Compressed Size: " + compressOutput.length() + " compressed: " + compressOutput);
        String decompressedOutput = LZString.decompress(compressOutput);
        System.out.println("Decompressed Size: " +decompressedOutput.length() + " decompressed: " + decompressedOutput);

        String outputUTF16 = LZString.compressToUTF16(html);
        System.out.println("Compressed UTF size: " + outputUTF16.length() + " compressed: " + outputUTF16);
        String decompressedUTF16 = LZString.decompressFromUTF16(outputUTF16);
        System.out.println("Decompressed UTF size: " + decompressedUTF16.length() + " decompressed: " + outputUTF16);
        String compressedLenghtTest = smsLenghtTest.substring(0,120);/*LZString.compress(smsLenghtTest);*/

        try {
            sentSmS(compressedLenghtTest.length() + " " +compressedLenghtTest);
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
        System.out.println("Message sent on: " + getCurrentTime() + " " + message.getSid());
    }
    public static String getCurrentTime(){
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        //.out.println( sdf.format(cal.getTime()) );
        return sdf.format(cal.getTime());
    }
}