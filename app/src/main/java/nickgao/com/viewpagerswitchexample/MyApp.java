package nickgao.com.viewpagerswitchexample;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApp extends Application {

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
