package io.github.shirohoo.library.application.persistence.book

import io.github.shirohoo.library.domain.book.Book
import io.github.shirohoo.library.domain.book.BookRepository
import org.springframework.stereotype.Repository

@Repository
class BookRepositoryImpl(
    private val jpaRepository: BookJpaRepository
) : BookRepository {
    override fun save(newBook: Book): Book = jpaRepository.save(newBook)

    override fun findAll(): List<Book> = jpaRepository.findAll()

    override fun findBy(bookTitle: String): Book {
        return jpaRepository.findByTitle(bookTitle) ?: throw NoSuchElementException("book not found.")
    }
}