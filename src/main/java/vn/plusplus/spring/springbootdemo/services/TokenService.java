package vn.plusplus.spring.springbootdemo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.plusplus.spring.springbootdemo.interceptor.Payload;
import vn.plusplus.spring.springbootdemo.repository.TokenRepository;
import vn.plusplus.spring.springbootdemo.repository.entity.ApiEntity;
import vn.plusplus.spring.springbootdemo.repository.entity.TokenEntity;

@Service
public class TokenService {
    private final static Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Autowired
    TokenRepository tokenRepository;

    private static final Integer tokenLifeTime = 3*60*60;

    public Payload getPayload(String accessToken, ApiEntity apiEntity){
        TokenEntity tokenEntity = tokenRepository.findOneByToken(accessToken);
        if(tokenEntity == null){
            logger.error("Not found access token [{}]", accessToken);
            throw new RuntimeException("Token not found");
        }
        if (isTimeExpired(tokenEntity.getExpiredTime().getTime(), tokenLifeTime)) {
            throw new RuntimeException("Token expired");
        }

        //TODO check user can call this api
        /*1. Finding role of user
        * 2. Get list API that role can be access
        * 3. Loop to check if this api is in list API access
        * 4. Throw exception if not found*/

        Payload payload = new Payload();
        payload.setUserId(tokenEntity.getUserId());
        payload.setAccessToken(accessToken);
        return payload;
    }
    public boolean isTimeExpired(long time, long validityTimeInSecond) {
        return time + validityTimeInSecond * 1000 < System.currentTimeMillis();
    }
}
