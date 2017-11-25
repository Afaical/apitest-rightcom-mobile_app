package api_test.abdelfaical.com.apitest.webservice;

/**
 * Created by abdel-faical on 25/11/17.
 */

public class QueryResponse {

    private boolean success;

    public QueryResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
