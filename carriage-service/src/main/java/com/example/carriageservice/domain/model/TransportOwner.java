package com.example.carriageservice.domain.model;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportOwner {
    @Id
    private String id;
    @NotBlank
    private String firstName;
    private String middleName;
    @NotBlank
    private String lastName;
    private String country;
}
