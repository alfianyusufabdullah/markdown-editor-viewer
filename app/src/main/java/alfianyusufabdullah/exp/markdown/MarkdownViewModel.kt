package alfianyusufabdullah.exp.markdown

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

@ExperimentalTime
@FlowPreview
class MarkdownViewModel : ViewModel() {

    private val _markdown = MutableLiveData<String>()
    val markdown: LiveData<String> = _markdown

    private val _html = MutableLiveData<String>()
    val html: LiveData<String> = _html

    fun onTextChanged(text: String) {
        viewModelScope.launch {

            flow {
                emit(text)
            }
                .debounce(1000.milliseconds)
                .map { MarkdownHelper.codeBlockToHtml(it) }
                .map { MarkdownHelper.singleLineFormatter(it, MarkdownHelper.TYPE_BOLD) }
                .map { MarkdownHelper.singleLineFormatter(it, MarkdownHelper.TYPE_ITALIC) }
                .map { MarkdownHelper.singleLineFormatter(it, MarkdownHelper.TYPE_INLINE_CODE) }
                .flowOn(Dispatchers.Main)
                .collect { _html.postValue(it) }

            flow { emit(text) }.apply {
                debounce(1000.milliseconds)
                collect {
                    _markdown.postValue(it)
                }
            }.flowOn(Dispatchers.Main)

        }
    }
}