/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;*/
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mobile")
public class MobileApplicationController {
/*
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    InstructorRepository instructorRepository;

    MobileService service;

    @Autowired
    GpsPointRepository gpsRepository;

    @Autowired
    TimetableRepository timetableRepository;
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    DrivingService drivingService;*/

    public MobileApplicationController() {
    }
    
    /*

    @PostMapping("send/{instructor}")
    public Message getCoordinates(@RequestBody ArrayList<LocationJson> coordinates,
            @PathVariable("instructor") Integer id) {
        GpsPoint gps;
        Integer idDriving;
        for (LocationJson json : coordinates) {
            idDriving = drivingService.getDrivingByTimeAndIdInstructor(json.getTime(), id);
            gps = new GpsPoint(json.getNs(), json.getNw(), json.getTime(), idDriving, id);
            gpsRepository.save(gps);
        }

        return new Message("true");
    }

    @GetMapping("timetable/{id}")
    public ArrayList<TimetableJson> getTimetable(@PathVariable("id") Integer id) {
        List<TimetableJson> timetableJsonList = new ArrayList<>();
        LocalDateTime today = LocalDateTime.now();

        List<Timetable> timetables = timetableRepository.queryByDayAndMonthAndYearAndInstructor(today.getDayOfMonth(),
                today.getMonthValue(), today.getYear(), id);
        TimetableJson json;
        List<Student> students = studentRepository.findAll();
        for (int i = 1; i < timetables.size(); i++) {
            json = new TimetableJson(students.get(i).getName() + " " + students.get(i).getSurname(),
                    timetables.get(i).getBegin().getHour() + ".00-" + timetables.get(i).getEnd().getHour() + ".00", timetables.get(i).getDrivingType().getType(),
                    getDayName(today) + " " + today.getDayOfMonth() + "." + today.getMonthValue() + "." + today.getYear() + "r.");
            timetableJsonList.add(json);
        }
        return (ArrayList<TimetableJson>) timetableJsonList;

    }*/

    @GetMapping("hello")
    public String sayHello() {
        System.out.println("Just sayin' hello");
        return "Hello world!";
    }
/*
    public String getDayName(LocalDateTime date) {
        int dayNumber = date.getDayOfWeek().getValue();
        switch (dayNumber) {
            case 1:
                return "Poniedziałek ";
            case 2:
                return "Wtorek ";
            case 3:
                return "Środa ";
            case 4:
                return "Czwartek ";
            case 5:
                return "Piątek ";
            case 6:
                return "Sobota ";
            case 7:
                return "Niedziela ";
            default:
                return "Dzisiaj ";
        }

    }*/
}
