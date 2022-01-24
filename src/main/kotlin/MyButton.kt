import javafx.scene.control.Button

internal class StandardButton(caption: String? = "Untitled") :
    Button(caption) {
    val BUTTON_MIN_WIDTH = 50.0
    val BUTTON_PREF_WIDTH = 100.0
    val BUTTON_MAX_WIDTH = 200.0
    init {
        // setText(caption); // call to super class already does this
        isVisible = true
        minWidth = BUTTON_MIN_WIDTH
        prefWidth = BUTTON_PREF_WIDTH
        maxWidth = BUTTON_MAX_WIDTH
    }
}
