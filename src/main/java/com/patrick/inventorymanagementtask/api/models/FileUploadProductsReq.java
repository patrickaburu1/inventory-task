package com.patrick.inventorymanagementtask.api.models;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author patrick on 11/13/20
 * @project inventory
 */
public class FileUploadProductsReq {
    @NotNull
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
