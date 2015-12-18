package koala.imageasyncload;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;
public class MainActivity extends Activity {

    private ListView imageList;
    private List<String> mData;
    //private AsyncImageLoader mImageLoader;
    //private ImageLoaderIWithCache mImageLoader;
    private ImageLoaderWithDoubleCache mImageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // mImageLoader = new AsyncImageLoader();
        mData = Arrays.asList(ImageUtil.IMAGE_URLS);
        imageList = (ListView) findViewById(R.id.imageList);
        mImageLoader = new ImageLoaderWithDoubleCache(MainActivity.this,imageList);
        //ListViewAdapter adapter= new ListViewAdapter(MainActivity.this);
        MyAdapterWithCaches adapter = new MyAdapterWithCaches(MainActivity.this,mData,imageList);
        imageList.setAdapter(adapter);
    }


    class ListViewAdapter extends BaseAdapter{
        private LayoutInflater inflater;

        ListViewAdapter(Context context){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);        }

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
    }

    class ViewHolder{
        ImageView image;
    }
}
