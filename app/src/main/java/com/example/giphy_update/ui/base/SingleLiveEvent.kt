package com.example.giphy_update.ui.base

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

open class SingleLiveEvent<T> : MutableLiveData<T>() {
    /**
     * 멀티쓰레딩 환경에서 동시성을 보장하는 AtomicBoolean.
     * false로 초기화되어 있음
     */
    private val mPending = AtomicBoolean(false)


    /**
     * View(Activity or Fragment 등 LifeCycleOwner)가 활성화 상태가 되거나
     * setValue로 값이 바뀌었을 때 호출되는 observe 함수.
     * pending.compareAndSet(true, false)라는 것은, 위의 pending 변수가
     * true면 if문 내의 로직을 처리하고 false로 바꾼다는 것이다.
     *
     * 아래의 setValue를 통해서만 pending값이 true로 바뀌기 때문에,
     * Configuration Changed가 일어나도 pending값은 false이기 때문에 observe가
     * 데이터를 전달하지 않는다!
     */
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Timber.v("$TAG ${"Multiple observers registered but only one will be notified of changes."}")
        }

        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }


    /**
     * LiveData로써 들고있는 데이터의 값을 변경하는 함수.
     * 여기서는 pending(AtomicBoolean)의 변수는 true로 바꾸어
     * observe내의 if문을 처리할 수 있도록 하였음.
     */
    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * 데이터의 속성을 지정해주지 않아도 call만으로 setValue를 호출 가능
     */
    @MainThread
    fun call() {
        value = null
    }

    companion object {
        private const val TAG = "SingleLiveEvent"
    }
}

/**
 * 즉 postValue나 setValue, call등을 통하여 setValue 함수를 거쳐야만이 Observer을 통하여 데이터를 전달 할 수 있으며,
 * 이는 Configuration Changed 등의 LifeCycleOwner의 재활성화 상태가 와도 원치 않는 이벤트가 일어나는 것을 방지할 수 있도록 해줍니다.
 */

