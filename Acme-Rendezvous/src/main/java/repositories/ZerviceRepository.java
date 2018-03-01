package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Zervice;

@Repository
public interface ZerviceRepository extends JpaRepository<Zervice, Integer> {

}
