package com.kaimuellercode.onnx_demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class testArrayConversion {

    @Test
    public void testConversion() throws JsonProcessingException {
        float[] ar1 = {1.0F, 0.0F, 1.0F};
        float[] ar2 = {1.0F, 0.0F, 1.0F};
        float[] ar3 = {1.0F, 0.0F, 1.0F};
        float[][] ar = {ar1, ar2, ar3};
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(ar));

    }
}
