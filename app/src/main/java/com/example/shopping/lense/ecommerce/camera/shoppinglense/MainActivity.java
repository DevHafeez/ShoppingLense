package com.example.shopping.lense.ecommerce.camera.shoppinglense;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopping.lense.ecommerce.camera.shoppinglense.interfaces.ObjectDetectionListener;
import com.example.shopping.lense.ecommerce.camera.shoppinglense.models.DetectedObjectInfo;
import com.example.shopping.lense.ecommerce.camera.shoppinglense.utils.ObjectDetectionHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView outputText;
    ImageView outputImage, outputColorImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputImage = findViewById(R.id.output_image);
        outputColorImage = findViewById(R.id.output_image_color);
        outputText = findViewById(R.id.output_text);

        Bitmap test = BitmapFactory.decodeResource(getResources(), R.drawable.test2);
        ObjectDetectionHelper.performObjectDetection(test, new ObjectDetectionListener() {
            @Override
            public void onObjectsDetected(List<DetectedObjectInfo> detectedObjects, Bitmap bitmapWithRectangles) {
                outputImage.setImageBitmap(bitmapWithRectangles);
                for (DetectedObjectInfo detectedObjectInfo : detectedObjects) {
                    Log.e("Objects", detectedObjectInfo.getName());
                    Log.e("Objects", detectedObjectInfo.getProperties().get(0));
                    outputColorImage.setBackgroundColor(Color.parseColor(detectedObjectInfo.getColor()));
                }
            }
        });

//        R.drawable.test
    }
}