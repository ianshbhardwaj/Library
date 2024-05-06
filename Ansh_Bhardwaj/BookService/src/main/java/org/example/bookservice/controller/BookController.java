package org.example.bookservice.controller;

import lombok.AllArgsConstructor;
import org.example.bookservice.model.Book;
import org.example.bookservice.repository.BookRepository;
import org.example.bookservice.service.BookService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        if (!bookService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        updatedBook.setId(id);
        bookService.save(updatedBook);
        return ResponseEntity.ok("Updated data stored successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateAvailability/{id}")
    public ResponseEntity<String> updateBookAvailability(@PathVariable Long id, @RequestParam String status) {
        Optional<Book> optionalBook = bookService.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setAvailability(status);
            bookService.save(book);
            return ResponseEntity.ok("Book availability updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/books/{id}/status/{status}")
    public ResponseEntity<String> updateBookStatus(@PathVariable Long id, @PathVariable String status) {
        boolean updated = bookService.updateBookStatus(id, status);
        if (updated) {
            return ResponseEntity.ok("Book status updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found or status update failed");
        }
    }

}

