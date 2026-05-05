import java.util.Scanner;

public class PROJECTUAS {

    // =====================================================================
    // STRUKTUR DATA: Record/Array manual (tanpa ArrayList/LinkedList)
    // =====================================================================

    static final int MAX = 100;

    // Data produk
    static int[]     id         = new int[MAX];
    static String[]  nama       = new String[MAX];
    static String[]  kategori   = new String[MAX];
    static int[]     stok       = new int[MAX];
    static double[]  harga      = new double[MAX];
    static boolean[] aktif      = new boolean[MAX]; // false = soft deleted
    static int       jumlahData = 0;

    // Log untuk soft delete
    static String[]  logHapus   = new String[MAX];
    static int       jumlahLog  = 0;

    static int nextId = 1; // Auto-increment ID

    static Scanner sc = new Scanner(System.in);

    // =====================================================================
    // MAIN MENU
    // =====================================================================

    public static void main(String[] args) {
        // Isi data awal supaya langsung bisa dicoba
        initDataAwal();

        int pilihan;
        do {
            System.out.println("\n╔══════════════════════════════════════════════╗");
            System.out.println("║     SISTEM MANAJEMEN STOK TOKO ELEKTRONIK    ║");
            System.out.println("╠══════════════════════════════════════════════╣");
            System.out.println("║  [CRUD]                                      ║");
            System.out.println("║  1. Tambah Produk Baru                       ║");
            System.out.println("║  2. Tampilkan Semua Produk                   ║");
            System.out.println("║  3. Edit Produk (berdasarkan ID)             ║");
            System.out.println("║  4. Hapus Produk (Soft Delete)               ║");
            System.out.println("║  5. Lihat Log Penghapusan                    ║");
            System.out.println("╠══════════════════════════════════════════════╣");
            System.out.println("║  [SEARCHING]                                 ║");
            System.out.println("║  6. Cari berdasarkan Nama (Linear Search)    ║");
            System.out.println("║  7. Cari berdasarkan ID   (Binary Search)    ║");
            System.out.println("║  8. Cari berdasarkan Kategori                ║");
            System.out.println("╠══════════════════════════════════════════════╣");
            System.out.println("║  [SORTING]                                   ║");
            System.out.println("║  9.  Urutkan ID Ascending  (Bubble Sort)     ║");
            System.out.println("║  10. Urutkan Nama A-Z      (Selection Sort)  ║");
            System.out.println("║  11. Urutkan Stok Terbanyak (Descending)     ║");
            System.out.println("╠══════════════════════════════════════════════╣");
            System.out.println("║  0. Keluar                                   ║");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.print("  Pilih menu: ");
            pilihan = sc.nextInt();
            sc.nextLine();

            switch (pilihan) {
                case 1:  tambahProduk();         break;
                case 2:  tampilkanSemua();       break;
                case 3:  editProduk();           break;
                case 4:  hapusProduk();          break;
                case 5:  lihatLog();             break;
                case 6:  cariNama();             break;
                case 7:  cariId();               break;
                case 8:  cariKategori();         break;
                case 9:  sortById();             break;
                case 10: sortByNama();           break;
                case 11: sortByStok();           break;
                case 0:  System.out.println("\n  Terima kasih! Program selesai."); break;
                default: System.out.println("\n  [!] Pilihan tidak valid.");
            }
        } while (pilihan != 0);
    }

    // =====================================================================
    // DATA AWAL (sample data)
    // =====================================================================

