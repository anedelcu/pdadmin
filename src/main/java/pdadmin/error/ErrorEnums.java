package pdadmin.error;

public class ErrorEnums {
    public enum ErrorCode {
        ACCESS_DENIED("Access denied."),
        INVALID_INPUT("Invalid input."),
        SERVER_ERROR("Internal server error."),
        NOT_FOUND("Not found."),
        UNAUTHORIZED("Unauthorized."),
        USER_DELETED("User deleted"),
        USER_NOT_FOUND("User not found"),
        USER_SUCCESS("User success");

        private final String message;

        ErrorCode(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}