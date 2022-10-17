package com.example.demo.Controller;

import com.example.demo.modelo.Message;
import com.example.demo.modelo.Reservation;
import com.example.demo.modelo.dto.CountClient;
import com.example.demo.modelo.dto.CountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.demo.servicio.ReservationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    @GetMapping("/all")
    public List<Reservation> getAll(){
        return reservationService.getAll();
    }
    @GetMapping("/{id}")
    public Optional<Reservation> getReservation(@PathVariable("id") int id) {

        return reservationService.getReservation(id);
    }
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation save (@RequestBody Reservation reservation) {
        return reservationService.save(reservation);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation update(@RequestBody Reservation reservation) {return reservationService.update(reservation);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean delete(@PathVariable("id") int id){return reservationService.delete(id);}

    @GetMapping("/report-clients")
    public List<CountClient> getReportClientesTop(){
        return reservationService.getClientesTop();
    }

    @GetMapping("report-dates/{dateOne}/{dateTwo}")
    public List<Reservation> getReportReservationBetweenDays(@PathVariable("dateOne") String dateOne, @PathVariable("dateTwo") String dateTwo){
        return reservationService.getReservationsBetweenDays(dateOne, dateTwo);
    }

    @GetMapping("report-status")
    public CountStatus getReportSatatus(){
        return reservationService.getReservationsStatus();
    }
}
