package com.Estateapp.estate;

import com.google.gson.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@SpringBootApplication
//@EnableEurekaClient
public class EstateApplication {

	private static final String OPENAI_API_KEY = "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";
	private static final String API_URL = "https://api.openai.com/v1/engines/text-davinci-002-render-sha";
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static void main(String[] args) throws IOException {
		SpringApplication.run(EstateApplication.class, args);


		String inputDate = "1999-10-06";

		// Parse the input date
		LocalDate date = LocalDate.parse(inputDate);

		// Format the date to the desired output format
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy", Locale.ENGLISH);
		String formattedDate = date.format(outputFormatter).toUpperCase();

		System.out.println("Formatted date: " + formattedDate);

//		String prompt = "Who is president of Nigeria?";
//
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//
//		HttpPost httpPost = new HttpPost("https://api.openai.com/v1/engines/text-davinci-003/completions");
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


//		File journalDirectory = new File("C:\\u01\\app\\oracle\\shared\\properties\\Terminal");
////		File footageDirectory = new File(path+Upload.getBranch()+"\\"+Upload.getTerminal()+"\\"+"Footages");
//
//		if (!journalDirectory.exists()) {
//			boolean status = journalDirectory.mkdir();
//		}
//
//		Set<Character> matchedChars = new HashSet<>();

	}

}
