package com.patrick.inventorymanagementtask.entities.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_category")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category implements Serializable {

    public static String DEFAULT_CATEGORY_NAME="Un-Categorized";
    public static String DEFAULT_CATEGORY_DESCRIPTION="Un-Categorized";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "shop_id")
    private Long shopId;

   /* @JsonIgnore
    @JoinColumn(name = "shop_id", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Shop shopLink;*/

    public Integer getId() {
        return id;
    }

    public Category setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Category setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getShopId() {
        return shopId;
    }

    public Category setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }


}
