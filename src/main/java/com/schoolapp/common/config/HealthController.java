package com.schoolapp.common.config;

import com.schoolapp.common.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

	@GetMapping("/api/v1/health")
	public ApiResponse<Map<String, String>> health() {
	    return ApiResponse.success(
	            "Application is running",
	            Map.of("status", "UP")
	    );
	}
}