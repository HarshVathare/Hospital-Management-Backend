package com.withHarsh.MediCore.Repository;

import com.withHarsh.MediCore.Entity.Docter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocterRepository extends JpaRepository<Docter, Long> {

    Docter findByUser(Long docter);

}