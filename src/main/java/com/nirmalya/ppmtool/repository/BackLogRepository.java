package com.nirmalya.ppmtool.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nirmalya.ppmtool.domain.BackLog;

@Repository
public interface BackLogRepository  extends CrudRepository<BackLog, Long> {
	BackLog findByProjectIdentifier(String projectIdentifier);
}
