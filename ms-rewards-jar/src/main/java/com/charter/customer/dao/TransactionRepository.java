package com.charter.customer.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.charter.customer.beans.Transaction;

/**
 * This repository class handles all Transaction table query
 * @author saiku
 *
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {


	/**
	 * This method fetches all the transactions made by customer during provided search date range
	 * @param customerId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Query("Select tx from Transaction as tx where tx.customer.id=:customerId AND tx.createdTs BETWEEN :startDate AND :endDate order by tx.createdTs asc")
	List<Transaction>findTransactionByCustomerIdAndCreatedTsBetween(@Param("customerId") int customerId,
			@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
	
}
