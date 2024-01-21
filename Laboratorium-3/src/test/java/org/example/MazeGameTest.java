package org.example;

import org.example.MazeGame;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MazeGameTest {

    @Test
    void testMazeGeneration() {
        MazeGame game = new MazeGame(10, 10);
        assertNotNull(game);

        char[][] maze = game.getMaze();
        assertNotNull(maze);
        assertEquals(10, maze.length);
        assertEquals(10, maze[0].length);
    }

    @Test
    void testStartAndEndPositions() {
        MazeGame game = new MazeGame(10, 10);
        char[][] maze = game.getMaze();

        int[] startPosition = game.getStart();
        int[] endPosition = game.getEnd();

        // Sprawdzenie, czy start i koniec są na krawędziach
        assertTrue(isOnEdge(startPosition[0], startPosition[1], 10, 10));
        assertTrue(isOnEdge(endPosition[0], endPosition[1], 10, 10));

        // Sprawdzenie, czy start i koniec są na przeciwnych krawędziach
        assertTrue(areOnOppositeEdges(startPosition, endPosition, 10, 10));

        // Sprawdzenie, czy start i koniec są osiągalne
        assertTrue(isAccessible(maze, startPosition[0], startPosition[1]));
        assertTrue(isAccessible(maze, endPosition[0], endPosition[1]));
    }

    private boolean isOnEdge(int x, int y, int width, int height) {
        return x == 0 || x == width - 1 || y == 0 || y == height - 1;
    }

    private boolean areOnOppositeEdges(int[] start, int[] end, int width, int height) {
        // todo
        return false;
    }

    private boolean isAccessible(char[][] maze, int x, int y) {
        // logika sprawdzająca, czy dane pole jest osiągalne
        // ...
        return false;
    }

    // Możesz dodać więcej testów pokrywających inne aspekty gry
}
