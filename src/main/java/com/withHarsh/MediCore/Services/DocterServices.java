package com.withHarsh.MediCore.Services;

import com.withHarsh.MediCore.DTO.DocterProfileResponceDTO;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;

public interface DocterServices {


    DocterProfileResponceDTO getProfile(Authentication authentication);





}
