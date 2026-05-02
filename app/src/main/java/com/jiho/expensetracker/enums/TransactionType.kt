package com.jiho.expensetracker.enums

import java.util.Objects

enum class TransactionType(val code: String) {
    OUTSIDE_FOOD("Outside Food and Drinks"),
    DELIVERY_FOOD("Delivery Food and Drinks"),
    RENT("Rent"),
    ELECTRONICS("Electronic Gadgets"),
    KARAOKE("Karaoke"),
    ENTERTAINMENTS("Entertainments"),
    PTPTN("PTPTN"),
    PAYMENTS("Payments"),
    TRANSPORTATION("Transportation"),
    SHOPPING("Shopping"),
    INVESTMENT("Investment");
}