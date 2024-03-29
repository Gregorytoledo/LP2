package com.example.alunos.fakeuser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by alunos on 04/10/17.
 */

public class NetworkUtils{
    public static String getJSONFromAPI(String url){
        String retorno = "";
        try{
            URL apiEnd = new URL(url);
            int codigoReposta;
            HttpURLConnection conexao;
            InputStream is;

            conexao = (HttpURLConnection) apiEnd.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setReadTimeout(15000);
            conexao.setConnectTimeout(15000);
            conexao.connect();

            codigoReposta = conexao.getResponseCode();
            if (codigoReposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                is = conexao.getInputStream();
            }else {
                is = conexao.getErrorStream();
            }

            retorno = converterInputStreamToString(is);
            is.close();
            conexao.disconnect();
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return retorno;
    }

    private static String converterInputStreamToString(InputStream is){
        StringBuffer buffer = new StringBuffer();
        try{
            BufferedReader br;
            String linha;
            br = new BufferedReader(new InputStreamReader(is));
            while ((linha = br.readLine()) != null){
                buffer.append(linha);
            }

            br.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return buffer.toString();
    }
}