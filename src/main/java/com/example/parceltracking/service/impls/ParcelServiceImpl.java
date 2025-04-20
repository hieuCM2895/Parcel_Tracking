package com.example.parceltracking.service.impls;

import com.example.parceltracking.exception.GuestNotFoundException;
import com.example.parceltracking.exception.ParcelNotFoundException;
import com.example.parceltracking.exception.ParcelOwnershipMismatchException;
import com.example.parceltracking.exception.ParcelToCheckedOutGuestException;
import com.example.parceltracking.model.Guest;
import com.example.parceltracking.model.Parcel;
import com.example.parceltracking.repository.GuestRepository;
import com.example.parceltracking.repository.ParcelRepository;
import com.example.parceltracking.service.ParcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.parceltracking.constants.LogMessages.GUEST_NOT_FOUND;
import static com.example.parceltracking.constants.LogMessages.FORGET_RECEIVE_PARCEL_WHEN_GUEST_CHECKED_OUT;
import static com.example.parceltracking.constants.LogMessages.PARCEL_RECEIVED;
import static com.example.parceltracking.constants.LogMessages.PARCEL_NOT_FOUND;
import static com.example.parceltracking.constants.LogMessages.PARCEL_MISMATCH;
import static com.example.parceltracking.constants.LogMessages.PARCEL_PICKED_UP;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParcelServiceImpl implements ParcelService {

    private final ParcelRepository parcelRepository;
    private final GuestRepository guestRepository;

    @Override
    public Parcel receiveParcel(Parcel parcel) {
        String guestId = parcel.getGuest().getId();
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestNotFoundException(String.format(GUEST_NOT_FOUND, guestId)));

        if (!guest.isCheckedIn()) {
            String message = String.format(FORGET_RECEIVE_PARCEL_WHEN_GUEST_CHECKED_OUT, guestId);
            throw new ParcelToCheckedOutGuestException(message);
        }

        parcel.setReceivedAt(LocalDateTime.now());
        parcel.setPickedUp(false);

        Parcel saved = parcelRepository.save(parcel);
        String logMessage = String.format(PARCEL_RECEIVED, saved.getId(), guestId);
        log.info(logMessage);
        return saved;
    }

    @Override
    public List<Parcel> getParcelsByGuest(String guestId) {
        guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestNotFoundException(String.format(GUEST_NOT_FOUND, guestId)));
        return parcelRepository.findByGuestId(guestId);
    }

    @Override
    public void markParcelAsPickedUp(String guestId, String parcelId) {
        guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestNotFoundException(String.format(GUEST_NOT_FOUND, guestId)));

        Parcel parcel = parcelRepository.findById(parcelId)
                .orElseThrow(() -> new ParcelNotFoundException(String.format(PARCEL_NOT_FOUND, parcelId)));

        if (!parcel.getGuest().getId().equals(guestId)) {
            String message = String.format(PARCEL_MISMATCH, parcelId, guestId);
            throw new ParcelOwnershipMismatchException(message);
        }

        parcel.setPickedUp(true);
        parcel.setPickedUpTime(LocalDateTime.now());
        parcelRepository.save(parcel);

        String logMessage = String.format(PARCEL_PICKED_UP, parcelId, guestId);
        log.info(logMessage);
    }
}
