package com.ec.proharvest.web.rest;

import com.ec.proharvest.service.impl.OnPremiseReporting;
import com.google.common.net.HttpHeaders;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpStatus;

import io.github.jhipster.web.util.PaginationUtil;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/api")
public class ReportResource {
    private final OnPremiseReporting reportsManager;

    public ReportResource(OnPremiseReporting reportsManager){
        this.reportsManager = reportsManager;
    }

    @GetMapping("/reports/compile")
    ResponseEntity<String> compileReport() {
    try{
        this.reportsManager.compileReportTemplate("example");
      // HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), "ok");      
      return new ResponseEntity<>("ok", HttpStatus.OK);
    } catch( Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
    }    
    }

    @GetMapping("/reports/generate")
    ResponseEntity<String> generateReport() {
    try{
        this.reportsManager.generateReport("example", "PDF");
      // HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), "ok");      
      return new ResponseEntity<>("ok", HttpStatus.OK);
    } catch( Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
    }    
    }    
  
//     @PostMapping("/reports")
//     Employee newEmployee(@RequestBody Employee newEmployee) {
//       return repository.save(newEmployee);
//     }
  
//     // Single item
  
//     @GetMapping("/reports/{id}")
//     Employee one(@PathVariable Long id) {
  
//       return repository.findById(id)
//         .orElseThrow(() -> new EmployeeNotFoundException(id));
//     }
  
//     @PutMapping("/reports/{id}")
//     Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
  
//       return repository.findById(id)
//         .map(employee -> {
//           employee.setName(newEmployee.getName());
//           employee.setRole(newEmployee.getRole());
//           return repository.save(employee);
//         })
//         .orElseGet(() -> {
//           newEmployee.setId(id);
//           return repository.save(newEmployee);
//         });
//     }
  
//     @DeleteMapping("/reports/{id}")
//     void deleteEmployee(@PathVariable Long id) {
//       repository.deleteById(id);
//     }
//   }

    
}
