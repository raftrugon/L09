package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Zervice;

@Repository
public interface ZerviceRepository extends JpaRepository<Zervice, Integer> {

	@Query("select z from Zervice z where z.inappropriate is false")
	Collection<Zervice> findAllNotInappropriate();

	@Query("select coalesce(avg(r.requests.size),0), coalesce(stddev(r.requests.size),0) from Rendezvous r")
	Double[] getZerviceAvgStdPerRendezvous();

	@Query("select min(r.requests.size),max(r.requests.size) from Rendezvous r")
	Double[] getZerviceMinMaxPerRendezvous();

	@Query("select z from Zervice z where z.requests.size = (select max(x.requests.size) from Zervice x) order by z.requests.size DESC")
	Collection<Zervice> getBestSellingZervices();

	@Query("select z from Zervice z order by z.requests.size DESC")
	Page<Zervice> findAllOrderedByRequestsSize(Pageable page);

}
