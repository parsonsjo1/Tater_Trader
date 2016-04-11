package com.joshwa.tater_trader;

/**
 * Created by Joshua on 4/10/2016.
 */
public class JSONResponse
{
    private boolean success;
    private String msg;

    public JSONResponse(boolean success, String msg)
    {
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = msg;
    }
}
