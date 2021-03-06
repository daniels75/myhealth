package org.daniels.jhipster.myhealth.repository;

import org.daniels.jhipster.myhealth.domain.Points;
import org.joda.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Points entity.
 */
public interface PointsRepository extends JpaRepository<Points,Long> {

    @Query("select points from Points points where points.user.login = ?#{principal.username} order by points.date desc")
    Page<Points> findAllForCurrentUser(Pageable pageable);

    List<Points> findAllByDateBetweenAndUserLogin(LocalDate firstDate, LocalDate secondDate, String login);

    Page<Points> findAllByOrderByDateDesc(Pageable pageable);
}
