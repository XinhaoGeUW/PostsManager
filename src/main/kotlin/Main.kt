import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.input.KeyEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage

// Demonstrates key event structure
class Main : Application() {
    override fun start(stage: Stage) {
        // input area
        val input = TextField()
        input.prefHeight = 25.0
        input.prefWidth = 400.0
        input.isEditable = true
        input.requestFocus()

        // controls
        val checkKeyPressed = CheckBox()
        checkKeyPressed.text = "OnKeyPressed"
        checkKeyPressed.isSelected = true

        val checkKeyReleased = CheckBox()
        checkKeyReleased.text = "OnKeyReleased"
        checkKeyReleased.isSelected = true

        val checkKeyTyped = CheckBox()
        checkKeyTyped.text = "OnKeyTyped"
        checkKeyTyped.isSelected = true

        // output area
        val values = TextArea()
        values.prefHeight = 575.0
        values.prefWidth = 400.0
        values.isEditable = false

        val clearButton = Button("Clear")
        clearButton.setOnAction {
            input.clear()
            values.clear()
        }

        // event handlers
        input.setOnKeyPressed { event ->
            if (checkKeyPressed.isSelected) {
                printEvent(values, event)
            }
        }
        input.setOnKeyReleased { event->
            if (checkKeyReleased.isSelected) {
                printEvent(values, event)
            }
        }
        input.setOnKeyTyped { event ->
            if (checkKeyTyped.isSelected) {
                printEvent(values, event)
            }
        }

        // setup toolbar
        val row = HBox(input, checkKeyPressed, checkKeyReleased, checkKeyTyped, clearButton)
        row.alignment = Pos.CENTER_LEFT
        row.spacing = 10.0

        // setup scene
        val scene = Scene(VBox(row, values), 800.0, 600.0)
        stage.scene = scene

        // setup stage
        stage.title = "KeyEvents Demo"
        stage.isResizable = false
        stage.show()
    }

    private fun printEvent(textWidget: TextArea, event: KeyEvent) {
        textWidget.appendText(
            event.eventType
                .toString() + ": {target:" + event.target.javaClass + "},"
                    + ": {code:" + event.code + "},"
                    + ": {text:" + event.text + "},"
                    + ": {character:" + event.character + "}\n"
        )
    }
}