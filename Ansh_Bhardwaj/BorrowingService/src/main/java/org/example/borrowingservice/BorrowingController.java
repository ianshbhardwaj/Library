package org.example.borrowingservice;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class BorrowingController {
    private final BookServiceFeignClient bookClient;
    private final BorrowingService borrowingService;
    @GetMapping("/borrowing-service/books")
    ResponseEntity<String> getBooks()
    {
        return ResponseEntity.ok(bookClient.getAllBooks().toString());
    }

    @PutMapping("/borrow/{bookId}/{status}")
    public void updateBookStatus(@PathVariable Long bookId, @PathVariable String status) {
        borrowingService.updateBookStatus(bookId, status);
    }

}
