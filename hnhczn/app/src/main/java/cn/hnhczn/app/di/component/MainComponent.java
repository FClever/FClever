package cn.hnhczn.app.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import cn.hnhczn.app.di.module.MainModule;
import cn.hnhczn.app.mvp.ui.activity.MainActivity;
import dagger.Component;

@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}