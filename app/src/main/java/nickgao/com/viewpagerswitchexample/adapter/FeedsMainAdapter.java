package nickgao.com.viewpagerswitchexample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import nickgao.com.viewpagerswitchexample.Images;
import nickgao.com.viewpagerswitchexample.R;

public class FeedsMainAdapter extends BaseAdapter{

    private ArrayList<String> mList;
    private Context mContext;

    public FeedsMainAdapter(Context context, ArrayList<String> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public String getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.feeds_main_item,null);
            holder = new ViewHolder();
            holder.mTextView = (TextView)convertView.findViewById(R.id.main_text);
            holder.mPic = (SimpleDraweeView)convertView.findViewById(R.id.pic);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.mTextView.setText(getItem(position));
        if(position < Images.imageUrls.length) {
            holder.mPic.setImageURI(Images.imageUrls[position]);
        }
        return convertView;
    }

    class ViewHolder{
        public TextView mTextView;
        public SimpleDraweeView mPic;
    }
}
