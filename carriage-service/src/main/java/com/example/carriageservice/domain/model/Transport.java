package com.example.carriageservice.domain.model;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Data
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
public class Transport {

    @Id
    private String id;
//    @NotNull
    private GeoJsonPoint geo;
    @NotNull
    private TransportOwner transportOwner;
    @NotNull
    private TransportDocument transportDocument;

    @Data
    @NoArgsConstructor
    private static final class TransportDocument {
        @NotBlank
        private String number;
//        @NotNull
        private LocalDate expiredDate;
    }
}
