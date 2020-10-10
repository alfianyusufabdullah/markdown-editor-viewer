package alfianyusufabdullah.exp.markdown

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.noties.markwon.Markwon
import io.noties.markwon.syntax.Prism4jThemeDefault
import io.noties.markwon.syntax.SyntaxHighlightPlugin
import io.noties.prism4j.Prism4j
import io.noties.prism4j.annotations.PrismBundle
import kotlinx.android.synthetic.main.fragment_preview.*
import kotlinx.coroutines.FlowPreview
import kotlin.time.ExperimentalTime

@ExperimentalTime
@FlowPreview
@PrismBundle(include = ["java", "kotlin"], grammarLocatorClassName = ".GrammarLocatorSourceCode")
class MarkdownPreviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prism4jThemeDefault = Prism4jThemeDefault(Color.parseColor("#f0f0f0"))
        val prism4j = Prism4j(GrammarLocatorSourceCode())

        val mark = Markwon.builder(requireContext())
            .usePlugin(SyntaxHighlightPlugin.create(prism4j, prism4jThemeDefault))
            .build()


        val mainViewModel = ViewModelProvider(requireActivity())[MarkdownViewModel::class.java]
        mainViewModel.text.observe(viewLifecycleOwner) {
            mark.setMarkdown(markdownPreview, it)
        }
    }
}