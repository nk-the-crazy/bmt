package io.gnt.bmt.identity.service;

import io.gnt.bmt.commons.exceptions.SystemSecurityException;
import io.gnt.bmt.identity.model.User;
import java.util.List;
import java.util.Map;

public interface IdentityService {
    List<User> getUsers();

    String generateToken(User user);

    User auhtenticateUser(String username, String password) throws SystemSecurityException;

    User getUser(long id);

}
