package cn.hnhczn.app.hnhczn.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import cn.hnhczn.app.hnhczn.mvp.contract.HnhcznHomeContract;
import cn.hnhczn.app.hnhczn.mvp.model.HnhcznHomeModel;


@Module
public class HnhcznHomeModule {
    private HnhcznHomeContract.View view;

    /**
     * 构建HnhcznHomeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public HnhcznHomeModule(HnhcznHomeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HnhcznHomeContract.View provideHnhcznHomeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HnhcznHomeContract.Model provideHnhcznHomeModel(HnhcznHomeModel model) {
        return model;
    }
}