    static void initDataAwal() {
        String[][] data = {
            {"Laptop ASUS VivoBook",    "Laptop",       "45",  "7500000"},
            {"Mouse Logitech M235",     "Aksesori",     "120", "250000"},
            {"Samsung Galaxy A54",      "Smartphone",   "60",  "4200000"},
            {"Keyboard Mechanical RGB", "Aksesori",     "35",  "850000"},
            {"Monitor LG 24 inch",      "Monitor",      "20",  "2300000"},
            {"Headphone Sony WH-1000",  "Audio",        "15",  "3500000"},
            {"Flash Drive SanDisk 64GB","Storage",      "200", "85000"},
            {"Laptop Lenovo IdeaPad",   "Laptop",       "30",  "6800000"},
            {"Charger USB-C 65W",       "Aksesori",     "90",  "175000"},
            {"SSD Samsung 500GB",       "Storage",      "55",  "950000"},
            {"Printer Epson L3210",      "Printer",      "25",  "2800000"},
            {"iPhone 13 128GB",          "Smartphone",   "18",  "9500000"},
            {"Router TP-Link Archer C6", "Networking",   "40",  "650000"},
            {"Webcam Logitech C270",     "Aksesori",     "70",  "320000"},
            {"Hard Disk Seagate 1TB",    "Storage",      "45",  "780000"},
            {"Smart TV Samsung 43 Inch", "Elektronik",   "12",  "5200000"},
            {"Tablet Xiaomi Pad 6",      "Tablet",       "28",  "4900000"},
            {"Speaker JBL Flip 6",       "Audio",        "22",  "2100000"},
            {"Kabel HDMI 2 Meter",       "Aksesori",     "150", "75000"},
            {"Canon EOS 1500D",          "Kamera",       "10",  "7200000"},
            {"Laptop HP Pavilion",       "Laptop",       "27",  "8900000"},
            {"Mouse Razer DeathAdder",   "Aksesori",     "65",  "450000"},
            {"Smartwatch Xiaomi Band 8", "Wearable",     "85",  "550000"},
            {"Monitor Samsung 27 Inch",  "Monitor",      "17",  "3100000"},
            {"Power Bank 20000mAh",      "Aksesori",     "95",  "300000"},
            {"AirPods Pro 2",            "Audio",        "20",  "4200000"},
            {"SSD Kingston 1TB",         "Storage",      "33",  "1450000"},
            {"Nintendo Switch OLED",     "Gaming",       "14",  "5800000"},
            {"Keyboard Logitech K120",   "Aksesori",     "110", "180000"},
            {"Drone DJI Mini 3",         "Drone",        "8",   "8900000"},
            {"Projector Epson X500",    "Elektronik",    "16",  "4100000"},
        };

        for (String[] d : data) {
            id[jumlahData]       = nextId++;
            nama[jumlahData]     = d[0];
            kategori[jumlahData] = d[1];
            stok[jumlahData]     = Integer.parseInt(d[2]);
            harga[jumlahData]    = Double.parseDouble(d[3]);
            aktif[jumlahData]    = true;
            jumlahData++;
        }
    }

    // =====================================================================
    // CRUD - 1. TAMBAH PRODUK
    // =====================================================================

    static void tambahProduk() {
        if (jumlahData >= MAX) {
            System.out.println("\n  [!] Kapasitas penuh!");
            return;
        }

        System.out.println("\n─── TAMBAH PRODUK BARU ───");
        System.out.print("  Nama Produk  : ");
        String nm = sc.nextLine();
        System.out.print("  Kategori     : ");
        String kat = sc.nextLine();
        System.out.print("  Stok         : ");
        int stk = sc.nextInt();
        System.out.print("  Harga (Rp)   : ");
        double hrg = sc.nextDouble();
        sc.nextLine();

        id[jumlahData]       = nextId++;
        nama[jumlahData]     = nm;
        kategori[jumlahData] = kat;
        stok[jumlahData]     = stk;
        harga[jumlahData]    = hrg;
        aktif[jumlahData]    = true;
        jumlahData++;

        System.out.println("\n  [✓] Produk berhasil ditambahkan! ID: " + (nextId - 1));
    }

    // =====================================================================
    // CRUD - 2. TAMPILKAN SEMUA DATA
    // =====================================================================

    static void tampilkanSemua() {
        System.out.println("\n─── DAFTAR SEMUA PRODUK ───");
        printHeader();

        int count = 0;
        for (int i = 0; i < jumlahData; i++) {
            if (aktif[i]) {
                printRow(i);
                count++;
            }
        }

        if (count == 0) {
            System.out.println("  (Tidak ada data produk aktif)");
        }
        System.out.println("  Total: " + count + " produk aktif.");
    }

    // =====================================================================
    // CRUD - 3. EDIT PRODUK
    // =====================================================================

