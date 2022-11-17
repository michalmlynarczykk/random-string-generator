package michalmlynarczyk.recruitmenttask.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSpecificationRepository extends JpaRepository<UserSpecification, Long> {
}
