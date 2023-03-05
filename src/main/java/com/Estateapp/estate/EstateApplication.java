package com.Estateapp.estate;

import com.google.gson.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@SpringBootApplication
public class EstateApplication {

	private static final String OPENAI_API_KEY = "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";
	private static final String API_URL = "https://api.openai.com/v1/engines/davinci-codex/completions";
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static void main(String[] args) throws IOException {
		SpringApplication.run(EstateApplication.class, args);


//		String prompt = "Who is president of Nigeria?";
//
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//
//		HttpPost httpPost = new HttpPost("https://api.openai.com/v1/engines/davinci-codex/completions");
//
//		httpPost.setHeader("Content-Type", "application/json");
//		httpPost.setHeader("Authorization", "Bearer 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");
//
//		StringEntity requestEntity = new StringEntity("{\"prompt\":\"" + prompt + "\",\"max_tokens\":1,\"temperature\":0,\"n\":1,\"stop\":\"\\n\"}");
//		httpPost.setEntity(requestEntity);
//
//		CloseableHttpResponse response = httpClient.execute(httpPost);
//		try {
//			HttpEntity responseEntity = response.getEntity();
//			String responseBody = EntityUtils.toString(responseEntity);
//			System.out.println(responseBody);
//		} finally {
//			response.close();
//		}
	}

}
