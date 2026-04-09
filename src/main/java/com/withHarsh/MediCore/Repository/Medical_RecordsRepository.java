package com.withHarsh.MediCore.Repository;

import com.withHarsh.MediCore.Entity.Medical_Records;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Medical_RecordsRepository extends JpaRepository<Medical_Records, Long> {
}
