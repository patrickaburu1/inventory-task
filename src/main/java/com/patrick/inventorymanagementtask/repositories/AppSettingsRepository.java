package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.configs.AppSettings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author patrick on 6/12/20
 * @project inventory
 */
@Repository
public interface AppSettingsRepository extends CrudRepository<AppSettings,Long> {

    /**
     * Fetch all app settings list
     *
     * @return List<AppSettings>
     */
    public AppSettings findByCode(String code);
}
