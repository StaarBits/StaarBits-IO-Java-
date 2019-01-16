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

import com.staarbits.io.json.JSONObject;
import com.staarbits.io.json.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class DeleteMethodExecutor extends HttpMethodExecutor<JSONObject>
{

    /** Constructs a new {@code DeleteMethodExecutor} */
    public DeleteMethodExecutor(int timeout, URL url)
    {
        super("DELETE", timeout, url);
    }

    /** {@inheritDoc} */
    @Override
    public JSONObject execute() throws HttpRequestFailException
    {
        URLConnection connection = null;

        try
        {
            connection = (HttpURLConnection) url.openConnection();
            ((HttpURLConnection) connection).setRequestMethod(toString());
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(useCaches());
            connection.setReadTimeout(timeout());
            connection.setConnectTimeout(timeout());

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
            return (JSONObject) JSONValue.parse(builder.toString());
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
        } finally
        {
            if (connection != null)
                ((HttpURLConnection) connection).disconnect();
        }
        return null;
    }
}