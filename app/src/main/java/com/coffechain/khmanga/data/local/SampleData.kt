package com.coffechain.khmanga.data.local

import com.coffechain.khmanga.domain.model.Booth

object SampleData {

    data class NewCafe(
        val name: String,
        val description: String,
        val address: String,
        val location: String,
        val imageUrl: String,
        val amenities: List<String>,
        val booths: List<Booth>
    )

    data class NewManga(
        val title: String,
        val genres: List<String>,
        val imageUrl: String
    )

    data class NewFood(
        val title: String,
        val price: Double,
        val category: String,
        val imageUrl: String,
        val description: String
    )

    val sampleCafes = listOf(
        NewCafe(
            name = "Kōhī Manga Haven",
            description = "Kafe manga nyaman dengan koleksi terlengkap di pusat kota.",
            address = "Jl. Merdeka No. 1, Jakarta",
            location = "Jakarta",
            imageUrl = "https://res.cloudinary.com/ddim30d7v/image/upload/v1751177308/jakarta_x5uswn.jpg",
            amenities = listOf("WiFi Cepat", "AC", "Mushola", "Area VIP"),
            booths = listOf(
                Booth(id = "kmh_b01", name = "Booth Single", price = 30000.0, capacity = 1, type = "Kursi"),
                Booth(id = "kmh_b02", name = "Booth Sofa Pasangan", price = 60000.0, capacity = 2, type = "Sofa"),
                Booth(id = "kmh_b03", name = "Booth Grup", price = 100000.0, capacity = 5, type = "Lesehan")
            )
        ),
        NewCafe(
            name = "Neko Neko Nook",
            description = "Sering ada event cosplay dan nobar anime di sini!",
            address = "Jl. Pahlawan No. 22, Surabaya",
            location = "Surabaya",
            imageUrl = "https://res.cloudinary.com/ddim30d7v/image/upload/v1751194179/surabaya_sgrmw1.jpg",
            amenities = listOf("WiFi", "Area Outdoor", "Live Music"),
            booths = listOf(
                Booth(id = "nnn_b01", name = "Meja Outdoor", price = 20000.0, capacity = 2, type = "Outdoor"),
                Booth(id = "nnn_b02", name = "Meja Bar", price = 25000.0, capacity = 1, type = "Kursi Bar")
            )
        ),
        NewCafe(
            name = "Otaku's Oasis",
            description = "Tempat yang tenang untuk membaca sambil menikmati kopi spesial.",
            address = "Jl. Pelajar No. 7, Yogyakarta",
            location = "Yogyakarta",
            imageUrl = "https://res.cloudinary.com/ddim30d7v/image/upload/v1751177308/jakarta_x5uswn.jpg",
            amenities = listOf("Sangat Tenang", "Banyak Stopkontak", "Buku Referensi"),
            booths = listOf(
                Booth(id = "oo_b01", name = "Bilik Baca", price = 40000.0, capacity = 1, type = "Private Booth")
            )
        ),
        NewCafe(
            name = "The Reading Lair",
            description = "Selain manga, kami juga punya banyak light novel dan artbook.",
            address = "Jl. Cendrawasih No. 15, Bandung",
            location = "Bandung",
            imageUrl = "https://res.cloudinary.com/ddim30d7v/image/upload/v1751194214/bandung_cahxy7.webp",
            amenities = listOf("WiFi", "Perpustakaan Mini", "Printer"),
            booths = emptyList() // Contoh kafe tanpa booth
        ),
        NewCafe(
            name = "Gamer's Grind",
            description = "Kafe manga dengan fasilitas konsol game di setiap booth.",
            address = "Jl. Teknologi No. 8, Tangerang",
            location = "Malang",
            imageUrl = "https://res.cloudinary.com/ddim30d7v/image/upload/v1751194177/malang_dskscy.webp",
            amenities = listOf("WiFi Kencang", "PS5", "Nintendo Switch", "PC Gaming"),
            booths = listOf(
                Booth(id = "gg_b01", name = "Booth Gaming Duo", price = 120000.0, capacity = 2, type = "Gaming Setup")
            )
        )
    )

    val sampleManga = listOf(
        NewManga(title = "One Piece", genres = listOf("Adventure", "Action", "Fantasy"), imageUrl = "https://res.cloudinary.com/ddim30d7v/image/upload/v1751232627/onepice_uqwf0r.jpg"),
        NewManga(title = "Jujutsu Kaisen", genres = listOf("Dark Fantasy", "Supernatural"), imageUrl = "https://res.cloudinary.com/ddim30d7v/image/upload/v1751232560/jjk_cg8iok.jpg"),
        NewManga(title = "Spy x Family", genres = listOf("Comedy", "Action", "Espionage"), imageUrl = "https://res.cloudinary.com/ddim30d7v/image/upload/v1751232628/spy_omuden.jpg"),
        NewManga(title = "Attack on Titan", genres = listOf("Action", "Dark Fantasy"), imageUrl = "https://res.cloudinary.com/ddim30d7v/image/upload/v1751232559/aot_vjxe3l.jpg"),
        NewManga(title = "Chainsaw Man", genres = listOf("Action", "Horror", "Dark Comedy"), imageUrl = "https://res.cloudinary.com/ddim30d7v/image/upload/v1751232589/cman_fql1vj.jpg")
    )

    val sampleFoods = listOf(
        NewFood(title = "Kopi Hitam Americano", price = 18000.0, category = "Minuman", description = "Espresso dengan tambahan air panas.", imageUrl = "https://res.cloudinary.com/ddim30d7v/image/upload/v1751232997/Kopi_Hitam_Americano_b0jjw5.jpg"),
        NewFood(title = "Matcha Latte", price = 25000.0, category = "Minuman", description = "Teh hijau Jepang dengan susu segar.", imageUrl = "https://res.cloudinary.com/ddim30d7v/image/upload/v1751232996/Matcha_Latte_ipeurz.jpg"),
        NewFood(title = "Roti Bakar Cokelat Keju", price = 22000.0, category = "Makanan", description = "Roti tawar dengan topping melimpah.", imageUrl = "https://res.cloudinary.com/ddim30d7v/image/upload/v1751232996/Matcha_Latte_ipeurz.jpg"),
        NewFood(title = "Nasi Goreng Spesial", price = 35000.0, category = "Makanan", description = "Nasi goreng dengan telur, ayam, dan sosis.", imageUrl = "https://res.cloudinary.com/ddim30d7v/image/upload/v1751232996/Matcha_Latte_ipeurz.jpg"),
        NewFood(title = "Kentang Goreng", price = 15000.0, category = "Cemilan", description = "Kentang goreng renyah dengan saus pilihan.", imageUrl = "https://res.cloudinary.com/ddim30d7v/image/upload/v1751233000/Kentang_Goreng_m6dzq1.jpg")
    )
    val sampleReviews = listOf(
        mapOf("userId" to "user_abc", "userName" to "Budi", "rating" to 5.0, "comment" to "Tempatnya juara! Wajib kembali lagi."),
        mapOf("userId" to "user_def", "userName" to "Citra", "rating" to 4.0, "comment" to "Koleksi manganya lengkap, tapi makanannya biasa saja."),
        mapOf("userId" to "user_ghi", "userName" to "Doni", "rating" to 5.0, "comment" to "Sangat nyaman untuk WFC (Work From Cafe)."),
        mapOf("userId" to "user_jkl", "userName" to "Eka", "rating" to 3.0, "comment" to "Agak berisik saat akhir pekan."),
        mapOf("userId" to "user_mno", "userName" to "Fira", "rating" to 4.5, "comment" to "Kopinya enak banget!")
    )
}