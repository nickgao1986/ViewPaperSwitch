package nickgao.com.viewpagerswitchexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import nickgao.com.viewpagerswitchexample.adapter.FeedsMainAdapter;

public class TestListActivity extends Activity implements AdapterView.OnItemClickListener {

    public static final String TAG = TestListActivity.class.getSimpleName();
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feeds_main);
        mListView = (ListView) findViewById(R.id.news_home_listview);
        mListView.setAdapter(new FeedsMainAdapter(this,getList()));
        mListView.setOnItemClickListener(this);

    }



    public ArrayList<String> getList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("手势从左向右是看历史");
        list.add("手势从右向左是看历史");
        list.add("手势从下到上是看历史");
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                ViewPaperSwitch1.startActivity(TestListActivity.this);
                break;
            case 1:
                ViewPaperSwitch2.startActivity(TestListActivity.this);
                break;
            case 2:
                ViewPaperSwitch3.startActivity(TestListActivity.this);
                break;

        }
    }
}

