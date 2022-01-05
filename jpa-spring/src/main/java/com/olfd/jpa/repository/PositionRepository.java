package com.olfd.jpa.repository;

import com.olfd.jpa.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long>, RevisionRepository<Position, Long, Long> {
}
