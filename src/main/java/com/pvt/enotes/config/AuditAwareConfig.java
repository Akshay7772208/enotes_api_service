package com.pvt.enotes.config;

import com.pvt.enotes.entity.User;
import com.pvt.enotes.util.CommonUtil;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAwareConfig implements AuditorAware<Integer> {


    @Override
    public Optional<Integer> getCurrentAuditor() {
        User loggedInUser=CommonUtil.getLoggedInUser();
        return Optional.of(loggedInUser.getId());
    }
}
