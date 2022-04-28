package com.example.userservice.userMenager.business.cron;


import com.example.userservice.business.service.JwtTokenProvider;
import com.example.userservice.userMenager.data.entity.ExpiredJwt;
import com.example.userservice.userMenager.data.repository.ExpiredJwtRepo;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UnactiveJwtCleaner {
    private ExpiredJwtRepo expiredJwtRepo;
    private JwtTokenProvider tokenProvider;

    @Scheduled(cron = "* */30 * * * *")
    public void clear() {
        List<ExpiredJwt> allExpiredJwt = expiredJwtRepo.findAll();
        List<ExpiredJwt> expiredNotValidJwt = allExpiredJwt.stream()
                .filter(expiredJwt -> tokenProvider.isTokenExpire(expiredJwt.getJwt())).collect(Collectors.toList());
        expiredJwtRepo.deleteAll(expiredNotValidJwt);
    }
}
