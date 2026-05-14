package devilstudio.com.farmerfriend

object SellerRepository {

    fun getAllSellers(): ArrayList<Seller> {
        val sellers = ArrayList<Seller>()

        sellers.add(
            Seller(
                id = 1,
                name = "Yeşil Tarım Market",
                phone = "0312 111 22 33",
                email = "yesiltarim@example.com",
                city = "Ankara",
                district = "Yenimahalle",
                address = "İvedik OSB Mahallesi, Tarım Sokak No:12",
                specialization = "Fungisit, yaprak gübresi ve organik ürün satışı",
                username = "yesiltarim",
                password = "1234"
            )
        )

        sellers.add(
            Seller(
                id = 2,
                name = "Bereket Zirai Ürünler",
                phone = "0312 222 33 44",
                email = "bereketzirai@example.com",
                city = "Ankara",
                district = "Polatlı",
                address = "Cumhuriyet Mahallesi, Zirai Ürünler Caddesi No:8",
                specialization = "Tahıl, sebze ve meyve hastalık ürünleri",
                username = "bereket",
                password = "1234"
            )
        )

        sellers.add(
            Seller(
                id = 3,
                name = "Anadolu Tarım Destek",
                phone = "0332 333 44 55",
                email = "anadolutarim@example.com",
                city = "Konya",
                district = "Selçuklu",
                address = "Horozluhan Mahallesi, Tarımcılar Sitesi No:21",
                specialization = "Yaprak gübresi, organik bakım ve bitki besleme",
                username = "anadolu",
                password = "1234"
            )
        )

        sellers.add(
            Seller(
                id = 4,
                name = "Ege Bitki Koruma",
                phone = "0232 444 55 66",
                email = "egebk@example.com",
                city = "İzmir",
                district = "Torbalı",
                address = "Ayrancılar Mahallesi, Üretici Sokak No:5",
                specialization = "Meyve ağaçları ve sebze hastalıkları için ürün önerisi",
                username = "ege",
                password = "1234"
            )
        )

        sellers.add(
            Seller(
                id = 5,
                name = "Akdeniz Zirai Market",
                phone = "0242 555 66 77",
                email = "akdenizzirai@example.com",
                city = "Antalya",
                district = "Kepez",
                address = "Varsak Mahallesi, Tarım Bulvarı No:18",
                specialization = "Domates, biber, sera ürünleri ve hastalık yönetimi",
                username = "akdeniz",
                password = "1234"
            )
        )

        sellers.add(
            Seller(
                id = 6,
                name = "Karadeniz Tarım Noktası",
                phone = "0462 666 77 88",
                email = "karadeniztarim@example.com",
                city = "Trabzon",
                district = "Ortahisar",
                address = "Sanayi Mahallesi, Bitki Koruma Sokak No:4",
                specialization = "Nemli bölgelerde mantari hastalık ürünleri",
                username = "karadeniz",
                password = "1234"
            )
        )

        sellers.add(
            Seller(
                id = 7,
                name = "Çukurova Zirai Destek",
                phone = "0322 777 88 99",
                email = "cukurovazirai@example.com",
                city = "Adana",
                district = "Seyhan",
                address = "Reşatbey Mahallesi, Üretim Caddesi No:15",
                specialization = "Sebze, narenciye ve tarla bitkileri ürünleri",
                username = "cukurova",
                password = "1234"
            )
        )

        sellers.add(
            Seller(
                id = 8,
                name = "Marmara Tarım Market",
                phone = "0224 888 99 00",
                email = "marmaratarim@example.com",
                city = "Bursa",
                district = "Nilüfer",
                address = "Beşevler Mahallesi, Tarım Plaza No:7",
                specialization = "Meyve, sebze ve yaprak gübresi ürünleri",
                username = "marmara",
                password = "1234"
            )
        )

        sellers.add(
            Seller(
                id = 9,
                name = "Güneydoğu Bitki Sağlığı",
                phone = "0412 123 45 67",
                email = "guneydogutarim@example.com",
                city = "Diyarbakır",
                district = "Kayapınar",
                address = "Peyas Mahallesi, Zirai Destek Sokak No:10",
                specialization = "Sıcak iklim bitki hastalıkları ve bakım ürünleri",
                username = "guneydogu",
                password = "1234"
            )
        )

        sellers.add(
            Seller(
                id = 10,
                name = "İstanbul Bahçe ve Tarım",
                phone = "0212 987 65 43",
                email = "istanbultarim@example.com",
                city = "İstanbul",
                district = "Pendik",
                address = "Kurtköy Mahallesi, Bahçe Ürünleri Caddesi No:6",
                specialization = "Hobi bahçesi, balkon bitkileri ve organik ürünler",
                username = "istanbul",
                password = "1234"
            )
        )

        return sellers
    }

    fun login(username: String, password: String): Seller? {
        val sellers = getAllSellers()

        for (seller in sellers) {
            if (seller.username == username && seller.password == password) {
                return seller
            }
        }

        return null
    }
}