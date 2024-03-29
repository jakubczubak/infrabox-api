package com.example.infraboxapi.material;

import com.example.infraboxapi.common.CommonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/material/")
@AllArgsConstructor
public class MaterialController {

    private final MaterialService materialService;
    private final CommonService commonService;

    @PostMapping("/create")
    public ResponseEntity<String> createMaterial(@Valid @RequestBody MaterialDTO materialDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return commonService.handleBindingResult(bindingResult);
        }

        try {
            materialService.createMaterial(materialDTO);
            return ResponseEntity.ok("Material created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating material: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateMaterial(@Valid @RequestBody MaterialDTO materialDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return commonService.handleBindingResult(bindingResult);
        }

        try {
            materialService.updateMaterial(materialDTO);
            return ResponseEntity.ok("Material updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating material: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMaterial(@PathVariable Integer id) {
        try {
            materialService.deleteMaterial(id);
            return ResponseEntity.ok("Material deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting material: " + e.getMessage());
        }
    }
}
