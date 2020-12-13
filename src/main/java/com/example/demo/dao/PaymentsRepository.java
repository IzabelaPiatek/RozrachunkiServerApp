package com.example.demo.dao;

import com.example.demo.entity.Payment;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<Payment, Integer> {

    public ArrayList<Payment> findByPaidByAndSettled(Integer idUser, boolean b);
    
}
