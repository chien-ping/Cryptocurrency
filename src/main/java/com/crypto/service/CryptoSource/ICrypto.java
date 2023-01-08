package com.crypto.service.CryptoSource;

import com.crypto.dto.CryptoRateDto;

/**
 * 虛擬貨幣資料來源處理界面
 */
public interface ICrypto {

    /**
     * 取得匯率資料
     * @return 貨幣資訊
     */
    CryptoRateDto getRateData();
}
