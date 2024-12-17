package com.example.RetryDemo.service;

import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class RetryableService {

    private int attempt = 0;

    @Retryable(
            value = RuntimeException.class,   // Retry on RuntimeException
            maxAttempts = 4,                  // Max retry attempts (3 retries + 1 original)
            backoff = @Backoff(delay = 2000)  // 2 seconds delay between retries
    )
    public HttpStatus retryableMethod() {
        attempt++;
        System.out.println("Attempt #" + attempt + ": Trying operation...");

        // Simulate failure
        if (attempt >= 0) {

            if (attempt == 4) {
                recoverMethod(new RuntimeException("Simulated Failure: Attempt " + attempt));
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }

            throw new RuntimeException("Simulated Failure: Attempt " + attempt);
        }

        System.out.println("Operation Succeeded on Attempt " + attempt);

        return HttpStatus.ACCEPTED;
    }

    // Recovery method
    @Recover
    public String recoverMethod(RuntimeException ex) {
        System.out.println("Recovery Method: Retried 3 times but operation failed.");
        return "Recovery response: Operation failed.";
    }
}
