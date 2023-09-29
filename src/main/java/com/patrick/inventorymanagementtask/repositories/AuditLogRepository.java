package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.user.AuditLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author patrick on 9/3/19
 * @project shop-pos
 */
@Repository
public interface AuditLogRepository extends CrudRepository<AuditLog,Long> {

}
