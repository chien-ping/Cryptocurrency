package com.crypto.model;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * 幣別對應
 */
@Data
@Entity
@Table(name = "currency")
public class Currency {

    /**
     * 幣別代碼
     */
    @Id
    @Column(name = "code")
    private String code;

    /**
     * 幣別中文名稱
     */
    @Column(name = "name_zh")
    private String nameZh;

    /**
     * 建立時間
     */
    @Column(name = "created_date")
    private Date createdDate;

    /**
     * 最後更新時間
     */
    @UpdateTimestamp
    @Column(name = "modified_date")
    private Date modifiedDate;
}
