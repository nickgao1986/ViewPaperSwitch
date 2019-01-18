package nickgao.com.viewpagerswitchexample.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import nickgao.com.viewpagerswitchexample.DensityUtil;
import nickgao.com.viewpagerswitchexample.MyApp;

/**
 * 卡片效果Transformer
 * @author:gaoyoujian
 */

public class CardTransformer2 implements ViewPager.PageTransformer {

    private int mOffset = 40;

    private Context context;
    private int spaceBetweenFirAndSecWith = 10 * 2;//第一张卡片和第二张卡片宽度差  dp单位
    private int spaceBetweenFirAndSecHeight = 10;//第一张卡片和第二张卡片高度差   dp单位


    protected void onPreTransform(View page, float position) {
        final float width = page.getWidth();
        final float height = page.getHeight();

        page.setRotationX(0);
        page.setRotationY(0);
        page.setRotation(0);
        page.setScaleX(1);
        page.setScaleY(1);
        page.setPivotX(0);
        page.setPivotY(0);
        page.setTranslationX(0);
        page.setTranslationY(-height * position);

        page.setAlpha(position <= -1f || position >= 1f ? 0f : 1f);


        /*final float normalizedposition = Math.abs(Math.abs(position) - 1);
        page.setAlpha(normalizedposition);*/
    }


    @Override
    public void transformPage(View page, float position) {
        context = MyApp.getContext();
        onPreTransform(page,position);
        if (position <= 0.0f) {
            page.setAlpha(1.0f);
            Log.e("onTransform", "position <= 0.0f ==>" + position);
            page.setTranslationY(0f);
            //控制停止滑动切换的时候，只有最上面的一张卡片可以点击
            page.setClickable(true);
        } else if (position <= 3.0f) {
            Log.e("onTransform", "position <= 3.0f ==>" + position);
            float scale = (float) (page.getWidth() - DensityUtil.dp2px(context, spaceBetweenFirAndSecWith * position)) / (float) (page.getWidth());
            //控制下面卡片的可见度
            page.setAlpha(1.0f);
            //控制停止滑动切换的时候，只有最上面的一张卡片可以点击
            page.setClickable(false);
            page.setPivotX(page.getWidth() / 2f);
            page.setPivotY(page.getHeight() / 2f);
            page.setScaleX(scale);
            page.setScaleY(scale);
            page.setTranslationY(-page.getHeight() * position + (page.getHeight() * 0.5f) * (1 - scale) + DensityUtil.dp2px(context, spaceBetweenFirAndSecHeight) * position);
        }

    }

}
