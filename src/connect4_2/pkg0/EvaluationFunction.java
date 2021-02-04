package connect4_2.pkg0;

import static connect4_2.pkg0.Connect4_20.AIcolor;
import static connect4_2.pkg0.Connect4_20.isWinner;
import static connect4_2.pkg0.Connect4_20.player;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Ched
 */
public class EvaluationFunction {

    private static int[][] evaluationTbl = {
        {3, 4, 5, 7, 5, 4, 3},
        {4, 6, 8, 10, 8, 6, 4},
        {5, 8, 11, 13, 11, 8, 5},
        {5, 8, 11, 13, 11, 8, 5},
        {4, 6, 8, 10, 8, 6, 4},
        {3, 4, 5, 7, 5, 4, 3}
    };

    public int Utility(char[][] temp) {
        //int utility = 138;
        int score = 0;
        if (AIcolor == 'W') {
            for (int row = 0; row < temp.length; row++) {
                for (int col = 0; col < temp[row].length; col++) {
                    if (temp[row][col] == 'W') {
                        score += evaluationTbl[row][col];
                    } else if (temp[row][col] == 'B') {
                        score -= evaluationTbl[row][col];
                    }
                }
            }
            for (int row = 0; row < temp.length; row++) {//HORIZONTAL
                for (int col = 0; col < temp[0].length - 3; col++) {
                    if (temp[row][col] == 'W' && temp[row][col + 1] == 'W' && temp[row][col + 2] == 'W' && temp[row][col + 3] == 'W') {
                        score += 9999;
                    } else if ((temp[row][col] != 'W' && temp[row][col] != 'B') && temp[row][col + 1] == 'W' && temp[row][col + 2] == 'W' && temp[row][col + 3] == 'W') {
                        score += 400;
                    } else if (temp[row][col] == 'W' && temp[row][col + 1] == 'W' && temp[row][col + 2] == 'W' && (temp[row][col + 3] != 'W' && temp[row][col + 3] != 'B')) {
                        score += 400;
                    } else if (temp[row][col] == 'W' && (temp[row][col + 1] != 'W' && temp[row][col + 1] != 'B') && temp[row][col + 2] == 'W' && temp[row][col + 3] == 'W') {
                        score += 200;
                    } else if (temp[row][col] == 'W' && temp[row][col + 1] == 'W' && (temp[row][col + 2] != 'W' && temp[row][col + 2] != 'B') && temp[row][col + 3] == 'W') {
                        score += 200;
                    } else if (temp[row][col] == 'B' && temp[row][col + 1] == 'B' && temp[row][col + 2] == 'B' && temp[row][col + 3] == 'B') {
                        score -= 9999;
                    } else if ((temp[row][col] != 'W' && temp[row][col] != 'B') && temp[row][col + 1] == 'B' && temp[row][col + 2] == 'B' && temp[row][col + 3] == 'B') {
                        score -= 400;
                    } else if (temp[row][col] == 'B' && temp[row][col + 1] == 'B' && temp[row][col + 2] == 'B' && (temp[row][col + 3] != 'W' && temp[row][col + 3] != 'B')) {
                        score -= 400;
                    } else if (temp[row][col] == 'B' && (temp[row][col + 1] != 'W' && temp[row][col + 1] != 'B') && temp[row][col + 2] == 'B' && temp[row][col + 3] == 'B') {
                        score -= 200;
                    } else if (temp[row][col] == 'B' && temp[row][col + 1] == 'B' && (temp[row][col + 2] != 'W' && temp[row][col + 2] != 'B') && temp[row][col + 3] == 'B') {
                        score -= 200;
                    }
                }
            }
            for (int row = 0; row < temp.length - 3; row++) {//VERTICALLY
                for (int col = 0; col < temp[0].length; col++) {
                    if (temp[row][col] == 'W' && temp[row + 1][col] == 'W' && temp[row + 2][col] == 'W' && temp[row + 3][col] == 'W') {
                        score += 9999;
                    } else if ((temp[row][col] != 'W' && temp[row][col] != 'B') && temp[row + 1][col] == 'W' && temp[row + 2][col] == 'W' && temp[row + 3][col] == 'W') {
                        score += 400;
                    } else if (temp[row][col] == 'B' && temp[row + 1][col] == 'B' && temp[row + 2][col] == 'B' && temp[row + 3][col] == 'B') {
                        score -= 9999;
                    } else if ((temp[row][col] != 'W' && temp[row][col] != 'B') && temp[row + 1][col] == 'B' && temp[row + 2][col] == 'B' && temp[row + 3][col] == 'B') {
                        score -= 400;
                    }
                }
            }
            for (int row = 3; row < temp.length; row++) {//UPWARD DIAGONAL
                for (int col = 0; col < temp[0].length - 3; col++) {
                    if (temp[row][col] == 'W' && temp[row - 1][col + 1] == 'W' && temp[row - 2][col + 2] == 'W' && temp[row - 3][col + 3] == 'W') {
                        score += 9999;
                    }/* else if ((temp[row][col] != 'W' && temp[row][col] != 'B') && temp[row - 1][col + 1] == 'W' && temp[row - 2][col + 2] == 'W' && temp[row - 3][col + 3] == 'W') {
                        score += 200;
                    } else if (temp[row][col] == 'W' && (temp[row - 1][col + 1] != 'W' && temp[row - 1][col + 1] != 'B') && temp[row - 2][col + 2] == 'W' && temp[row - 3][col + 3] == 'W') {
                        score += 200;
                    } else if (temp[row][col] == 'W' && temp[row - 1][col + 1] == 'W' && (temp[row - 2][col + 2] != 'W' && temp[row - 2][col + 2] != 'B') && temp[row - 3][col + 3] == 'W') {
                        score += 200;
                    } else if (temp[row][col] == 'W' && temp[row - 1][col + 1] == 'W' && temp[row - 2][col + 2] == 'W' && (temp[row - 3][col + 3] != 'W' && temp[row - 3][col + 3] != 'B')) {
                        score += 200;
                    }*/ else if (temp[row][col] == 'B' && temp[row - 1][col + 1] == 'B' && temp[row - 2][col + 2] == 'B' && temp[row - 3][col + 3] == 'B') {
                        score -= 9999;
                    }/* else if ((temp[row][col] != 'W' && temp[row][col] != 'B') && temp[row - 1][col + 1] == 'B' && temp[row - 2][col + 2] == 'B' && temp[row - 3][col + 3] == 'B') {
                        score -= 200;
                    } else if (temp[row][col] == 'B' && (temp[row - 1][col + 1] != 'W' && temp[row - 1][col + 1] != 'B') && temp[row - 2][col + 2] == 'B' && temp[row - 3][col + 3] == 'B') {
                        score -= 200;
                    } else if (temp[row][col] == 'B' && temp[row - 1][col + 1] == 'B' && (temp[row - 2][col + 2] != 'W' && temp[row - 2][col + 2] != 'B') && temp[row - 3][col + 3] == 'B') {
                        score -= 200;
                    } else if (temp[row][col] == 'B' && temp[row - 1][col + 1] == 'B' && temp[row - 2][col + 2] == 'B' && (temp[row - 3][col + 3] != 'W' && temp[row - 3][col + 3] != 'B')) {
                        score -= 200;
                    }*/
                }
            }
            for (int row = 0; row < temp.length - 3; row++) {
                for (int col = 0; col < temp[0].length - 3; col++) {
                    if (temp[row][col] == 'W' && temp[row + 1][col + 1] == 'W' && temp[row + 2][col + 2] == 'W' && temp[row + 3][col + 3] == 'W') {
                        score += 9999;
                    } /*else if ((temp[row][col] != 'W' && temp[row][col] != 'B') && temp[row + 1][col + 1] == 'W' && temp[row + 2][col + 2] == 'W' && temp[row + 3][col + 3] == 'W') {
                        score += 200;
                    } else if (temp[row][col] == 'W' && (temp[row+1][col+1] != 'W' && temp[row+1][col+1] != 'B') && temp[row + 2][col + 2] == 'W' && temp[row + 3][col + 3] == 'W') {
                        score += 200;
                    } else if (temp[row][col] == 'W' && temp[row + 1][col + 1] == 'W' && (temp[row+2][col+2] != 'W' && temp[row+2][col+2] != 'B') && temp[row + 3][col + 3] == 'W') {
                        score += 200;
                    } else if (temp[row][col] == 'W' && temp[row + 1][col + 1] == 'W' && temp[row + 2][col + 2] == 'W' && (temp[row+3][col+3] != 'W' && temp[row+3][col+3] != 'B')) {
                        score += 200;
                    }*/ else if (temp[row][col] == 'B' && temp[row + 1][col + 1] == 'B' && temp[row + 2][col + 2] == 'B' && temp[row + 3][col + 3] == 'B') {
                        score -= 9999;
                    }
                    /*else if ((temp[row][col] != 'W' && temp[row][col] != 'B') && temp[row + 1][col + 1] == 'B' && temp[row + 2][col + 2] == 'B' && temp[row + 3][col + 3] == 'B') {
                        score -= 200;
                    } else if (temp[row][col] == 'B' && (temp[row+1][col+1] != 'W' && temp[row+1][col+1] != 'B') && temp[row + 2][col + 2] == 'B' && temp[row + 3][col + 3] == 'B') {
                        score -= 200;
                    } else if (temp[row][col] == 'B' && temp[row + 1][col + 1] == 'B' && (temp[row+2][col+2] != 'W' && temp[row+2][col+2] != 'B') && temp[row + 3][col + 3] == 'B') {
                        score -= 200;
                    } else if (temp[row][col] == 'B' && temp[row + 1][col + 1] == 'B' && temp[row + 2][col + 2] == 'B' && (temp[row+3][col+3] != 'W' && temp[row+3][col+3] != 'B')) {
                        score -= 200;
                    }*/
                }
            }
        } else if (AIcolor == 'B') {
            for (int row = 0; row < temp.length; row++) {
                for (int col = 0; col < temp[row].length; col++) {
                    if (temp[row][col] == 'B') {
                        score += evaluationTbl[row][col];
                    } else if (temp[row][col] == 'W') {
                        score -= evaluationTbl[row][col];
                    }
                }
            }
            for (int row = 0; row < temp.length; row++) {//HORIZONTAL
                for (int col = 0; col < temp[0].length - 3; col++) {
                    if (temp[row][col] == 'B' && temp[row][col + 1] == 'B' && temp[row][col + 2] == 'B' && temp[row][col + 3] == 'B') {
                        score += 9999;
                    } else if ((temp[row][col] != 'W' && temp[row][col] != 'B') && temp[row][col + 1] == 'B' && temp[row][col + 2] == 'B' && temp[row][col + 3] == 'B') {
                        score += 400;
                    } else if (temp[row][col] == 'B' && temp[row][col + 1] == 'B' && temp[row][col + 2] == 'B' && (temp[row][col + 3] != 'W' && temp[row][col + 3] != 'B')) {
                        score += 400;
                    } else if (temp[row][col] == 'B' && (temp[row][col + 1] != 'W' && temp[row][col + 1] != 'B') && temp[row][col + 2] == 'B' && temp[row][col + 3] == 'B') {
                        score += 200;
                    } else if (temp[row][col] == 'B' && temp[row][col + 1] == 'B' && (temp[row][col + 2] != 'W' && temp[row][col + 2] != 'B') && temp[row][col + 3] == 'B') {
                        score += 200;
                    } else if (temp[row][col] == 'W' && temp[row][col + 1] == 'W' && temp[row][col + 2] == 'W' && temp[row][col + 3] == 'W') {
                        score -= 9999;
                    } else if ((temp[row][col] != 'W' && temp[row][col] != 'B') && temp[row][col + 1] == 'W' && temp[row][col + 2] == 'W' && temp[row][col + 3] == 'W') {
                        score -= 400;
                    } else if (temp[row][col] == 'W' && temp[row][col + 1] == 'W' && temp[row][col + 2] == 'W' && (temp[row][col + 3] != 'W' && temp[row][col + 3] != 'B')) {
                        score -= 400;
                    } else if (temp[row][col] == 'W' && (temp[row][col + 1] != 'W' && temp[row][col + 1] != 'B') && temp[row][col + 2] == 'W' && temp[row][col + 3] == 'W') {
                        score -= 200;
                    } else if (temp[row][col] == 'W' && temp[row][col + 1] == 'W' && (temp[row][col + 2] != 'W' && temp[row][col + 2] != 'B') && temp[row][col + 3] == 'W') {
                        score -= 200;
                    }
                }
            }
            for (int row = 0; row < temp.length - 3; row++) {//VERTICALLY
                for (int col = 0; col < temp[0].length; col++) {
                    if (temp[row][col] == 'B' && temp[row + 1][col] == 'B' && temp[row + 2][col] == 'B' && temp[row + 3][col] == 'B') {
                        score += 9999;
                    } else if ((temp[row][col] != 'W' && temp[row][col] != 'B') && temp[row + 1][col] == 'B' && temp[row + 2][col] == 'B' && temp[row + 3][col] == 'B') {
                        score += 200;
                    } else if (temp[row][col] == 'W' && temp[row + 1][col] == 'W' && temp[row + 2][col] == 'W' && temp[row + 3][col] == 'W') {
                        score -= 9999;
                    } else if ((temp[row][col] != 'W' && temp[row][col] != 'B') && temp[row + 1][col] == 'W' && temp[row + 2][col] == 'W' && temp[row + 3][col] == 'W') {
                        score -= 200;
                    }
                }
            }
            for (int row = 3; row < temp.length; row++) {//UPWARD DIAGONAL
                for (int col = 0; col < temp[0].length - 3; col++) {
                    if (temp[row][col] == 'B' && temp[row - 1][col + 1] == 'B' && temp[row - 2][col + 2] == 'B' && temp[row - 3][col + 3] == 'B') {
                        score += 9999;
                    } else if (temp[row][col] == 'W' && temp[row - 1][col + 1] == 'W' && temp[row - 2][col + 2] == 'W' && temp[row - 3][col + 3] == 'W') {
                        score -= 9999;
                    }
                }
            }
            for (int row = 0; row < temp.length - 3; row++) {
                for (int col = 0; col < temp[0].length - 3; col++) {
                    if (temp[row][col] == 'B' && temp[row + 1][col + 1] == 'B' && temp[row + 2][col + 2] == 'B' && temp[row + 3][col + 3] == 'B') {
                        score += 9999;
                    } else if (temp[row][col] == 'W' && temp[row + 1][col + 1] == 'W' && temp[row + 2][col + 2] == 'W' && temp[row + 3][col + 3] == 'W') {
                        score -= 9999;
                    }
                }
            }
        }
//        return utility + score;
        return score;
    }

