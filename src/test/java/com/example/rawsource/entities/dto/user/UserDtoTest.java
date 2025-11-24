package com.example.rawsource.entities.dto.user;

import com.example.rawsource.entities.Role;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void serializeShouldNotContainPassword() throws Exception {
        UUID id = UUID.randomUUID();
        UserDto user = new UserDto(id, "Nombre", "a@b.com", "secret", Role.BUYER);

        String json = mapper.writeValueAsString(user);
        JsonNode node = mapper.readTree(json);

        assertFalse(node.has("password"), "El JSON serializado no debe contener el campo 'password'");
        assertTrue(node.has("email"));
        assertEquals("a@b.com", node.get("email").asText());
    }

    @Test
    void deserializeShouldSetPassword() throws Exception {
        UUID id = UUID.randomUUID();
        String json = String.format(
                "{\"id\":\"%s\",\"name\":\"%s\",\"email\":\"%s\",\"password\":\"%s\",\"role\":\"%s\"}",
                id.toString(), "Nombre", "a@b.com", "secret", Role.BUYER.name()
        );

        UserDto user = mapper.readValue(json, UserDto.class);

        assertEquals("secret", user.getPassword(), "La deserialización debe llenar el campo password");
        assertEquals("a@b.com", user.getEmail());
    }
}
