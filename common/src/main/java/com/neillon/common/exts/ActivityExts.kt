package com.neillon.common.exts

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun FragmentManager.commit(block: FragmentTransaction.() -> Unit) {
    val transaction = beginTransaction()
    block(transaction)
    transaction.commit()
}