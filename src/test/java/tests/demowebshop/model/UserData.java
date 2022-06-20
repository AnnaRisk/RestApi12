package tests.demowebshop.model;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonProperty;
import lombok.Data;
import tests.demowebshop.lombok.User;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData {

    private String name;

    private String job;
    private String id;
    private String  token;
    @JsonProperty("data")
    private User user;

}


