package wonder.com.jigsaw.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by wonder on 2016/9/7.
 */
public class GridItemsAdapter extends BaseAdapter {
    //映射List
    private List<Bitmap> mBitmapItemLists;
    private Context mContext;

    public GridItemsAdapter(Context context,List<Bitmap> picList)
    {
        this.mContext = context;
        this.mBitmapItemLists = picList;
    }
    @Override
    public int getCount() {
        return mBitmapItemLists.size();
    }

    @Override
    public Object getItem(int i) {
        return mBitmapItemLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView iv_pic_item = new ImageView(mContext);
        if(view == null) {
            //设置布局 图片
            iv_pic_item.setLayoutParams(new GridView.LayoutParams(
                    mBitmapItemLists.get(i).getWidth(),
                    mBitmapItemLists.get(i).getHeight()));
            //设置显示比例
            iv_pic_item.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }else
        {
            iv_pic_item = (ImageView)view;
        }
        iv_pic_item.setImageBitmap(mBitmapItemLists.get(i));
        return iv_pic_item;
    }
}
