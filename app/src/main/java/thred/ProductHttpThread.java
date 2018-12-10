package thred;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ProductHttpThread extends Thread{

    private  String result;
    @Override
    public void run() {
        try {
            URL url = new URL("http://119.29.60.170:8080/shopping/product");

            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();

            InputStream is = httpUrlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is,"utf-8");
            String temp;
            StringBuffer sbResult = new StringBuffer();
            BufferedReader br = new BufferedReader(isr);
            while((temp=br.readLine())!=null){
                sbResult.append(temp);
            }
            setResult(sbResult.toString());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
