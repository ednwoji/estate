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


	}

}
