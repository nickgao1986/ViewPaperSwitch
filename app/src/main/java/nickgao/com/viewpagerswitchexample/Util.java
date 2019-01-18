package nickgao.com.viewpagerswitchexample;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class Util {

    public static String getStringFromAsset(Context mContext, String jsonFile) {
        String result = "";
        try {
            //读取文件数据
            InputStream is = mContext.getResources().getAssets().open(jsonFile);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);//输出流
            result = new String(buffer, "utf-8");
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
