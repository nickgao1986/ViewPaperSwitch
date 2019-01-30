package nickgao.com.viewpagerswitchexample.rain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nickgao.com.viewpagerswitchexample.DensityUtil;


public class EmoticonRainView extends View {
    private static final String TAG = "EmoticonRainView";
    private static final int DEFAULT_APPEAR_INTERVAL_MAX_TIME = 250;
    private static final int DEFAULT_APPEAR_DURATION = 2000;
    //是否正在下表情雨
    private boolean isRaining;
    //表情图高度
    private float mEmoticonHeight;
    private float mEmoticonWidth;

    private Random mRandom;
    private Matrix matrix;
    private Paint mPaint;

    private long mStartTimestamp;
    private final List<Bitmap> mBaseBitmaps = new ArrayList<>();
    private final List<EmoticonBean> mEmoticonList = new ArrayList<>();

    public EmoticonRainView(Context context) {
        super(context);
        init();
    }

    public EmoticonRainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EmoticonRainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mRandom = new Random();
        setVisibility(GONE);

        setWillNotDraw(false);

        initPaint();
    }



    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mPaint.setDither(true);
        mPaint.setColor(getResources().getColor(android.R.color.holo_red_light));
        matrix = new Matrix();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(!isRaining || mEmoticonList.size() == 0){
            return;
        }
        long currentTimestamp = System.currentTimeMillis();
        long totalTimeInterval = currentTimestamp - mStartTimestamp;

        for (int i = 0; i < mEmoticonList.size(); i++) {
            EmoticonBean emoticon = mEmoticonList.get(i);
            Bitmap bitmap = emoticon.getBitmap();
            if(bitmap.isRecycled() || isOutOfBottomBound(i) || totalTimeInterval < emoticon.getAppearTimestamp()){
                continue;
            }

            matrix.reset();

            float heightScale = mEmoticonHeight / bitmap.getHeight();
            float widthScale = mEmoticonWidth / bitmap.getWidth();
            matrix.setScale(widthScale, heightScale);

            emoticon.x = emoticon.x + emoticon.velocityX;
            emoticon.y = emoticon.y + emoticon.velocityY;

            matrix.postTranslate(emoticon.x, emoticon.y);

            canvas.drawBitmap(bitmap, matrix, mPaint);
        }
        postInvalidate();
    }

    /**
     * 某张图的位置是否超出下边界
     */
    private boolean isOutOfBottomBound(int position){
        return mEmoticonList.get(position).y > getHeight();
    }

    public void start(final List<Bitmap> list){
        if(list.size() == 0){
            throw new RuntimeException("EmoticonRainView bitmap list is 0!");
        }
        stop();
        setVisibility(VISIBLE);

        post(new Runnable() {
            @Override
            public void run() {
                initAndResetData(list);
                isRaining = true;
                invalidate();
            }
        });
    }

    private void initAndResetData(final List<Bitmap> list) {
        if(list.size() == 0){
            return;
        }
        this.mEmoticonWidth = mEmoticonHeight = DensityUtil.dp2px(getContext(),50);
        this.mStartTimestamp = System.currentTimeMillis();

        mBaseBitmaps.clear();
        mBaseBitmaps.addAll(list);
        mEmoticonList.clear();

        //开始画表情的总时间
        int currentDuration = 0;

        int i = 0;
        int size = list.size();
        while(currentDuration < DEFAULT_APPEAR_DURATION){
            EmoticonBean bean = new EmoticonBean();
            //因为要出现重复的图标
            bean.bitmap = mBaseBitmaps.get(i%size);

            bean.x = mRandom.nextInt(getWidth());

            bean.y = (int) - Math.ceil(mEmoticonHeight);


            float duration = (mRandom.nextInt(500) + DEFAULT_APPEAR_DURATION);
            bean.velocityY = (int) (getHeight() * 16 / duration);
            bean.scale = 1.0f;
            //round() 方法可把一个数字舍入为最接近的整数
            bean.velocityX = Math.round(mRandom.nextFloat());

            bean.appearTimestamp = currentDuration;
            mEmoticonList.add(bean);
            currentDuration += mRandom.nextInt(DEFAULT_APPEAR_INTERVAL_MAX_TIME);
            i++;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    /**
     * 停止并考虑回收
     */
    public void stop(){
        isRaining = false;
        setVisibility(GONE);
    }

}
