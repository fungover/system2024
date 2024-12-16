package org.fungover.system2024.controller;

import org.fungover.system2024.exception.ResourceNotFoundException;
import org.fungover.system2024.user.UserController;
import org.fungover.system2024.user.UserService;
import org.fungover.system2024.user.dto.UserDto;
import org.fungover.system2024.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


import java.util.Set;

@GraphQlTest(UserController.class)
public class UserControllerIntTest {

  @Autowired
  GraphQlTester graphQlTester;

  @MockBean
  UserService userServiceMock;

  @Test
  @DisplayName("GraphQL query users should return list of users")
  void graphQlQueryUsersShouldReturnListOfUsers() {

    Set<UserDto> mockUsers = Set.of(new UserDto("Test Testsson", "test@test.test"));

    when(userServiceMock.getAllUsers()).thenReturn(mockUsers);

    // language=GraphQL
    String document = """
        query {
            users {
                name
                email
            }
        }
        """;

    graphQlTester.document(document)
        .execute()
        .path("users")
        .entityList(User.class)
        .hasSize(1);
  }

  @Test
  @DisplayName("GraphQL query users should return ResourceNotFound exception when no users are found")
  void graphQlQueryUsersShouldReturnResourceNotFoundExceptionWhenNoUsersAreFound() {
    when(userServiceMock.getAllUsers()).thenThrow(new ResourceNotFoundException("No users found"));

    // language=GraphQL
    String document = """
        query {
            users {
                name
                email
            }
        }
        """;

    graphQlTester.document(document)
        .execute()
        .errors()
        .satisfy(errors -> {
          assertThat(errors).hasSize(1);
          assertThat(errors.getFirst().getMessage()).contains("No users found");
          assertThat(errors.getFirst().getPath()).isEqualTo("users");
          assertThat(errors.getFirst().getErrorType().toString()).isEqualTo("NOT_FOUND");
        });
  }

  @Test
  @DisplayName("Response should be internal error when unexpected exception is thrown")
  void responseShouldBeInternalErrorWhenUnexpectedExceptionIsThrown() {
    when(userServiceMock.getAllUsers()).thenThrow(new RuntimeException("Unexpected exception"));

    // language=GraphQL
    String document = """
        query {
            users {
                name
                email
            }
        }
        """;

    graphQlTester.document(document)
        .execute()
        .errors()
        .satisfy(errors -> {
          assertThat(errors).hasSize(1);
          assertThat(errors.getFirst().getMessage()).contains("An unexpected error occurred: Unexpected exception"); //null is typically replaced with the error message
          assertThat(errors.getFirst().getPath()).isEqualTo("users");
          assertThat(errors.getFirst().getErrorType().toString()).isEqualTo("INTERNAL_ERROR");
        });
  }

  //Add more graphql tests below...
}
