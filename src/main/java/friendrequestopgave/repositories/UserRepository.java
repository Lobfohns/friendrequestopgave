package friendrequestopgave.repositories;

import friendrequestopgave.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAll();

    Optional<User> findByEmail(String email);

    User findUserById(Integer id);

    User findUserByEmail(String email);

}
