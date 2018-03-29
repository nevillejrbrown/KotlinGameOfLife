package org.nevillejrbrown.gameoflife

data class StateChange(val row:Int, val col: Int, val state:State)

class Surface(val height:Int, val width:Int) {

    // contents are specified by row / col
    // gets modified on every call to generate() based on the generation rules
    // initialise with completely dead board
    var points:Array<Array<State>> = Array(height, {_ -> Array(width, {_ -> State.DEAD}) })

    /**
     * set the point based on the state change
     */
    fun setPointState(change:StateChange) {
        points[change.row][change.col] = change.state
    }

    fun getPointState(row:Int,col:Int):State {
        return points[row][col]
    }

    /**
        Run all the algorithms that determine what's in the next generation

        A cell can be made "alive"

        A cell can be "killed"

        A cell with fewer than two live neighbours dies of under-population

        A cell with 2 or 3 live neighbours lives on to the next generation

        A cell with more than 3 live neighbours dies of overcrowding

        An empty cell with exactly 3 live neighbours "comes to life"

        The board should wrap
     */
    fun generate() {
        val changes:MutableSet<StateChange> = HashSet<StateChange>()

        for ( (rowNum, row) in points.withIndex()) {
            for ( (colNum, state) in row.withIndex() ) {
                // only run the death rules if the cell is alive!
                val numberOfLiveNeighbours = countLiveNeighbours(rowNum, colNum)

                if (points[rowNum][colNum] == State.ALIVE) {
                    // A cell with fewer than two live neighbours dies of under-population
                    if (numberOfLiveNeighbours < 2) {
                        changes.add(StateChange(rowNum, colNum, State.DEAD))
                    //  A cell with more than 3 live neighbours dies of overcrowding
                    }else if (numberOfLiveNeighbours > 3) {
                        changes.add(StateChange(rowNum, colNum, State.DEAD))
                    }
                    //  An empty cell with exactly 3 live neighbours "comes to life"
                } else if (numberOfLiveNeighbours == 3) { // the point is head AND has 3 live neighbours
                    changes.add(StateChange(rowNum, colNum, State.ALIVE))
                }
            }
        }

        for (change in changes) {
            setPointState(change)
        }
    }

    private fun countLiveNeighbours(row:Int, col: Int):Int {

        var numLiveNeighbours = 0

        val maxRowNum = height -  1;
        val maxColNum = width - 1;

        val colToLeft = if (col > 0) col-1 else maxColNum // wrap around to the left if needed
        val colToRight = if (col < maxColNum) col+1 else  0 // wrap around to the right if needed

        val rowAbove = if (row > 0) row-1 else maxRowNum // wrap off the top
        val rowBelow = if (row < maxRowNum) row+1 else 0 // wrap off the bottom

        numLiveNeighbours += if (points[rowAbove][colToLeft] == State.ALIVE) 1 else 0
        numLiveNeighbours += if (points[rowAbove][col] == State.ALIVE) 1 else 0
        numLiveNeighbours += if (points[rowAbove][colToRight] == State.ALIVE) 1 else 0
        numLiveNeighbours += if (points[row][colToLeft] == State.ALIVE) 1 else 0
        numLiveNeighbours += if (points[row][colToRight] == State.ALIVE) 1 else 0
        numLiveNeighbours += if (points[rowBelow][colToLeft] == State.ALIVE) 1 else 0
        numLiveNeighbours += if (points[rowBelow][col] == State.ALIVE) 1 else 0
        numLiveNeighbours += if (points[rowBelow][colToRight] == State.ALIVE) 1 else 0

        return numLiveNeighbours
    }


    fun printSurface(){
        for (row in points) {
            for (place in row) {
                print(place)
            }
            print("\n")
        }
    }
}