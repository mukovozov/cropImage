package com.example.andrey.cropimage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private Resources mResources;
    private RelativeLayout mLayout;
    private Button mButton;
    private ImageView mImageView;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        mResources = getResources();

        mLayout = (RelativeLayout) findViewById(R.id.activity_main);
        mImageView = (ImageView) findViewById(R.id.image);
        mButton = (Button) findViewById(R.id.button);

        final int bitmapResourceID = R.drawable.ic_menu_background;

        mImageView.setImageBitmap(BitmapFactory.decodeResource(mResources, bitmapResourceID));

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBitmap = BitmapFactory.decodeResource(mResources, bitmapResourceID);
                mBitmap = getCircularBitmap(mBitmap);
                mBitmap = addBorderToCircularBitmap(mBitmap, 15, Color.GREEN);
                mImageView.setImageBitmap(mBitmap);
            }
        });
    }
    private Bitmap getCircularBitmap(Bitmap bitmap) {
        int squareBitmapWidth = Math.min(bitmap.getWidth(), bitmap.getHeight());

        Bitmap newBitmap = Bitmap.createBitmap(
                squareBitmapWidth,
                squareBitmapWidth,
                Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(newBitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Rect rect = new Rect(0, 0, squareBitmapWidth, squareBitmapWidth);
        RectF rectF = new RectF(rect);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        float left = (squareBitmapWidth-bitmap.getWidth())/2;
        float top = (squareBitmapWidth-bitmap.getHeight())/2;
        canvas.drawBitmap(bitmap, left, top, paint);
        bitmap.recycle();
        return newBitmap;
    }

    private Bitmap addBorderToCircularBitmap(Bitmap bitmap, int borderWidth, int borderColor) {
        int sizeBitmapWithBorder = bitmap.getWidth() + borderWidth * 2;
        Bitmap newBitmap = Bitmap.createBitmap(sizeBitmapWithBorder, sizeBitmapWithBorder, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(bitmap, borderWidth, borderWidth, null);

        Paint paint = new Paint();
        paint.setColor(borderColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);
        paint.setAntiAlias(true);
        canvas.drawCircle(
                canvas.getWidth() / 2,
                canvas.getWidth() / 2,
                canvas.getWidth() / 2 - borderWidth / 2,
                paint
        );
        bitmap.recycle();
        return newBitmap;
    }
}
