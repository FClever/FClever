package cn.hnhczn.app.commonres.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import cn.hnhczn.app.commonres.R;

/**
 * Created by FClever on 2017/11/29.
 */

public class ConfirmDialog {
    /**
     * 标题
     */
    private TextView tvTitle;
    /**
     * 左边按钮
     */
    private TextView btDialogLeft;
    /**
     * 右边按钮
     */
    private TextView btDialogRight;
    /**
     * 左边按钮值
     */
    private boolean leftBoolean = false;
    /**
     * 右边按钮值
     */
    private boolean rightBoolean = true;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * AlertDialog
     */
    private QMUIDialog qmuiDialog;
    /**
     * 监听对象
     */
    private OnClickListener listener;

    public ConfirmDialog(Context context) {
        this.mContext = context;
        initView();
    }

    private void initView() {
        qmuiDialog = new QMUIDialog(mContext);
        qmuiDialog.setCanceledOnTouchOutside(false);
        qmuiDialog.setContentView(ArmsUtils.inflate(mContext, R.layout.public_dialog_confirm));

        tvTitle = qmuiDialog.findViewById(R.id.tv_title);
        btDialogLeft = qmuiDialog.findViewById(R.id.bt_dialog_left);
        btDialogRight = qmuiDialog.findViewById(R.id.bt_dialog_right);

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay(); //获取屏幕宽高
        Point point = new Point();
        display.getSize(point);

        Window window = qmuiDialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes(); //获取当前对话框的参数值
        layoutParams.width = (int) (point.x * 0.5); //宽度设置为屏幕宽度的0.5
        layoutParams.height = (int) (point.y * 0.5); //高度设置为屏幕高度的0.5
        window.setAttributes(layoutParams);

        qmuiDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背景，加这句代码
//        qmuiDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        //点击空白处 false不消失 true消失
        qmuiDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失

        btDialogLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qmuiDialog.dismiss();
                if (listener != null) {
                    listener.onClick(leftBoolean);
                }

            }
        });

        btDialogRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qmuiDialog.dismiss();
                if (listener != null) {
                    listener.onClick(rightBoolean);
                }
            }
        });

    }

    /**
     * 设置监听事件
     *
     * @param listener
     * @return
     */
    public ConfirmDialog setOnClickListener(OnClickListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * 设置标题
     *
     * @param title 是否确认
     * @return ConfirmDialog
     */
    public ConfirmDialog setTitle(String title) {
        this.tvTitle.setText(title);
        return this;
    }

    /**
     * 设置标题颜色
     *
     * @param color
     * @return
     */
    public ConfirmDialog setTitleColor(int color) {
        this.tvTitle.setTextColor(ArmsUtils.getColor(mContext, color));
        return this;
    }

    /**
     * 设置左边按钮 显示值 返回值
     *
     * @param text
     * @param leftBoolean
     * @return ConfirmDialog
     */
    public ConfirmDialog setLeft(String text, boolean leftBoolean) {
        this.btDialogLeft.setText(text);
        this.leftBoolean = leftBoolean;
        return this;
    }

    /**
     * 设置标题颜色
     *
     * @param color
     * @return
     */
    public ConfirmDialog setLeftColor(int color) {
        this.btDialogLeft.setTextColor(ArmsUtils.getColor(mContext, color));
        return this;
    }

    /**
     * 右边按钮 显示值 返回值boolean
     *
     * @param text
     * @param rightBoolean
     * @return ConfirmDialog
     */
    public ConfirmDialog setRight(String text, boolean rightBoolean) {
        this.btDialogRight.setText(text);
        this.rightBoolean = rightBoolean;
        return this;
    }

    /**
     * 设置标题颜色
     *
     * @param color
     * @return
     */
    public ConfirmDialog setRightColor(int color) {
        this.btDialogRight.setTextColor(ArmsUtils.getColor(mContext,color));
        return this;
    }

    /**
     * 设置单击空白处是否消失 false不消失 true消失
     *
     * @param canceled
     * @return ConfirmDialog
     */
    public ConfirmDialog setCanceledOnTouchOutside(boolean canceled) {
        this.qmuiDialog.setCanceledOnTouchOutside(canceled);
        return this;
    }

    /**
     * 显示
     */
    public void show() {
        this.qmuiDialog.show();
    }

    /**
     * 消失
     */
    public void dismiss() {
        this.qmuiDialog.dismiss();
    }

    /**
     * 监听内部接口
     */
    public interface OnClickListener {
        /**
         * 点击事件
         *
         * @param isTrue
         */
        void onClick(boolean isTrue);
    }

}
