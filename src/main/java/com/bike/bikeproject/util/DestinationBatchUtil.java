package com.bike.bikeproject.util;

import java.io.IOException;

public interface DestinationBatchUtil {

    void batchInsert(DestinationType destinationType) throws IOException;

}
