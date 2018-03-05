package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Zervice;

@Repository
public interface ZerviceRepository extends JpaRepository<Zervice, Integer> {

	@Query("select z from Zervice z where z.inappropriate is false")
	Collection<Zervice> findAllNotInappropriate();

}
