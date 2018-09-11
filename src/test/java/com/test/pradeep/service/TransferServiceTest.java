package com.test.pradeep.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import com.pradeep.Application;
import com.pradeep.domain.TransactionLog;
import com.pradeep.request.TransferRequest;
import com.pradeep.responses.ResponseDetails;
import com.pradeep.responses.Status;
import com.pradeep.responses.UserResponseDetails;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransferServiceTest {

	private TestRestTemplate restTemplate = new TestRestTemplate();
	@Autowired
	private WebApplicationContext wac;
	@LocalServerPort
	private int port;

	@Test
	public void testCreateAccount() {
		String url = "http://localhost:" + port + "/v1/createAccount";
		ResponseEntity<ResponseDetails> response = restTemplate.exchange(url + "?guestName=JohnWick&intialDeposit=3000",
				HttpMethod.PUT, null, ResponseDetails.class);
		assert (response.getBody().getStatus().equals(Status.SUCCESS));
		response = restTemplate.exchange(url + "?guestName=SamAaron&intialDeposit=4566", HttpMethod.PUT, null,
				ResponseDetails.class);
		assert (response.getBody().getStatus().equals(Status.SUCCESS));
	}

	@Test
	public void testTransferFund() {
		String url = "http://localhost:" + port + "/v1/transferFund/";
		HttpEntity<TransferRequest> entity;
		TransferRequest transferRequest = new TransferRequest("JohnWick", "SamAaron", 400L);
		entity = new HttpEntity<TransferRequest>(transferRequest, null);
		ResponseEntity<ResponseDetails> response = restTemplate.exchange(url, HttpMethod.POST, entity,
				ResponseDetails.class);
		assert (response.getBody().getStatus().equals(Status.SUCCESS));
	}

	@Test
	public void testOverdrawFund() {
		String url = "http://localhost:" + port + "/v1/transferFund/";
		HttpEntity<TransferRequest> entity;
		TransferRequest transferRequest = new TransferRequest("JohnWick", "SamAaron", 40000L);
		entity = new HttpEntity<TransferRequest>(transferRequest, null);
		ResponseEntity<ResponseDetails> response = restTemplate.exchange(url, HttpMethod.POST, entity,
				ResponseDetails.class);
		assert (response.getBody().getStatus().equals(Status.FAILED));
	}

	@Test
	public void testInvalidFund() {
		String url = "http://localhost:" + port + "/v1/transferFund/";
		HttpEntity<TransferRequest> entity;
		TransferRequest transferRequest = new TransferRequest("JohnWick2", "SamAaron", 40000L);
		entity = new HttpEntity<TransferRequest>(transferRequest, null);
		ResponseEntity<ResponseDetails> response = restTemplate.exchange(url, HttpMethod.POST, entity,
				ResponseDetails.class);
		assert (response.getBody().getStatus().equals(Status.FAILED));
	}

	@Test
	public void testMultithreadedFundTransfer() {
		String[] users = { "JohnWick", "NoahJoel", "JasonBourne", "RameshPaul", "DavidDardsley" };

		List<String> usersList = Arrays.asList(users);

		usersList.stream().forEach(p -> {
			createAccount(p, 5000L);
		});

		ExecutorService executorService = Executors.newFixedThreadPool(5);

		try {
			int i = 0;
			while (i++ < 5) {
				executorService.execute(new Runnable() {
					public void run() {
						String from = users[randomNumberInRange(0, 4)];
						String to = users[randomNumberInRange(0, 4)];
						ResponseDetails response = transferFund(from, to, 200);
						if (from.equalsIgnoreCase(to)) {
							assert (response.getStatus().equals(Status.FAILED));
						} else if (response.getCode() == 98) {
							assert (response.getStatus().equals(Status.FAILED));
						} else {
							assert (response.getStatus().equals(Status.SUCCESS));
						}
					}
				});
			}
		} finally {
			executorService.shutdown();
			try {
				executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException e) {

			}
		}
		String url = "http://localhost:" + port + "/v1/viewTxnHistory/";
		List<TransactionLog> response = restTemplate.getForObject(url, List.class, Collections.EMPTY_MAP);
		System.out.println("***************Txn history****************");
		System.out.println(response);
		System.out.println("***************Txn history****************");
		usersList.stream().forEach(p -> {
			viewAccount(p);
		});
	}

	public static int randomNumberInRange(int min, int max) {
		Random random = new Random();
		return random.nextInt((max - min) + 1) + min;
	}

	private void createAccount(String username, long amount) {
		String url = "http://localhost:" + port + "/v1/createAccount";
		restTemplate.exchange(url + "?guestName=" + username + "&intialDeposit=" + amount, HttpMethod.PUT, null,
				ResponseDetails.class);

	}

	private void viewAccount(String username) {
		String url = "http://localhost:" + port + "/v1/viewAccount/" + username;
		UserResponseDetails resp = restTemplate.getForObject(url, UserResponseDetails.class, Collections.EMPTY_MAP);
		System.out.println("***************User account****************");
		System.out.println(resp);
		System.out.println("***************User account****************");

	}

	private ResponseDetails transferFund(String fromUserName, String toUserName, long amount) {
		String url = "http://localhost:" + port + "/v1/transferFund/";
		HttpEntity<TransferRequest> entity;
		TransferRequest transferRequest = new TransferRequest(fromUserName, toUserName, amount);
		entity = new HttpEntity<TransferRequest>(transferRequest, null);
		ResponseEntity<ResponseDetails> response = restTemplate.exchange(url, HttpMethod.POST, entity,
				ResponseDetails.class);
		return response.getBody();
	}

}
