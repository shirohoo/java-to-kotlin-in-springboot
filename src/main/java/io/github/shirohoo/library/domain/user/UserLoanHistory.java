package io.github.shirohoo.library.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class UserLoanHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String bookTitle;

    private boolean isReturn;

    public UserLoanHistory() {
    }

    public UserLoanHistory(User user, String bookTitle, boolean isReturn) {
        this.user = user;
        this.bookTitle = bookTitle;
        this.isReturn = isReturn;
    }

    public String getBookTitle() {
        return this.bookTitle;
    }

    public void doReturn() {
        this.isReturn = true;
    }
}
