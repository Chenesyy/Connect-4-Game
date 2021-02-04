package connect4_2.pkg0;

import static connect4_2.pkg0.Connect4_20.AIcolor;
import static connect4_2.pkg0.Connect4_20.grid;
import static connect4_2.pkg0.Connect4_20.player;
import static connect4_2.pkg0.Connect4_20.counter;

import java.util.*;

/**
 *
 * @author Ched
 */
public class MiniMax {

    EvaluationFunction eval = new EvaluationFunction();
    public static int best;
//mas mabilis ito pero parang mas prone sa column full?
//    public int bestMove(char[][] grid, int depth) {
//        char[][] copy = new char[6][7];
//        for (int i = 0; i < copy.length; i++) {
//            for (int j = 0; j < copy[i].length; j++) {
//                copy[i][j] = grid[i][j];
//            }
//        }
//        int bestScore = Integer.MIN_VALUE;
//        int bestMv = 0;
//        int row;
//        int score;
//        ArrayList<Integer> validCol = new ArrayList<Integer>();
//        validCol = validColumns(copy);
//        for (int col = 0; col < validCol.size(); col++) {
//            row = validRow(copy, validCol.get(col));
//            copy[row][col] = AIcolor;
//            score = minimax(copy, depth, false);
//            copy[row][col] = ' ';
//            if (score > bestScore) {
//                bestScore = score;
//                bestMv = col;
//            }
//        }
//
//        return bestMv;
//    }
//
//    public int minimax(char[][] grid, int depth, boolean isMaximizing) {
//        if (isWinner(grid) == 1 || isWinner(grid) == 2 || depth == 0) {
//            return eval.Utility(grid);
//        }
//
//        if (isMaximizing) {
//            ArrayList<Integer> vCol = new ArrayList<Integer>();
//            vCol = validColumns(grid);
//            int bestScore = Integer.MIN_VALUE;
//            int score;
//            int row;
//            for (int col = 0; col < vCol.size(); col++) {
//                row = validRow(grid, vCol.get(col));
//                //if (grid[row][col] != 'W' && grid[row][col] != 'B') {
//                grid[row][col] = AIcolor;
//                score = minimax(grid, depth - 1, false);
//                grid[row][col] = ' ';
//                bestScore = Math.max(score, bestScore);
//                //}
//            }
//            return bestScore;
//        } else {
//            ArrayList<Integer> vaCol = new ArrayList<Integer>();
//            vaCol = validColumns(grid);
//            int bstScore = Integer.MAX_VALUE;
//            int scor;
//            int row;
//            for (int col = 0; col < vaCol.size(); col++) {
//                row = validRow(grid, vaCol.get(col));
//                // if (grid[row][col] != 'W' && grid[row][col] != 'B') {
//                grid[row][col] = player;
//                scor = minimax(grid, depth - 1, true);
//                grid[row][col] = ' ';
//                bstScore = Math.min(scor, bstScore);
//                // }
//            }
//            return bstScore;
//        }
//    }
//slower but parang mas safe moves?
    public int minimax(char[][] grid, int depth) {
        char[][] copy = new char[6][7];
        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy[i].length; j++) {
                copy[i][j] = grid[i][j];
            }
        }
        int bestScore = Integer.MIN_VALUE;
        int bestMove = 0;
        int row;
        int score;
        ArrayList<Integer> validCol = new ArrayList<Integer>();
        validCol = validColumns(copy);
        for (int col = 0; col < validCol.size(); col++) {
            row = validRow(copy, validCol.get(col));
            copy[row][col] = AIcolor;
            score = minValue(copy, depth);
            copy[row][col] = ' ';
            if (score > bestScore) {
                bestScore = score;
                bestMove = col;
            }
        }

        return bestMove;
    }

    public int maxValue(char[][] grid, int depth) {
        if (terminalTest(grid) || depth == 0) {
            return eval.Utility(grid);
        }
        ArrayList<Integer> validCol = new ArrayList<Integer>();
        int v = Integer.MIN_VALUE;
        int row;
        int score;
        validCol = validColumns(grid);
        for (int col = 0; col < validCol.size(); col++) {
            row = validRow(grid, validCol.get(col));
            grid[row][col] = AIcolor;
            score = minValue(grid, depth);
            v = Math.max(score, v);
            grid[row][col] = ' ';
        }
        return v;
    }
    
    public int minValue(char[][] grid, int depth) {
        if (terminalTest(grid) || depth == 0) {
            return eval.Utility(grid);
        }
        ArrayList<Integer> validCol = new ArrayList<Integer>();
        int v = Integer.MAX_VALUE;
        int row;
        int score;
        validCol = validColumns(grid);
        for (int col = 0; col < validCol.size(); col++) {
            row = validRow(grid, validCol.get(col));
            grid[row][col] = player;
            score = maxValue(grid, depth - 1);
            v = Math.min(score, v);
            grid[row][col] = ' ';
        }
        return v;
    }

