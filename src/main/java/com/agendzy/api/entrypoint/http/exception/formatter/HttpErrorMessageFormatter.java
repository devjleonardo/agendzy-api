package com.agendzy.api.entrypoint.http.exception.formatter;

import com.agendzy.api.entrypoint.http.exception.message.HttpErrorMessage;

public final class HttpErrorMessageFormatter {

    private HttpErrorMessageFormatter() {
    }

    public static String extractExceptionMessage(Object message ) {
        if (message  instanceof HttpErrorMessage httpErrorMessage) {
            return httpErrorMessage.getMessage();
        } else if (message  instanceof String detailMessage) {
            return detailMessage;
        }

        return "";
    }

    public static String formatString(Object format, Object... args) {
        String formattedMessage = "";

        if (format instanceof HttpErrorMessage httpErrorMessage) {
            formattedMessage = httpErrorMessage.getMessage();
        } else if (format instanceof String string) {
            formattedMessage = string;
        }

        return String.format(formattedMessage, args);
    }

}
