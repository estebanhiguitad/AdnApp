package com.example.domain.model.debtcollector

import com.example.domain.model.Vehicle

interface IDebtCollector {
    fun getTotal (vehicle: Vehicle):Double
}