    public int eval(char[][] board) {
        int score = 0;
        if (AIcolor == 'W') {
            if (isWinner() == 1) {
                score += 9999;
            } else if (isWinner() == 2) {
                score -= 9999;
            }
        } else if (AIcolor == 'B') {
            if (isWinner() == 1) {
                score -= 9999;
            } else if (isWinner() == 2) {
                score += 9999;
            }
        }
        return score;
    }

    public int evaluationFunction(char[][] board) {

        if (isGoal(board, AIcolor)) {
            return 1000;
        }
        if (isGoal(board, player)) {
            return -1000;
        }

        return 0;
    }

    public boolean isGoal(char[][] board, char peice) {
        String find = "" + peice + "" + peice + "" + peice + "" + peice;

        for (int i = 0; i < board.length; i++) {
            if (String.valueOf(board[i]).contains(find)) {
                return true;
            }
        }

        for (int j = 0; j < board[0].length; j++) {
            String col = "";
            for (int i = 0; i < board.length; i++) {
                col += board[i][j];
            }

            if (col.contains(find)) {
                return true;
            }
        }

        ArrayList<Point> pos_right = new ArrayList<Point>();
        ArrayList<Point> pos_left = new ArrayList<Point>();

        for (int j = 0; j < board[0].length - 4 + 1; j++) {
            pos_right.add(new Point(0, j));
        }
        for (int j = 4 - 1; j < board[0].length; j++) {
            pos_left.add(new Point(0, j));
        }
        for (int i = 1; i < board.length - 4 + 1; i++) {
            pos_right.add(new Point(i, 0));
            pos_left.add(new Point(i, board[0].length - 1));
        }

        for (Point p : pos_right) {
            String d = "";
            int x = p.x, y = p.y;
            while (true) {
                if (x >= board.length || y >= board[0].length) {
                    break;
                }
                d += board[x][y];
                x += 1;
                y += 1;
            }
            if (d.contains(find)) {
                return true;
            }
        }

        for (Point p : pos_left) {
            String d = "";
            int x = p.x, y = p.y;
            while (true) {
                if (y < 0 || x >= board.length || y >= board[0].length) {
                    break;
                }
                d += board[x][y];
                x += 1;
                y -= 1;
            }
            if (d.contains(find)) {
                return true;
            }
        }
        return false;
    }

