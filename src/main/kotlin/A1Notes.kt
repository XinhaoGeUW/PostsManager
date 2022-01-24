import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Text
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.StageStyle


class A1Notes : Application() {
    private var Index = 0
    private var totalNum = 0
    private var selectedId = -1
    private var numFiltered = 0
    private var isFilter = false
    private var isToggle = false
    private var recPaneList = mutableListOf<MyStackPane>()
    private var filterRecPaneList = mutableListOf<MyStackPane>()
    private var recList = mutableListOf<MyRectangle>()
    private var lastStatus = "$totalNum"
    private var statusLabel = Label(lastStatus)

    override fun start(stage: Stage) {
        var scene1: Scene? = null
        val pane = BorderPane()
        // center flowpane
        val flowPane = FlowPane()
        flowPane.padding = Insets(10.0)
        flowPane.vgap = 10.0
        flowPane.hgap = 10.0


        val createRecPane = { rec: MyRectangle, title: Label, body: Label ->
            val textVBox = VBox(title, body)
            textVBox.padding = Insets(10.0)
            if (rec.isImportant) rec.fill = Color.LIGHTYELLOW else rec.fill = Color.WHITE
            recList.add(rec)
            val recPane = MyStackPane(Index, title.text, body.text, rec, rec.isImportant)
            recPane.prefWidth = 150.0
            recPane.prefHeight = 200.0
            recPane.children.addAll(recPane.myRectangle, textVBox)
            recPaneList.add(recPane)
            flowPane.children.add(recPaneList.last())
            totalNum += 1
            lastStatus = "Added Note #$Index"
            statusLabel.text = "$totalNum     $lastStatus"
            Index += 1
        }

        val popFormWindow = { action: String ->
            val addForm = GridPane()
            addForm.prefWidth = 420.0
            addForm.prefHeight = 350.0
            addForm.hgap = 8.0
            addForm.vgap = 8.0
            addForm.padding = Insets(10.0)

            val selectedNotes = recPaneList.filter { it.index == selectedId }
            val selecteNote = if (selectedNotes.size > 0) selectedNotes[0] else null

            val addFormTitle: Text = if (action == "add") {
                Text("Add New Note")
            } else {
                Text("Edit Note #$selectedId")
            }
            val tLabel = Label("Title")
            val tText :TextField = if (action == "add") TextField("") else TextField(selecteNote?.title)
            val bLabel = Label("Body")
            val bText :TextArea = if (action == "add") TextArea("") else TextArea(selecteNote?.body)
            val checkBox = CheckBox("Important")
            checkBox.isSelected = if (action == "add") false else selecteNote?.isImportant == true
            val saveButton = StandardButton("Save")
            val cancelButton = StandardButton("Cancel")

            addForm.add(addFormTitle, 0, 0)
            addForm.add(tLabel, 0,1, 1, 1)
            addForm.add(tText, 1, 1, 6, 1)
            addForm.add(bLabel, 0, 2, 1, 1)
            addForm.add(bText, 1, 2, 6, 1)
            addForm.add(checkBox, 1, 3)
            addForm.add(saveButton, 3, 4, 2, 1)
            addForm.add(cancelButton, 5, 4, 2, 1)

            val formScene = Scene(addForm)
            val formStage = Stage()
            formStage.scene = formScene
            formStage.initStyle(StageStyle.UNDECORATED);

            formStage.initModality(Modality.WINDOW_MODAL)
            formStage.initOwner(stage.scene.window)
            val greyRec = Rectangle(pane.width, pane.height)
            greyRec.fill = Color.DARKGRAY
            greyRec.opacity = 0.5
            pane.children.add(greyRec)
//            stageStackPane.children.add(greyRec)
//            scene2 = Scene(stageStackPane)
//            stage.scene = scene2



            saveButton.setOnMouseClicked { e ->
                val title = Label(tText.text)
                title.isWrapText = false
                val body = Label(bText.text)
                body.isWrapText = true
                val isImportant = checkBox.isSelected
                val rec = MyRectangle(150.0, 200.0, Index, false, isImportant)
                if (action == "add") {
                    createRecPane(rec, title, body)
                } else {
                    for (rp in recPaneList) {
                        if (rp.index == selectedId) {
                            rp.children.clear()
                            recList.remove(rp.myRectangle)
                            val textVBox = VBox(title, body)
                            textVBox.padding = Insets(10.0)
                            if (rec.isImportant) rec.fill = Color.LIGHTYELLOW else rec.fill = Color.WHITE
                            recList.add(rec)
                            rp.title = title.text
                            rp.body = body.text
                            rp.isImportant = isImportant
                            rp.myRectangle = rec
                            rp.children.addAll(rec, textVBox)
                            lastStatus = "Edited Note #$selectedId"
                            statusLabel.text = "$totalNum     $lastStatus"
                        }
                    }
                }
                // close window
                pane.children.remove(greyRec)
                formStage.close()
            }

            cancelButton.setOnMouseClicked { e ->
                formStage.close()
                pane.children.remove(greyRec)
            }
            formStage.showAndWait()
        }

        // Init the BorderPane

        //val stageStackPane = StackPane(pane)

        // Top
        val add: Button = StandardButton("Add")
        val random: Button = StandardButton("Random")
        val delete: Button = StandardButton("Delete")
        val clear: Button = StandardButton("Clear")
        val toggle = ToggleButton("!")
        val searchBox = TextField()
        val toolBar = HBox(add, random, delete, clear, toggle, searchBox)
        toolBar.style = ("")
        toolBar.alignment = Pos.TOP_LEFT
        toolBar.padding = Insets(10.0)
        toolBar.spacing = 10.0
        toolBar.style = "-fx-border-style: solid none solid none; -fx-border-color: lightgrey;"
        toolBar.minHeight = toolBar.width
        toolBar.scaleZ = 999.0
        pane.top = toolBar

        // Bottom
        val statusBar = HBox(statusLabel)
        statusBar.padding = Insets(10.0)
        pane.bottom = statusBar
        statusBar.alignment = Pos.BOTTOM_LEFT

        // Center
        random.setOnMouseClicked { e ->
            val title = Label("aaaaaaa")
            title.isWrapText = false
            val body = Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                    "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris"+"$totalNum")
            body.isWrapText = true
            val rec = MyRectangle(150.0, 200.0, Index)
            createRecPane(rec, title, body)
            for (rp in recPaneList) {
                val rec = rp.myRectangle
                rp.setOnMouseClicked { e ->
                    if (e.clickCount == 1) {
                        if (rec.isSelect) {
                            rec.stroke = null
                            rec.isSelect = false
                        } else {
                            for (r in recList) {
                                if (r.isSelect) {
                                    r.stroke = null
                                    r.isSelect = false
                                }
                            }
                            rec.stroke = Color.BLUE
                            rec.isSelect = true
                            selectedId = rec.index
                        }
                        if (isFilter) {
                            statusLabel.text = "#$selectedId | $numFiltered (of $totalNum)     $lastStatus"
                        } else {
                            statusLabel.text = "#$selectedId | $totalNum     $lastStatus"
                        }
                    } else if (e.clickCount == 2) {
                        popFormWindow("edit")
                    }
                }
            }
        }

