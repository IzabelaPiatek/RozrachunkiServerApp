package com.example.demo.controllers;

import com.example.demo.dao.BreakdownsRepository;
import com.example.demo.dao.FriendsRepository;
import com.example.demo.dao.PaymentsRepository;
import com.example.demo.dao.UsersRepository;
import com.example.demo.entity.Breakdown;
import com.example.demo.entity.Friendship;
import com.example.demo.entity.Payment;
import com.example.demo.entity.User;
import com.example.demo.json.FriendJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("breakdowns")
public class BreakdownsController {

    @Autowired
    private BreakdownsRepository breakdownsRepository;

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private UsersRepository userRepository;

    public BreakdownsController() {
    }

    @PostMapping(value="settleUpUser/{userId}/{borrowerUsername}")
    public int settleUpUser(@PathVariable Integer userId, @PathVariable String borrowerUsername) {

        ArrayList<FriendJson> friends = new ArrayList<>();

        int borrowerId = userRepository.findByUsername(borrowerUsername).getId();

        ArrayList<Payment> payments = paymentsRepository.findByPaidByAndSettled(userId, false);

        for (Payment payment: payments) {
            ArrayList<Breakdown> b = breakdownsRepository.findByIdBorrowerAndIdPayment(borrowerId, payment.getId());
            for (Breakdown breakdown : b) {
                if (breakdown.isSettled() == false) {
                    breakdown.setSettled(true);
                    breakdownsRepository.save(breakdown);
                }
            }
        }

        payments = paymentsRepository.findByPaidByAndSettled(borrowerId, false);

        for (Payment payment: payments) {
            ArrayList<Breakdown> b = breakdownsRepository.findByIdBorrowerAndIdPayment(userId, payment.getId());
            for (Breakdown breakdown : b) {
                if (breakdown.isSettled() == false) {
                    breakdown.setSettled(true);
                    breakdownsRepository.save(breakdown);
                }
            }
        }

        return 0;
    }

    /*@PostMapping(value="settleUpPayment/{paymentId}")
    public int settleUpPayment(@PathVariable Integer paymentId) {
    }*/
    
}
