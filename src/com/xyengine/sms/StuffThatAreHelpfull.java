package com.xyengine.sms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Created by XelnectMobileUser on 3/8/2015.
 */
public class StuffThatAreHelpfull {
    static byte[] bytes1;
    public static String decompressString(String str) throws IOException {
        bytes1 = str.getBytes();
        Inflater inflater = new Inflater();
        inflater.setInput(bytes1);
        //inflater.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes1.length);
        byte[] buffer = new byte[1024];
        while(!inflater.finished())
        {
            int bytesCompressed = 0;
            try {
                bytesCompressed = inflater.inflate(buffer);
            } catch (DataFormatException e) {
                e.printStackTrace();
            }
            bos.write(buffer,0,bytesCompressed);
        }
        try
        {
            //close the output stream
            bos.close();
        }
        catch(IOException ioe)
        {
            System.out.println("Error while closing the stream : " + ioe);
        }
        byte[] compressedArray = bos.toByteArray();

           /* System.out.println("Byte array has been compressed!");
            System.out.println("Size of original array is:" + bytes.length);
            System.out.println("Size of compressed array is:" + compressedArray.length);*/

        return new String(compressedArray, "UTF-16");
    }
    public static String compressString(String str) throws IOException {
        byte[] bytes = str.getBytes();
        Deflater deflater = new Deflater();
        deflater.setInput(bytes);
        deflater.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length);
        byte[] buffer = new byte[1024];
        while(!deflater.finished())
        {
            int bytesCompressed = deflater.deflate(buffer);
            bos.write(buffer,0,bytesCompressed);
        }
        try
        {
            //close the output stream
            bos.close();
        }
        catch(IOException ioe)
        {
            System.out.println("Error while closing the stream : " + ioe);
        }
        byte[] compressedArray = bos.toByteArray();

           /* System.out.println("Byte array has been compressed!");
            System.out.println("Size of original array is:" + bytes.length);
            System.out.println("Size of compressed array is:" + compressedArray.length);*/

        return new String(compressedArray, "UTF-16");
    }
    public static void decompress(String compressedString) {
        int index = 0;
        int numReps = 0;
        char nextChar = ' ';

        while (index < compressedString.length())
        {
            numReps = 0;
            char c = compressedString.charAt(index);
            if (!Character.isDigit(c))
            {
                nextChar = c;
                index++;
            }
            else
            {
                while (Character.isDigit(c))
                {
                    int temp = Integer.parseInt(""+c);
                    numReps = (numReps*10) + temp;
                    index++;
                    if (index >= compressedString.length()) break;
                    c = compressedString.charAt(index);
                }
                for (int i =0; i<numReps; i++)
                {
                    System.out.println(nextChar);
                }
            }
        }
    }
    public static String decode(String stringToBeDecoded) throws UnsupportedEncodingException {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedByteArray = decoder.decode(stringToBeDecoded);
        return new String(decodedByteArray, "UTF-16");
    }
    public static String encode(String websiteHtml){
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(websiteHtml.getBytes(StandardCharsets.UTF_16));
    }
}
