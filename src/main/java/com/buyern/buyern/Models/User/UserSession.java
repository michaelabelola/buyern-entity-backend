package com.buyern.buyern.Models.User;

import com.buyern.buyern.Configs.CustomAuthority;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@RedisHash(value = "userSessions", timeToLive = 86400L)
@Data
public class UserSession implements Serializable {
    /**
     * <h3>userId</h3>
     */
    @Id
    private String id;
    /**
     * <h3>Json web token Id</h3>
     * this is same as session id. it is unique per session
     */

    private String jwtId;
    private boolean verified = false;
    private boolean disabled = false;
    private boolean expired = false;
    private boolean credentialExpired = false;
    private boolean locked = false;
    private Date loginTime;
    private List<CustomAuthority> authorities;
}
