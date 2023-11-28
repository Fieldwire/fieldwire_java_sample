package net.fieldwire.models.response;

import java.util.Date;

public class ProjectUser extends AccountUser {
    public Date userDeletedAt;

    public String role;

    @Override
    public String toString() {
        return "ProjectUser{" +
                "userDeletedAt=" + userDeletedAt +
                ", role='" + role + '\'' +
                ", firstName='" + firstName + '\'' +
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
