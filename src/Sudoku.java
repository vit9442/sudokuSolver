import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Класс будет представлять из себя поле судоку.
public class Sudoku {
    private List<Integer> grid;     //Само поле судоку.

    public Sudoku(List<Integer> grid) {
        this.grid = grid;
    }

    // Конструктор для считывания из файла
    public Sudoku(String filename) throws IOException {
        System.out.println("Создаётся конструктор считывающий схему судоку из файла " + filename );
        grid = new ArrayList<Integer>();
        File file = new File(filename);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while (bufferedReader.ready()) {
            String str[] = bufferedReader.readLine().split("[ ,]+");
            for (String str1 : str)
                grid.add(Integer.parseInt(str1));
        }
        bufferedReader.close();
    }

    //Получаем index в нашем grid по номеру строки и столбца
    private int getIndex(int row, int column) {
        return row * 9 + column;
    }

    //Возвращает true, если схема судоку валидная, false в ином случае
    /*
    Нужно сделать 3 проверки:
    1. Проверка уникальности чисел в каждой строке
    2. Проверка уникальности чисел в каждом столбце
    3. Проверка уникальности чисел в каждом квадрате 3х3

     */
    public boolean isValid() {
        //1, 2. Проверка уникальности по строкам и столбцам.
        for(int i = 0; i < 9; ++i) {
            Set<Integer> numbersInRow = new HashSet<>();
            Set<Integer> numbersInColumn = new HashSet<>();
            for(int j = 0; j < 9; ++j) {
                int numberInRow = grid.get(getIndex(i, j));
                int numberInColumn = grid.get(getIndex(j, i));
                if(numberInRow != 0) {
                    //Возникло повторение
                    if(numbersInRow.contains(numberInRow)) {
                        return false;
                    }
                    numbersInRow.add(numberInRow);
                }
                if(numberInColumn != 0) {
                    //Возникло повторение
                    if(numbersInColumn.contains(numberInColumn)) {
                        return false;
                    }
                    numbersInColumn.add(numberInColumn);
                }
            }
        }

        //3. Проверка уникальности внутри квадратов 3х3
        for(int i = 0; i < 9; i += 3) {
            for(int j = 0; j < 9; j += 3) {
                //На каждом шаге мы имеем какой-то левый верхний угол квадрата 3х3.
                Set<Integer> numbersInSquare = new HashSet<>();
                //Перебираем всевозможные клетки внутри квадрата
                for(int deltaRow = 0; deltaRow < 3; deltaRow++) {
                    for(int deltaColumn = 0; deltaColumn < 3; deltaColumn++) {
                        int row = i + deltaRow;
                        int column = j + deltaColumn;
                        int number = grid.get(getIndex(row, column));
                        if(number != 0) {
                            if(numbersInSquare.contains(number)) {
                                return false;
                            }
                            numbersInSquare.add(number);
                        }
                    }
                }
            }
        }
        return true;
    }

    //Даёт нам значение, которое лежит в позиции row, column
    public int get(int row, int column) {
        int index = getIndex(row, column);
        return grid.get(index);
    }

    //Устанавливает в позиции row, column значение value
    public void set(int row, int column, int value) {
        int index = getIndex(row, column);
        grid.set(index, value);
    }

    // Выводит схему судоку в файл
    public void writeToFile(String fileName) throws IOException {
        System.out.println("Метод writeToFile, который записывает схему судоку в файл " + fileName);

        File file = new File(fileName);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (int i = 0; i < grid.size(); i++) {
            if (i % 9 == 0 && i !=0)
                bufferedWriter.write("\n");
            bufferedWriter.write(grid.get(i) + ", ");
        }
        bufferedWriter.close();
    }

}
