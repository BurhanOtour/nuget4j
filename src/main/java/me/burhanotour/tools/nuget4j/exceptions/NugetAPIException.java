package me.burhanotour.tools.nuget4j.exceptions;

public class NugetAPIException extends Exception {

    private static final long serialVersionUID = -2024884067776840628L;

    private final int mExitCode;

    private static final int NOT_INITIALIZED = Integer.MAX_VALUE;

    public NugetAPIException() {
        super();
        mExitCode = NOT_INITIALIZED;
    }

    public NugetAPIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                           int pExitCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        mExitCode = pExitCode;
    }

    public NugetAPIException(String message, Throwable cause, int pExitCode) {
        super(message, cause);
        mExitCode = pExitCode;
    }

    public NugetAPIException(String message, int pExitCode) {
        super(message);
        mExitCode = pExitCode;
    }

    public NugetAPIException(Throwable cause, int pExitCode) {
        super(cause);
        mExitCode = pExitCode;
    }

    public int getExitCode() {
        return mExitCode;
    }

    @Override
    public String getMessage() {
        return "ExitValue: " + mExitCode + ", " + super.getMessage();
    }

}
