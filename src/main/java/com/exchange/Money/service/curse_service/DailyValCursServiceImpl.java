package com.exchange.Money.service.curse_service;

import com.exchange.Money.model.Currency;
import com.exchange.Money.model.ValCurs;
import com.exchange.Money.repository.ValCursRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DailyValCursServiceImpl implements DailyValCursService {

    private final ValCursRepository repository;

    @Override
    public ValCurs getValCurs(LocalDate dateOfRequest) {

        ValCurs valCursInDB = repository.findById(1L).orElse(saveValcurs());
        if (isActualData(valCursInDB, dateOfRequest)) {
            return valCursInDB;
        } else {
            valCursInDB = getUpdatedValCurs(valCursInDB);
            return valCursInDB;
        }
    }

    private ValCurs saveValcurs() {
        ValCurs valCurs = CurrencyUpdater.getDailyCurs();
        return repository.save(valCurs);
    }

    private ValCurs getUpdatedValCurs(ValCurs valCursInDB) {
        ValCurs valCurs = CurrencyUpdater.getDailyCurs();
        valCursInDB.setDate(valCurs.getDate());
        valCursInDB.setName(valCurs.getName());
        List<Currency> updatedCurrency = getUpdatedCur(valCursInDB.getCurrencies(), valCurs.getCurrencies());
        valCursInDB.setCurrencies(updatedCurrency);
        return repository.saveAndFlush(valCursInDB);
    }

    private List<Currency> getUpdatedCur(List<Currency> fromDB, List<Currency> fromCB) {
        for (int i = 0; i < fromDB.size(); i++) {
            fromDB.get(i).setNominal(fromCB.get(i).getNominal());
            fromDB.get(i).setValue(fromCB.get(i).getValue());
        }
        return fromDB;
    }

    private boolean isActualData(ValCurs valCursFromDb, LocalDate dateOfRequest) {
        LocalDate dateFromDB = valCursFromDb.getDate();
        return dateFromDB.getDayOfYear() == dateOfRequest.getYear()
                && checkDateOfWeek(dateFromDB, dateOfRequest);
    }

    private boolean checkDateOfWeek(LocalDate dateFromDB, LocalDate dateOfRequest) {

        switch (dateOfRequest.getDayOfWeek()) {
            case SATURDAY:
                return dateOfRequest.getDayOfYear() - dateFromDB.getDayOfYear() == 0;
            case SUNDAY:
                return dateOfRequest.getDayOfYear() - dateFromDB.getDayOfYear() == 1;
            default:
                return dateFromDB.getYear() - 1 == dateOfRequest.getYear();
        }
    }
}
