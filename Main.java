import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    // ====== Node Single Linked List ======
    static class Node {
        String nim;
        String nama;
        String jurusan;
        Node next;

        Node(String nim, String nama, String jurusan) {
            this.nim = nim;
            this.nama = nama;
            this.jurusan = jurusan;
            this.next = null;
        }
    }

    // ====== Single Linked List Mahasiswa ======
    static class MahasiswaSLL {
        private Node head;
        private int size;

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return head == null;
        }

        // Push: tambah node ke bagian akhir
        public void push(String nim, String nama, String jurusan) {
            Node newNode = new Node(nim, nama, jurusan);
            if (head == null) {
                head = newNode;
            } else {
                Node cur = head;
                while (cur.next != null) {
                    cur = cur.next;
                }
                cur.next = newNode;
            }
            size++;
        }

        // Pop all: hapus semua data
        public void popAll() {
            head = null;
            size = 0;
        }

        // Ambil semua node jadi list (untuk sort saat tampil)
        public List<Node> toList() {
            List<Node> result = new ArrayList<>();
            Node cur = head;
            while (cur != null) {
                result.add(cur);
                cur = cur.next;
            }
            return result;
        }
    }

    // ====== Validasi Input ======
    static String inputNim(Scanner sc) {
        while (true) {
            System.out.print("Masukkan NIM (maks 10 angka): ");
            String nim = sc.nextLine().trim();

            if (nim.isEmpty()) {
                System.out.println("❌ NIM tidak boleh kosong.");
                continue;
            }
            if (nim.length() > 10) {
                System.out.println("❌ NIM maksimal 10 angka.");
                continue;
            }
            if (!nim.matches("\\d+")) {
                System.out.println("❌ NIM harus berupa angka saja.");
                continue;
            }
            return nim;
        }
    }

    static String inputText(Scanner sc, String label, int maxLen) {
        while (true) {
            System.out.print("Masukkan " + label + " (maks " + maxLen + " karakter): ");
            String text = sc.nextLine().trim();

            if (text.isEmpty()) {
                System.out.println("❌ " + label + " tidak boleh kosong.");
                continue;
            }
            if (text.length() > maxLen) {
                System.out.println("❌ " + label + " maksimal " + maxLen + " karakter.");
                continue;
            }
            return text;
        }
    }

    static long nimToLong(String nim) {
        // nim max 10 digit -> aman di long
        return Long.parseLong(nim);
    }

    // ====== Program Utama ======
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MahasiswaSLL db = new MahasiswaSLL();

        while (true) {
            System.out.println("\n===== DATABASE MAHASISWA (SINGLE LINKED LIST) =====");
            System.out.println("1. Push data mahasiswa");
            System.out.println("2. Tampilkan semua data (sort berdasarkan NIM)");
            System.out.println("3. Pop semua data mahasiswa");
            System.out.println("4. Keluar");
            System.out.print("Pilih menu (1-4): ");

            String pilihan = sc.nextLine().trim();

            if (pilihan.equals("1")) {
                if (db.size() >= 5) {
                    System.out.println("❌ Data sudah maksimal (5). Tidak bisa menambah lagi.");
                    continue;
                }

                String nim = inputNim(sc);
                String nama = inputText(sc, "Nama", 30);
                String jurusan = inputText(sc, "Jurusan", 50);

                db.push(nim, nama, jurusan);
                System.out.println("✅ Data berhasil ditambahkan. Total data: " + db.size());

            } else if (pilihan.equals("2")) {
                if (db.isEmpty()) {
                    System.out.println("📭 Data masih kosong.");
                    continue;
                }

                List<Node> list = db.toList();
                list.sort(Comparator.comparingLong(n -> nimToLong(n.nim)));

                System.out.println("\n===== DAFTAR MAHASISWA (SORT NIM) =====");
                System.out.printf("%-12s | %-30s | %-50s%n", "NIM", "Nama", "Jurusan");
                System.out.println("-----------------------------------------------------------------------------------------------");
                for (Node n : list) {
                    System.out.printf("%-12s | %-30s | %-50s%n", n.nim, n.nama, n.jurusan);
                }

            } else if (pilihan.equals("3")) {
                if (db.isEmpty()) {
                    System.out.println("📭 Data sudah kosong.");
                } else {
                    db.popAll();
                    System.out.println("🗑️ Semua data mahasiswa berhasil dihapus.");
                }

            } else if (pilihan.equals("4")) {
                System.out.println("👋 Keluar dari program.");
                sc.close();
                break;

            } else {
                System.out.println("❌ Pilihan tidak valid. Masukkan 1-4.");
            }
        }
    }
}
