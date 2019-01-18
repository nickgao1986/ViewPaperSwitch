package nickgao.com.viewpagerswitchexample;

import android.content.Context;

public class DensityUtil {

    public static int dp2px(Context context, float dpValue) {
        return (int)(dpValue * getDensity(context) + 0.5F);
    }

    private static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }
}
