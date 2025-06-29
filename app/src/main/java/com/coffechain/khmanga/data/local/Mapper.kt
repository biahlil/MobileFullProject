package com.coffechain.khmanga.data.local

import com.coffechain.khmanga.data.local.entity.CafeEntity
import com.coffechain.khmanga.data.local.entity.MangaEntity
import com.coffechain.khmanga.data.local.entity.UserEntity
import com.coffechain.khmanga.data.network.CafeDto
import com.coffechain.khmanga.data.network.FoodDto
import com.coffechain.khmanga.data.network.MangaDto
import com.coffechain.khmanga.domain.model.Booth
import com.coffechain.khmanga.domain.model.Cafe
import com.coffechain.khmanga.domain.model.Food
import com.coffechain.khmanga.domain.model.Manga
import com.coffechain.khmanga.domain.model.User

fun User.toEntity(): UserEntity {
    return UserEntity(
        uid = this.uid,
        displayName = this.displayName,
        email = this.email,
        photoUrl = this.photoUrl
    )
}

fun UserEntity.toDomainModel(): User {
    return User(
        uid = this.uid,
        displayName = this.displayName,
        email = this.email,
        photoUrl = this.photoUrl
    )
}

fun Cafe.toEntity(): CafeEntity {
    return CafeEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        address = this.address,
        location = this.location,
        imageUrl = this.imageUrl ?: "",
        averageRating = this.averageRating,
        amenities = this.amenities,
        booths = this.booths
    )
}

fun CafeDto.toDomainModel(id: String): Cafe {
    val boothList = this.booths?.map { boothMap ->
        Booth(
            id = boothMap["boothId"] as? String ?: "",
            name = boothMap["name"] as? String ?: "",
            price = boothMap["price"] as? Double ?: 0.0,
            capacity = (boothMap["capacity"] as? Long)?.toInt() ?: 0,
            type = boothMap["type"] as? String ?: ""
        )
    } ?: emptyList()

    return Cafe(
        id = id,
        name = this.name ?: "Tanpa Nama",
        description = this.description ?: "",
        address = this.address ?: "",
        location = this.location ?: "",
        imageUrl = this.imageUrl ?: "",
        averageRating = this.averageRating ?: 0.0,
        amenities = this.amenities ?: emptyList(),
        booths = boothList
    )
}

fun CafeDto.toEntity(id: String): CafeEntity {
    val boothList = this.booths?.map { boothMap ->
        Booth(
            id = boothMap["boothId"] as? String ?: "",
            name = boothMap["name"] as? String ?: "",
            price = boothMap["price"] as? Double ?: 0.0,
            capacity = (boothMap["capacity"] as? Long)?.toInt() ?: 0,
            type = boothMap["type"] as? String ?: ""
        )
    } ?: emptyList()

    return CafeEntity(
        id = id,
        name = this.name ?: "Tanpa Nama",
        description = this.description ?: "",
        address = this.address ?: "",
        averageRating = this.averageRating ?: 0.0,
        amenities = this.amenities ?: emptyList(),
        booths = boothList,
        imageUrl = this.imageUrl ?: "",
        location = this.location ?: ""
    )
}

fun CafeEntity.toDomainModel(): Cafe {
    return Cafe(
        id = this.id,
        name = this.name,
        description = this.description,
        address = this.address,
        location = "",
        imageUrl = this.imageUrl,
        averageRating = this.averageRating,
        amenities = this.amenities,
        booths = this.booths
    )
}

fun MangaDto.toEntity(id: String, cafeId: String): MangaEntity {
    return MangaEntity(
        id = id,
        cafeId = cafeId,
        name = this.title ?: "Tanpa Judul",
        imageUrl = this.imageUrl ?: "",
        genres = this.genres ?: emptyList(),
        availableVolumes = this.availableVolumes?.map { it.toInt() } ?: emptyList()
    )
}

fun MangaDto.toDomainModel(id: String): Manga {
    return Manga(
        id = id,
        title = this.title ?: "Tanpa Judul",
        imageUrl = this.imageUrl ?: "",
        genres = this.genres ?: emptyList(),
        // Konversi dari List<Long> ke List<Int>
        availableVolumes = this.availableVolumes?.map { it.toInt() } ?: emptyList()
    )
}

fun MangaEntity.toDomainModel(): Manga {
    return Manga(
        id = this.id,
        title = this.name,
        imageUrl = this.imageUrl ?: "",
        genres = this.genres,
        availableVolumes = this.availableVolumes
    )
}

fun FoodDto.toDomainModel(id: String): Food {
    return Food(
        id = id,
        title = this.title ?: "Tanpa Nama",
        imageUrl = this.imageUrl ?: "",
        description = this.description ?: "",
        price = this.price ?: 0.0,
        category = this.category ?: "",
        size = this.size
    )
}