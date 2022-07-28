
public class volna {

//очистка командной строки
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  

//копирование 2-мерного массива
    public static int[][] copyArr(int[][] arr) {
        int[][] result = new int[arr[0].length][arr[1].length];
        for (int i = 0; i < result[0].length; i++) {
            for (int j = 0; j < result[1].length; j++) {
                result[i][j] = arr[i][j];
            }        
        }   
        return result;
    }

// визуализация поля
    public static void printPole(int[][] pole, int fRow, int fCol) {
        int[][] arr = copyArr(pole);
        arr[fRow][fCol] = -2;
        for (int[] is : arr) {
            System.out.print("|");
            for (int is2 : is) {
                if (is2 == 0) {
                    System.out.print(" |");
                } else if (is2 == -1) {
                    System.out.print("\u001B[33mS\u001B[37m|");
                } else if (is2 == -2) {
                    System.out.print("\u001B[33mF\u001B[37m|");
                } else if (is2 == -3) {
                    System.out.print("\u001B[31mL\u001B[37m|");
                } else {
                    System.out.print(is2+"|");
                }
            }
            System.out.println();
        }
    }

//поиск пути от произвольной клетки

    public static int[][] findLet(int[][] let, int[][] pole, int fRow, int fCol, int finRow, int finCol) {
        int[][] temp = copyArr(pole);
        int step = pole[fRow][fCol];
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isPole(temp[0].length, temp[1].length, fRow+i, fCol+j)) {
                    if (temp[fRow+i][fCol+j] == step-1) {
                        let[fRow+i][fCol+j] = step-1;
                        findLet(let, temp, fRow+i, fCol+j, finRow, finCol);
                        let[fRow+i][fCol+j] = 0;
                    } else if (temp[fRow+i][fCol+j] == -1) {
                        let[fRow+i][fCol+j] = -1;
                        printResult(pole, let, finRow, finCol);
                        try {    
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            System.out.println("got interrupted!");
                        }
                    }
                }
            }
        }
        //printPole(let,fRow,fCol);
        return let;
    }

//визуализация результата
    public static void printResult(int[][] pole, int[][] let, int fRow, int fCol) {
        clearScreen();
        int[][] arr = copyArr(pole);
        arr[fRow][fCol] = -2;
        for (int i = 0; i < let[0].length; i++) {
            System.out.print("|");
            for (int j = 0; j < let[1].length; j++) {
                if (arr[i][j] == 0) {
                    System.out.print(" |");
                } else if (arr[i][j] == -1) {
                    System.out.print("\u001B[33mS\u001B[37m|");
                } else if (arr[i][j] == -2) {
                    System.out.print("\u001B[33mF\u001B[37m|");
                } else if (arr[i][j] == -3) {
                    System.out.print("\u001B[31mL\u001B[37m|");
                } else {
                    if (let[i][j] > 0) {
                        System.out.print(String.format("\u001B[30m\u001B[42m%d\u001B[40m\u001B[37m|", arr[i][j]));
                    } else {
                        System.out.print(arr[i][j]+"|");
                    }
                }
            }
            System.out.println();
        }
    }

// условие нахождения в поле
    public static boolean isPole(int n, int m, int i, int j) {
        if ((i >= 0 && j >= 0) && ( i < n && j < m)) {
            return true;
        } else {
            return false;
        }
    }

// перемещение волны
    public static int[][] steps(int[][] arr, int sRow, int sCol, int count) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isPole(arr[0].length, arr[1].length, sRow+i, sCol+j)) {
                    if (arr[sRow+i][sCol+j] == 0) {
                        arr[sRow+i][sCol+j] = count;
                        steps(arr, sRow+i, sCol+j, count+1);
                    } else if (count < arr[sRow+i][sCol+j]) {
                        arr[sRow+i][sCol+j] = count;
                        steps(arr, sRow+i, sCol+j, count+1);
                    }
                }
            }
        }
        return arr;
    }

// основной блок
    public static void main(String[] args) {
        clearScreen();
        int n = 10; // размер поля
        int m = 10; // 
        int[][] pole = new int[n][m];
        int sRow = 2;
        int sCol = 2;
        int fRow = 9;
        int fCol = 8;
        pole[sRow][sCol] = -1; // начало
        pole[6][4] = -3; // препятствие
        pole[6][5] = -3;
        pole[5][5] = -3;
        pole[5][6] = -3;
        pole = steps(pole, sRow, sCol, 1);
        //printPole(pole, fRow, fCol);
        int[][] let = new int[n][m];
        let = findLet(let, pole, fRow, fCol, fRow, fCol);
        System.out.println(String.format("Наименьшее количество ходов - %d", pole[fRow][fCol]));
        //printPole(let, fRow, fCol);
    }
}