package me.burhanotour.tools.nuget4j.exceptions;

import me.burhanotour.tools.nuget4j.nuget.api.NugetAPIConstants;

public class NugetCLITimeoutException extends NugetCLIException{
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -5615044392328139248L;

    public NugetCLITimeoutException() {
        super();
    }

    public NugetCLITimeoutException(String message) {
        super(message, NugetAPIConstants.ERR_TIMEOUT);
    }

    public NugetCLITimeoutException(String message, int pExitCode) {
        super(message, pExitCode);
    }

    public NugetCLITimeoutException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace, int pExitCode) {
        super(message, cause, enableSuppression, writableStackTrace, pExitCode);
    }

    public NugetCLITimeoutException(String message, Throwable cause, int pExitCode) {
        super(message, cause, pExitCode);
    }

    public NugetCLITimeoutException(Throwable cause, int pExitCode) {
        super(cause, pExitCode);
    }

}
