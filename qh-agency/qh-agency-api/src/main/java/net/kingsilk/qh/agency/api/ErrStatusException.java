package net.kingsilk.qh.agency.api;

/**
 *
 */
public class ErrStatusException extends RuntimeException {


    private Integer errStatus;

    public ErrStatusException(Integer errStatus) {
        this.errStatus = errStatus;
    }

    public ErrStatusException(Integer errStatus, String message) {
        super(message);
        this.errStatus = errStatus;
    }

    public ErrStatusException(Integer errStatus, String message, Throwable cause) {
        super(message, cause);
        this.errStatus = errStatus;
    }

    public ErrStatusException(Integer errStatus, Throwable cause) {
        super(cause);
        this.errStatus = errStatus;
    }


    public Integer getErrStatus() {
        return errStatus;
    }
}
