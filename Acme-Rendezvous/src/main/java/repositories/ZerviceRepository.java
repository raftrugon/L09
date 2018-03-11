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

	@Query("select coalesce(avg(r.requests.size),0),max(r.requests.size),min(r.requests.size)," +
			"coalesce(stddev(r.requests.size),0) from Rendezvous r")
	Double[] getZerviceStatsPerRendezvous();

	@Query("select z from Zervice z where z.requests.size = (select max(x.requests.size) from Zervice x)")
	Collection<Zervice> getBestSellingZervices();
}
