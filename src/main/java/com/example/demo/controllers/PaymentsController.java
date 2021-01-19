package com.example.demo.controllers;

import com.example.demo.dao.BreakdownsRepository;
import com.example.demo.dao.GroupMembersRepository;
import com.example.demo.dao.PaymentsRepository;
import com.example.demo.entity.Breakdown;
import com.example.demo.entity.GroupMember;
import com.example.demo.entity.Payment;
import com.example.demo.entity.User;
import com.example.demo.json.FriendJson;
import com.example.demo.json.GroupJson;
import com.example.demo.json.PaymentJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("payments")
public class PaymentsController {

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private BreakdownsRepository breakdownsRepository;

    @Autowired
    private GroupMembersRepository groupMembersRepository;

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
}