    public int evalFunc(char[][] temp) {
        int AI_fours = fourStreak(temp, AIcolor);
        int AI_threes = threeStreak(temp, AIcolor);
        int AI_twos = twoStreak(temp, AIcolor);

        int my_fours = fourStreak(temp, player);
        int my_threes = threeStreak(temp, player);
        int my_twos = twoStreak(temp, player);

        return (AI_fours + AI_threes + AI_twos) - (my_fours + my_threes + my_twos);

    }

    public int fourStreak(char[][] temp, char piece) {
        int count = 0;
        for (int row = 0; row < temp.length; row++) {//HORIZONTAL
            for (int col = 0; col < temp[0].length - 3; col++) {
                if (temp[row][col] == piece && temp[row][col + 1] == piece && temp[row][col + 2] == piece && temp[row][col + 3] == piece) {
                    count += 1000;
                }
            }
        }
        for (int row = 0; row < temp.length - 3; row++) {
            for (int col = 0; col < temp[0].length; col++) {
                if (temp[row][col] == piece && temp[row + 1][col] == piece && temp[row + 2][col] == piece && temp[row + 3][col] == piece) {
                    count += 1000;
                }
            }
        }
        for (int row = 3; row < temp.length; row++) {
            for (int col = 0; col < temp[0].length - 3; col++) {
                if (temp[row][col] == piece && temp[row - 1][col + 1] == piece && temp[row - 2][col + 2] == piece && temp[row - 3][col + 3] == piece) {
                    count += 1000;
                }
            }
        }
        for (int row = 0; row < temp.length - 3; row++) {
            for (int col = 0; col < temp[0].length - 3; col++) {
                if (temp[row][col] == piece && temp[row + 1][col + 1] == piece && temp[row + 2][col + 2] == piece && temp[row + 3][col + 3] == piece) {
                    count += 1000;
                }
            }
        }
        return count;
    }

