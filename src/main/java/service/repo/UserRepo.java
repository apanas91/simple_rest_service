package service.repo;

import service.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
    Users findByLogin(String login);
    Users findById(long id);
}