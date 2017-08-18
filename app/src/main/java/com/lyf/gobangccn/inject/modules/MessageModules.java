package com.lyf.gobangccn.inject.modules;

import com.lyf.gobangccn.ui.message.MessageFrament;
import com.lyf.gobangccn.ui.message.MessagePresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 01F on 2017/8/18.
 */
@Module
public class MessageModules {

    private MessageFrament mView;

    public MessageModules(MessageFrament view) {
        mView = view;
    }
    @Singleton
    @Provides
    public MessagePresenter provideMessagePresenter(){
        return new MessagePresenter(mView);
    }

}
