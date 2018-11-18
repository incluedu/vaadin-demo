package net.lustenauer.vaadindemo.service;

import net.lustenauer.vaadindemo.dao.PlcRepository;
import net.lustenauer.vaadindemo.model.Plc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlcService {
    private final Logger logger = LoggerFactory.getLogger(PlcService.class);

    private final PlcRepository repository;

    @Autowired
    public PlcService(PlcRepository repository) {
        this.repository = repository;
    }

    public int getPlcCount() {
        return repository.findAll().size();
    }

    public List<Plc> getAll() {
        return repository.findAll();
    }

    public void update(Plc plc) {
        repository.save(plc);
    }

    public Plc get(Long id) {
        return repository.findById(id).orElse(null);
    }
}
