package com.Estateapp.estate;

import com.google.gson.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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


		File journalDirectory = new File("C:\\u01\\app\\oracle\\shared\\properties\\Terminal");
//		File footageDirectory = new File(path+Upload.getBranch()+"\\"+Upload.getTerminal()+"\\"+"Footages");

		if (!journalDirectory.exists()) {
			boolean status = journalDirectory.mkdir();
		}

		Set<Character> matchedChars = new HashSet<>();

	}

}
