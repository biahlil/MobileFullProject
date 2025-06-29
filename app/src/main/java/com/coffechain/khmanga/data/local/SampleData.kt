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
        mapOf("title" to "One Piece", "genres" to listOf("Adventure", "Action", "Fantasy")),
        mapOf("title" to "Jujutsu Kaisen", "genres" to listOf("Dark Fantasy", "Supernatural")),
        mapOf("title" to "Spy x Family", "genres" to listOf("Comedy", "Action", "Espionage")),
        mapOf("title" to "Attack on Titan", "genres" to listOf("Action", "Dark Fantasy", "Post-apocalyptic")),
        mapOf("title" to "Chainsaw Man", "genres" to listOf("Action", "Horror", "Dark Comedy"))
    )

    val sampleFoods = listOf(
        mapOf("title" to "Kopi Hitam Americano", "price" to 18000.0, "category" to "Minuman"),
        mapOf("title" to "Matcha Latte", "price" to 25000.0, "category" to "Minuman"),
        mapOf("title" to "Roti Bakar Cokelat Keju", "price" to 22000.0, "category" to "Makanan"),
        mapOf("title" to "Nasi Goreng Spesial", "price" to 35000.0, "category" to "Makanan"),
        mapOf("title" to "Kentang Goreng", "price" to 15000.0, "category" to "Cemilan")
    )

    val sampleReviews = listOf(
        mapOf("userId" to "user_abc", "userName" to "Budi", "rating" to 5.0, "comment" to "Tempatnya juara! Wajib kembali lagi."),
        mapOf("userId" to "user_def", "userName" to "Citra", "rating" to 4.0, "comment" to "Koleksi manganya lengkap, tapi makanannya biasa saja."),
        mapOf("userId" to "user_ghi", "userName" to "Doni", "rating" to 5.0, "comment" to "Sangat nyaman untuk WFC (Work From Cafe)."),
        mapOf("userId" to "user_jkl", "userName" to "Eka", "rating" to 3.0, "comment" to "Agak berisik saat akhir pekan."),
        mapOf("userId" to "user_mno", "userName" to "Fira", "rating" to 4.5, "comment" to "Kopinya enak banget!")
    )
}