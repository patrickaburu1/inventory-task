package com.patrick.inventorymanagementtask.config;


import com.patrick.inventorymanagementtask.entities.configs.AppSettings;
import com.patrick.inventorymanagementtask.entities.configs.BusinessSellConfigs;
import com.patrick.inventorymanagementtask.repositories.AppSettingsRepository;
import com.patrick.inventorymanagementtask.repositories.BusinessSellConfigsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author patrick on 6/12/20
 * @project inventory
 */
@Service
public class AppSettingService {

    @Autowired
    private AppSettingsRepository appSettingsRepository;
    @Autowired
    private BusinessSellConfigsRepository businessSellConfigsRepository;

    public AppSettings getAppSettings(String settingsKey) {
        return appSettingsRepository.findByCode(settingsKey);
    }

    public Boolean shouldSellBelowPrices(Long shopId) {
        BusinessSellConfigs sellConfigs = businessSellConfigsRepository.findFirstByShopId(shopId);
        if (null == sellConfigs)
            return false;
        else
            return sellConfigs.getConfigValue();

    }

    public BusinessSellConfigs updateSetting(Long shopId, Integer userId) {
        BusinessSellConfigs sellConfigs = businessSellConfigsRepository.findFirstByShopId(shopId);
        if (null == sellConfigs) {
            sellConfigs = new BusinessSellConfigs();
            sellConfigs.setConfigValue(true);
            sellConfigs.setCreatedBy(userId);
            sellConfigs.setUpdatedBy(userId);
            sellConfigs.setShopId(shopId);
        } else if (sellConfigs.getConfigValue()) {
            sellConfigs.setConfigValue(false);
            sellConfigs.setUpdatedBy(userId);
            sellConfigs.setUpdatedOn(new Date());
        } else {
            sellConfigs.setConfigValue(true);
            sellConfigs.setUpdatedBy(userId);
            sellConfigs.setUpdatedOn(new Date());
        }
      return   businessSellConfigsRepository.save(sellConfigs);
    }
}
