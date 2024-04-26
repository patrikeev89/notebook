package notebook;

// public class NotebookStore {
    
// }
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

class Notebook {
    private int ram;
    private int hddSize;
    private String os;
    private String color;

    public Notebook(int ram, int hddSize, String os, String color) {
        this.ram = ram;
        this.hddSize = hddSize;
        this.os = os;
        this.color = color;
    }

    public int getRam() {
        return ram;
    }

    public int getHddSize() {
        return hddSize;
    }

    public String getOs() {
        return os;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Notebook [RAM=" + ram + "GB, HDD=" + hddSize + "GB, OS=" + os + ", Color=" + color + "]";
    }
}

public class NotebookStore {
    private static final Map<Integer, String> filterCriteria = new HashMap<>();
    private static final Map<String, Integer> filterValues = new HashMap<>();

    static {
        filterCriteria.put(1, "RAM");
        filterCriteria.put(2, "HDD Size");
        filterCriteria.put(3, "Operating System");
        filterCriteria.put(4, "Color");
    }

    public static void main(String[] args) {
        List<Notebook> notebooks = createNotebookSet();
        Scanner scanner = new Scanner(System.in);
        boolean shouldContinue = true;

        while (shouldContinue) {
            System.out.println("Введите цифру, соответствующую необходимому критерию фильтрации:");
            for (Map.Entry<Integer, String> entry : filterCriteria.entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
            System.out.println("0 - Выход");

            int choice = scanner.nextInt();

            if (choice == 0) {
                shouldContinue = false;
            } else if (filterCriteria.containsKey(choice)) {
                System.out.print("Введите минимальное значение для " + filterCriteria.get(choice) + ": ");
                int filterValue = scanner.nextInt();
                filterValues.put(filterCriteria.get(choice), filterValue);
            } else {
                System.out.println("Неверный выбор. Попробуйте еще раз.");
            }
        }

        scanner.close();

        List<Notebook> filteredNotebooks = filterNotebooks(notebooks);
        System.out.println("Отфильтрованные ноутбуки:");
        filteredNotebooks.forEach(System.out::println);
    }

    private static List<Notebook> createNotebookSet() {
        List<Notebook> notebooks = new ArrayList<>();
        notebooks.add(new Notebook(8, 512, "Windows 10", "Black"));
        notebooks.add(new Notebook(16, 1024, "macOS", "Silver"));
        notebooks.add(new Notebook(4, 256, "Linux", "White"));
        notebooks.add(new Notebook(8, 512, "Windows 11", "Blue"));
        notebooks.add(new Notebook(16, 1024, "macOS", "Space Gray"));
        return notebooks;
    }

    private static List<Notebook> filterNotebooks(List<Notebook> notebooks) {
        return notebooks.stream()
                .filter(notebook -> filterValues.getOrDefault("RAM", 0) <= notebook.getRam())
                .filter(notebook -> filterValues.getOrDefault("HDD Size", 0) <= notebook.getHddSize())
                .filter(notebook -> filterValues.containsKey("Operating System")
                        ? notebook.getOs().equals(filterValues.get("Operating System"))
                        : true)
                .filter(notebook -> filterValues.containsKey("Color")
                        ? notebook.getColor().equalsIgnoreCase(filterValues.get("Color").toString())
                        : true)
                .collect(Collectors.toList());
    }
}