    static void editProduk() {
        System.out.println("\n─── EDIT PRODUK ───");
        System.out.print("  Masukkan ID produk yang akan diedit: ");
        int cariId = sc.nextInt();
        sc.nextLine();

        int index = cariIndexById(cariId);

        if (index == -1 || !aktif[index]) {
            System.out.println("\n  [!] Produk dengan ID " + cariId + " tidak ditemukan.");
            return;
        }

        System.out.println("\n  Data saat ini:");
        printHeader();
        printRow(index);

        System.out.println("\n  Masukkan data baru (Enter = tidak berubah):");
        System.out.print("  Nama [" + nama[index] + "]: ");
        String nm = sc.nextLine();
        if (!nm.isEmpty()) nama[index] = nm;

        System.out.print("  Kategori [" + kategori[index] + "]: ");
        String kat = sc.nextLine();
        if (!kat.isEmpty()) kategori[index] = kat;

        System.out.print("  Stok [" + stok[index] + "]: ");
        String stokInput = sc.nextLine();
        if (!stokInput.isEmpty()) stok[index] = Integer.parseInt(stokInput);

        System.out.print("  Harga [" + harga[index] + "]: ");
        String hargaInput = sc.nextLine();
        if (!hargaInput.isEmpty()) harga[index] = Double.parseDouble(hargaInput);

        System.out.println("\n  [✓] Data produk ID " + cariId + " berhasil diperbarui.");
    }

    // =====================================================================
    // CRUD - 4. HAPUS PRODUK (SOFT DELETE)
    // =====================================================================

    static void hapusProduk() {
        System.out.println("\n─── HAPUS PRODUK (SOFT DELETE) ───");
        System.out.print("  Masukkan ID produk yang akan dihapus: ");
        int cariId = sc.nextInt();
        sc.nextLine();

        int index = cariIndexById(cariId);

        if (index == -1 || !aktif[index]) {
            System.out.println("\n  [!] Produk ID " + cariId + " tidak ditemukan atau sudah dihapus.");
            return;
        }

        System.out.println("\n  Produk yang akan dihapus:");
        printHeader();
        printRow(index);
        System.out.print("\n  Yakin ingin menghapus? (y/n): ");
        String konfirmasi = sc.nextLine();

        if (konfirmasi.equalsIgnoreCase("y")) {
            // Catat log sebelum dihapus
            logHapus[jumlahLog] = "[LOG] ID=" + id[index]
                + " | Nama=" + nama[index]
                + " | Kategori=" + kategori[index]
                + " | Stok=" + stok[index]
                + " | Harga=Rp" + (long) harga[index]
                + " | STATUS: DIHAPUS";
            jumlahLog++;

            // Soft delete: ubah status aktif menjadi false
            aktif[index] = false;

            System.out.println("\n  [✓] Produk ID " + cariId + " berhasil dihapus (soft delete).");
        } else {
            System.out.println("\n  [!] Penghapusan dibatalkan.");
        }
    }

    // =====================================================================
    // CRUD - 5. LIHAT LOG PENGHAPUSAN
    // =====================================================================

    static void lihatLog() {
        System.out.println("\n─── LOG PENGHAPUSAN PRODUK ───");
        if (jumlahLog == 0) {
            System.out.println("  (Belum ada produk yang dihapus)");
            return;
        }
        for (int i = 0; i < jumlahLog; i++) {
            System.out.println("  " + (i + 1) + ". " + logHapus[i]);
        }
    }

    // =====================================================================
    // SEARCHING - 6. LINEAR SEARCH (berdasarkan Nama)
    // =====================================================================

    static void cariNama() {
        System.out.println("\n─── CARI PRODUK BERDASARKAN NAMA (Linear Search) ───");
        System.out.print("  Masukkan kata kunci nama: ");
        String keyword = sc.nextLine().toLowerCase();

        System.out.println("\n  Hasil pencarian untuk \"" + keyword + "\":");
        printHeader();

        int count = 0;
        // Linear Search: cek satu per satu dari index 0 sampai akhir
        for (int i = 0; i < jumlahData; i++) {
            if (aktif[i] && nama[i].toLowerCase().contains(keyword)) {
                printRow(i);
                count++;
            }
        }

        if (count == 0) {
            System.out.println("  (Tidak ada produk yang cocok)");
        } else {
            System.out.println("  Ditemukan: " + count + " produk.");
        }
    }

