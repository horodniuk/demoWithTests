package com.example.demowithtests.service.passport;

import com.example.demowithtests.domain.Image;
import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.repository.ImageRepository;
import com.example.demowithtests.repository.PassportRepository;
import com.example.demowithtests.service.passport.image.ImageService;
import com.example.demowithtests.util.exception.passport.PassportIsHandledException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class PassportServiceBean implements PassportService {

    private final PassportRepository passportRepository;
    private final ImageRepository imageRepository;
    @Override
    public Passport create(Passport passport) {
        return passportRepository.save(passport);
    }

    @Override
    public Passport getById(Integer id) {
        return passportRepository.findById(id).orElseThrow();
    }

    @Override
    public Passport handlePassport(Integer id) {
        Passport passport = getById(id);
        if(passport.getIsHandled()){
            throw new PassportIsHandledException("Passport id = "+ id + " already handled");
        } else passport.setIsHandled(true);
        return passportRepository.save(passport);
    }

    @Override
    public Passport addImage(Integer imageId, Integer passportId) {
        Passport passport = passportRepository.findById(passportId).orElseThrow();
        Image image = imageRepository.findById(imageId).orElseThrow();
        passport.setImage(image);
        return passportRepository.save(passport);
    }

    @Override
    public Passport cancelPassport(Passport passport, Integer employeeId) {
        if (passport.getCanceledEmployeeId() == null) {
            passport.setCanceledEmployeeId(employeeId);
            return passportRepository.save(passport);
        }
        return passport;
    }
}
