package com.youthlin.leetcode.queue;

import com.youthlin.leetcode.util.DebugUtil;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 给定一个由 '1'（陆地）和 '0'（水）组成的的二维网格，计算岛屿的数量。一个岛被水包围，并且它是通过水平方向或垂直方向上相邻的陆地连接而成的。你可以假设网格的四个边均被水包围。
 * 输入:
 * 11110
 * 11010
 * 11000
 * 00000
 * <p>
 * 输出: 1
 * <p>
 * 输入:
 * 11000
 * 11000
 * 00100
 * 00011
 * <p>
 * 输出: 3
 *
 * @author : youthlin.chen @ 2019-06-22 11:27
 */
public class IslandsNum {
    public int numIslands(char[][] grid) {
        int result = 0;
        int rowCount = grid.length;
        for (int i = 0; i < rowCount; i++) {
            char[] row = grid[i];
            int columnCount = row.length;
            for (int j = 0; j < columnCount; j++) {
                char cell = grid[i][j];
                if (cell == '1') {
                    result++;
                    process(grid, rowCount, columnCount, i, j);
                    DebugUtil.printf("%02d: i=%02d j=%02d\n", result, i, j);
                    printGrid(grid);
                }
            }
        }
        return result;
    }

    /**
     * 队列版本 这里也可以用递归实现 见下方解法
     */
    private static void process(char[][] grid, int rowCount, int columnCount, int i, int j) {
        grid[i][j] = ' ';
        Queue<Integer> q = new LinkedList<>();
        int index = i * columnCount + j;
        q.offer(index);
        while (!q.isEmpty()) {
            index = q.poll();
            i = index / columnCount;
            j = index % columnCount;
            clearAndEnqueue(grid, i - 1, j, rowCount, columnCount, q);
            clearAndEnqueue(grid, i + 1, j, rowCount, columnCount, q);
            clearAndEnqueue(grid, i, j - 1, rowCount, columnCount, q);
            clearAndEnqueue(grid, i, j + 1, rowCount, columnCount, q);
        }
    }

    private static void clearAndEnqueue(char[][] grid, int i, int j, int rowCount, int columnCount, Queue<Integer> q) {
        if (i >= 0 && j >= 0 && i < rowCount && j < columnCount && grid[i][j] == '1') {
            int index = i * columnCount + j;
            q.offer(index);
            grid[i][j] = ' ';
        }
    }

    public static void main(String[] args) {
        IslandsNum test = new IslandsNum();
        char[][] grid = resetGrid();
        printGrid(grid);
        DebugUtil.println(test.numIslands(grid));
        DebugUtil.println("\n\n\n=======\n\n\n");
        Solution solution = new Solution();
        grid = resetGrid();
        printGrid(grid);
        DebugUtil.println(solution.numIslands(grid));
    }

    private static char[][] resetGrid() {
        return new char[][] {
                { '1', '0', '0', '1', '1', '1', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' },
                { '1', '0', '0', '1', '1', '0', '0', '1', '0', '0', '0', '1', '0', '1', '0', '1', '0', '0', '1', '0' },
                { '0', '0', '0', '1', '1', '1', '1', '0', '1', '0', '1', '1', '0', '0', '0', '0', '1', '0', '1', '0' },
                { '0', '0', '0', '1', '1', '0', '0', '1', '0', '0', '0', '1', '1', '1', '0', '0', '1', '0', '0', '1' },
                { '0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' },
                { '1', '0', '0', '0', '0', '1', '0', '1', '0', '1', '1', '0', '0', '0', '0', '0', '0', '1', '0', '1' },
                { '0', '0', '0', '1', '0', '0', '0', '1', '0', '1', '0', '1', '0', '1', '0', '1', '0', '1', '0', '1' },
                { '0', '0', '0', '1', '0', '1', '0', '0', '1', '1', '0', '1', '0', '1', '1', '0', '1', '1', '1', '0' },
                { '0', '0', '0', '0', '1', '0', '0', '1', '1', '0', '0', '0', '0', '1', '0', '0', '0', '1', '0', '1' },
                { '0', '0', '1', '0', '0', '1', '0', '0', '0', '0', '0', '1', '0', '0', '1', '0', '0', '0', '1', '0' },
                { '1', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '1', '0', '0', '1', '0', '1', '0', '1', '0' },
                { '0', '1', '0', '0', '0', '1', '0', '1', '0', '1', '1', '0', '1', '1', '1', '0', '1', '1', '0', '0' },
                { '1', '1', '0', '1', '0', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '1', '0', '0', '0', '1' },
                { '0', '1', '0', '0', '1', '1', '1', '0', '0', '0', '1', '1', '1', '1', '1', '0', '1', '0', '0', '0' },
                { '0', '0', '1', '1', '1', '0', '0', '0', '1', '1', '0', '0', '0', '1', '0', '1', '0', '0', '0', '0' },
                { '1', '0', '0', '1', '0', '1', '0', '0', '0', '0', '1', '0', '0', '0', '1', '0', '1', '0', '1', '1' },
                { '1', '0', '1', '0', '0', '0', '0', '0', '0', '1', '0', '0', '0', '1', '0', '1', '0', '0', '0', '0' },
                { '0', '1', '1', '0', '0', '0', '1', '1', '1', '0', '1', '0', '1', '0', '1', '1', '1', '1', '0', '0' },
                { '0', '1', '0', '0', '0', '0', '1', '1', '0', '0', '1', '0', '1', '0', '0', '1', '0', '0', '1', '1' },
                { '0', '0', '0', '0', '0', '0', '1', '1', '1', '1', '0', '1', '0', '0', '0', '1', '1', '0', '0', '0' }
        };
    }

    private static void printGrid(char[][] grid) {
        for (char[] row : grid) {
            StringBuilder sb = new StringBuilder();
            for (char c : row) {
                if (c == '1') {
                    sb.append('1');
                } else {
                    sb.append(' ');
                }
                sb.append(',');
            }
            DebugUtil.println(sb);
        }
    }

    /**
     * 递归版本
     */
    static class Solution {
        private int m, n;
        private int[][] direction = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

        public int numIslands(char[][] grid) {
            if (grid == null || grid.length == 0)
                return 0;
            int num = 0;
            m = grid.length;
            n = grid[0].length;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] != '0') {
                        dfs(grid, i, j);
                        num++;
                        DebugUtil.printf("%2d: i=%d, j=%d\n", num, i, j);
                        printGrid(grid);
                    }
                }
            }
            return num;
        }

        private void dfs(char[][] grid, int row, int col) {
            if (row < 0 || col >= n || col < 0 || row >= m || grid[row][col] == '0') {
                return;
            }
            grid[row][col] = '0';
            for (int[] d : direction) {
                dfs(grid, row + d[0], col + d[1]);
            }
        }
    }

}
