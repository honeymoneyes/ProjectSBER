import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CityFunctions {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("task.csv");

        List<City> cities = parse(file);

        sortWithoutCase(cities);

        sortWithCase(cities);

        maxPopulation(cities);

        quantityOfCitiesByRegion(cities);

    }

    // Получение списка городов из списка task.csv
    public static List<City> parse(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(file)));
        List<City> cities = new ArrayList<>();
        while (sc.hasNextLine()) {
            String[] array = sc.nextLine().split(";", 6);
            if (array[5].isEmpty()) {
                array[5] = null;
            }
            cities.add(new City(Integer.parseInt(array[0]),array[1], array[2], array[3], Integer.parseInt(array[4]), array[5]));
        }
        sc.close();
        return cities;
    }
    // Сортировка списка городов по наименованию в
    // алфавитном порядке по убыванию без учета регистра;
    public static void sortWithoutCase(List<City> cities) {
        cities.stream()
                .sorted((s1,s2)-> s1.getName()
                        .compareToIgnoreCase(s2.getName()))
                .forEach(System.out::println);
    }

    // Сортировка списка городов по федеральному округу и наименованию города
    // внутри каждого федерального округа в алфавитном порядке по убыванию с
    // учетом регистра;
    public static void sortWithCase(List<City> cities) {
        cities.stream()
                .sorted(Comparator.comparing(City::getDistrict)
                        .thenComparing(City::getName))
                .forEach(System.out::println);
    }

    // Максимальная популяция в городе среди всех городов
    public static void maxPopulation(List<City> cities) {
        City maxPopulation = cities.stream()
                .max(Comparator.comparing(City::getPopulation))
                .get();
        System.out.format("%s = %s", maxPopulation.getId(), maxPopulation.getPopulation());
    }

    // Количество городов в каждом регионе.
    private static void quantityOfCitiesByRegion(List<City> cities) {
        cities.stream()
                .collect(Collectors.groupingBy(City::getRegion,Collectors.counting()))
                .forEach((region, count)-> System.out.println(region + " - " + count));
    }
}
