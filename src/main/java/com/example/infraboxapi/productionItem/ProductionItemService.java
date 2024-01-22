package com.example.infraboxapi.productionItem;

import com.example.infraboxapi.FilePDF.FilePDF;
import com.example.infraboxapi.FilePDF.FilePDFService;
import com.example.infraboxapi.notification.NotificationDescription;
import com.example.infraboxapi.notification.NotificationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@AllArgsConstructor
public class ProductionItemService {

    private final ProductionItemRepository productionItemRepository;
    private final FilePDFService filePDFService;
    private final NotificationService notificationService;

    @Transactional
    public void createProductionItem(ProductionItemDTO productionItemDTO) throws IOException {
        ProductionItem productionItem = ProductionItem.builder()
                .partName(productionItemDTO.getPartName())
                .quantity(productionItemDTO.getQuantity())
                .status(productionItemDTO.getStatus())
                .camTime(productionItemDTO.getCamTime())
                .materialValue(productionItemDTO.getMaterialValue())
                .partType(productionItemDTO.getPartType())
                .build();

        if(productionItemDTO.getFilePDF() != null) {

            FilePDF filePDF = filePDFService.createFile(productionItemDTO.getFilePDF());
            productionItem.setFilePDF(filePDF);
        }

        productionItemRepository.save(productionItem);

        notificationService.createAndSendNotification("A new production item has been added: " + productionItem.getPartName(), NotificationDescription.ProductionItemAdded);

    }

    public Iterable<ProductionItem> getProductionItems() {
        return productionItemRepository.findAll();
    }

    public void deleteProductionItem(Integer id) {

        ProductionItem pr = productionItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Production Item not found"));
        productionItemRepository.deleteById(id);


        notificationService.createAndSendNotification("A production item has been deleted: " + pr.getPartName() , NotificationDescription.ProductionItemDeleted);
    }

    public void updateProductionItem(ProductionItemDTO productionItemDTO) throws IOException {
        ProductionItem productionItem = productionItemRepository.findById(productionItemDTO.getId()).orElseThrow(() -> new RuntimeException("Production Item not found"));

        productionItem.setPartName(productionItemDTO.getPartName());
        productionItem.setQuantity(productionItemDTO.getQuantity());
        productionItem.setStatus(productionItemDTO.getStatus());
        productionItem.setCamTime(productionItemDTO.getCamTime());
        productionItem.setMaterialValue(productionItemDTO.getMaterialValue());
        productionItem.setPartType(productionItemDTO.getPartType());

        if(productionItemDTO.getFilePDF() != null) {
            FilePDF filePDF = filePDFService.updateFile(productionItemDTO.getFilePDF(), productionItem.getFilePDF());
            productionItem.setFilePDF(filePDF);
        }

        productionItemRepository.save(productionItem);

        notificationService.createAndSendNotification(
                "The production item '" + productionItem.getPartName() + "' has been updated successfully.",
                NotificationDescription.ProductionItemUpdated
        );
    }
}