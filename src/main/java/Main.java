import org.apache.commons.csv.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

public class Main {
    private static final String CSV_FILE_PATH = "src/main/java/data_sekolah.csv";

    public static void main(String[] args) throws IOException {
        // Create instance I/O using Scanner
        Scanner scanner = new Scanner(System.in);

        // Menu options based on the selected condition
        while (true) {
            System.out.println("______________________________________________________________________________________");
            System.out.println("Aplikasi Pengolah Nilai Siswa");
            System.out.println("______________________________________________________________________________________");
            System.out.println("Letakan file csv dengan nama file data_sekolah di direktori berikut: c://tmp/direktori");
            System.out.println("Pilih menu:");
            System.out.println("1. Generate txt untuk menampilkan pengelompokan data");
            System.out.println("2. Generate txt untuk menampilkan statistik (mean, median, modus)");
            System.out.println("3. Generate kedua file");
            System.out.println("0. Exit");

            // Read the selected number and put to choice variable
            int choice = scanner.nextInt();

            scanner.nextLine();

            // conditions based on selected number/menu
            switch (choice) {
                case 1:
                    generateFrequencyFile();
                    break;
                case 2:
                    generateStatisticsFile();
                    break;
                case 3:
                    generateFrequencyFile();
                    generateStatisticsFile();
                    break;
                case 0:
                    System.out.println("Terimakasih! program pengolahan nilai siswa telah selesai.");
                    System.exit(0);
                default:
                    System.out.println("Opsi tidak valid. Silakan pilih kembali.");
            }
        }
    }

    // Function to load student score
    private static List<List<Integer>> loadStudentScores() {
        List<List<Integer>> scores = new ArrayList<>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ) {
            for (CSVRecord csvRecord : csvParser) {
                List<Integer> classScores = new ArrayList<>();
                for (int i = 1; i < csvRecord.size(); i++) {
                    classScores.add(Integer.parseInt(csvRecord.get(i)));
                }
                scores.add(classScores);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }

    private static void generateFrequencyFile() {
        List<List<Integer>> allScores = loadStudentScores();

        if (allScores.isEmpty()) {
            System.out.println("Tidak ada data untuk dihitung.");
            return;
        }

        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (List<Integer> classScores : allScores) {
            for (int score : classScores) {
                frequencyMap.put(score, frequencyMap.getOrDefault(score, 0) + 1);
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("src/main/java/output/frequency_data.txt"))) {
            writer.println("Frekuensi data berdasarkan jumlah nilai:");
            for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
                writer.println("Nilai: " + entry.getKey() + ", Frekuensi: " + entry.getValue());
            }
            System.out.println("File .txt Pengelompokan frekuensi data telah di-generate.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateStatisticsFile() {
        List<List<Integer>> allScores = loadStudentScores();

        if (allScores.isEmpty()) {
            System.out.println("Tidak ada data untuk dihitung.");
            return;
        }

        double mean = calculateMean(allScores);
        double median = calculateMedian(allScores);
        List<Integer> mode = calculateMode(allScores);

        try (PrintWriter writer = new PrintWriter(new FileWriter("src/main/java/output/statistics_data.txt"))) {
            writer.println("Statistik Data:");
            writer.println("Mean (Rata-rata): " + mean);
            writer.println("Median: " + median);
            writer.println("Modus: " + mode);
            System.out.println("File .txt statistik data telah di-generate.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double calculateMean(List<List<Integer>> allScores) {
        int sum = 0;
        int count = 0;

        for (List<Integer> classScores : allScores) {
            for (int score : classScores) {
                sum += score;
                count++;
            }
        }

        return (double) sum / count;
    }

    private static double calculateMedian(List<List<Integer>> allScores) {
        List<Integer> allValues = new ArrayList<>();

        for (List<Integer> classScores : allScores) {
            allValues.addAll(classScores);
        }

        Collections.sort(allValues);
        int n = allValues.size();

        if (n % 2 == 0) {
            int middle1 = allValues.get(n / 2 - 1);
            int middle2 = allValues.get(n / 2);
            return (double) (middle1 + middle2) / 2;
        } else {
            return (double) allValues.get(n / 2);
        }
    }

    private static List<Integer> calculateMode(List<List<Integer>> allScores) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        for (List<Integer> classScores : allScores) {
            for (int score : classScores) {
                frequencyMap.put(score, frequencyMap.getOrDefault(score, 0) + 1);
            }
        }

        int maxFrequency = Collections.max(frequencyMap.values());
        List<Integer> mode = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() == maxFrequency) {
                mode.add(entry.getKey());
            }
        }

        return mode;
    }
}
