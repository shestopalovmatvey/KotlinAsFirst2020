package Maze

import java.io.File



fun getCamerasList(inputName: String): MutableList<Pair<Int, Int>> {
    val maze = createMaze(inputName)
    val camerasList = mutableListOf<Pair<Int, Int>>()
    for (i in maze.indices) {
        for (j in maze[i].indices) {
            if (needsCamera(maze, j, i) && maze[i][j] != "#") {
                maze[i][j] = "C"
                camerasList.add(i to j)
            }
            print(maze[i][j])
        }
        println()
    }
    return camerasList
}


fun needsCamera(maze: MutableList<MutableList<String>>, x: Int, y: Int): Boolean {
    fun left(): Boolean {
        for (i in x downTo 0) {
            if (maze[y][i] == "C") {
                return false
            }
            if (maze[y][i] == "#") {
                if (i + 1 == x) {
                    return false
                }
                return true
            }
        }
        return true
    }

    fun right(): Boolean {
        for (i in x..maze[y].lastIndex) {
            if (maze[y][i] == "C") {
                return false
            }
            if (maze[y][i] == "#") {
                if (i - 1 == x) {
                    return false
                }
                return true
            }
        }
        return true
    }

    fun up(): Boolean {
        for (i in y downTo 0) {
            if (maze[i][x] == "C") {
                return false
            }
            if (maze[i][x] == "#") {
                if (i + 1 == x) {
                    return false
                }
                return true
            }
        }
        return true
    }

    fun down(): Boolean {
        for (i in y..maze.lastIndex) {
            if (maze[i][x] == "C") {
                return false
            }
            if (maze[i][x] == "#") {
                if (i - 1 == x) {
                    return false
                }
                return true
            }
        }
        return true
    }

    return left() && right() || up() && down()
}

fun createMaze(inputName: String): MutableList<MutableList<String>> {
    val maze = mutableListOf<MutableList<String>>()
    File(inputName).forEachLine {
        val x = mutableListOf<String>()
        it.forEach {
            x.add(it.toString())
        }
        maze.add(x)
    }
    return maze
}






