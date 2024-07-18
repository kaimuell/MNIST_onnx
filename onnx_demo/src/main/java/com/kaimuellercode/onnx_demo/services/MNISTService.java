package com.kaimuellercode.onnx_demo.services;


import ai.onnxruntime.*;
import com.kaimuellercode.onnx_demo.configuration.OnnxModelProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

/**
 * Predicts the written number on an b/w image with a neural network.
 * the background is expected to be black ( closer to 0).
 * the number is expected to be white ( closer to 1).
 */
@Service
public class MNISTService {

    private final OnnxModelProperties onnxModelProperties;
    private final OrtEnvironment env;
    private final OrtSession.SessionOptions options;
    private final OrtSession session;
    private Logger log = LoggerFactory.getLogger(MNISTService.class);


    public MNISTService(OnnxModelProperties onnxModelProperties) throws OrtException {
        this.onnxModelProperties = onnxModelProperties;
        this.env = OrtEnvironment.getEnvironment();
        this.options = new OrtSession.SessionOptions();
        File f = new File(onnxModelProperties.getPath());
        this.session = env.createSession(
                f.getAbsolutePath(), options);
        log.info("created Onnx session");
        for (NodeInfo i : session.getInputInfo().values()) {
            log.info("Input: " + i.toString());
        }
        for (NodeInfo i : session.getOutputInfo().values()) {
            log.info("Output: " + i.toString());
        }
    }

    public int runSession(float[][] image) throws OrtException {
        float[][][][] imageBatch = {{image}};
        var tensor = OnnxTensor.createTensor(env, imageBatch);
        OrtSession.Result result = session.run(Map.of("input", tensor));
        OnnxValue output = result.get("output").get();
        float[] outputProbs = ((float[][]) output.getValue())[0];
        log.info("Image processed with probabilities : {}", Arrays.toString(outputProbs));
        return predict(outputProbs);
    }

    private int predict(float[] probabilities) {
        assert probabilities.length == 10;
        float maxProbability = probabilities[0];
        int maxIndex = 0;
        for (int i = 1; i < probabilities.length; i++){
            if (probabilities[i] > maxProbability){
                maxProbability = probabilities[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
