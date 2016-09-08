package wonder.com.jigsaw.util;

import java.util.ArrayList;
import java.util.List;

import wonder.com.jigsaw.activity.PuzzleMain;
import wonder.com.jigsaw.bean.ItemBean;

/**
 * Created by wonder on 2016/9/7.
 * 拼图工具类：实现拼图的生成与交换算法
 *
 */
public class GameUtils {

    //游戏信息单元格Bean
    public static List<ItemBean> mItemBeans = new ArrayList<>();
    //空格单元格
    public static ItemBean mBlankItemBeans = new ItemBean();

    /**
     * 判断点击事件的Item是否可以移动
     * @param position
     * @return 能否移动
     */
    public static boolean isMoveable(int position)
    {
        int Type = PuzzleMain.TYPE;
        //获取空格ID
        int blankId = GameUtils.mBlankItemBeans.getmItemId() - 1;
        //不同行相差为Type
        if(Math.abs(blankId - position)==Type)
        {
            return true;
        }
        //相同行，相差1
        if((blankId/Type == position/Type) &&
                Math.abs(blankId - position)==1)
        {
            return true;
        }
        return false;
    }

    /**
     * 交换Item
     * @param from 交换的Item
     * @param blank 空白的Item
     */
    public static void swapItems(ItemBean from,ItemBean blank)
    {
        ItemBean tempItem = new ItemBean();
        //交换BitmapId
        tempItem.setmBitmapId(from.getmBitmapId());
        from.setmBitmapId(blank.getmBitmapId());
        blank.setmBitmapId(tempItem.getmBitmapId());
        //交换Bitmap
        tempItem.setmBitmap(from.getmBitmap());
        from.setmBitmap(blank.getmBitmap());
        blank.setmBitmap(tempItem.getmBitmap());
        //设置新的Blank
        GameUtils.mBlankItemBeans = from;
    }

    /**
     * 生成随机Item
     */
    public static void getPuzzleGenerator()
    {
        int index = 0;
        //随机打乱顺序
        for(int i=0;i<mItemBeans.size();i++)
        {
            index = (int)(Math.random() *
                    PuzzleMain.TYPE * PuzzleMain.TYPE);
            swapItems(mItemBeans.get(index), GameUtils.mBlankItemBeans);
        }
        List<Integer> data = new ArrayList<>();
        for(int i=0;i<mItemBeans.size();i++)
        {
            data.add(mItemBeans.get(i).getmBitmapId());
        }
        //判断生成是否有解
        if(canSolve(data))
        {
            return;
        }else
        {
            getPuzzleGenerator();
        }
    }

    /**
     * 是否拼图完成
     * @return  是否拼图完成
     */
    public static boolean isSuccess()
    {
        for(ItemBean tempBean : GameUtils.mItemBeans)
        {
            if(tempBean.getmBitmapId() != 0 &&
                    (tempBean.getmItemId()) == tempBean.getmBitmapId()){
                continue;
            } else if (tempBean.getmBitmapId() == 0 &&
                    tempBean.getmItemId() == PuzzleMain.TYPE * PuzzleMain.TYPE) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
    /**
     * 该数据是否有解
     *
     * @param data 拼图数组数据
     * @return 该数据是否有解
     */
    public static boolean canSolve(List<Integer> data) {
        // 获取空格Id
        int blankId = GameUtils.mBlankItemBeans.getmItemId();
        // 可行性原则
        if (data.size() % 2 == 1) {
            return getInversions(data) % 2 == 0;
        } else {
            // 从底往上数,空格位于奇数行
            if (((blankId - 1) / PuzzleMain.TYPE) % 2 == 1) {
                return getInversions(data) % 2 == 0;
            } else {
                // 从底往上数,空位位于偶数行
                return getInversions(data) % 2 == 1;
            }
        }
    }

    /**
     * 计算倒置和算法
     *
     * @param data 拼图数组数据
     * @return 该序列的倒置和
     */
    public static int getInversions(List<Integer> data) {
        int inversions = 0;
        int inversionCount = 0;
        for (int i = 0; i < data.size(); i++) {
            for (int j = i + 1; j < data.size(); j++) {
                int index = data.get(i);
                if (data.get(j) != 0 && data.get(j) < index) {
                    inversionCount++;
                }
            }
            inversions += inversionCount;
            inversionCount = 0;
        }
        return inversions;
    }
}
