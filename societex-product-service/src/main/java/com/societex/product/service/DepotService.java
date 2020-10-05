package com.societex.product.service;

import com.societex.product.model.Depot;
import com.societex.product.playload.product.DepotRequest;
import com.societex.product.repository.DepotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepotService {
    @Autowired
    private DepotRepository depotRepository;

    public List<Depot> getAll() {
        List<Depot> depotList = new ArrayList<>();
        depotRepository.findAll().forEach(depotList::add);
        return depotList;
    }

    public Page<Depot> getPaginatedDepots(Integer page, Integer size, String sort, String direction, String keyword) {
        Sort sortable = ("desc".equals(direction)) ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        Pageable pageable = PageRequest.of(page, size, sortable);
        Page<Depot> depots = null;
        if (keyword != null) {
            depots = depotRepository.findByNameLikeOrCodeLike(keyword, pageable);
        } else {
            depots = depotRepository.findAll(pageable);
        }
        return depots;
    }

    public Depot create(DepotRequest depotRequest) {
        Depot depot = this.setDepot(new Depot(), depotRequest);
        return depotRepository.save(depot);
    }

    public Depot update(Depot currentDepot, DepotRequest depotRequest) {
        Depot depot = this.setDepot(currentDepot, depotRequest);
        return depotRepository.save(currentDepot);

    }

    public Depot findDepotById(Integer depotId) {
        return depotRepository.findById(depotId).orElse(null);
    }

    public Depot findDepotByCode(String code) {
        return depotRepository.findByCode(code);
    }

    public Depot findDepotByName(String name) {
        return depotRepository.findByName(name);
    }

    public Depot findDepotByCodeAndId(String code, Integer id) {
        return depotRepository.findByCodeAndId(code, id);
    }

    public Depot findDepotByNameAndId(String name, Integer id) {
        return depotRepository.findByNameAndId(name, id);
    }

    public void delete(Depot depot) {
        depotRepository.delete(depot);
    }

    public Depot setDepot(Depot depot, DepotRequest depotRequest) {
        depot.setName(depotRequest.getName());
        depot.setCode(depotRequest.getCode());
        depot.setAdresse(depotRequest.getAdresse());
        return depot;
    }
}
