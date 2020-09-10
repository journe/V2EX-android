package com.journey.android.v2ex.libs.extension

import com.google.gson.reflect.TypeToken
import com.journey.android.v2ex.BuildConfig
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.reflect.Type
import kotlin.coroutines.CoroutineContext

fun CoroutineScope.launchThrowException(block: suspend CoroutineScope.() -> Unit) {
    launch {
        try {
            block.invoke(this)
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        }
    }
}

fun launchCoroutine(
    block: suspend (CoroutineScope) -> Unit,
    error: ((e: Exception) -> Unit)? = null,
    context: CoroutineContext = Dispatchers.Main
): Job {
    return GlobalScope.launch(context + CoroutineExceptionHandler { _, e ->
        Logger.e("==>coroutineException", e.message)    //1
    }) {
        try {
            block(this)
        } catch (e: Exception) {        //2
            Logger.e("==>coroutineError", e.message)
            if (error != null) {
                error(e)
            }
        }
    }
}

inline fun <reified E : Any> List<E>.toGson(): Type = object : TypeToken<List<E>>() {}.type

////带有权限获取的方法
//fun Fragment.actionWithPermission(permissions: List<String>, action: () -> Unit) {
//    PermissionX.init(this)
//        .permissions(permissions)
//        .request { allGranted, grantedList, deniedList ->
//            if (allGranted) {
//                action.invoke()
//            } else {
//                toast("授予相关权限才可以使用该功能哦～")
//            }
//        }
//}
//
//fun FragmentActivity.actionWithPermission(permissions: List<String>, action: () -> Unit) {
//    PermissionX.init(this)
//        .permissions(permissions)
//        .request { allGranted, grantedList, deniedList ->
//            if (allGranted) {
//                action.invoke()
//            } else {
//                toast("授予相关权限才可以使用该功能哦～")
//            }
//        }
//}