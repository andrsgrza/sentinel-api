package com.sentinel.collector.youtube;

import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class YoutubeDurationParser {

    public Integer parseToSeconds(String isoDuration) {
        if (isoDuration == null || isoDuration.isBlank()) {
            return null;
        }

        return Math.toIntExact(Duration.parse(isoDuration).getSeconds());
    }
}