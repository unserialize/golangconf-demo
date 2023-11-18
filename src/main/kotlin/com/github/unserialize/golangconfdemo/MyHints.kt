package com.github.unserialize.golangconfdemo

import com.goide.psi.GoElement
import com.goide.psi.GoStringLiteral
import com.goide.psi.GoValue
import com.intellij.codeInsight.hints.*
import com.intellij.codeInsight.hints.presentation.InsetPresentation
import com.intellij.codeInsight.hints.presentation.PresentationFactory
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.impl.EditorImpl
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import java.io.File
import javax.swing.JPanel

@Suppress("UnstableApiUsage")
class MyHints : InlayHintsProvider<NoSettings> {

    override val key = SettingsKey<NoSettings>("i18n_translations")
    override val name = "i18n translation hints"
    override val previewText = "todo"

    override fun getCollectorFor(file: PsiFile, editor: Editor, settings: NoSettings, sink: InlayHintsSink) = object : InlayHintsCollector {
        override fun collect(element: PsiElement, editor: Editor, sink: InlayHintsSink): Boolean {
            if (element !is GoStringLiteral)
                return true

            if (element.parent !is GoValue && element.parent.parent !is GoElement)
                return true
            val key = (element.parent.parent as GoElement).key ?: return true
            if (key.text != "MessageID")
                return true

            val hintText = getRuTranslation(element.text.trim('"'))
            val factory = PresentationFactory(editor as EditorImpl)
            sink.addInlineElement(element.textOffset, false, InsetPresentation(factory.roundWithBackground(factory.inset(factory.smallText(hintText), top = 3, down = 3)), right = 0, left = 3, top = -3), true)
            return true
        }
    }

    fun getRuTranslation(key: String): String {
        val fileName = "/Users/alexandr.kirsanov/golangconf-demo/goproject_example/translations/ru.json"
        val contents = File(fileName).readText()
        val json = Json.parseToJsonElement(contents)
        return json.jsonObject[key].toString()
    }

    override fun createConfigurable(settings: NoSettings) = object : ImmediateConfigurable {
        override fun createComponent(listener: ChangeListener) = JPanel()
    }

    override fun createSettings() = NoSettings()
}
