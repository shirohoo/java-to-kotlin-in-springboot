package io.github.shirohoo.library.persistance.user;

import io.github.shirohoo.library.domain.user.UserLoanHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoanHistoryJpaRepository extends JpaRepository<UserLoanHistory, Long> {
    boolean existsByBookTitleAndIsReturn(String bookTitle, boolean isReturn);
}