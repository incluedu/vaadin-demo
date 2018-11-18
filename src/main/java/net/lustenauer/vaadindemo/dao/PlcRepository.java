package net.lustenauer.vaadindemo.dao;

import net.lustenauer.vaadindemo.model.Plc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlcRepository extends JpaRepository<Plc, Long> {
    List<Plc> findByNameStartsWithIgnoreCase(String name);
}
