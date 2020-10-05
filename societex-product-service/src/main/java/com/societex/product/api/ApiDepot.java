package com.societex.product.api;

import com.societex.product.model.Depot;
import com.societex.product.playload.ApiResponse;
import com.societex.product.playload.error.ErrorSection;
import com.societex.product.playload.product.DepotRequest;
import com.societex.product.playload.product.DepotResponse;
import com.societex.product.service.DepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/depots")
public class ApiDepot {
    
    @Autowired
    DepotService depotService;

    @GetMapping(value = "", name = "Liste des depots")
    public ApiResponse getAllDepots(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(value = "sort", required = false, defaultValue = "name") String sort,
            @RequestParam(value = "direction", defaultValue = "asc", required = false) String direction,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        Page<Depot> depotPage = depotService.getPaginatedDepots(page, size, sort, direction, keyword);
        DepotResponse depotResponse = new DepotResponse();
        depotResponse.setPageStats(depotPage);
        depotResponse.setItems(depotPage.getContent());
        return new ApiResponse(
                true,
                HttpStatus.OK,
                "Liste des depots",
                depotResponse
        );
    }

    @GetMapping(value = "/{depotId}", name = "Avoir un depot")

    public ApiResponse getDepot(@PathVariable(value = "depotId") Integer depotId) {
        Depot currentDepot = depotService.findDepotById(depotId);
        if (currentDepot == null) {
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Le depot avec l'id = " + depotId + " est introuvable",
                    null
            );
        }
        return new ApiResponse(
                true,
                HttpStatus.OK,
                currentDepot.getCode() + " - " + currentDepot.getName(),
                currentDepot
        );
    }

    @PostMapping(value = "", name = "Création d'un depot")
    public ApiResponse createDepot(@RequestBody @Valid DepotRequest depotRequest, BindingResult bindingResult) {
        Depot existDepotByCode = depotService.findDepotByCode(depotRequest.getCode());
        Depot existDepotByName = depotService.findDepotByName(depotRequest.getName());
        if (existDepotByCode != null) {
            bindingResult.rejectValue("code", "error.depot", "Le code existe déjà");
        }
        if (existDepotByName != null) {
            bindingResult.rejectValue("name", "error.depot", "Le nom existe déjà");
        }
        if (bindingResult.hasErrors()) {
            ErrorSection es = new ErrorSection(depotRequest, bindingResult.getAllErrors());
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Erreur lors de la creation",
                    es
            );
        }
        Depot depot = depotService.create(depotRequest);
        return new ApiResponse(
                true,
                HttpStatus.OK,
                "Depot créé avec succès",
                depot
        );
    }


    @PutMapping(value = "/depots/{depotId}", name = "Modification d'un depot")

    public ApiResponse updateDepot(@PathVariable(value = "depotId") Integer depotId, @RequestBody @Valid DepotRequest depotRequest, BindingResult bindingResult) {
        Depot currentDepot = depotService.findDepotById(depotId);
        if (currentDepot == null) {
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Le depot avec l'id = " + depotId + " est introuvable",
                    new ErrorSection(depotRequest, null)
            );
        }

        Depot existDepotByCode = depotService.findDepotByCodeAndId(depotRequest.getCode(), currentDepot.getId());
        Depot existDepotByName = depotService.findDepotByNameAndId(depotRequest.getName(), currentDepot.getId());
        if (existDepotByCode != null) {
            bindingResult.rejectValue("code", "error.depot", "Le code existe déjà");
        }
        if (existDepotByName != null) {
            bindingResult.rejectValue("name", "error.depot", "Le nom existe déjà");
        }
        if (bindingResult.hasErrors()) {
            ErrorSection es = new ErrorSection(depotRequest, bindingResult.getAllErrors());
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Erreur lors de la creation",
                    es
            );
        }
        if (bindingResult.hasErrors()) {
            ErrorSection es = new ErrorSection(depotRequest, bindingResult.getAllErrors());
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Erreur lors de la mise à jour",
                    es
            );
        }
        Depot depot = depotService.update(currentDepot, depotRequest);
        return new ApiResponse(
                true,
                HttpStatus.OK,
                "Depot modifié avec succès",
                depot
        );
    }

    @DeleteMapping(value = "/depots/{depotId}", name = "Suppression d'un depot")
    public ApiResponse deleteDepot(@PathVariable(value = "depotId") Integer depotId) {
        Depot currentDepot = depotService.findDepotById(depotId);
        if (currentDepot == null) {
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Le depot avec l'id = " + depotId + " est introuvable",
                    null
            );
        }
        depotService.delete(currentDepot);
        return new ApiResponse(
                true,
                HttpStatus.OK,
                "Depot supprimé avec succès",
                null
        );
    }
}
