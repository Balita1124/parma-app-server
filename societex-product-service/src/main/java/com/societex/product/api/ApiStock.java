package com.societex.product.api;

import com.societex.product.model.Stock;
import com.societex.product.playload.ApiResponse;
import com.societex.product.playload.error.ErrorSection;
import com.societex.product.playload.product.StockRequest;
import com.societex.product.playload.product.StockResponse;
import com.societex.product.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("api/stocks")
public class ApiStock {
    @Autowired
    StockService stockService;

    @GetMapping(value = "", name = "Liste des stocks")
    public ApiResponse getAllStocks(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(value = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(value = "direction", defaultValue = "asc", required = false) String direction,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        Page<Stock> stockPage = stockService.getPaginatedStocks(page, size, sort, direction, keyword);
        StockResponse stockResponse = new StockResponse();
        stockResponse.setPageStats(stockPage);
        stockResponse.setItems(stockPage.getContent());
        return new ApiResponse(
                true,
                HttpStatus.OK,
                "Liste des stocks",
                stockResponse
        );
    }

    @GetMapping(value = "/{stockId}", name = "Avoir un stock")

    public ApiResponse getStock(@PathVariable(value = "stockId") Integer stockId) {
        Stock currentStock = stockService.findStockById(stockId);
        if (currentStock == null) {
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Le stock avec l'id = " + stockId + " est introuvable",
                    null
            );
        }
        return new ApiResponse(
                true,
                HttpStatus.OK,
                currentStock.getMachine().getName() + " - " + currentStock.getDepot().getName() + " : " + currentStock.getQuantite(),
                currentStock
        );
    }

    @PostMapping(value = "", name = "Création d'un stock")
    public ApiResponse createStock(@RequestBody @Valid StockRequest stockRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorSection es = new ErrorSection(stockRequest, bindingResult.getAllErrors());
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Erreur lors de la creation",
                    es
            );
        }
        Stock stock = stockService.create(stockRequest);
        return new ApiResponse(
                true,
                HttpStatus.OK,
                "Stock créé avec succès",
                stock
        );
    }


    @PutMapping(value = "/stocks/{stockId}", name = "Modification d'un stock")

    public ApiResponse updateStock(@PathVariable(value = "stockId") Integer stockId, @RequestBody @Valid StockRequest stockRequest, BindingResult bindingResult) {
        Stock currentStock = stockService.findStockById(stockId);
        if (currentStock == null) {
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Le stock avec l'id = " + stockId + " est introuvable",
                    new ErrorSection(stockRequest, null)
            );
        }
        if (bindingResult.hasErrors()) {
            ErrorSection es = new ErrorSection(stockRequest, bindingResult.getAllErrors());
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Erreur lors de la mise à jour",
                    es
            );
        }
        Stock stock = stockService.update(currentStock, stockRequest);
        return new ApiResponse(
                true,
                HttpStatus.OK,
                "Stock modifié avec succès",
                stock
        );
    }

    @DeleteMapping(value = "/stocks/{stockId}", name = "Suppression d'un stock")
    public ApiResponse deleteStock(@PathVariable(value = "stockId") Integer stockId) {
        Stock currentStock = stockService.findStockById(stockId);
        if (currentStock == null) {
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Le stock avec l'id = " + stockId + " est introuvable",
                    null
            );
        }
        stockService.delete(currentStock);
        return new ApiResponse(
                true,
                HttpStatus.OK,
                "Stock supprimé avec succès",
                null
        );
    }
}
