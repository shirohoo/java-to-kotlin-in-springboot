package io.github.shirohoo.library.application.controller.book

import io.github.shirohoo.library.application.data.book.BookLoanRequest
import io.github.shirohoo.library.application.data.book.BookRequest
import io.github.shirohoo.library.application.data.book.BookReturnRequest
import io.github.shirohoo.library.application.data.book.BookStatResponse
import io.github.shirohoo.library.application.service.book.BookService
import io.github.shirohoo.library.domain.book.Book
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/book")
class BookController(
    private val bookService: BookService
) {
    @GetMapping("/loan")
    fun countLoanedBooks(): Int = bookService.countLoanedBooks()

    @GetMapping("/stat")
    fun getBookStatistics(): List<BookStatResponse> {
        return bookService.getBookStatistics()
            .map { BookStatResponse(it.key, it.value.count()) }
    }

    @PostMapping
    fun saveBook(@RequestBody request: BookRequest) {
        val (title, type) = request
        val newBook = Book(title, type)
        bookService.saveBook(newBook)
    }

    @PostMapping("/loan")
    fun loanBook(@RequestBody request: BookLoanRequest) {
        val (username, bookTitle) = request
        bookService.loanBook(username, bookTitle)
    }

    @PutMapping("/return")
    fun returnBook(@RequestBody request: BookReturnRequest) {
        val (username, bookTitle) = request
        bookService.returnBook(username, bookTitle)
    }
}