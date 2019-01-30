package nickgao.com.viewpagerswitchexample.rain;

import android.graphics.Bitmap;


public class EmoticonBean {
    public int appearTimestamp;
    //对应的Bitmap
    public Bitmap bitmap;
    //随机缩放比例
    public float scale;
    //位置
    public int x, y;
    //位移速度
    public int velocityX, velocityY;


    public EmoticonBean() {

    }


    public int getAppearTimestamp() {
        return appearTimestamp;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }


}
