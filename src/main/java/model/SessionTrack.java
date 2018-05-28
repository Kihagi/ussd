package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_session_track")
public class SessionTrack extends Common {

    public static final String SESS_ID = "sessionId";

    @Column(name = "session_id")
    private String sessionId = "";

    @Column(name = "phone_number")
    private String phoneNumber = "";

    @Column(name = "service_code")
    private String serviceCode = "";

    @Column(name = "text_received")
    private String textReceived = "";

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getTextReceived() {
        return textReceived;
    }

    public void setTextReceived(String textReceived) {
        this.textReceived = textReceived;
    }
}
