@file:Suppress("unused")

package com.example.kotlindemo.utils

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


/**
 * ViewBinding拓展方法
 */

inline fun <reified VB : ViewBinding> ComponentActivity.binding() = lazy {
    inflateBinding<VB>(layoutInflater).also {
        setContentView(it.root)
    }
}

inline fun <reified VB : ViewBinding> ComponentActivity.bindingOnly() = lazy {
    inflateBinding<VB>(layoutInflater)
}

inline fun <reified VB : ViewBinding> Fragment.binding() = FragmentBindingDelegate<VB> { requireView().bind() }

inline fun <reified VB : ViewBinding> Fragment.binding(method: Method) =
        FragmentBindingDelegate<VB> { if (method == Method.BIND) requireView().bind() else inflateBinding(layoutInflater) }

inline fun <reified VB : ViewBinding> Dialog.binding() = lazy {
    inflateBinding<VB>(layoutInflater).also { setContentView(it.root) }
}

inline fun <reified VB : ViewBinding> ViewGroup.binding(attachToParent: Boolean = true) = lazy {
    inflateBinding<VB>(LayoutInflater.from(context), if (attachToParent) this else null, attachToParent)
}

inline fun <reified VB : ViewBinding> TabLayout.Tab.setCustomView(onBindView: VB.() -> Unit) {
    customView = inflateBinding<VB>(LayoutInflater.from(parent!!.context)).apply(onBindView).root
}

inline fun <reified VB : ViewBinding> TabLayout.Tab.bindCustomView(onBindView: VB.() -> Unit) =
        customView?.bind<VB>()?.run(onBindView)

inline fun <reified VB : ViewBinding> TabLayout.Tab.bindCustomView(bind: (View) -> VB, onBindView: VB.() -> Unit) =
        customView?.let { bind(it) }?.run(onBindView)

inline fun <reified VB : ViewBinding> NavigationView.setHeaderView(index: Int = 0, onBindView: VB.() -> Unit) =
        getHeaderView(index)?.bind<VB>()?.run(onBindView)

inline fun <reified VB : ViewBinding> inflateBinding(layoutInflater: LayoutInflater) =
        VB::class.java.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB

inline fun <reified VB : ViewBinding> inflateBinding(parent: ViewGroup) =
        inflateBinding<VB>(LayoutInflater.from(parent.context), parent, false)

inline fun <reified VB : ViewBinding> inflateBinding(
        layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean
) =
        VB::class.java.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
                .invoke(null, layoutInflater, parent, attachToParent) as VB

inline fun <reified VB : ViewBinding> View.bind() =
        VB::class.java.getMethod("bind", View::class.java).invoke(null, this) as VB

inline fun Fragment.doOnDestroyView(crossinline block: () -> Unit) =
        viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroyView() {
                block.invoke()
            }
        })

enum class Method { BIND, INFLATE }

interface BindingLifecycleOwner {
    fun onDestroyViewBinding(destroyingBinding: ViewBinding)
}

class FragmentBindingDelegate<VB : ViewBinding>(private val block: () -> VB) : ReadOnlyProperty<Fragment, VB> {
    private var binding: VB? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (binding == null) {
            binding = block()
            thisRef.doOnDestroyView {
                if (thisRef is BindingLifecycleOwner) thisRef.onDestroyViewBinding(binding!!)
                binding = null
            }
        }
        return binding!!
    }
}

inline fun <reified VB : ViewBinding> BindingViewHolder(parent: ViewGroup) =
        BindingViewHolder(inflateBinding<VB>(parent))

class BindingViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root) {
    constructor(parent: ViewGroup, inflate: (LayoutInflater, ViewGroup, Boolean) -> VB) :
            this(inflate(LayoutInflater.from(parent.context), parent, false))
}

//inline fun <VB : ViewBinding> BindingViewHolder<VB>.onClick(view: View, crossinline action: VB.(Int) -> Unit) =
//        apply { view.setOnClickListener { binding.action(adapterPosition) } }
//
//inline fun <VB : ViewBinding, T> BindingViewHolder<VB>.onClick(
//        view: View,
//        listener: OnItemClickListener<T>,
//        crossinline block: VB.(Int) -> T
//) =
//        onClick(view) { listener.onItemClick(block(it), it) }
//
//inline fun <VB : ViewBinding> BindingViewHolder<VB>.onItemClick(crossinline action: VB.(Int) -> Unit) =
//        onClick(itemView, action)
//
//inline fun <VB : ViewBinding, T> BindingViewHolder<VB>.onItemClick(listener: OnItemClickListener<T>, crossinline block: VB.(Int) -> T) =
//        onItemClick { listener.onItemClick(block(it), it) }
//
//fun interface OnItemClickListener<T> {
//    fun onItemClick(item: T, position: Int)
//}
//
//inline fun <T, reified VB : ViewBinding> ListAdapter(
//        diffCallback: DiffUtil.ItemCallback<T>,
//        crossinline onBindViewHolder: BindingViewHolder<VB>.(T) -> Unit
//) = object : ListAdapter<T, BindingViewHolder<VB>>(diffCallback) {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
//            BindingViewHolder<VB>(parent)
//
//    override fun onBindViewHolder(holder: BindingViewHolder<VB>, position: Int) {
//        onBindViewHolder(holder, currentList[position])
//    }
//}