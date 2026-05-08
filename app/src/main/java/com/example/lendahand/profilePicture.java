package com.example.lendahand;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

public class profilePicture {
    public Bitmap createInitialsDrawable(String name, Context className) {
        int size = 200; // px
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        // Draw circle
        paint.setColor(ContextCompat.getColor(className, R.color.md_theme_inversePrimary)); // background color
        canvas.drawCircle(size / 2, size / 2, size / 2, paint);

        // Extract initials
        String[] parts = name.split(" ");
        StringBuilder initials = new StringBuilder();
        for (int i = 0; i < 2; ++i) {
            if (!parts[i].isEmpty()) initials.append(parts[i].charAt(0));
        }

        // Draw text
        paint.setColor(ContextCompat.getColor(className, R.color.md_theme_scrim));
        paint.setTextSize(64);
        paint.setTextAlign(Paint.Align.CENTER);

        Rect bounds = new Rect();
        paint.getTextBounds(initials.toString(), 0, initials.length(), bounds);

        float x = size / 2f;
        float y = size / 2f - bounds.exactCenterY();
        canvas.drawText(initials.toString().toUpperCase(), x, y, paint);

        return bitmap;
    }
}
