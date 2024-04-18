package com.itr.codechallenge.config;


import com.itr.codechallenge.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User", description = "the User Api")
@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public interface UserControllerApi {
    @Operation(
            summary = "Fetch all users",
            description = "fetches all user entities", security ={ @SecurityRequirement(name = "Authorization") }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    ResponseEntity<String> getAllUsers();

    @Operation(
            summary = "adds a user",
            description = "Adds a user to the list of users",
            security ={ @SecurityRequirement(name = "Authorization") }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully added a user"),
            @ApiResponse(responseCode = "409", description = "duplicate user")
    })
    ResponseEntity<String> addUser(@RequestBody User user);

    @Operation(
            summary = "gets a user",
            description = "get a user",
            security ={ @SecurityRequirement(name = "Authorization") }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully on getting a user"),
            @ApiResponse(responseCode = "400", description = "user not found")
    })
    ResponseEntity<String> getUser(@PathVariable("id") Long id);

    @Operation(
            summary = "update a user",
            description = "updates a user",
            security ={ @SecurityRequirement(name = "Authorization") }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully updated user"),
            @ApiResponse(responseCode = "409", description = "duplicate user")
    })
    ResponseEntity<String> updateUser(@PathVariable("id") Long id, @RequestBody User userToUpdate);

    @Operation(
            summary = "deletes a user",
            description = "delete a user",
            security ={ @SecurityRequirement(name = "Authorization") }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully deleted user"),
            @ApiResponse(responseCode = "400", description = "duplicate user")
    })
    ResponseEntity<String> deleteUser(@PathVariable("user_id") Long user_id);

}
