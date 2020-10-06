package alfianyusufabdullah.exp.markdown

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnH3.setOnClickListener {
            val start = markdownEditor.selectionStart
            val end = markdownEditor.selectionEnd - 1

            val text = markdownEditor.text.substring(start..end)
            val mark = "### $text"

            val replace = markdownEditor.text.toString().replace(text, mark)

            markdownEditor.setText(replace)
            markdownEditor.requestFocus()
        }

        btnH5.setOnClickListener {
            val start = markdownEditor.selectionStart
            val end = markdownEditor.selectionEnd - 1

            val text = markdownEditor.text.substring(start..end)
            val mark = "##### $text"

            val replace = markdownEditor.text.toString().replace(text, mark)

            markdownEditor.setText(replace)
            markdownEditor.requestFocus()
        }

        btnBold.setOnClickListener {
            val start = markdownEditor.selectionStart
            val end = markdownEditor.selectionEnd - 1

            val text = markdownEditor.text.substring(start..end)
            val mark = "**$text**"

            val replace = markdownEditor.text.toString().replace(text, mark)

            markdownEditor.setText(replace)
            markdownEditor.requestFocus()
        }

        btnCode.setOnClickListener {
            val start = markdownEditor.selectionStart
            val end = markdownEditor.selectionEnd - 1

            val text = markdownEditor.text.substring(start..end)
            val mark = """```
                        |$text
                        |```""".trimMargin()

            val replace = markdownEditor.text.toString().replace(text, mark)

            markdownEditor.setText(replace)
            markdownEditor.requestFocus()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val item = menu?.add("Preview")
        item?.setIcon(R.drawable.ic_preview)
        item?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.title == "Preview") {
            markdownEditor.clearFocus()
            supportFragmentManager.beginTransaction()
                .add(
                    android.R.id.content,
                    PreviewFragment.newInstance(markdownEditor.text.toString())
                ).addToBackStack(null).commit()
        }
        return super.onOptionsItemSelected(item)
    }
}