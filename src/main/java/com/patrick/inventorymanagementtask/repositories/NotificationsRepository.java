package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.Notifications;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author patrick on 5/18/20
 * @project inventory
 */
@Repository
public interface NotificationsRepository extends CrudRepository<Notifications,Long> {

    Notifications findFirstByMessageId(String messageId);
}
