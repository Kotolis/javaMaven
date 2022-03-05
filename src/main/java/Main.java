
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static int GetIndex() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите индекс ячейки");
        String indexElement = scanner.nextLine();
        int result = 0;
        try {
            int intIndex = Integer.parseInt(indexElement);
            if (intIndex > 10) {
                System.out.println("Указан индекс больше 10. Количество элементов - 10. Укажите число в правильном диапазоне ");
                result = GetIndex();
            } else {
                result = intIndex;
            }
        } catch (NumberFormatException e) {
            System.out.println("Необходимо указать число");
            result = GetIndex();
        }
        return result;
    }

    public static boolean IsEmpty(List<String> storage) {
        for (int i = 0; i < 10; i++) {
            if (!storage.get(i).equals("Пустой")) {
                return false;
            }
        }
        return true;
    }

    public static boolean Clone(List<String> storage, String NewElement) {
        Scanner scanner = new Scanner(System.in);
        if (storage.contains(NewElement)) {
            System.out.println("Элемент уже находится в ячейке " + storage.indexOf(NewElement) + " добавить эту позицию?(Y|N)");
            String Answer = scanner.nextLine();
            if (Answer.equals("N")) {
                System.out.println("Элемент не добавлен");
                return false;
            }
            if (Answer.equals("Y")) {
                System.out.println("Элемент снова добавлен");
                return true;
            } else {
                System.out.println("Введите (Y|N)");
                return Clone(storage, NewElement);
            }
        } else {
            return true;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<String> storage = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            storage.add(i, "Пустой");
        }
        System.out.println(storage);

        boolean flag = true;
        while (flag) {
            System.out.println("Введите запрос");
            Scanner scanner = new Scanner(System.in);
            String request = scanner.nextLine();

            if (request.equals("add")) {
                System.out.println(storage);
                boolean fullness = false;
                for (int i = 0; i < 10; i++) {
                    if (storage.get(i).equals("Пустой")) {
                        break;
                    } else {
                        fullness = true;
                    }
                }
                if (!fullness) {
                    int index = GetIndex();
                    if (!storage.get(index).equals("Пустой")) {
                        System.out.println("Эта ячейка уже занята");
                    } else {

                        System.out.println("Напишите элемент для добавления");
                        String NewElement = scanner.nextLine();
                        if (Clone(storage, NewElement)) {
                            storage.set(index, NewElement);
                            System.out.println("Элемент " + NewElement + " добавлен");
                            System.out.println(storage);
                        }
                    }
                }

            }
            if (request.equals("delete")) {
                System.out.println(storage);
                boolean emptyty = IsEmpty(storage);
                if (emptyty) {
                    System.out.println("Склад пустой. Удаление элемента не доступно");
                } else {
                    int index = GetIndex();
                    if (storage.get(index).equals("Пустой")) {
                        System.out.println("Ячейка уже пуста");
                    } else {
                        String OldElement = storage.get(index);
                        storage.set(index, "Пустой");
                        System.out.println("Элемент " + OldElement + " удален");
                        System.out.println(storage);
                    }
                }
            }
            if (request.equals("stop")) {
                flag = false;
            }
            if (request.equals("search")) {
                System.out.println(storage);
                boolean emptyty = IsEmpty(storage);
                if (emptyty) {
                    System.out.println("Склад пустой. Поиск не доступен");
                } else {
                    System.out.println("Введите искомый элемент");
                    String Item = scanner.nextLine();
                    int count = 0;
                    for (int i = 0; i < 10; i++) {
                        if (storage.get(i).equals(Item)) {
                            System.out.println("Элемент имеет индекс " + i);
                            count += 1;
                        }
                    }
                    if (count == 0) {
                        System.out.println("Элемента нет на складе");
                    }
                }
            }
            if (request.equals("doEx")) {
                Workbook storeBook = new XSSFWorkbook();
                FileOutputStream exelDoc = new FileOutputStream(new File("./result.xlsx"));
                Sheet sheet = storeBook.createSheet("items");
                for (int i = 0; i < storage.size(); i++) {
                    if (storage.get(i) != null) {
                        Row rowI = sheet.createRow(i);
                        Cell item = rowI.createCell(0);
                        item.setCellValue(storage.get(i));
                    }
                }
                try {
                    storeBook.write(exelDoc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    exelDoc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

    }
}
