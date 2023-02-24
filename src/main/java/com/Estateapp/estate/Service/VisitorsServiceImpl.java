package com.Estateapp.estate.Service;

import com.Estateapp.estate.Entity.Visitors;
import com.Estateapp.estate.Repository.VisitorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorsServiceImpl implements VisitorsService {

    @Autowired
    private VisitorsRepository visitorsRepository;
    @Override
    public void saveNewVisitor(Visitors visitors) {

        visitorsRepository.save(visitors);

    }

    @Override
    public Visitors findId(String visitorId) {
        return visitorsRepository.findByVisitorCode(visitorId);
    }

    @Override
    public void updateVisitor(String entryStatus, String visitorId) {
//        visitorsRepository.updateVisitor(entryStatus, visitorId);
    }
}
