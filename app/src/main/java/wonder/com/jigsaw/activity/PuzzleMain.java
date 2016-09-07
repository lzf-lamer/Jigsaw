package wonder.com.jigsaw.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import wonder.com.jigsaw.R;
import wonder.com.jigsaw.adapter.GridItemsAdapter;

/**
 * Created by wonder on 2016/9/7.
 */
public class PuzzleMain extends Activity implements View.OnClickListener {

    //拼图完成时显示的最后一个图片
    public static Bitmap mLastBitmap;
    //设置为N*N显示
    public static int TYPE = 2;
    //步数显示
    public static int COUNT_INDEX = 0;
    //计时显示
    public static int TIMER_INDEX = 0;
    /**
     * UI更新Handler
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 1:
                    //更新计时器
                    TIMER_INDEX++;
                    mTvTimer.setText(""+TIMER_INDEX);
                    break;
                default:
                    break;
            }
        }
    };

    //选择的图片
    private Bitmap mPicSelected;
    //PuzzlePanel
    private GridView mGvPuzzleMainDetail;
    private int mResId;
    private String mPicPath;
    private ImageView mImageView;
    //Button
    private Button mBtnBack;
    private Button mBtnImage;
    private Button mBtnRestart;
    //显示步数
    private TextView mTvPuzzleMainCounts;
    //计时器
    private TextView mTvTimer;
    //切图后的图片
    private List<Bitmap> mBitmapItemLists;
    //GridView适配器
    private GridItemsAdapter mAdapter;
    //Flag 是否已显示原图
    private boolean mIsShowImg;
    //计时器类
    private Timer mTimer;

    /**
     * 计时器线程
     */
    private TimerTask mTimerTask;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.puzzle_main);
        //获取选择的图片
        Bitmap picSelectedTemp;
        //选择默认图片还是自定义图片
        mResId = getIntent().getExtras().getInt("picSelectedID");
        mPicPath = getIntent().getExtras().getString("mPicPath");
        if(mResId!=0)
        {
            picSelectedTemp = BitmapFactory.decodeResource(
                    getResources(),mResId);
        }else{
            picSelectedTemp = BitmapFactory.decodeFile(mPicPath);
        }
        TYPE = getIntent().getExtras().getInt("mType");
        //图片处理
        handlerImage(picSelectedTemp);
        //初始化Views
        initViews();
        //生成游戏数据
        generateGame();
        //GridView点击事件
        mGvPuzzleMainDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //判断是否可以移动
                if (GameUtils.isMoveable(i)) {
                    GameUtils.swapItems(
                            GameUtils.mItemBeans.get(i),
                            GamgeUtils.mBlankItemBean);
                    //重新获取图片
                    recreateData();
                    //通知GridView更新UI
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(PuzzleMain.this, "拼图成功",
                            Toast.LENGTH_LONG).show();
                    mGvPuzzleMainDetail.setEnabled(false);
                    mTimer.cancel();
                    mTimer.cancel();
                }
            }
        });
        //返回按钮点击事件
        mBtnBack.setOnClickListener(this);
        //显示原图按钮点击事件
        mBtnImage.setOnClickListener(this);
        //重置按钮点击事件
        mBtnRestart.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

    }
}
