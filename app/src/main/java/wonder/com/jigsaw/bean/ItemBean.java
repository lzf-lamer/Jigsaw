package wonder.com.jigsaw.bean;

import android.graphics.Bitmap;

/**
 * Created by wonder on 2016/9/7.
 * 拼图Item逻辑实体类
 */
public class ItemBean {
    //Item的ID
    private int mItemId;
    //Bitmap的ID
    private int mBitmapId;
    //mBitMap
    private Bitmap mBitmap;

    public ItemBean()
    {
    }
    public ItemBean(int mItemId,int mBitmapId,Bitmap mBitmap)
    {
        this.mItemId = mItemId;
        this.mBitmapId = mBitmapId;
        this.mBitmap = mBitmap;
    }

    public int getmBitmapId() {
        return mBitmapId;
    }

    public void setmBitmapId(int mBitmapId) {
        this.mBitmapId = mBitmapId;
    }

    public int getmItemId() {
        return mItemId;
    }

    public void setmItemId(int mItemId) {
        this.mItemId = mItemId;
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }
}
