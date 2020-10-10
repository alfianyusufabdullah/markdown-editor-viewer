package alfianyusufabdullah.exp.markdown

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import io.noties.markwon.Markwon
import io.noties.markwon.editor.MarkwonEditor
import io.noties.markwon.editor.MarkwonEditorTextWatcher
import kotlinx.android.synthetic.main.activity_markdown.*
import kotlinx.coroutines.FlowPreview
import java.util.concurrent.Executors
import kotlin.time.ExperimentalTime

@ExperimentalTime
@FlowPreview
class MarkdownActivity : AppCompatActivity(), View.OnClickListener {

    private var isPreviewShow = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_markdown)

        val mainViewModel = ViewModelProvider(this)[MarkdownViewModel::class.java]
        markdownEditor.doAfterTextChanged {
            mainViewModel.onTextChanged(it.toString())
        }

        val markwon = Markwon.create(this)
        val editor = MarkwonEditor.create(markwon)

        markdownEditor.addTextChangedListener(
            MarkwonEditorTextWatcher.withPreRender(
                editor,
                Executors.newCachedThreadPool(),
                markdownEditor
            )
        )

        btnBold.setOnClickListener(this)
        btnItalic.setOnClickListener(this)
        btnCode.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val start = markdownEditor.selectionStart
        val end = markdownEditor.selectionEnd - 1

        if (start - end == 1 || (end == 0)) return

        val text = markdownEditor.text.substring(start..end)
        val mark = when (p0?.id) {
            R.id.btnBold -> "**$text**"
            R.id.btnItalic -> "_${text}_"
            R.id.btnCode -> "```\n$text\n```"
            else -> throw IllegalArgumentException("Unknown view")
        }

        val replace = markdownEditor.text.toString().replace(text, mark)

        markdownEditor.setText(replace)
        markdownEditor.requestFocus()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val item = menu?.add("Preview")
        item?.setIcon(R.drawable.ic_preview)
        item?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.title == "Preview") {
            if (isPreviewShow) {
                onBackPressed()
            } else {
                supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .add(android.R.id.content, MarkdownFragment())
                    .commit()
            }

            isPreviewShow = !isPreviewShow
        }
        return super.onOptionsItemSelected(item)
    }
}