package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.Invoices;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author patrick on 4/17/20
 * @project inventory
 */
@Repository
public interface InvoicesRepository extends CrudRepository<Invoices,Long> {

    Invoices findTopByShopIdAndSupplierIdAndInvoiceNoAndInvoiceDate(Long shopId,Long supplierId,String invoiceNo,String invoiceDate);

    Invoices findTopByIdAndShopId(Long id,Long shopId);
}
