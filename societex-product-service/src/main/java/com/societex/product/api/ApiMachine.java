package com.societex.product.api;

import com.societex.product.model.Machine;
import com.societex.product.playload.ApiResponse;
import com.societex.product.playload.error.ErrorSection;
import com.societex.product.playload.product.MachineRequest;
import com.societex.product.playload.product.MachineResponse;
import com.societex.product.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("api/machines")
public class ApiMachine {
    @Autowired
    MachineService machineService;

    @GetMapping(value = "", name = "Liste des machines")
    public ApiResponse getAllMachines(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(value = "sort", required = false, defaultValue = "name") String sort,
            @RequestParam(value = "direction", defaultValue = "asc", required = false) String direction,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        Page<Machine> machinePage = machineService.getPaginatedMachines(page, size, sort, direction, keyword);
        MachineResponse machineResponse = new MachineResponse();
        machineResponse.setPageStats(machinePage);
        machineResponse.setItems(machinePage.getContent());
        return new ApiResponse(
                true,
                HttpStatus.OK,
                "Liste des machines",
                machineResponse
        );
    }

    @GetMapping(value = "/{machineId}", name = "Avoir un machine")

    public ApiResponse getMachine(@PathVariable(value = "machineId") Integer machineId) {
        Machine currentMachine = machineService.findMachineById(machineId);
        if (currentMachine == null) {
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Le machine avec l'id = " + machineId + " est introuvable",
                    null
            );
        }
        return new ApiResponse(
                true,
                HttpStatus.OK,
                currentMachine.getCode() + " - " + currentMachine.getName(),
                currentMachine
        );
    }

    @PostMapping(value = "", name = "Création d'un machine")
    public ApiResponse createMachine(@RequestBody @Valid MachineRequest machineRequest, BindingResult bindingResult) {
        Machine existMachineByCode = machineService.findMachineByCode(machineRequest.getCode());
        Machine existMachineByName = machineService.findMachineByName(machineRequest.getName());
        if (existMachineByCode != null) {
            bindingResult.rejectValue("code", "error.machine", "Le code existe déjà");
        }
        if (existMachineByName != null) {
            bindingResult.rejectValue("name", "error.machine", "Le nom existe déjà");
        }
        if (machineRequest.getPriceUnit().compareTo(BigDecimal.ZERO) < 0) {
            bindingResult.rejectValue("price", "error.machine", "Le prix de vente doit être positif");
        }
        if (machineRequest.getValidityDate().toInstant().isBefore(new Date().toInstant()) || machineRequest.getValidityDate().toInstant().equals(new Date().toInstant())) {
            bindingResult.rejectValue("validityDate", "error.machine", "La date de validité doit être supérieur à la date du jour");
        }
        if (bindingResult.hasErrors()) {
            ErrorSection es = new ErrorSection(machineRequest, bindingResult.getAllErrors());
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Erreur lors de la creation",
                    es
            );
        }
        Machine machine = machineService.create(machineRequest);
        return new ApiResponse(
                true,
                HttpStatus.OK,
                "Machine créé avec succès",
                machine
        );
    }


    @PutMapping(value = "/machines/{machineId}", name = "Modification d'un machine")

    public ApiResponse updateMachine(@PathVariable(value = "machineId") Integer machineId, @RequestBody @Valid MachineRequest machineRequest, BindingResult bindingResult) {
        Machine currentMachine = machineService.findMachineById(machineId);
        if (currentMachine == null) {
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Le machine avec l'id = " + machineId + " est introuvable",
                    new ErrorSection(machineRequest, null)
            );
        }

        Machine existMachineByCode = machineService.findMachineByCodeAndId(machineRequest.getCode(), currentMachine.getId());
        Machine existMachineByName = machineService.findMachineByNameAndId(machineRequest.getName(), currentMachine.getId());
        if (existMachineByCode != null) {
            bindingResult.rejectValue("code", "error.machine", "Le code existe déjà");
        }
        if (existMachineByName != null) {
            bindingResult.rejectValue("name", "error.machine", "Le nom existe déjà");
        }
        if (machineRequest.getPriceUnit().compareTo(BigDecimal.ZERO) < 0) {
            bindingResult.rejectValue("price", "error.machine", "Le prix de vente doit être positif");
        }
        if (machineRequest.getValidityDate().toInstant().isBefore(new Date().toInstant()) || machineRequest.getValidityDate().toInstant().equals(new Date().toInstant())) {
            bindingResult.rejectValue("validityDate", "error.machine", "La date de validité doit etre superieur au date du jour");
        }
        if (bindingResult.hasErrors()) {
            ErrorSection es = new ErrorSection(machineRequest, bindingResult.getAllErrors());
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Erreur lors de la creation",
                    es
            );
        }
        if (bindingResult.hasErrors()) {
            ErrorSection es = new ErrorSection(machineRequest, bindingResult.getAllErrors());
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Erreur lors de la mise à jour",
                    es
            );
        }
        Machine machine = machineService.update(currentMachine, machineRequest);
        return new ApiResponse(
                true,
                HttpStatus.OK,
                "Machine modifié avec succès",
                machine
        );
    }

    @DeleteMapping(value = "/machines/{machineId}", name = "Suppression d'un machine")
    public ApiResponse deleteMachine(@PathVariable(value = "machineId") Integer machineId) {
        Machine currentMachine = machineService.findMachineById(machineId);
        if (currentMachine == null) {
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Le machine avec l'id = " + machineId + " est introuvable",
                    null
            );
        }
        machineService.delete(currentMachine);
        return new ApiResponse(
                true,
                HttpStatus.OK,
                "Machine supprimé avec succès",
                null
        );
    }
}
