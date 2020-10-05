package com.societex.product.service;

import com.societex.product.model.Machine;
import com.societex.product.playload.product.MachineRequest;
import com.societex.product.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MachineService {

    @Autowired
    private MachineRepository machineRepository;

    public List<Machine> getAll() {
        List<Machine> machineList = new ArrayList<>();
        machineRepository.findAll().forEach(machineList::add);
        return machineList;
    }

    public Page<Machine> getPaginatedMachines(Integer page, Integer size, String sort, String direction, String keyword) {
        Sort sortable = ("desc".equals(direction)) ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        Pageable pageable = PageRequest.of(page, size, sortable);
        Page<Machine> machines = null;
        if (keyword != null) {
            machines = machineRepository.findByNameLikeOrCodeLike(keyword, pageable);
        } else {
            machines = machineRepository.findAll(pageable);
        }
        return machines;
    }

    public Machine create(MachineRequest machineRequest) {
        Machine machine = this.setMachine(new Machine(), machineRequest);
        return machineRepository.save(machine);
    }

    public Machine update(Machine currentMachine, MachineRequest machineRequest) {
        Machine machine = this.setMachine(currentMachine, machineRequest);
        return machineRepository.save(currentMachine);

    }

    public Machine findMachineById(Integer machineId) {
        return machineRepository.findById(machineId).orElse(null);
    }

    public Machine findMachineByCode(String code) {
        return machineRepository.findByCode(code);
    }

    public Machine findMachineByName(String name) {
        return machineRepository.findByName(name);
    }

    public Machine findMachineByCodeAndId(String code, Integer id) {
        return machineRepository.findByCodeAndId(code, id);
    }

    public Machine findMachineByNameAndId(String name, Integer id) {
        return machineRepository.findByNameAndId(name, id);
    }

    public void delete(Machine machine) {
        machineRepository.delete(machine);
    }

    public Machine setMachine(Machine machine, MachineRequest machineRequest) {
        machine.setName(machineRequest.getName());
        machine.setCode(machineRequest.getCode());
        machine.setValidityDate(machineRequest.getValidityDate());
        machine.setPricePurchaseAverage(machineRequest.getPricePurchaseAverage());
        machine.setPriceUnit(machineRequest.getPriceUnit());
        machine.setProductType(machineRequest.getProductType());
        machine.setLength(machineRequest.getLength());
        machine.setWeight(machineRequest.getWeight());
        machine.setWidth(machineRequest.getWidth());
        machine.setWatt(machineRequest.getWatt());
        return machine;
    }
}
