package com.cnf.utils;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.cnf.service.exception.HttpBadRequestException;
import com.cnf.service.exception.HttpNoFoundException;
import com.cnf.service.exception.HttpServerErrorException;
import com.cnf.service.exception.HttpUnAuthorizedException;
import com.cnf.service.exception.HttpUnknownErrorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RequestUtils {

    public static String sendGetRequest(String token, String path, Map<String, String> params)
            throws
            IOException,
            HttpUnAuthorizedException,
            HttpServerErrorException,
            HttpUnknownErrorException,
            HttpBadRequestException,
            HttpNoFoundException {
        HttpURLConnection conn;
        URL url;
        String response;
        InputStream inputStream = null;
        try {
            if (params != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(path);
                sb.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    sb.append((entry.getKey()) + "=" + entry.getValue());
                    sb.append("&");
                }
                sb.deleteCharAt(sb.length() - 1);
                path = sb.toString();
            }
            url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(10000);
            if (token != null) {
                conn.setRequestProperty("Authorization", token);
            }
            conn.connect();
            int status = conn.getResponseCode();
            // paras error
            if (status == HttpsURLConnection.HTTP_BAD_REQUEST) {
                throw new HttpBadRequestException(String.format("%s: Fail to send request to path of %s", "HTTP_BAD_REQUEST", path));
            }
            // invalid user
            if (status == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                throw new HttpUnAuthorizedException(String.format("%s: Fail to send request to path of %s", "HTTP_UNAUTHORIZED", path));
            }
            // server error
            if (status == HttpsURLConnection.HTTP_INTERNAL_ERROR) {
                throw new HttpServerErrorException(String.format("%s: Fail to send request to path of %s", "HTTP_INTERNAL_ERROR", path));
            }
            // device error
            if (status == HttpsURLConnection.HTTP_NOT_FOUND) {
                throw new HttpNoFoundException(String.format("%s: Fail to send request to path of %s", "HTTP_NOT_FOUND", path));
            }
            // unknown error
            if (status != HttpURLConnection.HTTP_OK) {
                throw new HttpUnknownErrorException("Error sending request to " + path + " : HTTP_UNKNOWN_ERROR");
            }
            inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            response = sb.toString();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, String.format("Date: %s, " + e, LocalDateTime.now()));
                }
            }
        }
        return response;
    }

    public static String sendPostRequest(String token, String requestBody, String path, Map<String, String> params)
            throws
            IOException,
            HttpUnAuthorizedException,
            HttpBadRequestException,
            HttpServerErrorException,
            HttpUnknownErrorException,
            HttpNoFoundException {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        String response;
        try {
            if (params != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(path);
                sb.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    sb.append((entry.getKey()) + "=" + entry.getValue());
                    sb.append("&");
                }
                sb.deleteCharAt(sb.length() - 1);
                path = sb.toString();
            }
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(10000);
            if (token != null) {
                conn.setRequestProperty("authorization", token);
            }
            conn.setDoInput(true);
            conn.setDoOutput(true);
            outputStream = conn.getOutputStream();
            if (requestBody != null) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }
            int status = conn.getResponseCode();
            // paras error
            if (status == HttpsURLConnection.HTTP_BAD_REQUEST) {
                throw new HttpBadRequestException(String.format("%s: Fail to send request to path of %s", "HTTP_BAD_REQUEST", path));
            }
            // invalid user
            if (status == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                throw new HttpUnAuthorizedException(String.format("%s: Fail to send request to path of %s", "HTTP_UNAUTHORIZED", path));
            }
            // server error
            if (status == HttpsURLConnection.HTTP_INTERNAL_ERROR) {
                throw new HttpServerErrorException(String.format("%s: Fail to send request to path of %s", "HTTP_INTERNAL_ERROR", path));
            }
            // device error
            if (status == HttpsURLConnection.HTTP_NOT_FOUND) {
                throw new HttpNoFoundException(String.format("%s: Fail to send request to path of %s", "HTTP_NOT_FOUND", path));
            }
            // unknown error
            if (status != HttpURLConnection.HTTP_OK) {
                throw new HttpUnknownErrorException("Error sending request to " + path + " : HTTP_UNKNOWN_ERROR");
            }
            inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            response = sb.toString();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, String.format("Date: %s, " + e, LocalDateTime.now()));
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, String.format("Date: %s, " + e, LocalDateTime.now()));
                }
            }
        }
        return response;
    }
}
