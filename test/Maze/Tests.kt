package Maze

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.io.File

class Tests {

    @Test
    fun getCamerasList() {

        assertEquals(
            mutableListOf(
                (1 to 1),
                (4 to 5)
            ), getCamerasList("input/maze", 2)
        )
        assertEquals(
            mutableListOf(
                (4 to 0),
                (8 to 0),
                (0 to 2)
            ), getCamerasList("input/Maze2", 3)
        )
        assertEquals(
            mutableListOf(
                (4 to 0),
                (8 to 0),
                (4 to 1),
                (0 to 2),
                (4 to 2),
                (8 to 2)
            ), getCamerasList("input/Maze2", 6)
        )


    }

}