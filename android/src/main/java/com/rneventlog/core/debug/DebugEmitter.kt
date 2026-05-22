package com.rneventlog.core.debug

import com.rneventlog.RnEventLogModule

object DebugEmitter {

    private var module: RnEventLogModule? = null

    fun initialize(module: RnEventLogModule) {
        this.module = module
    }

    fun emit(message: String) {
        if (!DebugConfig.enabled) {
  return
}
        module?.emitDebug(message)
    }
}