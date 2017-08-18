package com.lyf.gobangccn.inject.components;

import com.lyf.gobangccn.inject.modules.MessageModules;
import com.lyf.gobangccn.ui.message.MessageFrament;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by 01F on 2017/8/18.
 */
@Singleton
@Component(modules = MessageModules.class)
public interface MessageComponents {
    void inject(MessageFrament frament);
}
