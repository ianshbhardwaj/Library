package org.example.borrowingservice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "book-service")
public interface BookServiceFeignClient {

    @GetMapping("/books")
    List<BookDTO> getAllBooks();


    @PutMapping("/books/{id}/status/{status}")
    void updateBookStatus(@PathVariable("id") Long id, @PathVariable("status") String status);


}