package com.example.infraboxapi.calculation;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_calculation")
public class Calculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer shiftLength;
    private Integer numberOfMachines;
    private double hourlyRate;
    private double income;
    private double toolCost;
    private double materialCost;
    private double factor;
    private double camTime;
    private double variableCostsII;
    private double variableCostsI;
    private double leasingPrice;
    private double toolsPrice;
    private double depreciationPrice;
    private double mediaPrice;
    private double pricePerKwh;
    private double operatingHours;
    private double powerConsumption;
    private double employeeCosts;
    private String status;
    private String selectedDate;
    private String calculationName;
    private double billingPeriod;
    private double startupFee;
    private double cncOrderValuation;
}
