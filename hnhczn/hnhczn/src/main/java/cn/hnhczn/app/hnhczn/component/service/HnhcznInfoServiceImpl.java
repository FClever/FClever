package cn.hnhczn.app.hnhczn.component.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.utils.ArmsUtils;

import cn.hnhczn.app.commonsdk.core.RouterHub;
import cn.hnhczn.app.commonservice.hnhczn.bean.HnhcznInfo;
import cn.hnhczn.app.commonservice.hnhczn.service.HnhcznInfoService;
import cn.hnhczn.app.hnhczn.R;

/**
 * ================================================
 * 向外提供服务的接口实现类, 在此类中实现一些具有特定功能的方法提供给外部, 即可让一个组件与其他组件或宿主进行交互
 *
 * @see <a href="https://github.com/JessYanCoding/ArmsComponent/wiki#2.2.3.2">CommonService wiki 官方文档</a>
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@Route(path = RouterHub.HNHCZN_SERVICE_HNHCZNSERVICE, name = "海创智能")
public class HnhcznInfoServiceImpl implements HnhcznInfoService {
    private Context mContext;

    @Override
    public HnhcznInfo getInfo() {
        return new HnhcznInfo(ArmsUtils.getString(mContext, R.string.public_name_hnhczn));
    }

    @Override
    public void init(Context context) {
        mContext = context;
    }
}
