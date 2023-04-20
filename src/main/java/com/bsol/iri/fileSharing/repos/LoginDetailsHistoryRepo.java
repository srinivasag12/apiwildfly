package com.bsol.iri.fileSharing.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bsol.iri.fileSharing.entity.LoginDetailsHistory;

public interface LoginDetailsHistoryRepo extends JpaRepository<LoginDetailsHistory, Integer> {

	LoginDetailsHistory findByEmailAndStatus(String email, int status);
	
}
