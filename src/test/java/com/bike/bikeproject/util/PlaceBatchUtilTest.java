package com.bike.bikeproject.util;

import com.bike.bikeproject.util.impl.SimplePlaceBatchUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PlaceBatchUtilTest {

    @Autowired
    SimplePlaceBatchUtil placeBatchUtil;

    @Test
    public void readFileTest() {
        List<List<String>> res = placeBatchUtil.readFile(PlaceType.TRAVEL);
        for (List<String> innerList : res) {
            StringBuilder str = new StringBuilder();
            for (String data : innerList) {
                str.append(" "+data);
            }
            System.out.println(str);
        }
    }

}
