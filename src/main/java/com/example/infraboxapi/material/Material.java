package com.example.infraboxapi.material;

import com.example.infraboxapi.materialPriceHistory.MaterialPriceHistory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_material")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private BigDecimal pricePerKg;
    private float minQuantity;
    private float quantity;

    private float z;
    private float y;
    private float x;
    private float diameter;

    private float length;

    private float thickness;

    private String name;

    private BigDecimal price;

    private String type;

    private float quantityInTransit;
    private String additionalInfo;

    @Column(name = "updated_on")
    private String updatedOn;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "material_id")
    private List<MaterialPriceHistory> materialPriceHistoryList;


    @PreUpdate
    public void preUpdate() {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        updatedOn = now.format(formatter);
    }

}
