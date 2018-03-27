package com.sup.dev.android.libs.mvp.fragments.actions;

import android.support.annotation.NonNull;

import com.sup.dev.java.classes.callbacks.simple.CallbackSource;

/***
 *  Дабавляет действие в конец списка и удаляется после исполнения
 */
public class ActionSingleExecute<K> extends MvpAction<K> {

    public ActionSingleExecute(@NonNull CallbackSource<K> commandExecutor) {
        super(commandExecutor);
    }

    @Override
    public boolean afterExecution() {
        return true;
    }

}