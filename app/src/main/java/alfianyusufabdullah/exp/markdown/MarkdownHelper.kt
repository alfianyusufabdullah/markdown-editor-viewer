import java.util.regex.Pattern

object MarkdownHelper {

    private const val FIRST_INDEX = 0

    private const val INLINE_CODE_FIRST_FORMAT = "<code>"
    private const val INLINE_CODE_LAST_FORMAT = "</code>"

    private const val BOLD_FIRST_FORMAT = "<strong>"
    private const val BOLD_LAST_FORMAT = "</strong>"

    private const val ITALIC_FIRST_FORMAT = "<em>"
    private const val ITALIC_LAST_FORMAT = "</em>"

    const val TYPE_INLINE_CODE = "'"
    const val TYPE_BOLD = "**"
    const val TYPE_ITALIC = "_"

    fun codeBlockToHtml(value: String): String {
        val codePattern = Pattern.compile("((?i)```[\\w+]).*")
        val matchPattern = codePattern.matcher(value)

        var tempValue = value

        while (matchPattern.find()) {
            repeat(matchPattern.groupCount()) {
                tempValue =
                    tempValue.replace((matchPattern.group(it) ?: ".*").toRegex(), "<pre><code>")
            }
        }

        return tempValue.replace("```", "</code></pre>")
    }

    fun singleLineFormatter(value: String, type: String): String {
        val formattedType = if (type == TYPE_BOLD) "(\\*{2})" else type
        val htmlFormat = when (type) {
            TYPE_BOLD -> mapOf("first" to BOLD_FIRST_FORMAT, "last" to BOLD_LAST_FORMAT)
            TYPE_ITALIC -> mapOf("first" to ITALIC_FIRST_FORMAT, "last" to ITALIC_LAST_FORMAT)
            TYPE_INLINE_CODE -> mapOf(
                "first" to INLINE_CODE_FIRST_FORMAT,
                "last" to INLINE_CODE_LAST_FORMAT
            )
            else -> throw IllegalArgumentException("Unknown formatter")
        }

        val inlineCodePattern =
            Pattern.compile(
                "$formattedType([A-Za-z0-9 [:punct:]].*)$formattedType",
                Pattern.MULTILINE
            )
        val inlineMatch = inlineCodePattern.matcher(value)

        var tempValue = value

        while (inlineMatch.find()) {
            val inlineCodeMatch = inlineMatch.group(0)
            val newInlineCodeFormat =
                inlineCodeMatch?.substring(
                    FIRST_INDEX,
                    if (type == TYPE_BOLD) inlineCodeMatch.lastIndex - 1 else inlineCodeMatch.lastIndex
                )?.replace(type, htmlFormat["first"] ?: "</>") + htmlFormat["last"]

            tempValue = tempValue.replace(inlineCodeMatch ?: "</>", newInlineCodeFormat)
        }

        return tempValue
    }
}