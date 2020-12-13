package com.example.demo.dao;

import com.example.demo.entity.Breakdown;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreakdownsRepository extends JpaRepository<Breakdown, Integer> {

    public ArrayList<Breakdown> findByIdPaymentAndSettled(Integer id, boolean b);
    
}
