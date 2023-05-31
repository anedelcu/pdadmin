package pdadmin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pdadmin.model.User;

public interface UserRepository extends JpaRepository<Integer, User> {

}