        delete.setOnMouseClicked {
            for ((index, rp) in recPaneList.withIndex()) {
                if (rp.index == selectedId) {
                    flowPane.children.remove(rp)
                    recPaneList.removeAt(index)
                    recList.remove(rp.myRectangle)
                    numFiltered -= 1
                    break
                }
            }
            totalNum -= 1
            lastStatus = "Deleted Note #$selectedId"
            if (isFilter) {
                statusLabel.text = "$numFiltered (of $totalNum)     $lastStatus"
            } else {
                statusLabel.text = "$totalNum     $lastStatus"
            }
        }

        clear.setOnMouseClicked { e ->
            if (isFilter) {
                totalNum -= filterRecPaneList.size
                val copyList = recPaneList.toMutableList()
                for (rp in recPaneList) {
                    if (filterRecPaneList.contains(rp)) {
                        println(rp.title)
                        flowPane.children.remove(rp)
                        copyList.remove(rp)
                        recList.remove(rp.myRectangle)
                    }
                }
                recPaneList.clear()
                recPaneList = copyList.toMutableList()
                copyList.clear()
                lastStatus = "Cleared ${filterRecPaneList.size} Notes"
                statusLabel.text = "0 (of $totalNum)     $lastStatus"
            } else {
                totalNum = 0
                flowPane.children.clear()
                recPaneList.clear()
                recList.clear()
                lastStatus = "Cleared $totalNum Notes"
                statusLabel.text = "$totalNum     $lastStatus"
            }
        }

