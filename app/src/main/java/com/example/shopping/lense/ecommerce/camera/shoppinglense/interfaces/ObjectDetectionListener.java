package com.example.shopping.lense.ecommerce.camera.shoppinglense.interfaces;

import android.graphics.Bitmap;

import com.example.shopping.lense.ecommerce.camera.shoppinglense.models.DetectedObjectInfo;

import java.util.List;

public interface ObjectDetectionListener {
    void onObjectsDetected(List<DetectedObjectInfo> detectedObjects, Bitmap bitmapWithRectangles);
}