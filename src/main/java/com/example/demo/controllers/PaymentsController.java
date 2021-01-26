package com.example.demo.controllers;

import com.example.demo.RozrachunkiServerAppApplication;
import com.example.demo.dao.BreakdownsRepository;
import com.example.demo.dao.GroupMembersRepository;
import com.example.demo.dao.PaymentsRepository;
import com.example.demo.dao.UsersRepository;
import com.example.demo.entity.*;
import com.example.demo.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("payments")
public class PaymentsController {

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private BreakdownsRepository breakdownsRepository;

    @Autowired
    private GroupMembersRepository groupMembersRepository;

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping(value="add")
    public PaymentJson add(@RequestBody PaymentJson payment) throws SQLException {

        Blob blob = null;

        if (payment.getImage() != null) {
            blob = new SerialBlob(payment.getImage());
        }

        Payment p = new Payment(null, payment.getIdGroup(), payment.getPaidBy(), payment.getAmount(), payment.getDescription(), new java.sql.Date(payment.getDate().getTime()), blob, payment.getNote(), payment.isSettled(), payment.getPayment_option());

        paymentsRepository.save(p);

        //ArrayList<GroupMember> groupMembers = groupMembersRepository.findByIdGroup(payment.getIdGroup());

        for (Breakdown b : payment.getBreakdowns()) {
            b.setIdPayment(p.getId());
            breakdownsRepository.save(b);
        }

        return payment;
    }

    @GetMapping(value = "getAllForGroup/{idGroup}")
    public ArrayList<PaymentJson> getAllForGroup(@PathVariable Integer idGroup) throws SQLException {

        ArrayList<Payment> list = paymentsRepository.findByIdGroupAndSettled(idGroup, false);
        ArrayList<PaymentJson> payments = new ArrayList<>();

        for (Payment payment: list) {
            payment.setImage(null);
            payments.add(new PaymentJson(payment));
        }

        return payments;
    }

    @GetMapping(value = "get/{id}")
    public PaymentWithOwnerJson get(@PathVariable Integer id) throws SQLException {

        Payment payment = paymentsRepository.findById(id).get();
        String username = usersRepository.findById(payment.getPaidBy()).get().getUsername();

        return new PaymentWithOwnerJson(payment, username);
    }

    @Transactional
    @PostMapping(value = "delete/{id}")
    public int delete(@PathVariable Integer id) throws SQLException {

        paymentsRepository.delete(paymentsRepository.findById(id).get());

        return 1;
    }

    @GetMapping(value = "getBorrowers/{idPayment}")
    public ArrayList<BorrowerJson> getBorrowers(@PathVariable Integer idPayment) {
        ArrayList<BorrowerJson> borrowers = new ArrayList<>();

        Payment payment = paymentsRepository.findById(idPayment).get();
        ArrayList<Breakdown> breakdowns = breakdownsRepository.findByIdPayment(idPayment);

        for (Breakdown breakdown : breakdowns) {

            if (breakdown.getIdBorrower().equals(payment.getPaidBy())) {
                borrowers.add(new BorrowerJson(breakdown.getIdBorrower(),
                        usersRepository.findById(breakdown.getIdBorrower()).get().getUsername(),
                        payment.getAmount(), breakdown.getAmount(), breakdown.isSettled()));
            } else {
                borrowers.add(new BorrowerJson(breakdown.getIdBorrower(),
                        usersRepository.findById(breakdown.getIdBorrower()).get().getUsername(),
                        0, breakdown.getAmount(), breakdown.isSettled()));
            }
        }

        return borrowers;
    }
}
