package com.sentinel.collector;

public interface ContentCollector {

    String getPlatform();

    CollectionResult collect(CollectionConfig config);
}