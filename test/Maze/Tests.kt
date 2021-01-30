package Maze

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.io.File

class Tests {

    @Test
    fun getCamerasList() {

        assertEquals(
            mutableListOf(
                (0 to 0),
                (0 to 1),
                (0 to 3),
                (0 to 4),
                (0 to 7),
                (0 to 8),
                (0 to 9),
                (1 to 2),
                (1 to 6),
                (2 to 5),
                (2 to 9)
            ), getCamerasList("input/maze")
        )
        assertEquals(
            mutableListOf(
                (0 to 0),
                (0 to 1),
                (0 to 3),
                (0 to 4),
                (0 to 5),
                (0 to 7),
                (0 to 8),
                (0 to 9),
                (1 to 2),
                (1 to 6),
                (1 to 10),
                (2 to 5),
                (2 to 9)
            ), getCamerasList("input/Maze2")
        )

    }

}