package com.jiho.expensetracker.enums

import java.util.Objects

enum class TransactionType(val code: String) {
    OUTSIDE_FOOD("Outside Food and Drinks"),
    ELECTRONICS("Electronic Gadgets"),
    KARAOKE("Karaoke"),
    DELIVERY_FOOD("Delivery Food and Drinks"),
    ENTERTAINMENTS("Entertainments"),
    RENT("Rent"),
    PTPTN("PTPTN"),
    PAYMENTS("Payments"),
    TRANSPORTATION("Transportation"),
    SHOPPING("Shopping"),
    INVESTMENT("Investment");

    companion object {
        fun fromCode(code: String): TransactionType? {
            return entries.firstOrNull { Objects.equals(it.code,code) }
        }
    }
}