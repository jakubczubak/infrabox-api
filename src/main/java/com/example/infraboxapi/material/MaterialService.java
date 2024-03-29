package com.example.infraboxapi.material;

import com.example.infraboxapi.materialGroup.MaterialGroup;
import com.example.infraboxapi.materialGroup.MaterialGroupRepository;
import com.example.infraboxapi.materialPriceHistory.MaterialPriceHistory;
import com.example.infraboxapi.notification.NotificationDescription;
import com.example.infraboxapi.notification.NotificationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class MaterialService {

    private final MaterialGroupRepository materialGroupRepository;
    private final MaterialRepository materialRepository;
    private final NotificationService notificationService;

    @Transactional
    public void createMaterial(MaterialDTO materialDTO) {

        MaterialGroup materialGroup = materialGroupRepository.findById(materialDTO.getMaterialGroupID())
                .orElseThrow(() -> new RuntimeException("Material group not found"));


        Material newMaterial = Material.builder()

                .diameter(materialDTO.getDiameter())
                .length(materialDTO.getLength())
                .thickness(materialDTO.getThickness())
                .name(materialDTO.getName())
                .price(materialDTO.getPrice())
                .pricePerKg(materialDTO.getPricePerKg())
                .minQuantity(materialDTO.getMinQuantity())
                .quantity(materialDTO.getQuantity())
                .z(materialDTO.getZ())
                .y(materialDTO.getY())
                .x(materialDTO.getX())
                .type(materialDTO.getType())
                .quantityInTransit(materialDTO.getQuantityInTransit())

                .build();

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        MaterialPriceHistory materialPriceHistory = MaterialPriceHistory.builder()
                .price(materialDTO.getPricePerKg())
                .date(currentDateTime.format(formatter)).

                build();

        newMaterial.setMaterialPriceHistoryList(new ArrayList<>());
        newMaterial.getMaterialPriceHistoryList().add(materialPriceHistory);

        materialGroup.getMaterials().add(newMaterial);

        materialGroupRepository.save(materialGroup);

        notificationService.createAndSendNotification("A new material '" + newMaterial.getName() + "` has been added successfully.", NotificationDescription.MaterialAdded);
    }

    @Transactional
    public void deleteMaterial(Integer id) {
        String materialName = materialRepository.findById(id).orElseThrow(() -> new RuntimeException("Material not found")).getName();
        materialRepository.deleteById(id);
        notificationService.createAndSendNotification("The material '" + materialName + "' has been successfully deleted.", NotificationDescription.MaterialDeleted);
    }

    @Transactional
    public void updateMaterial(MaterialDTO materialDTO) {

        Material material = materialRepository.findById(materialDTO.getId())
                .orElseThrow(() -> new RuntimeException("Material not found"));


        //Check if pricePerKg is changed, if yes, add new price to history, if not, do nothing, just update the material

        if (material.getPricePerKg().compareTo(materialDTO.getPricePerKg()) != 0) {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            MaterialPriceHistory materialPriceHistory = MaterialPriceHistory.builder()
                    .price(materialDTO.getPricePerKg())
                    .date(currentDateTime.format(formatter)).

                    build();

            material.getMaterialPriceHistoryList().add(materialPriceHistory);
        }

        material.setPricePerKg(materialDTO.getPricePerKg());
        material.setPrice(materialDTO.getPrice());
        material.setMinQuantity(materialDTO.getMinQuantity());
        material.setQuantity(materialDTO.getQuantity());
        material.setZ(materialDTO.getZ());
        material.setY(materialDTO.getY());
        material.setX(materialDTO.getX());
        material.setDiameter(materialDTO.getDiameter());
        material.setLength(materialDTO.getLength());
        material.setThickness(materialDTO.getThickness());
        material.setName(materialDTO.getName());
        material.setType(materialDTO.getType());
        material.setQuantityInTransit(materialDTO.getQuantityInTransit());

        materialRepository.save(material);

        notificationService.createAndSendNotification("The material '" + material.getName() + "' has been successfully updated.", NotificationDescription.MaterialUpdated);
    }


}
