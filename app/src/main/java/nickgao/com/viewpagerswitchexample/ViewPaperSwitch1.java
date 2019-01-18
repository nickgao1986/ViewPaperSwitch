package nickgao.com.viewpagerswitchexample;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nickgao.com.viewpagerswitchexample.view.CardTransformer;

public class ViewPaperSwitch1 extends AppCompatActivity {

    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private List<QuestionInfo> mHistoryList = new ArrayList<>();

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,ViewPaperSwitch1.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        new MyTask().execute();

    }

    private void initData() {
        String str = Util.getStringFromAsset(this, "question.json");
        try{
           JSONObject jsonObject = new JSONObject(str);
           JSONObject dataInfo = (JSONObject) jsonObject.get("data");
           JSONArray historyInfoArray = (JSONArray)dataInfo.get("history_list");
           if(mHistoryList != null) {
               mHistoryList.clear();
           }

           for (int i=0;i<historyInfoArray.length();i++) {
               QuestionInfo info = new Gson().fromJson(historyInfoArray.get(i).toString(), QuestionInfo.class);
               info.card_type = Integer.valueOf(info.type);
               mHistoryList.add(info);
           }
       }catch (Exception ex) {
           ex.printStackTrace();
       }
    }

    private class MyTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            initData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setAdapter();

        }
    }

    private void setAdapter() {
        Collections.reverse(mHistoryList);
        mViewPagerAdapter = new ViewPagerAdapter(this, mHistoryList);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(mHistoryList.size()-1,false);
        CardTransformer mCardPageTransformer = new CardTransformer();
        //设置limit
        mViewPager.setOffscreenPageLimit(3);
        //设置transformer
        mViewPager.setPageTransformer(true, mCardPageTransformer);


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int position) {

            }
        });

    }


    public class ViewPagerAdapter extends PagerAdapter {

        private Context mContext;
        private List<QuestionInfo> list;
        private View mCurrentView;
        TextView contentTv;
        TextView TipContentTv;
        ImageView first_btn_right_icon;
        ImageView second_btn_right_icon;
        LinearLayout tip_layout;
        TextView optionTv1;
        TextView optionTv2;
        LinearLayout no_network_layout;
        RelativeLayout item_data_layout;
        LinearLayout all_question_done_layout;
        TextView donw_layout_tip_work;
        Button tip_button;
        RelativeLayout check_more_layout;


        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            mCurrentView = (View) object;

        }

        //获去当前VIew的方法

        public View getPrimaryItem() {
            return mCurrentView;

        }

        public ViewPagerAdapter(Context context, List<QuestionInfo> list) {
            this.mContext = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        public void setList(List<QuestionInfo> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        public QuestionInfo getItem(int position) {
            return list.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.test_item_layout, container, false);
            initView(view);
            bindData(list.get(position));
            container.addView(view);
            return view;
        }

        private void saveQuestionInfo(final int option) {

            optionTv1.setSelected(true);
            optionTv2.setSelected(false);
            optionTv1.setEnabled(false);
            optionTv2.setEnabled(false);
        }


        private void initView(View view) {
            contentTv = (TextView) view.findViewById(R.id.card_text);
            TipContentTv = (TextView) view.findViewById(R.id.tip_text);
            optionTv1 = (TextView) view.findViewById(R.id.first_btn);
            optionTv2 = (TextView) view.findViewById(R.id.second_btn);
            tip_layout = (LinearLayout) view.findViewById(R.id.tip_layout);
            no_network_layout = (LinearLayout) view.findViewById(R.id.no_network_layout);
            tip_button = (Button) view.findViewById(R.id.tip_button);
            item_data_layout = (RelativeLayout) view.findViewById(R.id.item_data_layout);
            all_question_done_layout = (LinearLayout) view.findViewById(R.id.all_question_done_layout);
            donw_layout_tip_work = (TextView) view.findViewById(R.id.donw_layout_tip_work);

            check_more_layout = (RelativeLayout) view.findViewById(R.id.check_more_layout);
            first_btn_right_icon = (ImageView) view.findViewById(R.id.first_btn_right_icon);
            second_btn_right_icon = (ImageView) view.findViewById(R.id.second_btn_right_icon);
            optionTv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveQuestionInfo(1);
                }
            });
            optionTv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveQuestionInfo(2);
                }
            });

            tip_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        public void bindData(final QuestionInfo itemData) {
            try {
                if (itemData == null) {
                    return;
                }

                contentTv.setText(itemData.title);
                int type = itemData.card_type;
                int answer = 0;
                int user_option = 0;
                if (!TextUtils.isEmpty(itemData.answer)) {
                    answer = Integer.valueOf(itemData.answer);
                }
                if (!TextUtils.isEmpty(itemData.option)) {
                    user_option = Integer.valueOf(itemData.option);
                }


                optionTv1.setText(itemData.options.get(0));
                optionTv2.setText(itemData.options.get(1));


            }catch (Exception ex) {
                ex.printStackTrace();
            }

        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
