package com.example.kotlindemo.compose.paging.datasource

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

abstract class DataSource<T : Any>(coroutineScope: CoroutineScope = MainScope()) : BaseDataSource<T>(coroutineScope) {
    private val fetchStateHolder = FetchStateHolder()
    private val invalid = AtomicBoolean(false)
    private val endPage = AtomicBoolean(false)
    private var retryFunc: () -> Unit = {}

    /**
     * Retry fetching.
     */
    fun retry() {
        ensureMainThread {
            retryFunc()
        }
    }

    /**
     * Invalidate the current data source.
     */
    fun invalidate(clear: Boolean = true) {
        ensureMainThread {
            if (invalid.compareAndSet(false, true)) {
                dispatchLoadInitial(clear)
            }
        }
    }

    private fun dispatchLoadInitial(clear: Boolean) {
        endPage.set(false)

        if (clear) {
            clearAll(delay = true)
        }

        launch {
            val result = loadFirst()
            onLoadResult(result)

            invalid.compareAndSet(true, false)

            if (result == null) {
                retryFunc = { dispatchLoadInitial(clear) }
            }

            onLoadInitialFinished(getFetchState())
        }
    }

    protected fun dispatchLoadAround(position: Int) {
        if (isInvalid()) return

        if (shouldLoadAfter(position) && !isEndPage()) {
            if (fetchStateHolder.isNotReady()) {
                return
            }
            scheduleLoadAfter()
        }
    }

    private fun scheduleLoadAfter() {
        changeFetchState(FetchState.Fetching)

        launch {
            val result = loadMore()
            if (isInvalid()) return@launch

            onLoadResult(result)

            if (result == null) {
                retryFunc = { scheduleLoadAfter() }
            }

            onLoadAfterFinished(getFetchState())
        }
    }

    private fun onLoadResult(data: List<T>?) {
        if (data != null) {
            if (data.isEmpty()) {
                changeFetchState(FetchState.DoneFetching)
            } else {
                addItems(data, delay = true)
                notifySubmitList()

                changeFetchState(FetchState.ReadyToFetch)
            }
        } else {
            changeFetchState(FetchState.FetchingError)
        }
    }

    private fun isInvalid(): Boolean {
        return invalid.get()
    }


    fun setEndPage() {
        endPage.compareAndSet(false, true)
    }

    private fun isEndPage(): Boolean {
        return endPage.get()
    }

    private fun changeFetchState(newState: FetchState) {
        fetchStateHolder.setState(newState)
        onFetchStateChanged(newState)
    }

    open suspend fun loadFirst(): List<T>? = emptyList()

    open suspend fun loadMore(): List<T>? = emptyList()

    open fun shouldLoadAfter(position: Int): Boolean {
        return position == totalSize() - 2
    }

    fun getFetchState() = fetchStateHolder.getState()

    /**
     * Call on state changed.
     * @param newState May be these values:
     *  [FetchState.Fetching], [FetchState.FetchingError],
     *  [FetchState.DoneFetching], [FetchState.ReadyToFetch]
     */
    protected open fun onFetchStateChanged(newState: FetchState) {}

    /**
     * Call on load initial finish
     */
    protected open fun onLoadInitialFinished(currentState: FetchState) {}

    /**
     * Call on load after finish
     */
    protected open fun onLoadAfterFinished(currentState: FetchState) {}
}