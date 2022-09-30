package iskander.tabaev.admin.repositories;


import iskander.tabaev.admin.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepositories extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByLogin(String name);
}
