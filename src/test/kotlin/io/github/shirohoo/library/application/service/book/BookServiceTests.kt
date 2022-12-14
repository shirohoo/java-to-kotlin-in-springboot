package io.github.shirohoo.library.application.service.book

import io.github.shirohoo.library.application.persistence.book.BookJpaRepository
import io.github.shirohoo.library.application.persistence.user.UserJpaRepository
import io.github.shirohoo.library.domain.book.Book
import io.github.shirohoo.library.domain.book.BookType
import io.github.shirohoo.library.domain.user.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTests @Autowired constructor(
    private var sut: BookService,
    private var userJpaRepository: UserJpaRepository,
    private var bookJpaRepository: BookJpaRepository
) {
    @Test
    fun `새로운 도서를 저장한다`() {
        val newBook = bookFixture()
        assertDoesNotThrow { sut.saveBook(newBook) }
    }

    @Test
    fun `도서를 대출할 수 있다`() {
        // given
        val newUser = User("username")
        userJpaRepository.save(newUser)

        val newBook = bookFixture()
        sut.saveBook(newBook)

        // when and then
        assertDoesNotThrow { sut.loanBook("username", "book title") }
    }

    @Test
    fun `이미 대출된 도서를 대출할수는 없다`() {
        // given
        val newUser = User("username")
        userJpaRepository.save(newUser)

        val newBook = bookFixture()
        sut.saveBook(newBook)

        sut.loanBook("username", "book title")

        // when and then
        assertThrows<IllegalArgumentException>("this book has already been borrowed.") {
            sut.loanBook("username", "book title")
        }
    }

    @Test
    fun `대출한 도서를 반납할 수 있다`() {
        // given
        val newUser = User("username")
        userJpaRepository.save(newUser)

        val newBook = bookFixture()
        sut.saveBook(newBook)

        sut.loanBook("username", "book title")

        // when and then
        assertDoesNotThrow { sut.returnBook("username", "book title") }
    }

    @Test
    fun `대출한 도서의 수량을 확인할 수 있다`() {
        // given
        val newUser = User("username")
        userJpaRepository.save(newUser)

        val newBook = bookFixture()
        sut.saveBook(newBook)

        sut.loanBook("username", "book title")

        // when
        val loanedCount = sut.countLoanedBooks()

        // then
        assertThat(loanedCount).isEqualTo(1)
    }

    @Test
    fun `대출중인 도서들의 현황을 파악할 수 있다`() {
        // given
        sut.saveBook(Book("clean code", BookType.COMPUTER))
        sut.saveBook(Book("clean architecture", BookType.COMPUTER))
        sut.saveBook(Book("computer science", BookType.SCIENCE))

        // when
        val statistics = sut.getBookStatistics()

        // then
        assertThat(statistics[BookType.COMPUTER]).hasSize(2)
        assertThat(statistics[BookType.SCIENCE]).hasSize(1)
    }

    @AfterEach
    fun tearDown() {
        userJpaRepository.deleteAll()
        bookJpaRepository.deleteAll()
    }

    fun bookFixture(): Book {
        return Book("book title", BookType.COMPUTER)
    }
}