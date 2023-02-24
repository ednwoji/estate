package com.Estateapp.estate.Service;

import com.Estateapp.estate.Entity.Visitors;

public interface VisitorsService {
    void saveNewVisitor(Visitors visitors);

    Visitors findId(String visitorId);

    void updateVisitor(String entryStatus, String visitorId);
}
