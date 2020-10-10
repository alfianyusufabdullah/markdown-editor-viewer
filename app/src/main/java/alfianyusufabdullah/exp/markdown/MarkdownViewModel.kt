package alfianyusufabdullah.exp.markdown

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

@ExperimentalTime
@FlowPreview
class MarkdownViewModel : ViewModel() {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    fun onTextChanged(text: String) {
        viewModelScope.launch {
            flow { emit(text) }.apply {
                debounce(1000.milliseconds)
                collect {
                    _text.postValue(it)
                }
            }.flowOn(Dispatchers.Main)
        }
    }
}