    public int threeStreak(char[][] temp, char piece) {
        int count = 0;
        for (int row = 0; row < temp.length; row++) {//HORIZONTAL
            for (int col = 0; col < temp[0].length - 2; col++) {
                if (temp[row][col] == piece && temp[row][col + 1] == piece && temp[row][col + 2] == piece) {
                    count += 500;
                }
            }
        }
        for (int row = 0; row < temp.length - 2; row++) {
            for (int col = 0; col < temp[0].length; col++) {
                if (temp[row][col] == piece && temp[row + 1][col] == piece && temp[row + 2][col] == piece) {
                    count += 500;
                }
            }
        }
        for (int row = 2; row < temp.length; row++) {
            for (int col = 0; col < temp[0].length - 2; col++) {
                if (temp[row][col] == piece && temp[row - 1][col + 1] == piece && temp[row - 2][col + 2] == piece) {
                    count += 500;
                }
            }
        }
        for (int row = 0; row < temp.length - 2; row++) {
            for (int col = 0; col < temp[0].length - 2; col++) {
                if (temp[row][col] == piece && temp[row + 1][col + 1] == piece && temp[row + 2][col + 2] == piece) {
                    count += 500;
                }
            }
        }
        return count;
    }

    public int twoStreak(char[][] temp, char piece) {
        int count = 0;
        for (int row = 0; row < temp.length; row++) {//HORIZONTAL
            for (int col = 0; col < temp[0].length - 1; col++) {
                if (temp[row][col] == piece && temp[row][col + 1] == piece) {
                    count += 200;
                }
            }
        }
        for (int row = 0; row < temp.length - 1; row++) {
            for (int col = 0; col < temp[0].length; col++) {
                if (temp[row][col] == piece && temp[row + 1][col] == piece) {
                    count += 200;
                }
            }
        }
        for (int row = 1; row < temp.length; row++) {
            for (int col = 0; col < temp[0].length - 1; col++) {
                if (temp[row][col] == piece && temp[row - 1][col + 1] == piece) {
                    count += 200;
                }
            }
        }
        for (int row = 0; row < temp.length - 1; row++) {
            for (int col = 0; col < temp[0].length - 1; col++) {
                if (temp[row][col] == piece && temp[row + 1][col + 1] == piece) {
                    count += 200;
                }
            }
        }
        return count;
    }

//    public int isWinner(char[][] grid) {
//        if (player == 'W') {
//            //check for 4 across
//            for (int row = 0; row < grid.length; row++) {
//                for (int col = 0; col < grid[0].length - 3; col++) {
//                    if (grid[row][col] == 'B' && grid[row][col + 1] == 'B' && grid[row][col + 2] == 'B' && grid[row][col + 3] == 'B') {
//                        return 2;
//                    }
//                }
//            }
//            for (int row = 0; row < grid.length; row++) {
//                for (int col = 0; col < grid[0].length - 3; col++) {
//                    if (grid[row][col] == 'W' && grid[row][col + 1] == 'W' && grid[row][col + 2] == 'W' && grid[row][col + 3] == 'W') {
//                        return 1;
//                    }
//                }
//            }
//            //check for 4 up and down
//            for (int row = 0; row < grid.length - 3; row++) {
//                for (int col = 0; col < grid[0].length; col++) {
//                    if (grid[row][col] == 'B' && grid[row + 1][col] == 'B' && grid[row + 2][col] == 'B' && grid[row + 3][col] == 'B') {
//                        return 2;
//                    } else if (grid[row][col] == 'W' && grid[row + 1][col] == 'W' && grid[row + 2][col] == 'W' && grid[row + 3][col] == 'W') {
//                        return 1;
//                    }
//                }
//            }
//            //check upward diagonal
//            for (int row = 3; row < grid.length; row++) {
//                for (int col = 0; col < grid[0].length - 3; col++) {
//                    if (grid[row][col] == 'B' && grid[row - 1][col + 1] == 'B' && grid[row - 2][col + 2] == 'B' && grid[row - 3][col + 3] == 'B') {
//                        return 2;
//                    }
//                }
//            }
//            for (int row = 3; row < grid.length; row++) {
//                for (int col = 0; col < grid[0].length - 3; col++) {
//                    if (grid[row][col] == 'W' && grid[row - 1][col + 1] == 'W' && grid[row - 2][col + 2] == 'W' && grid[row - 3][col + 3] == 'W') {
//                        return 1;
//                    }
//                }
//            }
//            //check downward diagonal
//            for (int row = 0; row < grid.length - 3; row++) {
//                for (int col = 0; col < grid[0].length - 3; col++) {
//                    if (grid[row][col] == 'B' && grid[row + 1][col + 1] == 'B' && grid[row + 2][col + 2] == 'B' && grid[row + 3][col + 3] == 'B') {
//                        return 2;
//                    }
//                }
//            }
//            for (int row = 0; row < grid.length - 3; row++) {
//                for (int col = 0; col < grid[0].length - 3; col++) {
//                    if (grid[row][col] == 'W' && grid[row + 1][col + 1] == 'W' && grid[row + 2][col + 2] == 'W' && grid[row + 3][col + 3] == 'W') {
//                        return 1;
//                    }
//                }
//            }
//        } else if (player == 'B') {
//            //check for 4 across
//            for (int row = 0; row < grid.length; row++) {
//                for (int col = 0; col < grid[0].length - 3; col++) {
//                    if (grid[row][col] == 'W' && grid[row][col + 1] == 'W' && grid[row][col + 2] == 'W' && grid[row][col + 3] == 'W') {
//                        return 1;
//                    }
//                }
//            }
//            for (int row = 0; row < grid.length; row++) {
//                for (int col = 0; col < grid[0].length - 3; col++) {
//                    if (grid[row][col] == 'B' && grid[row][col + 1] == 'B' && grid[row][col + 2] == 'B' && grid[row][col + 3] == 'B') {
//                        return 2;
//                    }
//                }
//            }
//            //check for 4 up and down
//            for (int row = 0; row < grid.length - 3; row++) {
//                for (int col = 0; col < grid[0].length; col++) {
//                    if (grid[row][col] == 'W' && grid[row + 1][col] == 'W' && grid[row + 2][col] == 'W' && grid[row + 3][col] == 'W') {
//                        return 1;
//                    } else if (grid[row][col] == 'B' && grid[row + 1][col] == 'B' && grid[row + 2][col] == 'B' && grid[row + 3][col] == 'B') {
//                        return 2;
//                    }
//                }
//            }
//            //check upward diagonal
//            for (int row = 3; row < grid.length; row++) {
//                for (int col = 0; col < grid[0].length - 3; col++) {
//                    if (grid[row][col] == 'W' && grid[row - 1][col + 1] == 'W' && grid[row - 2][col + 2] == 'W' && grid[row - 3][col + 3] == 'W') {
//                        return 1;
//                    }
//                }
//            }
//            for (int row = 3; row < grid.length; row++) {
//                for (int col = 0; col < grid[0].length - 3; col++) {
//                    if (grid[row][col] == 'B' && grid[row - 1][col + 1] == 'B' && grid[row - 2][col + 2] == 'B' && grid[row - 3][col + 3] == 'B') {
//                        return 2;
//                    }
//                }
//            }
//            //check downward diagonal
//            for (int row = 0; row < grid.length - 3; row++) {
//                for (int col = 0; col < grid[0].length - 3; col++) {
//                    if (grid[row][col] == 'W' && grid[row + 1][col + 1] == 'W' && grid[row + 2][col + 2] == 'W' && grid[row + 3][col + 3] == 'W') {
//                        return 1;
//                    }
//                }
//            }
//            for (int row = 0; row < grid.length - 3; row++) {
//                for (int col = 0; col < grid[0].length - 3; col++) {
//                    if (grid[row][col] == 'B' && grid[row + 1][col + 1] == 'B' && grid[row + 2][col + 2] == 'B' && grid[row + 3][col + 3] == 'B') {
//                        return 2;
//                    }
//                }
//            }
//        }
//        return 0;
//    }
}
