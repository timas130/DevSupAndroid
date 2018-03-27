package com.sup.dev.android.libs.mvp.fragments.actions;

import android.support.annotation.NonNull;

import com.sup.dev.java.classes.callbacks.simple.CallbackSource;

import java.util.List;

/***
 *  Не добавляет в список. Выполняется только один раз и если view подключено к презентору
 */
public class ActionSkip<K> extends MvpAction<K> {

    public ActionSkip(@NonNull CallbackSource<K> commandExecutor) {
        super(commandExecutor);
    }

    @Override
    public void add(@NonNull List<MvpAction<K>> mvpActions) {

    }

}
