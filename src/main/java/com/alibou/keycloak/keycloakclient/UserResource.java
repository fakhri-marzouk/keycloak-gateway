package com.alibou.keycloak.keycloakclient;

import com.alibou.keycloak.dto.UserDTO;
import com.alibou.keycloak.security.KeyclaokSecurityUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.common.util.CollectionUtil;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/keycloak")
@SecurityRequirement(name = "keycloak")
@RequiredArgsConstructor
public class UserResource {

    private final KeyclaokSecurityUtil keyclaokSecurityUtil;

    @Value("${realm}")
    private String realm;

    @GetMapping("/user")
    public List<UserDTO> getUsers() {
        Keycloak keycloak = keyclaokSecurityUtil.getKeycloakInstance();
        List<UserRepresentation> userRepresentations =
                keycloak.realm(realm).users().list();
        return mapUsers(userRepresentations);

    }

    @PostMapping("/user")
    public Response createUser(UserDTO userDTO) {
        UserRepresentation userRepresentation = mapUserRep(userDTO);
        Keycloak keycloak = keyclaokSecurityUtil.getKeycloakInstance();
        keycloak.realm(realm).users().create(userRepresentation);
        return Response.ok(userDTO).build();
    }


    private List<UserDTO> mapUsers(List<UserRepresentation> userRepresentations) {

        List<UserDTO> userDTOS = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(userRepresentations)) {
            userRepresentations.forEach(userRepresentation -> userDTOS.add(mapUser(userRepresentation)));
        }
        return userDTOS;
    }

    private UserDTO mapUser(UserRepresentation userRepresentation) {

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(userRepresentation.getFirstName());
        userDTO.setLastName(userRepresentation.getLastName());
        userDTO.setEmail(userRepresentation.getEmail());
        userDTO.setUserName(userRepresentation.getUsername());
        return userDTO;
    }

    private UserRepresentation mapUserRep(UserDTO user) {
        UserRepresentation userRep = new UserRepresentation();
        userRep.setUsername(user.getUserName());
        userRep.setFirstName(user.getFirstName());
        userRep.setLastName(user.getLastName());
        userRep.setEmail(user.getEmail());
        userRep.setEnabled(true);
        userRep.setEmailVerified(true);

        List<CredentialRepresentation> creds = new ArrayList<>();

        CredentialRepresentation cred = new CredentialRepresentation();

        cred.setTemporary(false);
        cred.setValue(user.getPassword());
        creds.add(cred);
        userRep.setCredentials(creds);
        return userRep;
    }


}
