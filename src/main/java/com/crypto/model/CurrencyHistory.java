package com.crypto.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 幣別對應歷史資料
 */
@Data
@Entity
@Table(name = "currency_history")
public class CurrencyHistory {

    /**
     * Id 自增主鍵
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 幣別代碼
     */
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
     * 刪除時間
     */
    @Column(name = "deleted_date")
    private Date deletedDate;
}
