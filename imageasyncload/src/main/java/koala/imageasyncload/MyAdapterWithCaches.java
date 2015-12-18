package koala.imageasyncload;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

/**
 * Created by taoxj on 15-12-18.
 */
public class MyAdapterWithCaches extends BaseAdapter implements AbsListView.OnScrollListener {

    private List<String> mData;
    private ListView listView;
    private Context context;
    private LayoutInflater inflater;
    private ImageLoaderWithDoubleCache mImageLoader;
    private int start = 0, end = 0;
    private boolean firstFlag = true;

    public MyAdapterWithCaches(Context ctx,List<String> data,ListView lv){
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = data;
        listView = lv;
        context = ctx;
        mImageLoader = new ImageLoaderWithDoubleCache(context,listView);
        listView.setOnScrollListener(this);
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String url = mData.get(position);
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.image_item, null);
            holder.image = (ImageView) convertView.findViewById(R.id.image_item);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.image.setTag(url);
        holder.image.setImageResource(R.mipmap.ic_launcher);
        mImageLoader.showImageByAsync(holder.image,url);
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
       if(scrollState == SCROLL_STATE_IDLE){
           mImageLoader.loadImage(start,end);
       }else{
           mImageLoader.cancleAllTasks();
       }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
       start = firstVisibleItem;
        end =visibleItemCount + firstVisibleItem;
        if(firstFlag && visibleItemCount > 0){
            mImageLoader.loadImage(start,end);
            firstFlag = false;
        }
    }

    class ViewHolder{
        ImageView image;
    }
}
