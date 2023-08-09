package com.example.demowithtests.service.passport;

import com.example.demowithtests.domain.Passport;





public interface PassportService {
    Passport create(Passport passport);

    Passport getById(Integer id);

    Passport handlePassport(Integer id);

    Passport addImage(Integer passportId, Integer imageId);

    Passport cancelPassport(Passport passport, Integer employeeId);
}
