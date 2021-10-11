package ru.awm.newsblog.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.awm.newsblog.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
