package com.example.pubseeker;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadUrl {

    public  String readUrl(String myurl) throws IOException{

        String data = "";
        InputStream ip = null;
        HttpURLConnection huc = null;

        try{
            URL url = new URL(myurl);
            huc = (HttpURLConnection) url.openConnection();
            huc.connect();

            ip = huc.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(ip));
            StringBuffer sb = new StringBuffer();

            String line ="";
            while((line = br.readLine()) != null){
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        }catch(MalformedURLException e){
            Log.i("DownloadUrl","readUrl: "+e.getMessage());
        }
        catch(IOException e){
            e.printStackTrace();

        }
        finally{
            ip.close();
            huc.disconnect();
        }
        return data;
    }
}
