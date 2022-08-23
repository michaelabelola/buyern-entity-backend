package com.buyern.buyern.Repositories;

import com.buyern.buyern.Models.User.UserAuthSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends CrudRepository<UserAuthSession, String> {

}
