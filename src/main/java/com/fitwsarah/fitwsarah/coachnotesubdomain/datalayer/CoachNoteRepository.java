package com.fitwsarah.fitwsarah.coachnotesubdomain.datalayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoachNoteRepository extends JpaRepository<CoachNote, Integer> {

    List<CoachNote> findCoachNoteByUserId(String userId);

    CoachNote findCoachNoteByCoachNoteIdentifier_CoachNoteId(String coachNoteId);
}
