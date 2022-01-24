import javafx.scene.layout.StackPane

internal class MyStackPane(var index: Int, var title: String, var body: String,
                           var myRectangle: MyRectangle, var isImportant: Boolean = false) : StackPane() {
}