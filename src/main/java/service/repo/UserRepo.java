package service.repo;

import service.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    Users findById(long id);
}