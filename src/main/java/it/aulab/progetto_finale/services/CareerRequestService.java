package it.aulab.progetto_finale.services;

import it.aulab.progetto_finale.models.CareerRequest;
import it.aulab.progetto_finale.models.User;

public interface CareerRequestService {
    boolean isRoleAlreadyAssigned(User user, CareerRequest careerRequest);
    void save(CareerRequest careerRequest, User user);
    void careerAccept(Long requestId);
    CareerRequest find(Long id);
}
