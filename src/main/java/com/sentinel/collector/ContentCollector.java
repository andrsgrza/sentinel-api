package com.sentinel.collector;

public interface ContentCollector {

    String getPlatform();

    CollectionResult collect(CollectionRequest request);
}