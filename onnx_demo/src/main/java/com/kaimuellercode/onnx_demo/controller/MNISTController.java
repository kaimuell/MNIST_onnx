package com.kaimuellercode.onnx_demo.controller;


import ai.onnxruntime.OrtException;
import com.kaimuellercode.onnx_demo.services.MNISTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class MNISTController {
    Logger logger = LoggerFactory.getLogger(MNISTController.class);

    private final MNISTService service;

    MNISTController(MNISTService mnistService){
        this.service = mnistService;
    }

    @PostMapping("float")
    public int predictNumberInImage(@RequestBody float[][] image){
        assert (28 == image.length);
        assert (28 == image[0].length);
        return predictNumber(image);
    }

    private int predictNumber(float[][] image) {
        try {
            var result =  service.runSession(image);
            logger.info("Processed with result : " + result);
            return result;
        } catch (OrtException e) {
            throw new RuntimeException("could not process image : " + e);
        }
    }

    @PostMapping("int")
    public int predictNumberInImageInt(@RequestBody Integer[][] image){
        assert (28 == image.length);
        assert (28 == image[0].length);
        float[][] normalizedImage = normalize(image);
        return predictNumber(normalizedImage);
    }

    private float[][] normalize(Integer[][] image) {
        float[][] normalizedArray = new float[28][28];
        assert image.length == normalizedArray.length;
        assert image[0].length == normalizedArray[0].length;
        for (int i=0; i < image.length; i++){
            for (int j=0; j < image[i].length; j++){
                normalizedArray[i][j] = ((float) image[i][j]) / 255;
            }
        }
        return normalizedArray;
    }
}
