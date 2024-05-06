package org.example.borrowingservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowingService {
    private final BookServiceFeignClient bookServiceFeignClient;

    @Autowired
    public BorrowingService(BookServiceFeignClient bookServiceFeignClient) {
        this.bookServiceFeignClient = bookServiceFeignClient;
    }

    public void updateBookStatus(Long bookId, String status) {
        bookServiceFeignClient.updateBookStatus(bookId, status);
    }
}
