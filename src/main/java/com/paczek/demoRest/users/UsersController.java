package com.paczek.demoRest.users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paczek.demoRest.users.UserDto;
import com.paczek.demoRest.users.UserService;
import com.paczek.demoRest.util.Mappers;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
public class UsersController {

    private final UserService userService;
    final ObjectMapper ob = new ObjectMapper();

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> users(@RequestParam(value = "email", required = false) String email,
                                               @RequestParam(value = "gender", required = false) String gender
    ) {

        return new ResponseEntity<>(userService.findByEmailLikeAndGender(email, gender)
                .stream()
                .map(Mappers::map)
                .toList(), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/users")
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {
        try {
            return new ResponseEntity<>(ob.writeValueAsString(userService.save(userDto)), HttpStatusCode.valueOf(201));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/users/import")
    public ResponseEntity<List<UserDto> > importUser(@RequestParam("file") MultipartFile file) {
        try {
            final List<UserDto> from = ob.readValue(file.getBytes(), new TypeReference<>() {});

            List<UserDto> savedList = from.stream()
                    .map(userService::save)
                    .toList();

            return new ResponseEntity<>(savedList, HttpStatusCode.valueOf(201));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(Mappers.map(userService.getUser(id)));
    }

}
