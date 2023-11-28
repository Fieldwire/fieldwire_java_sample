package net.fieldwire.models.response;

import java.util.Date;

public class AccountUser extends BaseModel<Integer> {
    public String firstName;
    public String lastName;
    public String accountRole;
    public String email;
    public String company;
    public String phoneNumber;
    public String photoUrl;
    public Date currentSignInAt;
    public int invitedById;

    @Override
    public String toString() {
        return "AccountUser{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", accountRole='" + accountRole + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", currentSignInAt=" + currentSignInAt +
                ", invitedById=" + invitedById +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", id=" + id +
                '}';
    }
}
