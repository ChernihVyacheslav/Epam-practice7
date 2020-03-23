package ua.nure.chernykh.practice7.entity;

import java.util.Date;

import static ua.nure.chernykh.practice7.constants.Constants.LINE_SEPARATOR;

public class PharmCertificate {

    private int certificateNumber;
    private Date certificateStartDate;
    private Date certificateEndDate;
    private String certificateOrganisation;

    public int getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(int certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public Date getCertificateStartDate() {
        return certificateStartDate;
    }

    public void setCertificateStartDate(Date certificateStartDate) {
        this.certificateStartDate = certificateStartDate;
    }

    public Date getCertificateEndDate() {
        return certificateEndDate;
    }

    public void setCertificateEndDate(Date certificateEndDate) {
        this.certificateEndDate = certificateEndDate;
    }

    public String getCertificateOrganisation() {
        return certificateOrganisation;
    }

    public void setCertificateOrganisation(String certificateOrganisation) {
        this.certificateOrganisation = certificateOrganisation;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(Integer.toString(certificateNumber)).append(LINE_SEPARATOR);
        result.append(certificateStartDate).append(LINE_SEPARATOR).append(certificateEndDate).append(LINE_SEPARATOR)
                .append(certificateOrganisation);
        return result.toString();
    }
}
