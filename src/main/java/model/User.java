package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_user")
public class User extends Common {

    public static final String NATIONAL_ID = "nationalId";
    public static final String PHONE_NUMBER = "phoneNumber";

    @Column(name = "names")
    private String names = "";

    @Column(name = "phone_number")
    private String phoneNumber = "";

    @Column(name = "national_id")
    private String nationalId = "";

    @Column(name = "nhif_number")
    private String nhifNumber = "";

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getNhifNumber() {
        return nhifNumber;
    }

    public void setNhifNumber(String nhifNumber) {
        this.nhifNumber = nhifNumber;
    }
}
