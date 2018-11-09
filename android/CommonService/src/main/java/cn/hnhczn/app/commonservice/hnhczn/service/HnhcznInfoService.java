package cn.hnhczn.app.commonservice.hnhczn.service;

import com.alibaba.android.arouter.facade.template.IProvider;

import cn.hnhczn.app.commonservice.hnhczn.bean.HnhcznInfo;

/**
 * ================================================
 * 向外提供服务的接口, 在此接口中声明一些具有特定功能的方法提供给外部, 即可让一个组件与其他组件或宿主进行交互
 *
 * @see <a href="https://github.com/JessYanCoding/ArmsComponent/wiki#2.2.3.2">CommonService wiki 官方文档</a>
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface HnhcznInfoService extends IProvider {
    HnhcznInfo getInfo();
}
