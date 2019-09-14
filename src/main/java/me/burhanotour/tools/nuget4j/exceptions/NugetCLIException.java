package me.burhanotour.tools.nuget4j.exceptions;

public class NugetCLIException extends NugetAPIException {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 961456227016762693L;

    public NugetCLIException() {
        super();
    }

    public NugetCLIException(String message, int pExitCode) {
        super(message, pExitCode);
    }

    public NugetCLIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                           int pExitCode) {
        super(message, cause, enableSuppression, writableStackTrace, pExitCode);
    }

    public NugetCLIException(String message, Throwable cause, int pExitCode) {
        super(message, cause, pExitCode);
    }

    public NugetCLIException(Throwable cause, int pExitCode) {
        super(cause, pExitCode);
    }



}
