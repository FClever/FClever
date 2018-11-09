package cn.hnhczn.app.hnhczn.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.hnhczn.app.hnhczn.di.module.HnhcznHomeModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.hnhczn.app.hnhczn.mvp.ui.activity.HnhcznHomeActivity;

@ActivityScope
@Component(modules = HnhcznHomeModule.class, dependencies = AppComponent.class)
public interface HnhcznHomeComponent {
    void inject(HnhcznHomeActivity activity);
}