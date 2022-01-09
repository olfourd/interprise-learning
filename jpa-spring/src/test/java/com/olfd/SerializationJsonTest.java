package com.olfd;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@JsonTest
class SerializationJsonTest {

    private final static String TEST_JSON = """
                {"id":1,"name":"some"}""";

    @Autowired
    private JacksonTester<TestDto> testDtoJacksonTester;

    @Test
    @SneakyThrows
    void serialize() {
        TestDto testDto = new TestDto(1L, "some");
        String json = testDtoJacksonTester.write(testDto).getJson();

        assertThat(json, is(TEST_JSON));
    }

    @Test
    @SneakyThrows
    void deserialize() {
        TestDto testDto = testDtoJacksonTester.parse(TEST_JSON).getObject();

        assertThat(testDto.getId(), is(1L));
        assertThat(testDto.getName(), is("some"));
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    private final static class TestDto {
        private Long id;
        private String name;
    }
}
