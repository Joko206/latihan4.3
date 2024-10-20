import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class FileSplitter {
    private Queue<String> lineQueue; // Queue untuk menyimpan baris-baris dari file
    private String inputFilePath; // Path dari file input

    // Konstruktor untuk menginisialisasi FileSplitter dengan path file input
    public FileSplitter(String inputFilePath) {
        this.inputFilePath = inputFilePath;
        lineQueue = new LinkedList<>(); // Menginisialisasi queue
    }

    // Membaca file dan menambahkan setiap baris ke dalam queue
    public void readFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineQueue.offer(line); // Menambahkan baris ke dalam queue
            }
        }
    }

    // Memotong file berdasarkan ukuran yang diberikan (jumlah baris)
    public void splitFile(int chunkSize) throws IOException {
        int partNumber = 1; // Menyimpan nomor bagian file
        StringBuilder chunk = new StringBuilder(); // Untuk menyimpan potongan teks

        // Selama queue tidak kosong, ambil baris dari queue
        while (!lineQueue.isEmpty()) {
            for (int i = 0; i < chunkSize && !lineQueue.isEmpty(); i++) {
                chunk.append(lineQueue.poll()).append(System.lineSeparator()); // Menambahkan baris ke potongan
            }

            // Menyimpan potongan ke file baru
            try (FileWriter writer = new FileWriter("output_part_" + partNumber + ".txt")) {
                writer.write(chunk.toString()); // Menulis potongan ke file
                System.out.println("File output_part_" + partNumber + ".txt telah dibuat.");
                partNumber++; // Meningkatkan nomor bagian
                chunk.setLength(0); // Mengosongkan StringBuilder untuk potongan berikutnya
            }
        }
    }

    // Metode utama untuk menjalankan program
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan path file input: ");
        String inputFilePath = scanner.nextLine(); // Membaca path file input
        FileSplitter fileSplitter = new FileSplitter(inputFilePath); // Membuat objek FileSplitter

        try {
            fileSplitter.readFile(); // Membaca file
            System.out.print("Masukkan ukuran potongan (jumlah baris): ");
            int chunkSize = scanner.nextInt(); // Membaca ukuran potongan
            fileSplitter.splitFile(chunkSize); // Memotong file
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat memproses file: " + e.getMessage());
        } finally {
            scanner.close(); // Menutup scanner
        }
    }
}