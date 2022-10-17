package com.example.demo.servicio;

import com.example.demo.modelo.Reservation;
import com.example.demo.modelo.dto.CountClient;
import com.example.demo.modelo.dto.CountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repositorio.ReservationRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> getAll(){
        return reservationRepository.getAll();
    }
    public Optional<Reservation> getReservation(int id){
        return reservationRepository.getReservation(id);
    }
    public Reservation save (Reservation reservation){
        if(reservation.getIdReservation()==null){
            return reservationRepository.save(reservation);
        }else{
            Optional<Reservation> reservationEncontrado = getReservation(reservation.getIdReservation());
            if(reservationEncontrado.isEmpty()){
                return reservationRepository.save(reservation);
            }else{
                return reservation;
            }
        }
    }

    public Reservation update (Reservation reservation){
        if(reservation.getIdReservation()!=null){
            Optional<Reservation> reservationEncontrado = getReservation(reservation.getIdReservation());
            if(!reservationEncontrado.isEmpty()){
                if(reservation.getStartDate()!=null){
                    reservationEncontrado.get().setStartDate(reservation.getStartDate());
                }
                if(reservation.getDevolutionDate()!=null){
                    reservationEncontrado.get().setDevolutionDate(reservation.getDevolutionDate());
                }
                if(reservation.getStatus()!=null){
                    reservationEncontrado.get().setStatus(reservation.getStatus());
                }
                return reservationRepository.save(reservationEncontrado.get());
            }
        }
        return reservation;

    }

    public boolean delete(int id){
        Boolean respuesta = getReservation(id).map(elemento ->{
            reservationRepository.delete(elemento);
            return true;
        }).orElse(false);
        return respuesta;
    }

    //Reto 5
    public List<CountClient> getClientesTop(){
        return reservationRepository.getClientesTop();
    }


    public List<Reservation> getReservationsBetweenDays(String dateA, String dateB) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date a = new Date();
        Date b = new Date();
        try {
            a = parser.parse(dateA);
            b = parser.parse(dateB);
        } catch (ParseException error) {
            error.printStackTrace();
        }
        if (a.before(b)) {
            return reservationRepository.getReservationBetweenDays(a, b);
        } else {
            return new ArrayList<>();
        }
    }
        public CountStatus getReservationsStatus(){
            List<Reservation> reservasCompletadas =reservationRepository.getReservationByStatus("completed");
            List<Reservation> reservasCanceladas =reservationRepository.getReservationByStatus("cancelled");
            return new CountStatus((long) reservasCompletadas.size(), (long) reservasCanceladas.size());
        }

}
