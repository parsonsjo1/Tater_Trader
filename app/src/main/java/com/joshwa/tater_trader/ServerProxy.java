package com.joshwa.tater_trader;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    public JSONResponse registerUser(User user)
    {
        try
        {
            url = new URL("http://ec2-52-90-159-103.compute-1.amazonaws.com:3010/signup");
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



        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        JSONResponse jsonResponse = gson.fromJson(br, JSONResponse.class);
        return jsonResponse;
    }

    public JSONResponse authenticate(User user)
    {
        try
        {
            url = new URL("http://ec2-52-90-159-103.compute-1.amazonaws.com:3010/authenticate");
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        HttpURLConnection connection = null;
        JSONObject jsonParam = new JSONObject();
        try
        {
            jsonParam.put("email", user.getEmailAddress());
            jsonParam.put("password", user.getPassword());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        try
        {
            connection = (HttpURLConnection) url.openConnection();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            connection.setRequestMethod("POST");
        } catch (ProtocolException e)
        {
            e.printStackTrace();
        }
        //connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
        //connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.setRequestProperty("Content-Type", "application/json");

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

        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        JSONResponse jsonResponse = gson.fromJson(br, JSONResponse.class);
        return jsonResponse;
    }

    public UPCInfo getUPCInfo(String upc)
    {
        try
        {
            url = new URL("http://www.searchupc.com/handlers/upcsearch.ashx?request_type=3&access_token=A5596BD1-3624-42E2-A8CD-E5053BE97F07&upc=" + upc);
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        HttpURLConnection connection = null;

        try
        {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
        }
        catch (ProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        connection.setRequestProperty("Content-length", "0");
        connection.setUseCaches(false);
        connection.setAllowUserInteraction(false);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);

        int responseCode = 0; // getting the response code
        try
        {
            connection.connect();
            responseCode = connection.getResponseCode();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        BufferedReader br = null;
        String jsonString = null;
        try
        {
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            jsonString = org.apache.commons.io.IOUtils.toString(br);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        JSONObject json = null;
        Gson gson = new Gson();
        UPCInfo upcInfo = null;
        try
        {
             json = new JSONObject(jsonString);
            upcInfo = gson.fromJson(json.getString("0"), UPCInfo.class);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        storeUPCInfo(upcInfo);

        return upcInfo;
    }

    private void storeUPCInfo(UPCInfo upcInfo)
    {
        try
        {
            url = new URL("http://ec2-52-90-159-103.compute-1.amazonaws.com:3010/upc");
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        HttpURLConnection connection = null;
        JSONObject jsonParam = new JSONObject();
        try
        {
            jsonParam.put("productname", upcInfo.getProductName());
            jsonParam.put("imageurl", upcInfo.getImageURL());
            jsonParam.put("producturl", upcInfo.getProductURL());
            jsonParam.put("price", upcInfo.getPrice());
            jsonParam.put("currency", upcInfo.getCurrency());
            jsonParam.put("saleprice", upcInfo.getSaleprice());
            jsonParam.put("storename", upcInfo.getStorename());

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        try
        {
            connection = (HttpURLConnection) url.openConnection();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            connection.setRequestMethod("POST");
        } catch (ProtocolException e)
        {
            e.printStackTrace();
        }
        //connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
        //connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.setRequestProperty("Content-Type", "application/json");

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
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        JSONResponse jsonResponse = gson.fromJson(br, JSONResponse.class);
        System.out.println("JSON RESPONSE: " +  jsonResponse.getMessage());
    }

    public List<UPCInfo> getUPCInfo()
    {
        try
        {
            url = new URL("http://ec2-52-90-159-103.compute-1.amazonaws.com:3010/upc");
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        HttpURLConnection connection = null;

        try
        {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
        }
        catch (ProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        connection.setRequestProperty("Content-length", "0");
        connection.setUseCaches(false);
        connection.setAllowUserInteraction(false);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);

        int responseCode = 0; // getting the response code
        try
        {
            connection.connect();
            responseCode = connection.getResponseCode();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        JSONObject json = null;
        Gson gson = new Gson();
        List<UPCInfo> upcInfoList = gson.fromJson(br, new TypeToken<List<UPCInfo>>(){}.getType());

        return upcInfoList;
    }
}
