package com.example.kotlindemo.study.mvi.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kotlindemo.compose.base.ComposeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty1

/**
 * @Description MVI Flow扩展
 * @Author LuoJia
 * @Date 2023/7/18
 */

/** EventFlow别名 */
typealias SharedFlowEvents<Event> = MutableSharedFlow<List<Event>>

@Suppress("FunctionName")
fun <Event> SharedFlowEvents(): SharedFlowEvents<Event> {
    return MutableSharedFlow()
}

/** 订阅Event */
fun <Event> SharedFlow<List<Event>>.collectEvent(
    lifecycleOwner: LifecycleOwner,
    action: (Event) -> Unit
) {
    lifecycleOwner.lifecycleScope.launchWhenStarted {
        this@collectEvent.collect {
            it.forEach { event ->
                action.invoke(event)
            }
        }
    }
}


/** 【Compose】EffectFlow别名 */
typealias SharedFlowEffects<Effect> = MutableSharedFlow<List<Effect>>

/** 【Compose】 */
@Suppress("FunctionName")
fun <Effect> SharedFlowEffects(): SharedFlowEffects<Effect> {
    return MutableSharedFlow()
}

/**
 * 【Compose】收集副作用
 */
@Composable
fun <State : IUiState, Effect : IUiEffect> ComposeViewModel<State, Effect>.collectSideEffect(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    sideEffect: (suspend (sideEffect: Effect) -> Unit),
) {
    val sideEffectFlow = this.effectFlow
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(sideEffectFlow, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            sideEffectFlow.collect{
                it.forEach { effect ->
                    sideEffect.invoke(effect)
                }
            }
        }
    }
}

/**
 * 【Compose】收集状态
 */
@Composable
fun <S : IUiState, Effect : IUiEffect> ComposeViewModel<S, Effect>.collectAsStateWithLifecycle(): State<S> {
    return this.stateFlow.collectAsStateWithLifecycle()
}

/** 订阅所有State,会根据生命周期取消订阅 */
fun <State> Flow<State>.collectState(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    action: (State) -> Unit
): Job = lifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect(action)
}

/** 订阅所有State,在生命周期内不会取消订阅 */
fun <State> Flow<State>.collectStateLast(
    lifecycleOwner: LifecycleOwner,
    action: (State) -> Unit
): Job = lifecycleOwner.lifecycleScope.launchWhenStarted {
    collectLatest(action)
}

/**
 * Flow订阅局部刷新
 * 1个参数
 */
fun <State, A> StateFlow<State>.collectState(
    lifecycleOwner: LifecycleOwner,
    prop1: KProperty1<State, A>,
    action: (A) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@collectState.map {
                StateTuple1(prop1.get(it))
            }.distinctUntilChanged().collect { (a) ->
                action.invoke(a)
            }
        }
    }
}

/**
 * Flow订阅局部刷新
 * 1个参数
 * ！！！KProperty1 属性使用反射，注意State 避免混淆
 */
fun <State, A> StateFlow<State>.collectStateLast(
    lifecycleOwner: LifecycleOwner,
    prop1: KProperty1<State, A>,
    action: (A) -> Unit
) {
    lifecycleOwner.lifecycleScope.launchWhenStarted {
        this@collectStateLast.map {
            StateTuple1(prop1.get(it))
        }.distinctUntilChanged().collect { (a) ->
            action.invoke(a)
        }
    }
}

/**
 * Flow订阅局部刷新
 * 2个参数
 */
fun <State, A, B> StateFlow<State>.collectState(
    lifecycleOwner: LifecycleOwner,
    prop1: KProperty1<State, A>,
    prop2: KProperty1<State, B>,
    action: (A, B) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@collectState.map {
                StateTuple2(prop1.get(it), prop2.get(it))
            }.distinctUntilChanged().collect { (a, b) ->
                action.invoke(a, b)
            }
        }
    }
}

/**
 * Flow订阅局部刷新
 * 3个参数
 */
fun <State, A, B, C> StateFlow<State>.collectState(
    lifecycleOwner: LifecycleOwner,
    prop1: KProperty1<State, A>,
    prop2: KProperty1<State, B>,
    prop3: KProperty1<State, C>,
    action: (A, B, C) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@collectState.map {
                StateTuple3(prop1.get(it), prop2.get(it), prop3.get(it))
            }.distinctUntilChanged().collect { (a, b, c) ->
                action.invoke(a, b, c)
            }
        }
    }
}

internal data class StateTuple1<A>(val a: A)
internal data class StateTuple2<A, B>(val a: A, val b: B)
internal data class StateTuple3<A, B, C>(val a: A, val b: B, val c: C)
