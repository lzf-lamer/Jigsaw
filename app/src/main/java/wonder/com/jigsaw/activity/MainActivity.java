package wonder.com.jigsaw.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import wonder.com.jigsaw.R;
import wonder.com.jigsaw.adapter.GridPicListAdapter;
import wonder.com.jigsaw.util.ScreenUtils;

/**
 * @author wonder
 * 2016/9/4.
 */
public class MainActivity extends AppCompatActivity {

    //返回值:图库
    private static final int RESULT_IMAGE = 100;
    //返回值:相机
    private static final int RESULT_CAMERA = 200;
    private PopupWindow mPopupWindow;
    private View mPopupView;
    // GridView 显示图片
    private GridView mGvPicList;
    private List<Bitmap> mPicList;
    // 主页图片资源ID
    private int[] mResPicId;
    // 游戏类型N*N
    private int mType = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPicList = new ArrayList<Bitmap>();
        // 初始化Views
        initViews();
        // 数据适配器
        mGvPicList.setAdapter(new GridPicListAdapter(
                MainActivity.this, mPicList));
        //Item监听事件
        mGvPicList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==mResPicId.length-1)
                {
                    //选择本地图库 相机
                    showDialogCustom();
                }else
                {
                    //选择默认图片
                    Intent intent = new Intent(
                            MainActivity.this,
                            PuzzleMain.class);
                    intent.putExtra("picSelectedID",mResPicId[i]);
                    intent.putExtra("mType",mType);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    /**
     * 显示Popup Window
     * @param view popup window
     */
    private void popupShow(View view)
    {
        int density = (int) ScreenUtils.getDeviceDensity(this);
        //显示popup window
        mPopupWindow = new PopupWindow(mPopupView,
                200*density,50*density);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        //设置透明背景
        Drawable tanslate = new ColorDrawable(Color.TRANSPARENT);
        mPopupWindow.setBackgroundDrawable(tanslate);
        //获取位置
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        mPopupWindow.showAtLocation(
                view, Gravity.NO_GRAVITY,
                location[0] - 40 * density,
                location[1] - 30 * density);
    }
    /**
     * 初始化Views
     */
    private  void initViews()
    {
        mGvPicList = (GridView)findViewById(R.id.gv_xpuzzle_main_pic_list);
        mResPicId = new int[]{
                R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,
                R.drawable.pic4,R.drawable.pic5,R.drawable.pic6,
                R.drawable.pic7,R.drawable.pic8,R.drawable.pic9,
                R.drawable.pic10,R.drawable.pic11,R.drawable.pic13,
                R.drawable.pic14,R.drawable.pic15,R.drawable.pic16,
                R.mipmap.ic_launcher};
        Bitmap[] bitmaps = new Bitmap[mResPicId.length];
        for(int i=0;i<bitmaps.length;i++)
        {
            bitmaps[i] = BitmapFactory.decodeResource(
                    getResources(),mResPicId[i]);
            mPicList.add(bitmaps[i]);
        }
    }
}
