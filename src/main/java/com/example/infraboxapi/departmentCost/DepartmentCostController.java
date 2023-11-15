package com.example.infraboxapi.departmentCost;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/department_cost/")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class DepartmentCostController {

    private final DepartmentCostService departmentCostService;


    @GetMapping("/get")
    public ResponseEntity<DepartmentCost> getDepartmentCost() {

        try {
            return ResponseEntity.ok(departmentCostService.getDepartmentCost());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<String> updateDepartmentCost(@Valid @RequestBody DepartmentCostDTO departmentCostDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data: " + "Please check the provided information and try again.");
        }
        try {
            departmentCostService.updateDepartmentCost(departmentCostDTO);
            return ResponseEntity.ok("Department Cost updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating department cost: " + e.getMessage());
        }
    }
}

