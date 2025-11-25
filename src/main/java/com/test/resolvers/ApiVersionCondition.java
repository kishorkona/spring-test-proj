package com.test.resolvers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import java.util.regex.Pattern;

public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {

    // Extract the version from the header. e.g. "v1", "v2", etc.
    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile("v(\\d+).+");
    private final String apiVersion;

    public ApiVersionCondition(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    // How to combine a class-level with a method-level condition.
    // Method-level annotations override class-level ones.
    @Override
    @NonNull
    public ApiVersionCondition combine(@NonNull ApiVersionCondition other) {
        return new ApiVersionCondition(other.apiVersion);
    }

    // The logic to match the request. This is the most important method.
    @Override
    public ApiVersionCondition getMatchingCondition(@NonNull HttpServletRequest request) {
        // Check for the custom header
        String versionHeader = request.getHeader("X-API-Version");
        if (versionHeader != null && versionHeader.equals(this.apiVersion)) {
            return this; // It's a match!
        }
        return null; // Not a match
    }

    // Used to compare matches. A higher version number can be considered a "better" match.
    @Override
    public int compareTo(@NonNull ApiVersionCondition other, @NonNull HttpServletRequest request) {
        return other.apiVersion.compareTo(this.apiVersion);
    }
}