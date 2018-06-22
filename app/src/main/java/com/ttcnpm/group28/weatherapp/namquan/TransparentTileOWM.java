package com.ttcnpm.group28.weatherapp.namquan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class TransparentTileOWM implements TileProvider {
    private static final String OWM_TILE_URL = "https://tile.openweathermap.org/map/%s/%d/%d/%d.png?appid=1af5471452978b9b863e32a587c0e7f4";
    private String tileType;
    private Paint opacityPaint = new Paint();

    @Override
    public Tile getTile(final int x, final int y, final int zoom) {

        Tile tile = null;
        URL tileUrl = getTileUrl(x, y, zoom);
        ByteArrayOutputStream stream = null;
        Bitmap image = null;
        try {
            image = BitmapFactory.decodeStream(tileUrl.openConnection().getInputStream());
            Log.d("Bitmap size: ",String.valueOf(image.getByteCount()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        image = adjustOpacity(image);

        stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);

        byte[] byteArray = stream.toByteArray();

        tile = new Tile(256, 256, byteArray);

        return tile;
    }

    public TransparentTileOWM(String tileType) {
        this.tileType = tileType;
        setOpacity(50);
    }

    private void setOpacity(int opacity) {
        int alpha = (int)Math.round(opacity * 2.55 * 4);    // 2.55 = 255 * 0.01
        opacityPaint.setAlpha(alpha);
    }

    private URL getTileUrl(int x, int y, int zoom) {
        String tileUrl = String.format(OWM_TILE_URL, tileType, zoom, x, y);
        try
        {
            return new URL(tileUrl);
        }
        catch(MalformedURLException e)
        {
            throw new AssertionError(e);
        }
    }

    private Bitmap adjustOpacity(Bitmap bitmap)
    {
        Bitmap adjustedBitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(adjustedBitmap);
        canvas.drawBitmap(bitmap, 0, 0, opacityPaint);

        return adjustedBitmap;
    }
}