    // =====================================================================
    // SEARCHING - 7. BINARY SEARCH (berdasarkan ID)
    // Prasyarat: data harus diurutkan berdasarkan ID dulu
    // =====================================================================

    static void cariId() {
        System.out.println("\n─── CARI PRODUK BERDASARKAN ID (Binary Search) ───");
        System.out.print("  Masukkan ID yang dicari: ");
        int targetId = sc.nextInt();
        sc.nextLine();

        // Prasyarat Binary Search: urutkan data berdasarkan ID dulu (Bubble Sort sementara)
        // Kita sort pada salinan index saja untuk binary search
        // Namun karena data kita bisa sudah terurut by ID (auto-increment),
        // kita tetap lakukan sort for correctness

        // Buat array index terurut berdasarkan ID (hanya yang aktif)
        int[] idx = new int[jumlahData];
        int n = 0;
        for (int i = 0; i < jumlahData; i++) {
            if (aktif[i]) idx[n++] = i;
        }

        // Bubble sort index array berdasarkan nilai id[]
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (id[idx[j]] > id[idx[j + 1]]) {
                    int tmp = idx[j];
                    idx[j]  = idx[j + 1];
                    idx[j + 1] = tmp;
                }
            }
        }

        // Binary Search pada idx[]
        int kiri   = 0;
        int kanan  = n - 1;
        int hasil  = -1;

        while (kiri <= kanan) {
            int tengah = (kiri + kanan) / 2;
            if (id[idx[tengah]] == targetId) {
                hasil = idx[tengah];
                break;
            } else if (id[idx[tengah]] < targetId) {
                kiri = tengah + 1;
            } else {
                kanan = tengah - 1;
            }
        }

        if (hasil == -1) {
            System.out.println("\n  [!] Produk dengan ID " + targetId + " tidak ditemukan.");
        } else {
            System.out.println("\n  Produk ditemukan:");
            printHeader();
            printRow(hasil);
        }
    }

    // =====================================================================
    // SEARCHING - 8. CARI BERDASARKAN KATEGORI
    // =====================================================================

    static void cariKategori() {
        System.out.println("\n─── CARI PRODUK BERDASARKAN KATEGORI ───");
        System.out.print("  Masukkan nama kategori: ");
        String kat = sc.nextLine().toLowerCase();

        System.out.println("\n  Produk dalam kategori \"" + kat + "\":");
        printHeader();

        int count = 0;
        for (int i = 0; i < jumlahData; i++) {
            if (aktif[i] && kategori[i].toLowerCase().contains(kat)) {
                printRow(i);
                count++;
            }
        }

        if (count == 0) {
            System.out.println("  (Tidak ada produk dalam kategori tersebut)");
        } else {
            System.out.println("  Ditemukan: " + count + " produk.");
        }
    }

    // =====================================================================
    // SORTING - 9. BUBBLE SORT berdasarkan ID (Ascending)
    // =====================================================================

    static void sortById() {
        System.out.println("\n─── URUTKAN BERDASARKAN ID ASCENDING (Bubble Sort) ───");

        // Bubble Sort: bandingkan elemen berdekatan, swap jika tidak urut
        for (int i = 0; i < jumlahData - 1; i++) {
            for (int j = 0; j < jumlahData - i - 1; j++) {
                if (id[j] > id[j + 1]) {
                    swap(j, j + 1);
                }
            }
        }

        System.out.println("  [✓] Data berhasil diurutkan berdasarkan ID (Ascending).");
        tampilkanSemua();
    }

    // =====================================================================
    // SORTING - 10. SELECTION SORT berdasarkan Nama (Alphabetical)
    // =====================================================================

    static void sortByNama() {
        System.out.println("\n─── URUTKAN BERDASARKAN NAMA A-Z (Selection Sort) ───");

        // Selection Sort: cari minimum di sisa array, taruh di depan
        for (int i = 0; i < jumlahData - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < jumlahData; j++) {
                if (nama[j].compareToIgnoreCase(nama[minIdx]) < 0) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                swap(i, minIdx);
            }
        }

        System.out.println("  [✓] Data berhasil diurutkan berdasarkan Nama (A-Z).");
        tampilkanSemua();
    }

    // =====================================================================
    // SORTING - 11. SORT berdasarkan Stok Terbanyak (Descending)
    //              Menggunakan Insertion Sort
    // =====================================================================

    static void sortByStok() {
        System.out.println("\n─── URUTKAN BERDASARKAN STOK TERBANYAK (Descending) ───");

        // Insertion Sort descending berdasarkan stok
        for (int i = 1; i < jumlahData; i++) {
            // Simpan semua nilai index i dulu sebagai "key"
            int     keyId       = id[i];
            String  keyNama     = nama[i];
            String  keyKategori = kategori[i];
            int     keyStok     = stok[i];
            double  keyHarga    = harga[i];
            boolean keyAktif    = aktif[i];

            int j = i - 1;
            // Geser elemen yang stoknya lebih kecil ke kanan
            while (j >= 0 && stok[j] < keyStok) {
                id[j + 1]       = id[j];
                nama[j + 1]     = nama[j];
                kategori[j + 1] = kategori[j];
                stok[j + 1]     = stok[j];
                harga[j + 1]    = harga[j];
                aktif[j + 1]    = aktif[j];
                j--;
            }
            // Taruh key di posisi yang benar
            id[j + 1]       = keyId;
            nama[j + 1]     = keyNama;
            kategori[j + 1] = keyKategori;
            stok[j + 1]     = keyStok;
            harga[j + 1]    = keyHarga;
            aktif[j + 1]    = keyAktif;
        }

        System.out.println("  [✓] Data berhasil diurutkan berdasarkan Stok Terbanyak (Descending).");
        tampilkanSemua();
    }

    // =====================================================================
    // HELPER METHODS
    // =====================================================================

    // Swap semua field di index i dan j
    static void swap(int i, int j) {
        int     tmpId       = id[i];       id[i]       = id[j];       id[j]       = tmpId;
        String  tmpNama     = nama[i];     nama[i]     = nama[j];     nama[j]     = tmpNama;
        String  tmpKat      = kategori[i]; kategori[i] = kategori[j]; kategori[j] = tmpKat;
        int     tmpStok     = stok[i];     stok[i]     = stok[j];     stok[j]     = tmpStok;
        double  tmpHarga    = harga[i];    harga[i]    = harga[j];    harga[j]    = tmpHarga;
        boolean tmpAktif    = aktif[i];    aktif[i]    = aktif[j];    aktif[j]    = tmpAktif;
    }

    // Cari index berdasarkan ID (bukan binary search, murni untuk keperluan internal)
    static int cariIndexById(int targetId) {
        for (int i = 0; i < jumlahData; i++) {
            if (id[i] == targetId) return i;
        }
        return -1;
    }

    // Cetak header tabel
    static void printHeader() {
        System.out.println("  ┌──────┬──────────────────────────────┬──────────────┬───────┬────────────────┐");
        System.out.println("  │  ID  │ Nama Produk                  │ Kategori     │ Stok  │ Harga (Rp)     │");
        System.out.println("  ├──────┼──────────────────────────────┼──────────────┼───────┼────────────────┤");
    }

    // Cetak satu baris data
    static void printRow(int i) {
        System.out.printf("  │ %-4d │ %-28s │ %-12s │ %-5d │ %-14s │%n",
            id[i],
            truncate(nama[i], 28),
            truncate(kategori[i], 12),
            stok[i],
            formatRupiah((long) harga[i])
        );
    }

    // Potong string jika terlalu panjang
    static String truncate(String s, int max) {
        if (s.length() <= max) return s;
        return s.substring(0, max - 2) + "..";
    }

    // Format angka ke rupiah sederhana (tanpa library)
    static String formatRupiah(long angka) {
        String s = Long.toString(angka);
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (count > 0 && count % 3 == 0) sb.insert(0, '.');
            sb.insert(0, s.charAt(i));
            count++;
        }
        return sb.toString();
    }
}