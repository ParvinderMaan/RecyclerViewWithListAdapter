package com.maan.expert.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import com.maan.expert.R
import com.maan.expert.databinding.ViewUiStateBinding

class UiStateView : ConstraintLayout {


    private  var errorActionCallback: (() -> Unit)? =null
    private  val binder by lazy{
        ViewUiStateBinding.inflate(LayoutInflater.from(context),this)
    }


    constructor(context: Context) : super(context){}
    constructor(context: Context, attr: AttributeSet?) : super(context, attr){}


    override fun onFinishInflate() {
        super.onFinishInflate()
        initialize()
    }

    private fun initialize() {
        showProgress(true)
        showErrorView(false)
        binder.buttonError.setOnClickListener {
            this.errorActionCallback?.invoke()
        }

    }

    private fun showErrorView(value: Boolean) {
        binder.groupError.isVisible=value
    }

    private fun showProgress(value: Boolean) {
        binder.progressBar.isVisible=value
    }

    fun showContent() {
        showProgress(false)
        showErrorView(false)
    }

    fun showError(uiState: UiState.Error) {
        showProgress(false)
        showErrorView(true)
        setError(uiState.msg)
        setErrorAction(uiState.action)
    }

    private fun setErrorAction(action: Boolean) {
        binder.buttonError.isVisible=action
    }

    private fun setError(msg: String) {
        binder.textViewError.text = msg
    }

    fun showProgress() {
        showProgress(true)
        showErrorView(false)
    }


    fun errorActionCallback(action: ()-> Unit){
        this.errorActionCallback=action
    }

}