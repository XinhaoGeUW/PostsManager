import javafx.scene.shape.Rectangle

internal class MyRectangle : Rectangle {
    constructor(width:Double, height:Double) : super(width, height) {}
    constructor(width:Double, height:Double, index:Int, isSelect:Boolean = false, isImportant:Boolean = false) : super(width, height) {
        this.index = index
        this.isSelect = isSelect
        this.isImportant = isImportant
    }
    var index = 0
    var isSelect = false
    var isImportant = false
}