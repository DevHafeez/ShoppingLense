package com.example.shopping.lense.ecommerce.camera.shoppinglense.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.example.shopping.lense.ecommerce.camera.shoppinglense.models.DetectedObjectInfo;
import com.example.shopping.lense.ecommerce.camera.shoppinglense.interfaces.ObjectDetectionListener;
import com.google.gson.Gson;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;

import java.util.ArrayList;
import java.util.List;

public class ObjectDetectionHelper {

    public static void performObjectDetection(Bitmap inputBitmap, ObjectDetectionListener listener) {
        // Create ML Kit ObjectDetector
        ObjectDetectorOptions options =
                new ObjectDetectorOptions.Builder()
                        .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
//                        .enableMultipleObjects()
                        .enableClassification()  // Optional
                        .build();
        ObjectDetector objectDetector = ObjectDetection.getClient(options);

        // Convert Bitmap to InputImage
        InputImage inputImage = InputImage.fromBitmap(inputBitmap, 0);

        // Process the image with ML Kit ObjectDetector
        objectDetector.process(inputImage)
                .addOnSuccessListener(detectedObjects -> {
                    List<DetectedObjectInfo> detectedObjectList = new ArrayList<>();

                    // Draw rectangles on the bitmap
                    Bitmap outputBitmap = inputBitmap.copy(inputBitmap.getConfig(), true);
                    Canvas canvas = new Canvas(outputBitmap);
                    Paint paint = new Paint();
                    paint.setColor(Color.RED);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(5);

                    for (DetectedObject detectedObject : detectedObjects) {
                        Log.e("Objects - detected", "Objects: " + new Gson().toJson(detectedObject));
                        String objectName = "Object";
                        List<String> objectProperties = new ArrayList<>();
                        if (detectedObject.getLabels().size() > 0) {
                            objectName = detectedObject.getLabels().get(0).getText(); // Assuming the first label is the main label

                            // Extract additional properties if needed
                            float confidence = detectedObject.getLabels().get(0).getConfidence();
                            objectProperties.add("Confidence: " + confidence);
                        }

                        // Extract average color (example, you might need a more advanced color extraction method)
                        int averageColor = extractAverageColor(inputBitmap, detectedObject.getBoundingBox());
                        String color = ("#" + Integer.toHexString(averageColor));

                        // Draw rectangle on the bitmap around the detected object
                        canvas.drawRect(detectedObject.getBoundingBox(), paint);

                        detectedObjectList.add(new DetectedObjectInfo(objectName, color, objectProperties));
                    }


                    // Notify the listener with the detected objects and modified bitmap
                    listener.onObjectsDetected(detectedObjectList, outputBitmap);
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    e.printStackTrace();
                });
    }

    private static int extractAverageColor(Bitmap bitmap, Rect boundingBox) {
        int left = boundingBox.left;
        int top = boundingBox.top;
        int right = boundingBox.right;
        int bottom = boundingBox.bottom;

        int[] pixels = new int[(right - left) * (bottom - top)];
        bitmap.getPixels(pixels, 0, right - left, left, top, right - left, bottom - top);

        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;

        for (int pixel : pixels) {
            redSum += Color.red(pixel);
            greenSum += Color.green(pixel);
            blueSum += Color.blue(pixel);
        }

        int pixelCount = pixels.length;
        int averageRed = redSum / pixelCount;
        int averageGreen = greenSum / pixelCount;
        int averageBlue = blueSum / pixelCount;

        return Color.rgb(averageRed, averageGreen, averageBlue);
    }

}

