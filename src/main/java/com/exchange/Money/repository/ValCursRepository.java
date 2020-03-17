package com.exchange.Money.repository;

import com.exchange.Money.model.ValCurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValCursRepository extends JpaRepository<ValCurs,Long> {
    ValCurs findByName(String name);
}
