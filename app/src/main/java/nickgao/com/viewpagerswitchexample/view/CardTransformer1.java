package nickgao.com.viewpagerswitchexample.view;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 卡片效果Transformer
 * @author:gaoyoujian
 */

public class CardTransformer1 implements ViewPager.PageTransformer {

    private int mOffset = 40;

    @Override
    public void transformPage(View page, float position) {

        if (position > 0){
            //移动X轴坐标，使得卡片在同一坐标
            page.setTranslationX(-position * page.getWidth());
            //缩放卡片并调整位置
            float scale = (page.getWidth() - mOffset * position) / page.getWidth();
            page.setScaleX(scale);
            page.setScaleY(scale);
            //移动Y轴坐标
            page.setTranslationY(position * mOffset);
        }

    }

}
