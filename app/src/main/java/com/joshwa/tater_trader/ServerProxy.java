package com.joshwa.tater_trader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by Joshua on 4/9/2016.
 * This will handle the GET and POST to the server
 */
public class ServerProxy
{
    private URL url = null;

    private static ServerProxy instance = null;
    private ServerProxy()
    {
        // Exists only to defeat instantiation.
    }
    public static ServerProxy getInstance()
    {
        if(instance == null) {
            instance = new ServerProxy();
        }
        return instance;
    }

    /**
     *
     * @param user
     */
    public void registerUser(User user)
    {
        try
        {
            url = new URL("http://ec2-52-90-159-103.compute-1.amazonaws.com:3010/user");
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        HttpURLConnection connection = null;
        JSONObject jsonParam = new JSONObject();
        try
        {
            jsonParam.put("firstname", user.getFirstName());
            jsonParam.put("lastname", user.getLastName());
            jsonParam.put("email", user.getEmailAddress());
            jsonParam.put("password", user.getPassword());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        try
        {
            connection = (HttpURLConnection)url.openConnection();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            connection.setRequestMethod("POST");
        }
        catch (ProtocolException e)
        {
            e.printStackTrace();
        }
        //connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
        //connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.setRequestProperty("Content-Type","application/json");

        int responseCode = 0; // getting the response code
        try
        {
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(jsonParam.toString());
            out.close();
            responseCode = connection.getResponseCode();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        final StringBuilder output = new StringBuilder("Request URL " + url);
        output.append(System.getProperty("line.separator") + "Request Parameters " + jsonParam.toString());
        output.append(System.getProperty("line.separator")  + "Response Code " + responseCode);
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line = "";
        StringBuilder responseOutput = new StringBuilder();
        System.out.println("output===============" + br);
        try
        {
            while((line = br.readLine()) != null )
            {
                responseOutput.append(line);
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());
    }

    public User getUser(User user)
    {
        return null;
    }

}
