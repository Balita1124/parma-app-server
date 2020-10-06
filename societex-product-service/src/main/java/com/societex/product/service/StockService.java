package com.societex.product.service;

import com.societex.product.model.Depot;
import com.societex.product.model.Machine;
import com.societex.product.model.Stock;
import com.societex.product.playload.product.StockRequest;
import com.societex.product.repository.DepotRepository;
import com.societex.product.repository.MachineRepository;
import com.societex.product.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private DepotRepository depotRepository;

    @Autowired
    private MachineRepository machineRepository;

    public List<Stock> getAll() {
        List<Stock> stockList = new ArrayList<>();
        stockRepository.findAll().forEach(stockList::add);
        return stockList;
    }

    public Page<Stock> getPaginatedStocks(Integer page, Integer size, String sort, String direction, String keyword) {
        Sort sortable = ("desc".equals(direction)) ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        Pageable pageable = PageRequest.of(page, size, sortable);
        Page<Stock> stocks = stockRepository.findAll(pageable);
        return stocks;
    }

    public Stock create(StockRequest stockRequest) {
        Stock stock = this.setStock(new Stock(), stockRequest);
        return stockRepository.save(stock);
    }

    public Stock update(Stock currentStock, StockRequest stockRequest) {
        Stock stock = this.setStock(currentStock, stockRequest);
        return stockRepository.save(currentStock);

    }

    public Stock findStockById(Integer stockId) {
        return stockRepository.findById(stockId).orElse(null);
    }

    public void delete(Stock stock) {
        stockRepository.delete(stock);
    }

    public Stock setStock(Stock stock, StockRequest stockRequest) {
        Depot depot = depotRepository.findById(stockRequest.getDepotId()).orElse(null);
        Machine machine = machineRepository.findById(stockRequest.getMachineId()).orElse(null);
        stock.setDepot(depot);
        stock.setMachine(machine);
        stock.setQuantite(stockRequest.getQuantite());
        stock.setMini(stockRequest.getMini());
        stock.setMax(stockRequest.getMax());
        stock.setReppro(stockRequest.getReppro());
        return stock;
    }
}
