package com.example.infraboxapi.productionItem;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductionItemDTO {
    private Integer id;
    @NotBlank(message = "Field 'part name' cannot be blank")
    @Size(min = 2, max = 100, message = "Field 'part name' must have a length between 2 and 100 characters")
    private String partName;
    @PositiveOrZero(message = "Value must be a positive number or zero")
    private float quantity;
    @NotBlank(message = "Field 'status' cannot be blank")
    @Size(min = 2, max = 100, message = "Field 'status' must have a length between 2 and 100 characters")
    private String status;
    @PositiveOrZero(message = "Value must be a positive number or zero")
    private double camTime;
    @PositiveOrZero(message = "Value must be a positive number or zero")
    private double startUpTime;
    @PositiveOrZero(message = "Value must be a positive number or zero")
    private double finishingTime;
    @PositiveOrZero(message = "Value must be a positive number or zero")
    private double totalTime;
    @PositiveOrZero(message = "Value must be a positive number or zero")
    private double fixtureTime;
    @PositiveOrZero(message = "Value must be a positive number or zero")
    private double factor;
    @PositiveOrZero(message = "Value must be a positive number or zero")
    private BigDecimal materialValue;
    @PositiveOrZero(message = "Value must be a positive number or zero")
    private BigDecimal toolValue;
    @NotBlank(message = "Field 'part type' cannot be blank")
    @Size(min = 2, max = 100, message = "Field 'part type' must have a length between 2 and 100 characters")
    private String partType;
    @NotBlank(message = "Field 'type of processing' cannot be blank")
    private String typeOfProcessing;

    private MultipartFile filePDF;
    private Integer projectID;



    private Integer materialTypeID;
    @DecimalMin(value = "0", message = "Price per kg must be greater than or equal to 0")
    private BigDecimal pricePerKg;

    private String type;
    @PositiveOrZero(message = "Value must be a positive number or zero")
    private float z;
    @PositiveOrZero(message = "Value must be a positive number or zero")
    private float y;
    @PositiveOrZero(message = "Value must be a positive number or zero")
    private float x;
    @PositiveOrZero(message = "Value must be a positive number or zero")
    private float diameter;
    @PositiveOrZero(message = "Value must be a positive number or zero")
    private float length;
    @PositiveOrZero(message = "Value must be a positive number or zero")
    private float thickness;
}
