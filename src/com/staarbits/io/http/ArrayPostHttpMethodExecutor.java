/*
 * Copyright (c) 2019. StaarBits Network & Development says that this file is under the StaarBits Global Copyright (SGC).
 * Every file which contains this annotation as one of the first things written is under the SGC protocol.
 * The SGC (StaarBits Global Copyright) demonstrates that the file which has it cannot be copied and pasted as
 * an annotation file by anyone else who has not gotten the Owner rank at StaarBits. So... The most powerful rank
 * at the executive can spread this file. If someone uses this file without the permission given by the executive
 * administration, this same person will be able to be sued by the SEA (StaarBits Executive Administration); if
 * someone who works at StaarBits spreads this file, this person will as sooner as possible be removed from our
 * team and (s)he will also be able to response a lawsuit as well.
 */

package com.staarbits.io.http;

import com.staarbits.io.json.JSONArray;
import com.staarbits.io.json.JSONObject;
import com.staarbits.io.json.JSONValue;

import java.io.*;
import java.net.*;

public class ArrayPostHttpMethodExecutor extends HttpMethodExecutor<JSONArray>
{

    /** An object representation in JSON */
    private JSONObject jsonObject;

    /** Constructs a new {@code ArrayPostHttpMethodExecutor} */
    public ArrayPostHttpMethodExecutor(int timeout, URL url, JSONObject jsonObject)
    {
        super("POST", timeout, url);
        this.jsonObject = jsonObject;
    }

    /** {@inheritDoc} */
    @Override
    public JSONArray execute() throws HttpRequestFailException
    {
        URLConnection connection = null;

        try
        {
            connection = (HttpURLConnection) url.openConnection();
            ((HttpURLConnection) connection).setRequestMethod(toString());
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(useCaches());
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setReadTimeout(timeout());
            connection.setConnectTimeout(timeout());

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(this.jsonObject.toJSONString());
            out.flush();
            out.close();

            int responseCode = ((HttpURLConnection) connection).getResponseCode();

            if (responseCode < 200 || responseCode >= 300)
            {
                InputStream errorStream = ((HttpURLConnection) connection).getErrorStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(errorStream));
                StringBuilder builder = new StringBuilder();
                String inputLine;

                while ((inputLine = bufferedReader.readLine()) != null)
                {
                    builder.append(inputLine);
                }
                bufferedReader.close();
                throw new HttpRequestFailException(responseCode, builder.toString());
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String inputLine;

            while ((inputLine = bufferedReader.readLine()) != null)
            {
                builder.append(inputLine);
            }
            bufferedReader.close();
            return (JSONArray) JSONValue.parse(builder.toString());
        } catch (MalformedURLException error)
        {
            error.printStackTrace();
        } catch (ProtocolException error)
        {
            error.printStackTrace();
        } catch (IOException error)
        {
            if (error instanceof SocketTimeoutException)
            {
                throw new HttpRequestFailException(RequestFail.TIMEOUT);
            }
            error.printStackTrace();
        } finally
        {
            if (connection != null)
                ((HttpURLConnection) connection).disconnect();
        }
        return null;
    }
}
