package com.bike.bikeproject.service;

import com.bike.bikeproject.util.DestinationType;

import java.io.IOException;

public interface SimpleDestinationBatchService {

    void batchInsert(DestinationType destinationType) throws IOException;

}
