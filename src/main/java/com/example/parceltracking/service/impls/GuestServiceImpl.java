package com.example.parceltracking.service.impls;

import com.example.parceltracking.exception.GuestNotFoundException;
import com.example.parceltracking.exception.UnclaimedParcelException;
import com.example.parceltracking.model.Guest;
import com.example.parceltracking.model.Parcel;
import com.example.parceltracking.repository.GuestRepository;
import com.example.parceltracking.repository.ParcelRepository;
import com.example.parceltracking.service.GuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.parceltracking.constants.LogMessages.GUEST_NOT_FOUND;
import static com.example.parceltracking.constants.LogMessages.CHECK_IN_START;
import static com.example.parceltracking.constants.LogMessages.GUEST_SAVED;
import static com.example.parceltracking.constants.LogMessages.CHECK_IN_FINISH;
import static com.example.parceltracking.constants.LogMessages.CHECK_OUT_START;
import static com.example.parceltracking.constants.LogMessages.UNCLAIMED_PARCELS_WARNING;
import static com.example.parceltracking.constants.LogMessages.GUEST_UNCLAIMED_PARCELS;
import static com.example.parceltracking.constants.LogMessages.GUEST_CHECK_OUT_SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final ParcelRepository parcelRepository;

    @Override
    public Guest checkIn(Guest guest) {
        log.info(String.format(CHECK_IN_START, guest.getId()));
        guest.setCheckedIn(true);
        guest.setCheckInTime(LocalDateTime.now());
        Guest saved = guestRepository.save(guest);
        log.info(String.format(GUEST_SAVED, saved.getId()));
        log.info(String.format(CHECK_IN_FINISH, saved.getId()));
        return saved;
    }

    @Override
    public void checkOut(String guestId) {
        log.info(String.format(CHECK_OUT_START, guestId));

        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestNotFoundException(String.format(GUEST_NOT_FOUND, guestId)));

        List<Parcel> unclaimedParcels = parcelRepository.findUnclaimedParcelsByGuestId(guestId);

        if (!unclaimedParcels.isEmpty()) {
            log.warn(String.format(UNCLAIMED_PARCELS_WARNING, guestId, unclaimedParcels.size()));
            throw new UnclaimedParcelException(GUEST_UNCLAIMED_PARCELS);
        }

        guest.setCheckedIn(false);
        guest.setCheckOutTime(LocalDateTime.now());
        guestRepository.save(guest);

        log.info(String.format(GUEST_CHECK_OUT_SUCCESS, guestId));
    }

    @Override
    public boolean isGuestCheckedIn(String guestId) {
        return guestRepository.findById(guestId)
                .map(Guest::isCheckedIn)
                .orElse(false);
    }
}