        toggle.setOnMouseClicked { e ->
            isToggle = !isToggle
            if (isToggle) {
                val fRecPaneList = recPaneList.filter{ it.isImportant }
                isFilter = true
                numFiltered = fRecPaneList.size
                filterRecPaneList = fRecPaneList.toMutableList()
                flowPane.children.clear()
                flowPane.children.addAll(fRecPaneList)
                statusLabel.text = "${numFiltered}(of $totalNum)     $lastStatus"
            } else {
                isFilter = false
                numFiltered = 0
                filterRecPaneList.clear()
                flowPane.children.clear()
                flowPane.children.addAll(recPaneList)
                statusLabel.text = "$totalNum     $lastStatus"
            }
        }

        searchBox.setOnKeyReleased { e ->
            val text = searchBox.text
            if (!text.isEmpty()) {
                val fRecPaneList = recPaneList.filter {
                    it.title.contains(text, ignoreCase = true) || it.body.contains(text, ignoreCase = true)
                }
                isFilter = true
                numFiltered = fRecPaneList.size
                filterRecPaneList = fRecPaneList.toMutableList()
                flowPane.children.clear()
                flowPane.children.addAll(fRecPaneList)
                statusLabel.text = "${fRecPaneList.size}(of $totalNum)     $lastStatus"
            } else {
                isFilter = false
                numFiltered = 0
                filterRecPaneList.clear()
                flowPane.children.clear()
                flowPane.children.addAll(recPaneList)
                statusLabel.text = "$totalNum     $lastStatus"
            }

        }

        //Add Dialog Window
        add.setOnMouseClicked { e ->
            popFormWindow("add")
            for (rp in recPaneList) {
                val rec = rp.myRectangle
                rp.setOnMouseClicked { e ->
                    if (e.clickCount == 1) {
                        if (rec.isSelect) {
                            rec.stroke = null
                            rec.isSelect = false
                        } else {
                            for (r in recList) {
                                if (r.isSelect) {
                                    r.stroke = null
                                    r.isSelect = false
                                }
                            }
                            rec.stroke = Color.BLUE
                            rec.isSelect = true
                            selectedId = rec.index
                        }
                        if (isFilter) {
                            statusLabel.text = "#$selectedId | $numFiltered (of $totalNum)     $lastStatus"
                        } else {
                            statusLabel.text = "#$selectedId | $totalNum     $lastStatus"
                        }
                    } else if (e.clickCount == 2) {
                        popFormWindow("edit")
                    }
                }
            }
        }

        val scrollPane = ScrollPane(flowPane)
        scrollPane.isFitToWidth = true
        scrollPane.isFitToHeight = true
        pane.center = scrollPane





        scene1 = Scene(pane)
        stage.scene = scene1
        stage.width = 800.0
        stage.height = 600.0
        stage.minWidth = 400.0
        stage.minHeight = 400.0
        stage.title = "A1 Notes (x27ge)"
        stage.isResizable = true
        stage.show()

    }
}
