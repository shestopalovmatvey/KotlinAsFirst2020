package Maze

import java.io.File

fun main() {
    print(getCamerasList("input/Maze2", 3))
}

fun getCamerasList(inputName: String, cameras: Int): MutableList<Pair<Int, Int>> {
    val maze = createMaze(inputName)
    setCameras(maze, cameras)
    val camerasList = mutableListOf<Pair<Int, Int>>()
    for (i in maze.indices) {
        for (j in maze[i].indices) {
            if (maze[i][j] == "C") {
                camerasList.add(j to i)
            }
        }
    }
    printMaze(maze)
    return camerasList
}

fun printMaze(maze: MutableList<MutableList<String>>) {
    for (i in maze) {
        for (j in i) {
            print("$j ")
        }
        println()
    }
    println()
}

fun setCameras(maze: MutableList<MutableList<String>>, cameras: Int) {
    for (i in 1..cameras) {
        val xy = maxView(maze)
        maze[xy.second][xy.first] = "C"
    }
}

fun maxView(maze: MutableList<MutableList<String>>): Pair<Int, Int> {
    var max = getView(maze, 0, 0)
    var answer = 0 to 0
    for (i in maze.indices) {
        for (j in maze[i].indices) {
            val value = getView(maze, j, i)
            if (max < value) {
                max = value
                answer = j to i
            }
        }
    }
    return answer
}

fun getView(maze: MutableList<MutableList<String>>, x: Int, y: Int): Int {
    if (maze[y][x] == "#") {
        return 0
    }
    fun leftView(): Int {
        var count = -1
        for (i in x downTo 0) {
            if (maze[y][i] == "C") {
                return 0
            }
            if (maze[y][i] == "#") {
                return count
            } else {
                count++
            }
        }
        return count
    }

    fun rightView(): Int {
        var count = -1
        for (i in x..maze[y].lastIndex) {
            if (maze[y][i] == "C") {
                return 0
            }
            if (maze[y][i] == "#") {
                return count
            } else {
                count++
            }
        }
        return count
    }

    fun upView(): Int {
        var count = -1
        for (i in y downTo 0) {
            if (maze[i][x] == "C") {
                return 0
            }
            if (maze[i][x] == "#") {
                return count
            } else {
                count++
            }
        }
        return count
    }

    fun downView(): Int {
        var count = -1
        for (i in y..maze.lastIndex) {
            if (maze[i][x] == "C") {
                return 0
            }
            if (maze[i][x] == "#") {
                return count
            } else {
                count++
            }
        }
        return count
    }

    return leftView() + rightView() + upView() + downView()

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






