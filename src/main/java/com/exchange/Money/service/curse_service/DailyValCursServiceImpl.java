package com.exchange.Money.service.curse_service;

import com.exchange.Money.model.Currency;
import com.exchange.Money.model.ValCurs;
import com.exchange.Money.repository.ValCursRepository;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DailyValCursServiceImpl implements DailyValCursService {
    private static Logger logger = Logger.getLogger(DailyValCursServiceImpl.class);
    private final ValCursRepository repository;
    private final static String NAME = "Foreign Currency Market";
    @Override
    public ValCurs getValCurs(LocalDate dateOfRequest) {

        ValCurs valCursInDB = repository.findByName(NAME);
        if (valCursInDB == null){
            valCursInDB = downloadAndSaveValCurs();
        }

        if (isActualData(valCursInDB, dateOfRequest)) {
            logger.info("Database is up to date");
            return valCursInDB;
        } else {
            logger.info("Data in DataBase is old. Need to update");
            valCursInDB = getUpdatedValCurs(valCursInDB);
            return valCursInDB;
        }
    }

    private ValCurs downloadAndSaveValCurs() {
        ValCurs valCurs = CurrencyUpdater.getDailyCurs();
        logger.info("DataBase created for the first time date: " + valCurs.getDate());
        return repository.saveAndFlush(valCurs);
    }

    private ValCurs getUpdatedValCurs(ValCurs valCursInDB) {
        ValCurs valCurs = CurrencyUpdater.getDailyCurs();
        valCursInDB.setDate(valCurs.getDate());
        valCursInDB.setName(valCurs.getName());
        List<Currency> updatedCurrency = getUpdatedCur(valCursInDB.getCurrencies(), valCurs.getCurrencies());
        valCursInDB.setCurrencies(updatedCurrency);
        logger.info("Currency courses updated. DataBase is up to date!");
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
        return dateFromDB.getYear() == dateOfRequest.getYear()
                && checkDateOfWeek(dateFromDB, dateOfRequest);
    }

    private boolean checkDateOfWeek(LocalDate dateFromDB, LocalDate dateOfRequest) {
        switch (dateOfRequest.getDayOfWeek()) {
            case SATURDAY:
                return dateOfRequest.getDayOfYear() - dateFromDB.getDayOfYear() == 0;
            case SUNDAY:
                return dateOfRequest.getDayOfYear() - dateFromDB.getDayOfYear() == 1;
            default:
                return dateFromDB.getDayOfYear() - 1 == dateOfRequest.getDayOfYear();
        }
    }
}
