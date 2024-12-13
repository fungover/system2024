package org.fungover.system2024.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class GraphQLExceptionResolver extends DataFetcherExceptionResolverAdapter {

  @Override
  protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
    if (ex instanceof ResourceNotFoundException) {
      return GraphqlErrorBuilder.newError()
          .errorType(ErrorType.NOT_FOUND)
          .message(ex.getMessage())
          .path(env.getExecutionStepInfo().getPath())
          .location(env.getField().getSourceLocation())
          .build();
    }
    if (ex instanceof RuntimeException) {
      return GraphqlErrorBuilder.newError()
          .errorType(ErrorType.INTERNAL_ERROR)  // Return INTERNAL_ERROR error type
          .message("An unexpected error occurred: " + ex.getMessage())  // Include the message
          .path(env.getExecutionStepInfo().getPath())  // Path to the GraphQL query
          .location(env.getField().getSourceLocation())  // Location in the query
          .build();
    }

      return null;

  }
}