//    public int decideMove(char[][] grid, int depth) {
//        char[][] copy = new char[6][7];
//        for (int i = 0; i < copy.length; i++) {
//            for (int j = 0; j < copy[i].length; j++) {
//                copy[i][j] = grid[i][j];
//            }
//        }    
//        int bestScore = Integer.MIN_VALUE;
//        int bestMove = 0;
//        int row;
//        int score;
//        ArrayList<Integer> validCol = new ArrayList<Integer>();
//        validCol = validColumns(copy);
//        for (int col = 0; col < validCol.size(); col++) {
//            row = validRow(copy, validCol.get(col));
//            copy[row][col] = AIcolor;
//            score = minimizer(copy, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
//            copy[row][col] = ' ';
//            if (score > bestScore) {
//                bestScore = score;
//                bestMove = col;
//            }
//        }
//
//        return bestMove;
//    }
//
//    public int maximizer(char[][] grid, int depth, int alpha, int beta) {
//        if (terminalTest(grid) || depth == 0) {
//            return eval.Utility(grid);
//        }
//
//        ArrayList<Integer> validCol = new ArrayList<Integer>();
//        validCol = validColumns(grid);
//        int row;
//        int rating = Integer.MIN_VALUE;
//        for (int col = 0; col < validCol.size(); col++) {
//            row = validRow(grid, validCol.get(col));
//            grid[row][col] = AIcolor;
//            rating = Math.max(rating, minimizer(grid, depth - 1, alpha, beta));
//            grid[row][col] = ' ';
//
////            if (rating >= beta) {
////                return rating;
////            } else {
////                alpha = Math.max(alpha, rating);
////                best = col;
////            }
//            if (rating > alpha) {
//                alpha = rating;
//            }
//            if (alpha >= beta) {
//                return alpha;
//            }
//            
//        }
//        return alpha;
//    }
//
//    public int minimizer(char[][] grid, int depth, int alpha, int beta) {
//        if (terminalTest(grid) || depth == 0) {
//            return eval.Utility(grid);
//        }
//
//        ArrayList<Integer> validCol = new ArrayList<Integer>();
//        validCol = validColumns(grid);
//        int row;
//        int rating = Integer.MAX_VALUE;
//        for (int col = 0; col < validCol.size(); col++) {
//            row = validRow(grid, validCol.get(col));
//            grid[row][col] = player;
//            rating = Math.min(rating, maximizer(grid, depth - 1, alpha, beta));
//            grid[row][col] = ' ';
//
////            if (rating <= alpha) {
////                return rating;
////            } else {
////                beta = Math.min(beta, rating);
////            }
//            if (rating <= beta) {
//                beta = rating;
//            }
//            if (alpha >= beta) {
//                return beta;
//            }
//        }
//        return beta;
//    }

    public boolean terminalTest(char[][] grid) {
        if (isWinner(grid) == 1) {
            return true;
        } else if (isWinner(grid) == 2) {
            return true;
        } else if (counter == 42) {
            return true;
        }
        return false;
    }

    public ArrayList<Integer> validColumns(char[][] grid) {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for (int col = 0; col < grid[0].length; col++) {
            if (grid[0][col] != 'W' && grid[0][col] != 'B') {
                arr.add(col);
            }
        }
        return arr;
    }

    public int validRow(char[][] grid, int col) {
        int rw = 0;
        for (int row = grid.length - 1; row >= 0; row--) {
            if (grid[row][col] != 'W' && grid[row][col] != 'B') {
                rw = row;
                break;
            }
        }
        return rw;
    }

    public void dropPiece(char[][] grid, int row, int col, char piece) {
        grid[row][col] = piece;
    }

    public int isWinner(char[][] grid) {
        if (player == 'W') {
            //check for 4 across
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'B' && grid[row][col + 1] == 'B' && grid[row][col + 2] == 'B' && grid[row][col + 3] == 'B') {
                        return 2;
                    }
                }
            }
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'W' && grid[row][col + 1] == 'W' && grid[row][col + 2] == 'W' && grid[row][col + 3] == 'W') {
                        return 1;
                    }
                }
            }
            //check for 4 up and down
            for (int row = 0; row < grid.length - 3; row++) {
                for (int col = 0; col < grid[0].length; col++) {
                    if (grid[row][col] == 'B' && grid[row + 1][col] == 'B' && grid[row + 2][col] == 'B' && grid[row + 3][col] == 'B') {
                        return 2;
                    } else if (grid[row][col] == 'W' && grid[row + 1][col] == 'W' && grid[row + 2][col] == 'W' && grid[row + 3][col] == 'W') {
                        return 1;
                    }
                }
            }
            //check upward diagonal
            for (int row = 3; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'B' && grid[row - 1][col + 1] == 'B' && grid[row - 2][col + 2] == 'B' && grid[row - 3][col + 3] == 'B') {
                        return 2;
                    }
                }
            }
            for (int row = 3; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'W' && grid[row - 1][col + 1] == 'W' && grid[row - 2][col + 2] == 'W' && grid[row - 3][col + 3] == 'W') {
                        return 1;
                    }
                }
            }
            //check downward diagonal
            for (int row = 0; row < grid.length - 3; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'B' && grid[row + 1][col + 1] == 'B' && grid[row + 2][col + 2] == 'B' && grid[row + 3][col + 3] == 'B') {
                        return 2;
                    }
                }
            }
            for (int row = 0; row < grid.length - 3; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'W' && grid[row + 1][col + 1] == 'W' && grid[row + 2][col + 2] == 'W' && grid[row + 3][col + 3] == 'W') {
                        return 1;
                    }
                }
            }
        } else if (player == 'B') {
            //check for 4 across
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'W' && grid[row][col + 1] == 'W' && grid[row][col + 2] == 'W' && grid[row][col + 3] == 'W') {
                        return 1;
                    }
                }
            }
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'B' && grid[row][col + 1] == 'B' && grid[row][col + 2] == 'B' && grid[row][col + 3] == 'B') {
                        return 2;
                    }
                }
            }
            //check for 4 up and down
            for (int row = 0; row < grid.length - 3; row++) {
                for (int col = 0; col < grid[0].length; col++) {
                    if (grid[row][col] == 'W' && grid[row + 1][col] == 'W' && grid[row + 2][col] == 'W' && grid[row + 3][col] == 'W') {
                        return 1;
                    } else if (grid[row][col] == 'B' && grid[row + 1][col] == 'B' && grid[row + 2][col] == 'B' && grid[row + 3][col] == 'B') {
                        return 2;
                    }
                }
            }
            //check upward diagonal
            for (int row = 3; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'W' && grid[row - 1][col + 1] == 'W' && grid[row - 2][col + 2] == 'W' && grid[row - 3][col + 3] == 'W') {
                        return 1;
                    }
                }
            }
            for (int row = 3; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'B' && grid[row - 1][col + 1] == 'B' && grid[row - 2][col + 2] == 'B' && grid[row - 3][col + 3] == 'B') {
                        return 2;
                    }
                }
            }
            //check downward diagonal
            for (int row = 0; row < grid.length - 3; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'W' && grid[row + 1][col + 1] == 'W' && grid[row + 2][col + 2] == 'W' && grid[row + 3][col + 3] == 'W') {
                        return 1;
                    }
                }
            }
            for (int row = 0; row < grid.length - 3; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'B' && grid[row + 1][col + 1] == 'B' && grid[row + 2][col + 2] == 'B' && grid[row + 3][col + 3] == 'B') {
                        return 2;
                    }
                }
            }
        }
        return 0;
    }
}
