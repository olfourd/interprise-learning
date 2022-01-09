package com.olfd.userservice.config;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationProperties {
    private List<String> availableRoles;
}
