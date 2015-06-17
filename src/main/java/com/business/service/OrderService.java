package com.business.service;

import com.business.domain.Purchase;

/**
 * Created by billb on 2015-04-10.
 */
public interface OrderService {
    void seavOrder(Purchase purchase);

    void verifyOrderSheet(String sheetId);

    void cancelOrdersBySheetId(String sheetId);

}
